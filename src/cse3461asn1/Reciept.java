import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

public class Reciept {
    
    public static void main(String[] args) {

        boolean accepted = false;
        RecieptFrame frame = new RecieptFrame(accepted);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Parking Kiosk Reciept");
		frame.setPreferredSize(new Dimension(800, 400));
		frame.setMinimumSize(new Dimension(800, 400));
		frame.pack();
		frame.setVisible(true);
    }

}

class RecieptFrame extends JFrame implements ActionListener {
    private boolean ok;

    private String firstName;
    private String lastName;

    private JTextPane pane;
    private JPanel panel;

    private String face;
    private int size;
    private int style;
    private Font font;

    public RecieptFrame(boolean isAccepted) {

        // set required vars for output
        face = "Times New Roman";
        size = 24;
        style = Font.PLAIN;

        panel = new JPanel();

        pane = new JTextPane();
        pane.setEditable(false);

        font = new Font(face, style, size);
        pane.setFont(font);
        pane.setContentType("text/html");
        /* pane.setText(DisplayAcceptedText(font)); */
        pane.setText(DisplayText(isAccepted, font));

        panel.setLayout(new BorderLayout());
        panel.add(pane);
        this.setContentPane(panel);

    }
    
    /* Displays if the kiosk determines the records are valid */
    private static String DisplayAcceptedText(Font f) {

        String date = new Date().toString();
        return   ("<html><center><font face=\""+f.getFamily() + "\" size=\"" 
                + f.getSize() + "\"> Accepted"
                + "<br> Add the Stuff here " 
                + "<br>Issued on " + date
                + " </font></center></html>"
                );

    }

    /* Displays if the kiosk determines the records are invalid */
    private static String DisplayDeniedText(Font f) {

        String date = new Date().toString();
        return   ("<html><center><font face=\""+f.getFamily() + "\" size=\"" 
                + f.getSize() + "\"> Denied"
                + "<br> Add the Stuff here " 
                + "<br>Issued on " + date
                + " </font><center></html>"
                );

    }

    /* Abstracts the Displays text methods with a boolean value */
    private static String DisplayText(boolean b, Font f) {
        if (b)
            return DisplayAcceptedText(f);
        else
            return DisplayDeniedText(f);
    }


	@Override
	public void actionPerformed(ActionEvent ae) { }
}
