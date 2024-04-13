package Messages;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class LoginFailed extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFailed frame = new LoginFailed();
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
	public LoginFailed() {
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
		
		JLabel failedLabel = new JLabel("Login Failed. Invalid credentials.");
		failedLabel.setForeground(Color.WHITE);
		failedLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		failedLabel.setBounds(54, 52, 253, 40);
		centerPanel.add(failedLabel);
		
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
        timer.start();
	}

}
