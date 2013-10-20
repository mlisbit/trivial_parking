import java.awt.*;
import javax.swing.*;
import java.io.IOException;

public class TestReceiptFrame {

    public static void main(String []args) {
        StudentDatabase sdb;
        try {
            sdb = new StudentDatabase("students.txt");
            Student s = sdb.getStudent(123456789);
            ReceiptFrame frame = new ReceiptFrame(s, false);         
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Testing Testing");
            frame.setPreferredSize(new Dimension(800, 400));
            frame.setMinimumSize(new Dimension(800, 400));
            frame.pack();
            frame.setVisible(true);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
