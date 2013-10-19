import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.KeyboardFocusManager;
import java.util.ArrayList;
import java.io.IOException;

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
	StudentDatabase sdb;
	InsuranceDatabase idb;
	ParkingDatabase pdb;
	int current_step = 1;


	String studentNumber, studentFirstName, studentLastName, studentCarLicense, studentCarModel, studentCarInsurance, studentPassword;
	String purchaseHour, purchaseAMPM, purchaseMinute, purchaseYear, purchaseMonth, purchaseDay;

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
		JButton b0, b1, b2, b3, b4; 
		JPanel sidePane = new JPanel();
		sidePane.setLayout(new GridLayout(5,1, 5, 5));

		b0 = createSimpleButton("Cancel");
		b1 = createSimpleButton("Step 1.");
		b2 = createSimpleButton("Step 2.");
		b3 = createSimpleButton("Step 3.");
		b4 = createSimpleButton("Complete");

		b0.setActionCommand("Cancel");
		b1.setActionCommand("Student_Information");
		b2.setActionCommand("Car_Information");
		b3.setActionCommand("Choose_Password");
		b4.setActionCommand("complete");

		b0.addActionListener(this);	
		b1.addActionListener(this);	
		b2.addActionListener(this);	
		b3.addActionListener(this);	
		b4.addActionListener(this);	

		sidePane.add(b0);
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
		   }
	  }
	  public void mouseClicked(MouseEvent e){}
	  public void mouseEntered(MouseEvent e){}
	  public void mouseExited(MouseEvent e){} 
	  public void mouseReleased(MouseEvent e){
	  }
	 
	 //recursively find the compent in panel.
	public static ArrayList getAllComponents(final Container c) {
	    Component[] comps = c.getComponents();
	    ArrayList compList = new ArrayList<Component>();
	    ArrayList objectList = new ArrayList<JTextField>();
	    for (Component comp : comps) {
	        compList.add(comp);
	        if (comp instanceof Container)
	        		for (Component k : ((Container)comp).getComponents()) {
	        			if (k instanceof JTextField) {
	        				JTextField textField = (JTextField)k;
	        				objectList.add(textField);
	        				//System.out.println(textField.getName());
	        			}
	        		}    
	            compList.addAll(getAllComponents((Container)comp));
	    }
	   	 return objectList;
		}

	public static ArrayList getGroupComponents(final Container c) {
	    Component[] comps = c.getComponents();
	    ArrayList compList = new ArrayList<Component>();
	    ArrayList objectList = new ArrayList<JComboBox>();
	    for (Component comp : comps) {
	        compList.add(comp);
	        if (comp instanceof Container) 
	        		for (Component k : ((Container)comp).getComponents()) {
	        			if (k instanceof JComboBox) {
	        				JComboBox comboField = (JComboBox)k;
	        				objectList.add(comboField);
	        				//System.out.println(textField.getName());
	        			}
	        		compList.addAll(getAllComponents((Container)comp));		   
	        }
	    	}
	   	return objectList;
		}


	public void getStudentInfo() {
		try {
				ArrayList<JTextField> fields = getAllComponents(this.panel);

				//only change the values if the fields are not blank
				if (fields.get(0).getText() != "") 
					studentFirstName = fields.get(0).getText();
				if (fields.get(1).getText() != "") 
					studentLastName = fields.get(1).getText();
				if (fields.get(2).getText() != "") 
					studentNumber = fields.get(2).getText();

				initCarInfoPage();

			} catch (Exception exception) {
				System.out.println("incorrect info");
				
			}
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "login_authenticate") {
			current_step = 1;
			try {
				ArrayList<JTextField> fields = getAllComponents(this.panel);

				String studentnumber = fields.get(0).getText();
				String password = fields.get(1).getText();

				if (sdb.authorizeStudent(Integer.parseInt(studentnumber), Integer.parseInt(password))) {
					initPurchaseView();
				}

			} catch (Exception exception) {
				initErrorView();
			}
		}
		else if (e.getActionCommand() == "Car_Information") {	
			//we know the view prior this one was the student view, so we must collect the information
			if (current_step == 1) {
				getStudentInfo();
				current_step++;
			}
		}
		else if (e.getActionCommand() == "Student_Information") {
			if (current_step == 1) {
				initCustomerInfoPage();
			}
		}
		else if (e.getActionCommand() == "Choose_Password") {
			if (current_step == 2) {
				ArrayList<JComboBox> fields = getGroupComponents(this.panel);
				ArrayList<JTextField> otherfields = getAllComponents(this.panel);

				studentCarModel = otherfields.get(0).getText();
				studentCarLicense = otherfields.get(1).getText();
				studentCarInsurance = String.valueOf(fields.get(0).getSelectedItem());
				//String studentCarInsurance = fields.get(0).getSelectedItem();
	
				current_step++;
				initChoosePassPage();
			}
				
		}
		else if (e.getActionCommand() == "new_client") {
			initCustomerInfoPage();
		}
		else if (e.getActionCommand() == "complete") {
			if (current_step==3) {
				current_step=1;
			
				try {
				if(studentNumber == null && studentNumber.isEmpty()) {
					if (sdb.saveStudent(Integer.parseInt(studentNumber), Integer.parseInt(studentPassword), studentLastName, studentFirstName))
						sdb.saveInsuranceCompany(Integer.parseInt(studentNumber), studentCarInsurance, Integer.parseInt(studentCarLicense));
						//sdb.saveCarModel(studentCarModel);
						initLoginPage();
					}
					else 
						initErrorView();
				} catch (IOException exception) {
				initLoginPage();
			}
			}
		}
		else if (e.getActionCommand() == "Cancel") {
			initLoginPage();
			current_step = 1;
		}
		else if (e.getActionCommand() == "Purchase") {
			ArrayList<JComboBox> purchaseFields = getGroupComponents(this.panel);
	
			purchaseMonth 	= String.valueOf(purchaseFields.get(0).getSelectedItem());
			purchaseDay 	= String.valueOf(purchaseFields.get(1).getSelectedItem());
			purchaseYear 	= String.valueOf(purchaseFields.get(2).getSelectedItem());
			purchaseHour 	= String.valueOf(purchaseFields.get(3).getSelectedItem());
			purchaseMinute = String.valueOf(purchaseFields.get(4).getSelectedItem());
			purchaseAMPM 	= String.valueOf(purchaseFields.get(5).getSelectedItem());
			
			initConfirmationView();
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
		JTextField studentNumberField = new JTextField("",20);
		JTextField passwordField = new JTextField("",20);
		//styling of the input form
		studentNumberField.setFont(Global.formFont);
		passwordField.setFont(Global.formFont);
		studentNumberField.addMouseListener(this);
		passwordField.addMouseListener(this);

		JLabel welcomeBanner = new JLabel("Welcome", JLabel.CENTER);
		JLabel studentNumberLabel = new JLabel("Student Number:", JLabel.CENTER);
		JLabel passwordLabel = new JLabel("Password:", JLabel.CENTER);

		welcomeBanner.setFont(Global.titleFont);
		studentNumberLabel.setFont(Global.labelFont);
		passwordLabel.setFont(Global.labelFont);

		mainPane.add(welcomeBanner);
		mainPane.add(Box.createHorizontalStrut(15)); // a spacer
		//adding the componenets

		mainPane.add(studentNumberLabel);
      mainPane.add(studentNumberField);

      mainPane.add(Box.createHorizontalStrut(15)); // a spacer

      mainPane.add(passwordLabel);
      mainPane.add(passwordField);

      mainPane.add(Box.createVerticalStrut(5)); // a spacer

    	mainPane.add(loginButtonView());

      mainPane.setLayout(new GridLayout(10,1));
      mainPane.setBackground(Global.mainBackground);
      mainPane.setBorder( new EmptyBorder( 80, 80, 50, 80 ));

      //studentNumberField.getText();
      return mainPane;
	}

	//sets up the buttons on the main page
	public JPanel loginButtonView() {
		JPanel buttonHolders =  new JPanel();
		buttonHolders.setLayout(new BorderLayout(0,0));
		buttonHolders.setBackground(Global.mainBackground);

		JButton login = createSimpleButton("Login");
		JButton newClient = createSimpleButton("New Client");
		
		login.setFont(Global.buttonFont);
		newClient.setFont(Global.buttonFont);

		login.setActionCommand("login_authenticate");
		newClient.setActionCommand("new_client");

		login.addActionListener(this);	
		newClient.addActionListener(this);	

		login.setBackground(new Color (34, 139, 34));
		buttonHolders.add(newClient, "West");
		buttonHolders.add(login, "East");
		
		return buttonHolders;
	}

	//The view where the user inputs their car information!
	public JPanel carInfoView() {
		JPanel mainPane = new JPanel();
		
		JTextField carModelField = new JTextField("",20);
		JTextField licensePlateField = new JTextField("",20);
		JComboBox insuranceChoice;

		String [] insuranceCompanies = new String[idb.getCompanies().size()];
		
		for (int counter = 0; counter < idb.getCompanies().size() ; counter++) 
			insuranceCompanies[counter] = idb.getCompanies().get(counter).toString();
		
		insuranceChoice = createSimpleGroupBox(insuranceCompanies);

		carModelField.setFont(Global.formFont);
		carModelField.addMouseListener(this);
		licensePlateField.setFont(Global.formFont);
		licensePlateField.addMouseListener(this);

		mainPane.add(new JLabel("License Plate Number:", JLabel.CENTER));
      mainPane.add(licensePlateField);
      mainPane.add(Box.createHorizontalStrut(15)); // a spacer
      mainPane.add(new JLabel("Car Model:", JLabel.CENTER));
      mainPane.add(carModelField);
      mainPane.add(new JLabel("Insurance Company:", JLabel.CENTER));
      mainPane.add(insuranceChoice);


      mainPane.setLayout(new GridLayout(10,1));
      mainPane.setBackground(Global.mainBackground);
      mainPane.setBorder( new EmptyBorder( 80, 80, 50, 80 ) );
		return mainPane;
	}

	public JPanel mainPurchasebuttons() {
		JPanel buttonHolders =  new JPanel();
		buttonHolders.setBackground(Global.mainBackground);

		JButton login = createSimpleButton("Purchase");
		JButton newClient = createSimpleButton("Cancel");
		
		login.setActionCommand("Purchase");
		newClient.setActionCommand("Cancel");

		login.setFont(Global.buttonFont);
		newClient.setFont(Global.buttonFont);
		login.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
		newClient.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );

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

		String[] hours 	= 	{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		String[] minutes 	= 	{"00", "15", "30", "45"};
		String[] ampm 		= 	{"AM", "PM"};
		String[] months 	= 	{"Jan", "Feb", "March", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
		String[] years 	= 	{"2013", "2014", "2015"};

		JComboBox hourList;
		JComboBox minuteList;
		JComboBox ampmList;
		JComboBox monthList;
		JComboBox yearList;
		JComboBox dayList;

		monthList 	= createSimpleGroupBox(months);
		yearList 	= createSimpleGroupBox(years);
		dayList 		= createSimpleGroupBox(getDaysInMonth(0));
		hourList 	= createSimpleGroupBox(hours);
		minuteList 	= createSimpleGroupBox(minutes);
		ampmList 	= createSimpleGroupBox(ampm);

		JPanel datePane = new JPanel();
		datePane.setLayout(new GridLayout(1,6));

		mainPane.add(new JLabel("To what time?", JLabel.CENTER));
		mainPane.add(monthList);
		mainPane.add(yearList);
		mainPane.add(dayList);
		mainPane.add(new JLabel("To what day?", JLabel.CENTER));
		mainPane.add(hourList);
		mainPane.add(minuteList);
		mainPane.add(ampmList);
		
		mainPane.add(new JLabel("Parking Rate 25cents / hour:", JLabel.CENTER));
		mainPane.add(mainPurchasebuttons());
		mainPane.setLayout(new GridLayout(10,1));
		return mainPane;
	}

	public JPanel mainErrorView() {
		JPanel mainPane = new JPanel();
		mainPane.setBackground(Global.mainBackground);
		JLabel warning = new JLabel("Sorry, but there has been error", JLabel.CENTER);
		JLabel warning2 = new JLabel("The information you have entered is invalid!", JLabel.CENTER);
		mainPane.add(warning);
		mainPane.add(warning2);

		JButton ok = createSimpleButton("OK");
		ok.addActionListener(this);	
		ok.setActionCommand("Cancel");

		ok.setFont(Global.buttonFont);
		ok.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
		ok.setBackground(new Color (34, 139, 34));
		mainPane.add(ok);
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

	public JPanel mainConfirmationView() {
		JPanel mainPane = new JPanel();

		mainPane.add(new JLabel(purchaseMonth, JLabel.CENTER));
		mainPane.add(new JLabel(purchaseDay, JLabel.CENTER));
		mainPane.add(new JLabel(purchaseYear, JLabel.CENTER));
		mainPane.add(new JLabel(purchaseHour, JLabel.CENTER));
		mainPane.add(new JLabel(purchaseMinute, JLabel.CENTER));
		mainPane.add(new JLabel(purchaseAMPM, JLabel.CENTER));

		mainPane.add(new JLabel("do you accept these charges?", JLabel.CENTER));
		JButton ok = createSimpleButton("OK");
		ok.addActionListener(this);	
		ok.setActionCommand("Cancel");

		ok.setFont(Global.buttonFont);
		ok.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
		ok.setBackground(new Color (34, 139, 34));
		mainPane.add(ok);
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

	public void initConfirmationView() {
		panel.removeAll();
		panel.add(mainConfirmationView(), "Center");
	}

	public void initErrorView() {
		panel.removeAll();
		panel.add(mainErrorView(), "Center");
	}

	public KioskMainFrame() {
		try {
			sdb = new StudentDatabase("students.txt");
			idb = new InsuranceDatabase("companies.txt");
			pdb = new ParkingDatabase("parking.txt");
		}catch(IOException e){
  			System.out.println("error loading db files. please make sure they are in the same directory");
		}
		panel.setLayout(new BorderLayout(0, 0)); 
		panel.setPreferredSize(new Dimension(1000, 800));
		initLoginPage();
		this.setContentPane(panel);
	}
}

