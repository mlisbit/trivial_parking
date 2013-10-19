import java.awt.*;
import javax.swing.*;

public class TestRecieptFrame {

    public static void main(String []args) {
        Student s = new Student(
                111222333,
                1234,
                "Blah",
                "Who",
                "ok"
                );
        RecieptFrame frame = new RecieptFrame(s, false);         
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Testing Testing");
        frame.setPreferredSize(new Dimension(800, 400));
        frame.setMinimumSize(new Dimension(800, 400));
        frame.pack();
        frame.setVisible(true);
    }
}
