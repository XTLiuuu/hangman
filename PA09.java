import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class PA09 extends JPanel{

  Map<Integer,String> vocabulary = new HashMap<Integer,String>();
  Map<String,Integer> repeat = new HashMap<String,Integer>();
  JTextArea intro,intro1;
  String response;
  int times;
  int left = 6;
  int lengths;
  int records = 0;
  int counts = 0;
  JTextField[] guess;
  JTextArea check;
  ArrayList<String> his;
  MouseDrawDemo center;
  JButton checkB;

	public PA09(){
    super();
    JPanel content = this;
    content.setLayout(new BorderLayout());

    getFile("datasource.txt");
    String word = getWord();
    lengths = word.length();
    JLabel title = new JLabel("<html><h1>Hangled Man</h1></html>");
    title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    JButton start = new JButton("Start");
    start.setEnabled(false);
    intro = new JTextArea();
    intro.setText("At the beginning of this game, there is a randomly selected word."
      +" You are able to guess this word letter by letter.\n"
      +"You have 6 opportunities to have a wrong guess."
      +" After the man in the middle of the window is drawn completely, you lose.\n"
      +"Press “Start” Button to start the game, and the “End” Button to exit the game.");
    intro.setEditable(false);

    intro1 = new JTextArea();
    intro1.setEditable(false);

    JPanel ending2 = new JPanel();
    ending2.setLayout(new GridLayout(2,0));

    center = new MouseDrawDemo(6);
    JButton end = new JButton("Try again");
    JPanel wordguess = new JPanel();
    wordguess.setLayout(new GridLayout(1,word.length()+1));
    JPanel ending = new JPanel();
    ending.setLayout(new GridLayout(2,0));

    JPanel ending1 = new JPanel();
    ending1.setLayout(new GridLayout(2,0));

    JPanel checkletter = new JPanel();
    checkletter.setLayout(new FlowLayout());
    check = new JTextArea(2,5);
    JLabel checkword = new JLabel("guess");
    checkB = new JButton("check");
    check.setEditable(false);
    checkB.setEnabled(false);
    end.setEnabled(false);
    checkletter.add(checkword);
    checkletter.add(check);
    checkletter.add(checkB);


    guess = new JTextField[word.length()];
    wordguess.add(new JLabel("Correct word:"));
    for(int i=0; i<word.length();i++){
      guess[i] = new JTextField();
      wordguess.add(guess[i]);
      guess[i].setEditable(false);
    }

    ending1.add(checkletter);
    ending1.add(wordguess);
    ending2.add(intro);
    ending2.add(intro1);
    ending.add(ending1);
    ending.add(ending2);

    JCheckBox easyB = new JCheckBox("Easy");
    easyB.setEnabled(false);
    JCheckBox mediumB = new JCheckBox("Medium");
    mediumB.setEnabled(false);
    JCheckBox hardB = new JCheckBox("Hard");
    hardB.setEnabled(false);
    JPanel checkPanel = new JPanel(new GridLayout(0, 1));
    checkPanel.add(easyB);
    checkPanel.add(mediumB);
    checkPanel.add(hardB);

    JPanel pass = new JPanel();
    pass.setLayout(new GridLayout(0,1));
    JLabel passpass = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField(10);
    JButton okB = new JButton("OK");
    JTextArea hint = new JTextArea("Hint: Everyone has it\n"+
                                "and no one can lose it");
    hint.setEditable(false);
    pass.add(passpass);
    pass.add(passwordField);
    pass.add(okB);
    pass.add(hint);

    JPanel rightbar = new JPanel();
    rightbar.setLayout(new GridLayout(0,3));
    rightbar.add(end);rightbar.add(checkPanel);rightbar.add(pass);

    content.add(title,BorderLayout.PAGE_START);
    content.add(start,BorderLayout.LINE_START);
    content.add(center,BorderLayout.CENTER);
    content.add(rightbar,BorderLayout.LINE_END);
    content.add(ending,BorderLayout.PAGE_END);
    System.out.print(word);

    okB.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        char[] input = passwordField.getPassword();
        char[] correctPassword = { 's', 'h', 'a', 'd','o','w' };
        if (Arrays.equals (input, correctPassword)) {
          easyB.setEnabled(true);
          mediumB.setEnabled(true);
          hardB.setEnabled(true);
        }
      }
    });

    easyB.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        start.setEnabled(true);
        mediumB.setEnabled(false);
        hardB.setEnabled(false);
      }
    });

    mediumB.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        start.setEnabled(true);
        easyB.setEnabled(false);
        hardB.setEnabled(false);
      }
    });

    hardB.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        start.setEnabled(true);
        easyB.setEnabled(false);
        mediumB.setEnabled(false);
      }
    });

    start.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        start.setToolTipText("Click this button to start the game.");
        delete("userinput.txt");
        ArrayList<String> nw = new ArrayList<String>();
        writeMapToFile(nw,"userinput.txt");
        guessGame(word);
        check.setEditable(true);
        checkB.setEnabled(true);
        end.setEnabled(true);
        left = 6;
        records = 0;
        counts = 0;
        repeat.clear();
        his.clear();
      }
    });

    checkB.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        checkB.setToolTipText("Click this button to check the letter you enter.");
        String responseInfo = check.getText();
        if(responseInfo.length()!=1){
          intro1.setText("Please enter one letter.");
        }
        else if(repeat.containsKey(responseInfo)){
          intro1.setText("You have entered that letter. Please try again.");
        }
        else{
          response = responseInfo;
          repeat.put(response,times);
          times++;
          checking(word);
        }
        check.setText("");
      }
    });

    end.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        end.setToolTipText("Click this button to end the game.");
        center.left = 6;
        center.repaint();
        intro.setText("");
        intro1.setText("");
        check.setText("");
        check.setEditable(false);
        checkB.setEnabled(false);
        end.setEnabled(false);
        for(int i=0; i<lengths; i++){
          guess[i].setText("");
        }
      }
    });
	}

  public void guessGame(String word){
    String inst = "I have generated a word that has " + lengths + " letters.\n"
     + "You will have six chances to get it wrong.";
    intro.setText(inst);
  }

  public void checking(String word){
    for (int digit = 0; digit < lengths; digit++){
      if (response.equals(word.substring(digit,digit+1))){
          counts++;
          records++;
          guess[digit].setText(response);
        }
      }
      if (records != 0){
        intro1.setText("Right guess! Continue");
        records=0;
      }
      else {
        left --;
        String pop = "Wrong guess! You have " + left +" chances left.";
        wrongguess(left);
        intro1.setText(pop);
      }
    history(response);
    checkresults(counts, left, word);
  }


  public void history (String response){
    his = readMapFromFile("userinput.txt");
    his.add(response);
    writeMapToFile(his,"userinput.txt");
    String currentguess= "You have already guessed: ";
    for (int x = 0; x < his.size(); x++){
      currentguess+=his.get(x);
      currentguess+=", ";
    }
    intro.setText(currentguess);
  }

  public void writeMapToFile(ArrayList<String> d,String filename){
    try {
      PrintWriter writer = new PrintWriter(filename, "UTF-8");
      for(String input: d){
        writer.println(input);
      }
      writer.close();
    } catch (Exception e){
      System.out.println("Problem writing to file: "+e);
    }
  }

  public void delete(String filename){
    File file = new File(filename);
    file.delete();
  }

  public static ArrayList<String> readMapFromFile(String filename){
      ArrayList d = new ArrayList<String>();
      try{
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()){
          String line = scanner.nextLine();
          d.add(line);
        }
        scanner.close();
      } catch (FileNotFoundException e){
        System.out.println("Problem reading map from file "+e);
      }
      return d;
  }

  public void checkresults (int counts, int left, String word){
    if (counts == word.length()){
      intro1.setText("Congratulations, you get the word and save a life!");
      check.setEditable(false);
      checkB.setEnabled(false);
    }
    if (left == 0){
      String sentence = "Sorry you lose the game. Good luck next time!\n"
        +"The word is: " + word;
      intro1.setText(sentence);
      check.setEditable(false);
    }
  }


  public void wrongguess(int left){
      if (left == 5){
        center.left = 5;
        center.repaint();
      }else if (left == 4){
        center.left = 4;
        center.repaint();
      }else if (left == 3){
        center.left = 3;
        center.repaint();
      }else if (left == 2){
        center.left = 2;
        center.repaint();
      }else if (left == 1){
        center.left = 1;
        center.repaint();
      }else if (left == 0){
        center.left = 0;
        center.repaint();
      }
  }

  public void getFile(String filename){
    File play;
    String lastWord = "";
    String word;
    int i = 1;
    try{
      play = new File(filename);
      Scanner scanner = new Scanner(play);
      while (scanner.hasNext()){
        word = scanner.next();
        vocabulary.put(i,word);
        i ++;
      }
    } catch(Exception e) {
      System.out.println("Exception:  "+e);
    }
  }

  public String getWord(){
    int value = (int) (19785 * Math.random());
    String word = vocabulary.get(value);
    return word;
  }


	private static void createAndShowGUI() {
      //Create and set up the window.
      JFrame frame = new JFrame("HangledMan");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JOptionPane.showMessageDialog(frame,
      "Enter the right password and choose the difficulty level, then start the game!!!",
      "Inane warning",
      JOptionPane.WARNING_MESSAGE);

      //Create and set up the content pane.
      JComponent newContentPane = new PA09();
      newContentPane.setOpaque(true); //content panes must be opaque
      frame.setContentPane(newContentPane);
			frame.setLocation(100,70);
			frame.setSize(1200,500);
      frame.setVisible(true);
  }

  public static void main(String[] args) {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
          public void run() {
              createAndShowGUI();
          }
      });
  }
}
