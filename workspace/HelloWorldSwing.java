import javax.swing.*;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelloWorldSwing implements ActionListener {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        //ImageIcon
        Border border = BorderFactory.createLineBorder(Color.red);
        JFrame frame = new JFrame(/* A NAME GOES HERE (OPTIONAL)*/ "Swing Excersises");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //When we hit the X on the top right to close the application, it will actually close
        frame.setSize(240,80);
        frame.getContentPane().setBackground(new Color(171, 169, 161));

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("HNANANONO");
        label.setBorder(border);
        label.setText("Hello World!");
        //label.setHorizontalAlignment(40);
        //label.setHorizontalTextPosition();
        JButton button = new JButton("Press me!");
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
            
        });
        frame.add(button);

        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(label);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

   
}
