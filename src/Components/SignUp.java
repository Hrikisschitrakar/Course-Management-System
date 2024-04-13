package Components;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import Database.Database;
import Messages.EmptyFields;
import Messages.SignUpSuccessful;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField phoneTextField;
	private JTextField emailTextField;
	private JComboBox<String> modeComboBox;
	private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JCheckBox showPasswordCheckBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
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
	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 600, 600, 720);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(Color.GRAY);
		headerPanel.setBounds(18, 31, 563, 59);
		contentPane.add(headerPanel);
		headerPanel.setLayout(null);
		
		JLabel headerLabel = new JLabel("Sign Up");
		headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		headerLabel.setForeground(Color.BLACK);
		headerLabel.setBounds(199, 6, 189, 46);
		headerPanel.add(headerLabel);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.GRAY);
		centerPanel.setBounds(18, 96, 563, 561);
		contentPane.add(centerPanel);
		centerPanel.setLayout(null);
		
		JPanel namePanel = new JPanel();
		namePanel.setBounds(6, 19, 551, 45);
		centerPanel.add(namePanel);
		namePanel.setLayout(null);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setBounds(37, 6, 76, 31);
		namePanel.add(nameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(164, 2, 340, 39);
		namePanel.add(nameTextField);
		nameTextField.setColumns(10);
		
		JPanel phonePanel = new JPanel();
		phonePanel.setLayout(null);
		phonePanel.setBounds(6, 76, 551, 45);
		centerPanel.add(phonePanel);

		JLabel phoneLabel = new JLabel("Phone No.");
		phoneLabel.setBounds(38, 6, 76, 31);
		phonePanel.add(phoneLabel);
		
		phoneTextField = new JTextField();
		phoneTextField.setColumns(10);
		phoneTextField.setBounds(164, 2, 340, 39);
		phonePanel.add(phoneTextField);
		
		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(null);
		emailPanel.setBounds(6, 134, 551, 45);
		centerPanel.add(emailPanel);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(37, 6, 76, 31);
		emailPanel.add(emailLabel);
		
		emailTextField = new JTextField();
		emailTextField.setColumns(10);
		emailTextField.setBounds(164, 2, 340, 39);
		emailPanel.add(emailTextField);
						
		JPanel modePanel = new JPanel();
		modePanel.setLayout(null);
		modePanel.setBounds(6, 191, 551, 45);
		centerPanel.add(modePanel);	

		JLabel modeLabel = new JLabel("User Mode");
		modeLabel.setBounds(35, 6, 75, 31);
		modePanel.add(modeLabel);
		
		// use JComboBox for the role with the required options
        String[] modeOptions = {"Student", "Instructor"};
        modeComboBox = new JComboBox<>(modeOptions);
        modeComboBox.setBounds(164, 2, 340, 39);
        modePanel.add(modeComboBox);
        
        JPanel coursePanel = new JPanel();
		coursePanel.setLayout(null);
		coursePanel.setBounds(6, 248, 551, 45);
		centerPanel.add(coursePanel);
		
		JLabel courseLabel = new JLabel("Course");
		courseLabel.setBounds(34, 6, 76, 31);
		coursePanel.add(courseLabel);
		
		JComboBox<String> courseComboBox = new JComboBox<String>();
		courseComboBox.setBounds(164, 2, 340, 39);
		coursePanel.add(courseComboBox);
		// method call to add courses the combo box
		viewCourseOptions(courseComboBox);
        
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(null);
		passwordPanel.setBounds(6, 305, 551, 45);
		centerPanel.add(passwordPanel);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(34, 6, 76, 31);
		passwordPanel.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(164, 2, 340, 39);
		passwordPanel.add(passwordField);
		
		JPanel confirmPasswordPanel = new JPanel();
		confirmPasswordPanel.setLayout(null);
		confirmPasswordPanel.setBounds(6, 362, 551, 45);
		centerPanel.add(confirmPasswordPanel);
		
		JLabel comfirmPasswordLabel = new JLabel("Confirm Password");
		comfirmPasswordLabel.setBounds(34, 6, 130, 31);
		confirmPasswordPanel.add(comfirmPasswordLabel);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setColumns(10);
		confirmPasswordField.setBounds(164, 2, 340, 39);
		confirmPasswordPanel.add(confirmPasswordField);
		
		showPasswordCheckBox = new JCheckBox("Show Password");
		showPasswordCheckBox.setBounds(165, 409, 134, 25);
		centerPanel.add(showPasswordCheckBox);
		// show or hide password event
        showPasswordCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// password visibility
                if (showPasswordCheckBox.isSelected()) {
                	// show password
                    passwordField.setEchoChar('\u0000'); 
                    confirmPasswordField.setEchoChar('\u0000');
                } else {
                	// hide by setting default echo char for password
                    passwordField.setEchoChar('\u2022'); 
                    confirmPasswordField.setEchoChar('\u2022');
                }
            }
        });        
		
		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(223, 450, 117, 45);
		centerPanel.add(signUpButton);
		// sign up event
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 // get user inputs from the text fields and combo box
                String name = nameTextField.getText();
                String phone = phoneTextField.getText();
                String email = emailTextField.getText();
                String mode = (String) modeComboBox.getSelectedItem();
                String course = (String) courseComboBox.getSelectedItem();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());               
                
                // extract only course code from the chosen course option to add to database
                String courseCode = course.substring(0, Math.min(course.length(), 5));
                saveToDatabase(name, phone, email, mode, courseCode, password, confirmPassword);
			}
		});
		
		JLabel loginLabel = new JLabel("Already have an account?");
		loginLabel.setBounds(165, 528, 164, 16);
		centerPanel.add(loginLabel);
		
		JButton loginButton = new JButton("Log In");
		loginButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		loginButton.setForeground(Color.WHITE);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// open the Login page when the button is pressed
                dispose(); // close the current SignUp page
                openLoginPage(); // open the Login page
			}
		});
		loginButton.setBounds(327, 520, 88, 35);
		loginButton.setBorderPainted(false);
		centerPanel.add(loginButton);
	}
	
	/**
	 * Fetch and add courses to combo box
	 */
	private void viewCourseOptions(JComboBox<String> courseComboBox) {
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		comboBoxModel.addElement("Choose your course");
		
		Database database = new Database();
		try (Connection connection = database.getConnection()) {
			if (connection != null) {
				// insert query
				String sql = "SELECT CourseCode, CourseName FROM Course";
	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                ResultSet rs = statement.executeQuery();

	                // loop through the result set and add course codes to the combo box model
	                while (rs.next()) {
	                    String code = rs.getString("CourseCode");
	                    String title = rs.getString("CourseName");
	                    comboBoxModel.addElement(code + " - " + title);
	                }
	            }
			}
		} catch (SQLException e) {
            e.printStackTrace();
        }
		courseComboBox.setModel(comboBoxModel);
	}
	
	/**
	 * Save sign up details to the database.
	 */
	private void saveToDatabase(String name, String phone, String email, String mode,  String course, String password, String confirmPassword) {
		if (name.isEmpty() == true || phone.isEmpty() == true || email.isEmpty() == true || mode.isEmpty() == true ||password.isEmpty() == true || 
			confirmPassword.isEmpty() == true) {
        	openEmptyFieldsPage();
        } else {
			Database database = new Database();
			try (Connection connection = database.getConnection()) {
				if (connection != null) {
					String tableName = mode;
					String sql1 = "INSERT INTO " + tableName + " (Name, Phone, Email, Password, Course) VALUES (?, ?, ?, ?, ?)";
		            try (PreparedStatement statement1 = connection.prepareStatement(sql1)) {
		                // set parameters for the prepared statement
		            	statement1.setString(1, name);
		            	statement1.setString(2, phone);
		            	statement1.setString(3, email);
		            	statement1.setString(4, password);
		            	statement1.setString(5, course);
	
		                int rows1 = statement1.executeUpdate();
//		                
//		                String sql2 = "INSERT INTO StudentCourse (Student_Email, Code) VALUES (?, ?)";
//			            try (PreparedStatement statement2 = connection.prepareStatement(sql2)) {
//			            	statement2.setString(1, email);
//			            	statement2.setString(2, course);
//	
//			                int rows2 = statement2.executeUpdate();
		                if (rows1 > 0) {
		                	openSuccessfulPage();
		                    System.out.println("Sign up details added to the database.");	               	                    
		                } else {
		                	System.out.println("Failed to add sign up details to the database.");    			                
			            }
		            }
				}
			} catch (SQLException e) {
	            e.printStackTrace();
	        }
        }
	}
	
	/**
	 * Direct to the Login Successful frame.
	 */
	private void openSuccessfulPage() {
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
	 * Direct to the Login frame.
	 */
	private void openLoginPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login loginPage = new Login();
                    loginPage.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	/**
	 * Direct to the Empty Fields frame.
	 */
	private void openEmptyFieldsPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	EmptyFields frame = new EmptyFields();
					frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }	
}
