package Messages;

import java.awt.Color;
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

public class CourseDeleteSuccessful extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseDeleteSuccessful frame = new CourseDeleteSuccessful();
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
	public CourseDeleteSuccessful() {
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
		
		JLabel welcomeLabel = new JLabel("Course Deleted Successfully.");
		welcomeLabel.setForeground(Color.WHITE);
		welcomeLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		welcomeLabel.setBounds(65, 57, 230, 40);
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
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // close the current frame
                dispose();
            }
        });
        timer.start();
	}

}
