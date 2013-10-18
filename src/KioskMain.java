import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.KeyboardFocusManager;

public class KioskMain {
	public static void main(String[] args) {
		// use look and feel for my system (Win32)
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}
		KioskMainFrame frame = new KioskMainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("bitchin parking kiosk");
		frame.pack();
		frame.setVisible(true);
	}
}

class Global {

}

class KioskMainFrame extends JFrame implements ActionListener, MouseListener {
	// the following avoids a "warning" with Java 1.5.0 complier (?)
	static final long serialVersionUID = 42L;

	JPanel panel = new JPanel();
	Font formFont = new Font("Book Antiqua", Font.PLAIN, 28);

	
	static Color mainBackground = new Color(198, 226, 255);
	static Color mainButtonColor = new Color(46, 46, 46);
	static Color completed = new Color(66, 66, 66);

	JTextField selectedTextField = new JTextField("",20);

	public JPanel mainLoginView() {
		JPanel mainPane = new JPanel();
		
		JTextField emailField = new JTextField("",20);
		JTextField passwordField = new JTextField("",20);
		//styling of the input form
		emailField.setFont(formFont);
		passwordField.setFont(formFont);

		JLabel welcomeBanner = new JLabel("Welcome", JLabel.CENTER);
		mainPane.add(welcomeBanner);
		mainPane.add(Box.createHorizontalStrut(15)); // a spacer
		//adding the componenets
		mainPane.add(new JLabel("Email Address:", JLabel.CENTER));
      mainPane.add(emailField);
      mainPane.add(Box.createHorizontalStrut(15)); // a spacer
      mainPane.add(new JLabel("Password:", JLabel.CENTER));
      mainPane.add(passwordField);
      mainPane.add(Box.createVerticalStrut(5)); // a spacer
    	mainPane.add(loginButtonView());

      mainPane.setLayout(new GridLayout(10,1));
      mainPane.setBackground(mainBackground);
      mainPane.setBorder( new EmptyBorder( 80, 80, 50, 80 ));
      return mainPane;
	}

public void initLoginPage() {
		panel.removeAll();
		panel.add(mainLoginView(), "Center");
		panel.add(keyboardView(), "South");
	}

public void initCustomerInfoPage() {
		panel.removeAll();
		panel.add(mainStudentProfileView(), "Center");
		panel.add(keyboardView(), "South");
		panel.add(sidePanelView(), "East");
	}

public JPanel keyboardView() {
		String firstRow[][] = {
										{"~","1","2","3","4","5","6","7","8","9","0","Back"},
		 								{"Tab","Q","W","E","R","T","Y","U","I","O","P","@"},
										{"Caps","A","S","D","F","G","H","J","K","L","Enter"},
										{"Shift","Z","X","C","V","B","N","M",",","."},
										{"Space" },
									};	

		JPanel keyBoardPane = new JPanel();

		keyBoardPane.setLayout(new GridLayout(5,1));
    	//pack the components
   	pack();
		for (int r = 0 ; r < 5 ; r++) {
			JButton row[] = new JButton[firstRow[r].length];
		   JPanel p = new JPanel(new GridLayout(1, firstRow[r].length));
		   for(int i = 0; i < firstRow[r].length; ++i) 
		   {
		   	JButton b;
		   	b = createSimpleButton(firstRow[r][i]);
		   	b.addActionListener(this);	
				b.setPreferredSize(new Dimension(100,50));
				b.setBorder(new LineBorder(mainBackground));
				row[i] = b;
				p.add(row[i]);
			}
			keyBoardPane.add(p);
		}

		keyBoardPane.setPreferredSize(new Dimension(0, 300));
		keyBoardPane.setBackground(mainButtonColor);

      return keyBoardPane;
	}

public JPanel sidePanelView() {
		JButton b1, b2, b3, b4; 
		b1 = createSimpleButton("Student Information");
		b2 = createSimpleButton("Car Information");
		b3 = createSimpleButton("Addictional Information");
		b4 = createSimpleButton("Complete");
		JPanel sidePane = new JPanel();
		sidePane.setLayout(new GridLayout(4,1, 5, 5));
		sidePane.add(b1);
		sidePane.add(b2);
		sidePane.add(b3);
		sidePane.add(b4);
      sidePane.setBackground(mainBackground);
      sidePane.setBorder( new EmptyBorder( 5, 0, 5, 5 ) );
      return sidePane;
	}
	private static JButton createSimpleButton(String text) {
	  JButton button = new JButton(text);
	  button.setForeground(Color.WHITE);
	  button.setBackground(mainButtonColor);
	  Border line = new LineBorder(Color.BLACK);
	  Border margin = new EmptyBorder(5, 15, 5, 15);
	  Border compound = new CompoundBorder(line, margin);
	  button.setBorder(margin);
	  return button;
	}

	public JPanel mainCarProfileView() {
		JTextField makeOfCarField = new JTextField(20);
		JTextField modelField = new JTextField(20);
		JTextField plateNumber = new JTextField(20);

		return null;
	}

	public JPanel mainStudentProfileView() {
		JPanel mainPane = new JPanel();

		JTextField firstNameField = new JTextField("",20);
		JTextField lastNameField = new JTextField("",20);
		JTextField emailField = new JTextField("",20);

		firstNameField.setFont(formFont);
		firstNameField.addMouseListener(this);
		lastNameField.setFont(formFont);
		lastNameField.addMouseListener(this);
		emailField.setFont(formFont);
		emailField.addMouseListener(this);

		mainPane.add(new JLabel("First Name:", JLabel.CENTER));
      mainPane.add(firstNameField);
      mainPane.add(Box.createHorizontalStrut(15)); // a spacer
      mainPane.add(new JLabel("Last Name:", JLabel.CENTER));
      mainPane.add(lastNameField);
      mainPane.add(Box.createHorizontalStrut(15)); // a spacer
		mainPane.add(new JLabel("email:", JLabel.CENTER));
      mainPane.add(emailField);      

      mainPane.setLayout(new GridLayout(10,1));
      mainPane.setBackground(mainBackground);
      mainPane.setBorder( new EmptyBorder( 80, 80, 50, 80 ) );

		return mainPane;
	}

	
	
	public void initAdditionInfoPage() {}
	public void initCarInfoPage() {}

	public JPanel loginButtonView() {
		JPanel buttonHolders =  new JPanel();
		buttonHolders.setLayout(new BorderLayout(0,0));
		buttonHolders.setBackground(mainBackground);
		JButton login = createSimpleButton("Login");
		login.addActionListener(this);	
		login.setActionCommand("login_authenticate");
		JButton newClient = createSimpleButton("New Client");

		login.setBackground(new Color (34, 139, 34));
		buttonHolders.add(newClient, "West");
		buttonHolders.add(login, "East");
		
		return buttonHolders;
	}

	
	  public void mousePressed(MouseEvent e)
	  {
		   //System.out.println(e.toString());
		   if (e.getSource() instanceof JTextField) {
		   	selectedTextField = (JTextField)(e.getSource());
		   	System.out.println("boom");
		   }
	  }
	  public void mouseClicked(MouseEvent e){}
	  public void mouseEntered(MouseEvent e){}
	  public void mouseExited(MouseEvent e){}
	  public void mouseReleased(MouseEvent e){
	  }
	 

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "login_authenticate") {
			initCustomerInfoPage();
		}
		else {
			//System.out.println(e.getActionCommand());
			//System.out.println(selectedTextField.getText());
			selectedTextField.setText(selectedTextField.getText() + e.getActionCommand());
		}
		//System.out.println(e);
		this.setContentPane(panel);
	}

	public KioskMainFrame() {
		panel.setLayout(new BorderLayout(0, 0)); 
		initCustomerInfoPage();
		
		this.setContentPane(panel);

	}
}
