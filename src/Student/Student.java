package Student;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

import Admin.Admin;
import Admin.Dashboard;

public class Student extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel centerPanel;
    private JLabel headerLabel;

	/**
	 * Create the frame.
	 */
    
    // provides the email for the student.
	public Student(String email) {		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1468, 917);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 238, 238));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));

		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		/* Logo */
		JPanel logoPanel = new JPanel();
		logoPanel.setBackground(Color.WHITE);
		logoPanel.setBounds(0, 0, 320, 209);
		contentPane.add(logoPanel);
		logoPanel.setLayout(null);
		
		JLabel logoLabel = new JLabel("");
		logoLabel.setIcon(new ImageIcon(Admin.class.getResource("/Images/logo.png")));
		logoLabel.setBounds(60, 67, 215, 136);
		logoPanel.add(logoLabel);
		
		/* Header (Current Button) */
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(Color.BLACK);
        headerPanel.setBounds(378, 50, 1048, 60);
        contentPane.add(headerPanel);
        headerPanel.setLayout(null);

        headerLabel = new JLabel("DASHBOARD");
        headerLabel.setBackground(Color.GRAY);
        headerLabel.setBounds(6, 12, 361, 31);
        headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        
        /* Navigation */
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(Color.BLACK);
        navigationPanel.setBounds(0, 209, 320, 680);
        contentPane.add(navigationPanel);
        navigationPanel.setLayout(null);
        
        // Dashboard Button
        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.setForeground(Color.BLACK);
        dashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headerLabel.setText("DASHBOARD");
                // add dashboard content to the center panel by
                addCenterPanelContent(new Dashboard());
            }
        });
        dashboardButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        dashboardButton.setBounds(60, 40, 195, 55);
        navigationPanel.add(dashboardButton);
        
        // Manage Courses Button
        JButton myModulesBtn = new JButton("My Modules");
        myModulesBtn.setForeground(Color.BLACK);
        myModulesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headerLabel.setText("MY MODULES");
                // add manage courses content to the center panel
                addCenterPanelContent(new MyModules(email)); // Replace JPanel() with your manage courses content
            }
        });
        myModulesBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        myModulesBtn.setBounds(60, 140, 195, 55);
        navigationPanel.add(myModulesBtn);
        
        // Manage Students Button
        JButton viewResultButton = new JButton("View Result");
        viewResultButton.setForeground(Color.BLACK);
        viewResultButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headerLabel.setText("VIEW RESULT");
                // add manage students content to the center panel
                addCenterPanelContent(new ViewResult(email)); // Replace JPanel() with your manage students content
            }
        });
        viewResultButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        viewResultButton.setBounds(60, 240, 195, 55);
        navigationPanel.add(viewResultButton);
        
        // Manage Instructors Button
        JButton enrollButton = new JButton("Enroll");
        enrollButton.setForeground(Color.BLACK);
        enrollButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headerLabel.setText("ENROLL");
                // add manage instructors content to the center panel
                addCenterPanelContent(new Enroll(email));
            }
        });
        enrollButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        enrollButton.setBounds(60, 340, 195, 55);
        navigationPanel.add(enrollButton);
        
        // Log Out Button
        JButton logOutButton = new JButton("Log Out");
        logOutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        logOutButton.setForeground(Color.BLACK);
        logOutButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        logOutButton.setBounds(60, 440, 195, 55);
        navigationPanel.add(logOutButton);

        centerPanel = new JPanel();
        centerPanel.setBounds(378, 122, 1048, 720);
        contentPane.add(centerPanel);
        centerPanel.setLayout(null);
        
        addCenterPanelContent(new Dashboard());
        
        // Horizontal Separator between header and contents
        JSeparator horizontalSeparator = new JSeparator();
        horizontalSeparator.setForeground(SystemColor.windowBorder);
        horizontalSeparator.setBounds(385, 108, 1010, 12);
        contentPane.add(horizontalSeparator);
    }
	
	/**
	 * Method to add specific content to the center panel 
	 * when each button is pressed.
	 */
    // 
    private void addCenterPanelContent(JPanel panel) {
        // Clear existing content
        centerPanel.removeAll();
        // Add new content
        centerPanel.add(panel);
        // Repaint
        centerPanel.repaint();
    }
}
