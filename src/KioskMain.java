import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.KeyboardFocusManager;
import java.util.ArrayList;
import java.io.IOException;
import java.util.*;

public class KioskMain {
	public static void main(String[] args) {
		// use look and feel for my system (Win32)
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}
		KioskMainFrame frame = new KioskMainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("YORKU Parking Service");
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
	//steps are used to ensure the client is filling out the form in the proper order. 
	int current_step = 1;

	//save gathered new signup information from form in strings.
	String studentNumber, studentFirstName, studentLastName, studentCarLicense, studentCarModel, studentCarInsurance, studentPassword, studentPIN;
	//save gathered ticket information entered. 
	String purchaseHour, purchaseAMPM, purchaseMinute, purchaseYear, purchaseMonth, purchaseDay;

	//get the days in a month. return a string. This needs to be done properly... 
	public String[] getDaysInMonth(int month) {
		String[] i = new String[31];
		for (int k = 1 ; k <= 31 ; k++)
			i[k-1] = String.valueOf(k);
		return i;
	}
	//main keyboard view.
	public JPanel keyboardView() {
		//array storing the characters in each row. 
		String firstRow[][] = {
										{"1","2","3","4","5","6","7","8","9","0","Back"},
		 								{"Q","W","E","R","T","Y","U","I","O","P"},
										{".", "A","S","D","F","G","H","J","K","L","@"},
										{"Z","X","C","V","B","N","M",","},
										{"Space" },
									};	

		JPanel keyBoardPane = new JPanel();

		keyBoardPane.setLayout(new GridLayout(5,1));
    	
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
		b1 = createSimpleButton("Student Information");
		b2 = createSimpleButton("Car Information");
		b3 = createSimpleButton("PIN Number");
		b4 = createSimpleButton("Complete");

		b0.setActionCommand("Cancel");
		b1.setActionCommand("Student_Information");
		b2.setActionCommand("Car_Information");
		b3.setActionCommand("Enter_PIN");
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

	//validates the students information
	public void getStudentInfo() {
		try {
				//gets all the textfields in current view
				ArrayList<JTextField> fields = getAllComponents(this.panel);

				//only change the values if the fields are not blank
				if (fields.get(0).getText() != "") 
					studentFirstName = fields.get(0).getText();
				if (fields.get(1).getText() != "") 
					studentLastName = fields.get(1).getText();
				if (fields.get(2).getText() != "") 
					studentNumber = fields.get(2).getText();

				initCarInfoPage();

			} catch (Exception exception) {}
	}

	//action listener for views
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		//if the user initially logs in, this is the action taken to validate their info
		if (cmd == "login_authenticate") {
			//reset the current step they are on. we know they are on this view, so theres no way for them to get to it without bing on step 1
			current_step = 1;
			try {
				//get all the textfields from login page (their student number and PIN)
				ArrayList<JTextField> fields = getAllComponents(this.panel);

				studentNumber = fields.get(0).getText();
				studentPIN = fields.get(1).getText();

				//checks if the student is already in the database, and if so takes them to purchase view.
				if (sdb.authorizeStudent(Integer.parseInt(studentNumber), Integer.parseInt(studentPIN))) {
					initConfirmationView();
				}

			} catch (Exception exception) {
				initErrorView(); //if the student is not found or entered invalid information
			}
		}
		else if (cmd == "Car_Information") {	
			//we know the view prior this one was the student view, so we must collect the information
			if (current_step == 1) {
				getStudentInfo();
				current_step++;
			}
		}
		//this is the initial view after clicking new client, so there is no information to view, just return the view. 
		else if (cmd == "Student_Information") {
			if (current_step == 1) {
				initCustomerInfoPage();
			}
		}
		//we know the prior view was the car info page, so we must first collect all that information
		else if (cmd == "Enter_PIN") {
			if (current_step == 2) {
				ArrayList<JComboBox> fields = getGroupComponents(this.panel);
				ArrayList<JTextField> otherfields = getAllComponents(this.panel);

				studentCarModel = otherfields.get(0).getText();
				studentCarLicense = otherfields.get(1).getText();
				studentCarInsurance = String.valueOf(fields.get(0).getSelectedItem());
				
				//move on to the final step
				current_step++;
				initChoosePassPage();
			}
				
		}
		// Confirmed payment, display receipt permit.
		else if (cmd == "Confirm") { 
		    int sn = Integer.parseInt(studentNumber);
		    Student s = sdb.getStudent(sn);
            //
            // Try to get a parking spot
		    String spot = pdb.getAvailableSpot();
		    if (spot.equals(""))
		        initErrorView();

		    // Save the spot in the student database and change status
		    try {
		        sdb.saveParkingSpot(sn, spot, pdb);
                ReceiptFrame frame = new ReceiptFrame(s, true);     
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("Kiosk Permit");
                frame.setPreferredSize(new Dimension(800, 400));
                frame.pack();
                frame.setVisible(true);
                initLoginPage();

		    } catch (IOException ioe) {
                initErrorView();
		    }

		    /* ReceiptFrame frame = new ReceiptFrame(s, true); 	 */
		    /* frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); */
		    /* frame.setTitle("Kiosk Permit"); */
		    /* frame.setPreferredSize(new Dimension(800, 400)); */
		    /* frame.pack(); */
		    /* frame.setVisible(true); */
		    /* initLoginPage(); */
		}
		//final button on new client form
		else if (cmd == "complete") {
			//ensure they are on this step.
			if (current_step==3) {
				current_step=1;
			
				try {
				//collect all the information from the form and validate it 
				if(studentNumber == null && studentNumber.isEmpty()) {
					if (sdb.saveStudent(Integer.parseInt(studentNumber), Integer.parseInt(studentPassword), studentLastName, studentFirstName))
						sdb.saveInsuranceCompany(Integer.parseInt(studentNumber), studentCarInsurance, Integer.parseInt(studentCarLicense));
						//sdb.saveCarModel(studentCarModel);
						initLoginPage();
					}
					else 
						initErrorView();
				} catch (IOException exception) {
				initErrorView();
			}
			}
		}
		//if the user decides to leave the form for new client signup
		else if (cmd == "Cancel") {
			initLoginPage();
			current_step = 1;
		}
		//collect all the information from initPurchaseView()
		else if (cmd == "Purchase") {
			//take them to the confirmation view page
			initConfirmationView();
		}
		//every other action must be a keyboard input, so validate that,
		else {
			switch(cmd) {
				case 	"Space": 	
				    Global.selectedTextField.setText(Global.selectedTextField.getText() + " ");
					break;
				case	"Back":		
				    if (Global.selectedTextField.getText().length() > 0) {
    				    Global.selectedTextField.setText(Global.selectedTextField.getText().substring(0, Global.selectedTextField.getText().length()-1));
  					}
  					break;
				default:				
				    Global.selectedTextField.setText(Global.selectedTextField.getText() + cmd);
					break;
			}
		}
		this.setContentPane(panel);
	}
	
	//new client signup step 1, enter information (student#, lastname, firstname)
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

	//initial login screen, asking for student number and pin
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
		JLabel pinLabel = new JLabel("PIN Number:", JLabel.CENTER);

		welcomeBanner.setFont(Global.titleFont);
		studentNumberLabel.setFont(Global.labelFont);
		pinLabel.setFont(Global.labelFont);

		mainPane.add(welcomeBanner);
		mainPane.add(Box.createHorizontalStrut(15)); // a spacer
		//adding the componenets

		mainPane.add(studentNumberLabel);
        mainPane.add(studentNumberField);

        mainPane.add(Box.createHorizontalStrut(15)); // a spacer

        mainPane.add(pinLabel);
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
		newClient.setActionCommand("Student_Information");

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

	//styling of the buttons on the purchase page
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

	//where the user inputs the time information for their ticket.
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
		
		mainPane.add(new JLabel("Parking Rate $3.50 / day", JLabel.CENTER));
		mainPane.add(mainPurchasebuttons());
		mainPane.setLayout(new GridLayout(10,1));
		return mainPane;
	}

	//when theres an error of some sort (user didnt input proper information, call this.)
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

	//on the newclient signup page, this is step 3, where the user chooses their password 
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

	//after the purchase page, this is where the user confirms his ticket information
	public JPanel mainConfirmationView() {
		JPanel mainPane = new JPanel();
		JLabel chargeLabel = new JLabel(); 

		chargeLabel.setFont(Global.formFont);
		chargeLabel.setText("The charge is $3.50 / day. Please confirm payment");
        chargeLabel.setHorizontalAlignment(JLabel.CENTER);

		JButton ok = createSimpleButton("Confirm");
		JButton cancel = createSimpleButton("Cancel");
		ok.addActionListener(this);	
		cancel.addActionListener(this);

		// Set button properties
		ok.setFont(Global.buttonFont);
		ok.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
		ok.setBackground(new Color (34, 139, 34));

        cancel.setFont(Global.buttonFont);
        cancel.setBorder( new EmptyBorder( 10, 10, 10, 10) );
        cancel.setBackground(new Color (200, 0, 0));

		mainPane.setLayout(new GridLayout(8, 1, 1, 0));
		mainPane.add(chargeLabel);
		mainPane.add(ok);
		mainPane.add(cancel);
		return mainPane;
	}

	//initialise the ENTIRE view of the car info page in the new client signup
	public void initCarInfoPage() {
		panel.removeAll();
		panel.add(carInfoView(), "Center");
		panel.add(keyboardView(), "South");
		panel.add(sidePanelView(), "East");
	}
	//initialise the ENTIRE view of the choose password page in the new client signup
	public void initChoosePassPage() {
		panel.removeAll();
		panel.add(choosePasswordView(), "Center");
		panel.add(keyboardView(), "South");
		panel.add(sidePanelView(), "East");
	}
	//initialise the ENTIRE view of the login page.
	public void initLoginPage() {
		panel.removeAll();
		panel.add(mainLoginView(), "Center");
		panel.add(keyboardView(), "South");
	}
	//initialise the ENTIRE view for the Customer info page (student number, alstname, firstname view.).
	public void initCustomerInfoPage() {
		panel.removeAll();
		panel.add(mainStudentProfileView(), "Center");
		panel.add(keyboardView(), "South");
		panel.add(sidePanelView(), "East");
	}
	//initialise the ENTIRE view for the purchase view. 
	public void initPurchaseView() {
		panel.removeAll();
		panel.add(mainPurchaseView(), "Center");
		panel.add(keyboardView(), "South");
	}

	//initialise the ENTIRE view for the confirmation of ticket purchase view. 
	public void initConfirmationView() {
		panel.removeAll();
		panel.add(mainConfirmationView(), "Center");
	}

	//initialise the ENTIRE error page, displayed when something went wrong.
	public void initErrorView() {
		panel.removeAll();
		panel.add(mainErrorView(), "Center");
	}

	//main method.
	public KioskMainFrame() {
		//load in the databases 
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

