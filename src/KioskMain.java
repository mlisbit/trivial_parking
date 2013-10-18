import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.KeyboardFocusManager;
import java.util.ArrayList;

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

class KioskMainFrame extends JFrame implements ActionListener, MouseListener {
	// the following avoids a "warning" with Java 1.5.0 complier (?)
	static final long serialVersionUID = 42L;

	JPanel panel = new JPanel();

	public String[] getDaysInMonth(int month) {
		String[] i = new String[31];
		for (int k = 1 ; k <= 31 ; k++) {
			i[k-1] = String.valueOf(k);
		}

		return i;
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
				b.setBorder(new LineBorder(Global.mainBackground));
				row[i] = b;
				p.add(row[i]);
			}
			keyBoardPane.add(p);
		}

		keyBoardPane.setPreferredSize(new Dimension(0, 300));
		keyBoardPane.setBackground(Global.mainButtonColor);

      return keyBoardPane;
	}

public JPanel sidePanelView() {
		JButton b1, b2, b3, b4; 
		JPanel sidePane = new JPanel();
		sidePane.setLayout(new GridLayout(4,1, 5, 5));

		b1 = createSimpleButton("Student Information");
		b2 = createSimpleButton("Car Information");
		b3 = createSimpleButton("Choose Password");
		b4 = createSimpleButton("Complete");

		b1.setActionCommand("Student_Information");
		b2.setActionCommand("Car_Information");
		b3.setActionCommand("Choose_Password");
		b4.setActionCommand("complete");

		b1.addActionListener(this);	
		b2.addActionListener(this);	
		b3.addActionListener(this);	
		b4.addActionListener(this);	

		sidePane.add(b1);
		sidePane.add(b2);
		sidePane.add(b3);
		sidePane.add(b4);

      sidePane.setBackground(Global.mainBackground);
      sidePane.setBorder( new EmptyBorder( 5, 0, 5, 5 ) );
      return sidePane;
	}

	public static JButton createSimpleButton(String text) {
	  JButton button = new JButton(text);
	  button.setForeground(Color.WHITE);
	  button.setBackground(Global.mainButtonColor);
	  Border line = new LineBorder(Color.BLACK);
	  Border margin = new EmptyBorder(5, 15, 5, 15);
	  Border compound = new CompoundBorder(line, margin);
	  button.setBorder(margin);
	  return button;
	}

	public static JComboBox createSimpleGroupBox(String[] options) {
		JComboBox boxy = new JComboBox(options);
		boxy.setForeground(Color.BLACK);
	  	boxy.setBackground(Color.WHITE);
	  	boxy.setBorder(BorderFactory.createLineBorder(Global.mainBackground));
	  	boxy.setFont(Global.formFont);
	  	
	  	return boxy;
	}

	public JPanel mainCarProfileView() {
		JTextField makeOfCarField = new JTextField(20);
		JTextField modelField = new JTextField(20);
		JTextField plateNumber = new JTextField(20);

		return null;
	}
	
	public void mousePressed(MouseEvent e)
	  {
		  	//rememebrs the last textfield that was pressed.
	  		//prevents loosing focus while typing.
		   if (e.getSource() instanceof JTextField) {
		   	Global.selectedTextField = (JTextField)(e.getSource());
		   	System.out.println("boom");
		   }
	  }
	  public void mouseClicked(MouseEvent e){}
	  public void mouseEntered(MouseEvent e){}
	  public void mouseExited(MouseEvent e){} 
	  public void mouseReleased(MouseEvent e){
	  }
	 

	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if (e.getActionCommand() == "login_authenticate") {
			initPurchaseView();
		}
		else if (e.getActionCommand() == "Car_Information") {
			initCarInfoPage();
		}
		else if (e.getActionCommand() == "Student_Information") {
			initCustomerInfoPage();
		}
		else if (e.getActionCommand() == "Choose_Password") {
			initChoosePassPage();
		}
		else if (e.getActionCommand() == "new_client") {
			initCustomerInfoPage();
		}
		else if (e.getActionCommand() == "complete") {
			initLoginPage();
		}
		else if (e.getActionCommand() == "Cancel") {
			initLoginPage();
		}
		else {
			switch(e.getActionCommand()) {
				case 	"Space": 	Global.selectedTextField.setText(Global.selectedTextField.getText() + " ");
										break;
				case	"Back":		if (Global.selectedTextField.getText().length() > 0) {
    										Global.selectedTextField.setText(Global.selectedTextField.getText().substring(0, Global.selectedTextField.getText().length()-1));
  										}
  										break;
				default:				Global.selectedTextField.setText(Global.selectedTextField.getText() + e.getActionCommand());
										break;
			}
		}
		this.setContentPane(panel);
	}
	
	public JPanel mainStudentProfileView() {
		JPanel mainPane = new JPanel();

		JTextField firstNameField = new JTextField("",20);
		JTextField lastNameField = new JTextField("",20);
		JTextField studentNumberField = new JTextField("",20);

		firstNameField.setFont(Global.formFont);
		firstNameField.addMouseListener(this);
		lastNameField.setFont(Global.formFont);
		lastNameField.addMouseListener(this);
		studentNumberField.setFont(Global.formFont);
		studentNumberField.addMouseListener(this);

		mainPane.add(new JLabel("First Name:", JLabel.CENTER));
      mainPane.add(firstNameField);
      mainPane.add(Box.createHorizontalStrut(15)); // a spacer
      mainPane.add(new JLabel("Last Name:", JLabel.CENTER));
      mainPane.add(lastNameField);
      mainPane.add(Box.createHorizontalStrut(15)); // a spacer
		mainPane.add(new JLabel("Student Number:", JLabel.CENTER));
      mainPane.add(studentNumberField);      

      mainPane.setLayout(new GridLayout(10,1));
      mainPane.setBackground(Global.mainBackground);
      mainPane.setBorder( new EmptyBorder( 80, 80, 50, 80 ) );
		return mainPane;
	}

	public JPanel mainLoginView() {
		JPanel mainPane = new JPanel();
		JTextField emailField = new JTextField("",20);
		JTextField passwordField = new JTextField("",20);
		//styling of the input form
		emailField.setFont(Global.formFont);
		passwordField.setFont(Global.formFont);
		emailField.addMouseListener(this);
		passwordField.addMouseListener(this);

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
      mainPane.setBackground(Global.mainBackground);
      mainPane.setBorder( new EmptyBorder( 80, 80, 50, 80 ));
      return mainPane;
	}

	//sets up the buttons on the main page
	public JPanel loginButtonView() {
		JPanel buttonHolders =  new JPanel();
		buttonHolders.setLayout(new BorderLayout(0,0));
		buttonHolders.setBackground(Global.mainBackground);

		JButton login = createSimpleButton("Login");
		JButton newClient = createSimpleButton("New Client");
		
		login.setActionCommand("login_authenticate");
		newClient.setActionCommand("new_client");

		login.addActionListener(this);	
		newClient.addActionListener(this);	

		login.setBackground(new Color (34, 139, 34));
		buttonHolders.add(newClient, "West");
		buttonHolders.add(login, "East");
		
		return buttonHolders;
	}

	public JPanel carInfoView() {
		JPanel mainPane = new JPanel();

		JTextField carModelField = new JTextField("",20);
		JTextField licensePlateField = new JTextField("",20);

		carModelField.setFont(Global.formFont);
		carModelField.addMouseListener(this);
		licensePlateField.setFont(Global.formFont);
		licensePlateField.addMouseListener(this);

		mainPane.add(new JLabel("License Plate Number:", JLabel.CENTER));
      mainPane.add(licensePlateField);
      mainPane.add(Box.createHorizontalStrut(15)); // a spacer
      mainPane.add(new JLabel("Car Model:", JLabel.CENTER));
      mainPane.add(carModelField);

      mainPane.setLayout(new GridLayout(10,1));
      mainPane.setBackground(Global.mainBackground);
      mainPane.setBorder( new EmptyBorder( 80, 80, 50, 80 ) );
		return mainPane;
	}

	//date picking buttons yo
	public JPanel mainPurchaseDateView() {
		JPanel mainPane = new JPanel();
		JPanel datePane = new JPanel();
		datePane.setLayout(new GridLayout(1,6));
		String[] months = {"Jan", "Feb", "March", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
		String[] years = {"2013", "2014", "2015"};
		mainPane.setBackground(Global.mainBackground);
		JComboBox monthList;
		JComboBox yearList;
		JComboBox dayList;

		JLabel label = new JLabel("Till what day? : ");
		label.setFont(Global.formFont);
		mainPane.add(label);
		monthList = createSimpleGroupBox(months);
		yearList = createSimpleGroupBox(years);
		dayList = createSimpleGroupBox(getDaysInMonth(0));
		monthList.setSelectedIndex(9);
		dayList.setSelectedIndex(9);
		yearList.setSelectedIndex(2);

		datePane.add(monthList);
		datePane.add(dayList);
		datePane.add(yearList);
		mainPane.add(datePane);

		return mainPane;
	}

	public JPanel mainPurchaseTimeView() {
		JPanel mainPane = new JPanel();

		JPanel timePane = new JPanel();
		timePane.setLayout(new GridLayout(1,6));
		String[] hours = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		String[] minutes = {"00", "15", "30", "45"};
		String[] ampm = {"AM", "PM"};

		mainPane.setBackground(Global.mainBackground);
		JComboBox hourList;
		JComboBox minuteList;
		JComboBox ampmList;

		JLabel label = new JLabel("Till what time? : ");
		label.setFont(Global.formFont);
		mainPane.add(label);

		hourList = createSimpleGroupBox(hours);
		minuteList = createSimpleGroupBox(minutes);
		ampmList = createSimpleGroupBox(ampm);
		/*
		hourList.setSelectedIndex();
		minuteList.setSelectedIndex();
		ampmList.setSelectedIndex();
		*/
		timePane.add(hourList);
		timePane.add(minuteList);
		timePane.add(ampmList);
		mainPane.add(timePane);
		return mainPane;
	}

	public JPanel mainPurchasebuttons() {
		JPanel buttonHolders =  new JPanel();
		buttonHolders.setLayout(new GridLayout(1,2));
		buttonHolders.setBackground(Global.mainBackground);

		JButton login = createSimpleButton("Purchase");
		JButton newClient = createSimpleButton("Cancel");
		
		login.setActionCommand("Purchase");
		newClient.setActionCommand("Cancel");

		login.addActionListener(this);	
		newClient.addActionListener(this);	

		login.setBackground(new Color (34, 139, 34));
		buttonHolders.add(newClient);
		buttonHolders.add(login);
		
		return buttonHolders;
	}
	public JPanel mainPurchaseView() {
		JPanel mainPane = new JPanel();
		mainPane.setBackground(Global.mainBackground);
		
		mainPane.add(mainPurchaseDateView());
		mainPane.add(mainPurchaseTimeView());
		mainPane.add(new JLabel("Parking Rate 25cents / hour:", JLabel.CENTER));
		mainPane.add(mainPurchasebuttons());

		mainPane.setLayout(new GridLayout(10,1));

		return mainPane;
	}

	public JPanel choosePasswordView() {
		JPanel mainPane = new JPanel();

		JTextField firstPassField = new JTextField("",20);
		JTextField secondPassField = new JTextField("",20);

		firstPassField.setFont(Global.formFont);
		firstPassField.addMouseListener(this);
		secondPassField.setFont(Global.formFont);
		secondPassField.addMouseListener(this);

		mainPane.add(new JLabel("Enter Desired Password:", JLabel.CENTER));
      mainPane.add(firstPassField);
      mainPane.add(Box.createHorizontalStrut(15)); // a spacer
      mainPane.add(new JLabel("Confirm Password", JLabel.CENTER));
      mainPane.add(secondPassField);

      mainPane.setLayout(new GridLayout(10,1));
      mainPane.setBackground(Global.mainBackground);
      mainPane.setBorder( new EmptyBorder( 80, 80, 50, 80 ) );
		return mainPane;
	}

	public void initCarInfoPage() {
		panel.removeAll();
		panel.add(carInfoView(), "Center");
		panel.add(keyboardView(), "South");
		panel.add(sidePanelView(), "East");
	}

	public void initChoosePassPage() {
		panel.removeAll();
		panel.add(choosePasswordView(), "Center");
		panel.add(keyboardView(), "South");
		panel.add(sidePanelView(), "East");
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

	public void initPurchaseView() {
		panel.removeAll();
		panel.add(mainPurchaseView(), "Center");
		panel.add(keyboardView(), "South");
	}

	public KioskMainFrame() {
		panel.setLayout(new BorderLayout(0, 0)); 
		panel.setPreferredSize(new Dimension(1000, 800));
		initLoginPage();
		this.setContentPane(panel);
	}
}

