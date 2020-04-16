import javax.swing.*;

public class Main {
    public static void main(String[] args)  {
        JFrame frame = new JFrame("Drops");
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.requestFocus();
        Panel panel = new Panel();
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setSize(panel.sizeX, panel.sizeY);
    }
}
