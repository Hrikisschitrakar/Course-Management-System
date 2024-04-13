package Messages;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class SignUpSuccessful extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpSuccessful frame = new SignUpSuccessful();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignUpSuccessful() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(70, 70, 370, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.DARK_GRAY);
		centerPanel.setBounds(6, 0, 358, 166);
		contentPane.add(centerPanel);
		centerPanel.setLayout(null);
		
		JLabel successfulLabel = new JLabel("Sign Up Successful!!!");
		successfulLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		successfulLabel.setForeground(Color.WHITE);
		successfulLabel.setBackground(new Color(255, 255, 255));
		successfulLabel.setBounds(112, 6, 138, 16);
		centerPanel.add(successfulLabel);
		
		JLabel welcomeLabel = new JLabel("Welcome to Dashboard");
		welcomeLabel.setForeground(Color.WHITE);
		welcomeLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		welcomeLabel.setBounds(93, 57, 196, 40);
		centerPanel.add(welcomeLabel);
		
		JButton okButton = new JButton("OK");
		okButton.setForeground(Color.WHITE);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// close the current frame
				dispose();
			}
		});
		okButton.setBounds(270, 125, 82, 29);
		centerPanel.add(okButton);
		
		// timer to remove the frame after 5 seconds
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // close the current frame
                dispose();
            }
        });
//        timer.setRepeats(false); // only run the timer once
        timer.start();
	}

}
