import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

public class ReceiptFrame extends JFrame implements ActionListener {
    private boolean ok;

    private String firstName;
    private String lastName;

    private JTextPane pane;
    private JPanel panel;
    private JButton exitButton;

    private String face;
    private int size;
    private int style;
    private Font font;

    /* Pass in a student object and value that determines */
    /* whether they were permitted to park. Then displays */
    /* permit information based on decision and student */
    /* information. */
    public ReceiptFrame(Student s, boolean isAccepted) {

        // set required vars for output
        int gap = 15;
        face = "Times New Roman";
        size = 20;
        style = Font.PLAIN;

        panel = new JPanel();

        pane = new JTextPane();
        pane.setEditable(false);

        font = new Font(face, style, size);
        pane.setFont(font);
        pane.setContentType("text/html");

        /* This will display the appropriate information */
        /* based on whether the permit is accepted or not. */
        pane.setText(DisplayText(s, isAccepted, font));

        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));


        exitButton = new JButton("OK");
        exitButton.addActionListener(this);
        exitButton.setFont(font);
        exitButton.setBounds(80, 80, 80, 80);

        panel.add(pane, BorderLayout.CENTER);
        panel.add(exitButton, BorderLayout.SOUTH);
        this.setContentPane(panel);

    }
    
    /* Displays if the kiosk determines the records are valid */
    private static String DisplayAcceptedText(Student s, Font f) {

        String date = new Date().toString();
        return   ("<html><center><font face=\""+f.getFamily() + "\" size=\"" 
                + f.getSize() + "\"> Accepted"
                + "<br>Issued on " + date
                + "<br>Student Name: " + s.getFullName()
                + "<br>Student Number: " + s.getStudentNumber()
                + "<br>Parking spot: " + s.getParkingSpot()
                + " </font></center></html>"
                );

    }

    /* Displays if the kiosk determines the records are invalid */
    private static String DisplayDeniedText(Student s, Font f) {

        String date = new Date().toString();
        return   ("<html><center><font face=\""+f.getFamily() + "\" size=\"" 
                + f.getSize() + "\"> Denied"
                + "<br>Issued on " + date
                + "<br> No parking spaces available"
                + "<br> Please try again later."
                + " </font><center></html>"
                );

    }

    /* Abstracts the Displays text methods with a boolean value */
    private static String DisplayText(Student s, boolean b, Font f) {
        if (b)
            return DisplayAcceptedText(s, f);
        else
            return DisplayDeniedText(s, f);
    }


	@Override
	public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();	
        if (src == exitButton) {
            this.setVisible(false);
            this.dispose();
        }
            
	}

}
