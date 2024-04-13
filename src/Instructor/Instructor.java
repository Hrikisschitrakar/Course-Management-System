package Instructor;

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

public class Instructor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel centerPanel;
    private JLabel headerLabel;

	/**
	 * Create the frame.
	 */
	public Instructor(String email) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1468, 917);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));

		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		/* Logo */
		JPanel logoPanel = new JPanel();
		logoPanel.setBackground(Color.WHITE);
		logoPanel.setBounds(0, 0, 320, 244);
		contentPane.add(logoPanel);
		logoPanel.setLayout(null);
		
		JLabel logoLabel = new JLabel("");
		logoLabel.setIcon(new ImageIcon(Admin.class.getResource("/Images/logo.jpg")));
		logoLabel.setBounds(92, 105, 120, 136);
		logoPanel.add(logoLabel);
		
		/* Header (Current Button) */
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(Color.GRAY);
        headerPanel.setBounds(378, 50, 1048, 60);
        contentPane.add(headerPanel);
        headerPanel.setLayout(null);

        headerLabel = new JLabel("DASHBOARD");
        headerLabel.setBounds(6, 12, 361, 31);
        headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        
        /* Navigation */
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(Color.BLACK);
        navigationPanel.setBounds(0, 242, 320, 647);
        contentPane.add(navigationPanel);
        navigationPanel.setLayout(null);
        
        // Dashboard Button
        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.setBackground(Color.GRAY);
        dashboardButton.setForeground(Color.WHITE);
        dashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headerLabel.setText("DASHBOARD");
                addCenterPanelContent(new Dashboard());
            }
        });
        dashboardButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        dashboardButton.setBounds(60, 40, 195, 55);
        navigationPanel.add(dashboardButton);
        
        // My Students Button
        JButton myStudentsButton = new JButton("My Students");
        myStudentsButton.setBackground(Color.GRAY);
        myStudentsButton.setForeground(Color.WHITE);
        myStudentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headerLabel.setText("MY STUDENTS");
                addCenterPanelContent(new MyStudents(email));
            }
        });
        myStudentsButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        myStudentsButton.setBounds(60, 140, 195, 55);
        navigationPanel.add(myStudentsButton);
        
        // Manage Instructors Button
        JButton prepareResultButton = new JButton("Prepare Result");
        prepareResultButton.setBackground(Color.GRAY);
        prepareResultButton.setForeground(Color.WHITE);
        prepareResultButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headerLabel.setText("PREPARE RESULT");
                addCenterPanelContent(new PrepareResult(email));
            }
        });
        prepareResultButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        prepareResultButton.setBounds(60, 240, 195, 55);
        navigationPanel.add(prepareResultButton);
        
        // Log Out Button
        JButton logOutButton = new JButton("Log Out");
        logOutButton.setBackground(Color.GRAY);
        logOutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        logOutButton.setForeground(Color.RED);
        logOutButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        logOutButton.setBounds(60, 340, 195, 55);
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
