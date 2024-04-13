package Components;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Admin.Admin;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import Database.Database;
import Instructor.Instructor;
import Interfaces.LoginEmailProvider;
import Messages.EmptyFields;
import Messages.LoginFailed;
import Student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements LoginEmailProvider {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> modeComboBox;
	private JTextField emailTextField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 600, 600, 650);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(Color.GRAY);
		headerPanel.setLayout(null);
		headerPanel.setBounds(18, 33, 563, 63);
		contentPane.add(headerPanel);
		
		JLabel headerLabel = new JLabel("Login Page");
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		headerLabel.setBounds(194, 6, 363, 46);
		headerPanel.add(headerLabel);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.GRAY);
		centerPanel.setLayout(null);
		centerPanel.setBounds(18, 108, 563, 471);
		contentPane.add(centerPanel);
		
		JPanel modePanel = new JPanel();
		modePanel.setLayout(null);
		modePanel.setBounds(6, 53, 551, 45);
		centerPanel.add(modePanel);
		
		JLabel modeLabel = new JLabel("User Mode");
		modeLabel.setForeground(Color.BLACK);
		modeLabel.setBounds(56, 6, 69, 31);
		modePanel.add(modeLabel);
		
		String[] modeOptions = {"Admin", "Student", "Instructor"};
		modeComboBox = new JComboBox<>(modeOptions);
		modeComboBox.setBounds(145, 3, 340, 39);
		modePanel.add(modeComboBox);
		
		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(null);
		emailPanel.setBounds(6, 120, 551, 45);
		centerPanel.add(emailPanel);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(56, 6, 76, 31);
		emailPanel.add(emailLabel);
		
		emailTextField = new JTextField();
		emailTextField.setColumns(10);
		emailTextField.setBounds(145, 2, 340, 39);
		emailPanel.add(emailTextField);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(null);
		passwordPanel.setBounds(6, 188, 551, 45);
		centerPanel.add(passwordPanel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(56, 6, 76, 31);
		passwordPanel.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(145, 2, 340, 39);
		passwordPanel.add(passwordField);
		
		JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
		showPasswordCheckBox.setBounds(149, 245, 134, 25);
		centerPanel.add(showPasswordCheckBox);
		// show or hide password event
        showPasswordCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// password visibility
                if (showPasswordCheckBox.isSelected()) {
                	// show password
                    passwordField.setEchoChar('\u0000'); 
                } else {
                	// hide by setting default echo char for password
                    passwordField.setEchoChar('\u2022'); 
                }
            }
        });       
		
		JButton loginButton = new JButton("Log In");
		loginButton.setBounds(222, 295, 117, 45);
		centerPanel.add(loginButton);
		// sign up event
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get user inputs from the text fields and combo box
				String mode = (String) modeComboBox.getSelectedItem();
			    String email = emailTextField.getText();
			    String password = new String(passwordField.getPassword());
			                
			    checkDetails(mode, email, password);
			}
		});

		JLabel signUpLabel = new JLabel("Not Registered?");
		signUpLabel.setBounds(159, 373, 108, 29);
		centerPanel.add(signUpLabel);
		
		JButton createAccountButton = new JButton("Create an account");
		createAccountButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		createAccountButton.setForeground(Color.WHITE);
		createAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// open the Login page when the button is pressed
                dispose(); // close the current Login page
                openSignUpPage(); // open the SignUp page
			}
		});
		createAccountButton.setBounds(259, 374, 166, 29);
		createAccountButton.setBorderPainted(false);
		centerPanel.add(createAccountButton);
	}
	
	/**
	 * Check login details in the database.
	 */
	private void checkDetails(String mode, String email, String password) {
		if (mode.isEmpty() == true || email.isEmpty() == true || password.isEmpty() == true) {
        	openEmptyFieldsPage();
        } else {
			Database database = new Database();
			try (Connection connection = database.getConnection()) {
				if (connection != null) {
					String tableName = mode;
					// select query 
					String sql = "SELECT * FROM " + tableName + " WHERE email = ? AND password = ?";
					try (PreparedStatement statement = connection.prepareStatement(sql)) {
						// set parameters for the prepared statement
						statement.setString(1, email);
						statement.setString(2, password);
			            // execute the query and store it as a ResultSet
			            ResultSet resultSet = statement.executeQuery();
			            if (resultSet.next()) {
	                        // if a matching row is found, login is successful
			            	dispose();
//	                        openSuccessfulPage();
	                        if (mode.equals("Admin")) {
	                        	openAdminFrame();
	                        } else if (mode.equals("Student")) {
	                        	openStudentFrame(email);
	                        } else if (mode.equals("Instructor")) {
	                        	openInstructorFrame(email);
	                        }
	                    } else {
	                        // if no matching row is found, login failed
	                    	openFailedPage();                  
	                    }
					}
				}
			} catch (SQLException e) {
	            e.printStackTrace();
	        }
        }
	}

	/**
	 * Direct to the Login Failed frame.
	 */
	private void openFailedPage() {
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
	
	/**
	 * Direct to the SignUp frame.
	 */
	private void openSignUpPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	SignUp signUpPage = new SignUp();
                	signUpPage.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	/**
	 * Direct to the Admin frame.
	 */
	private void openAdminFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin frame = new Admin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Direct to the Student frame.
	 */
	private void openStudentFrame(String email) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Student frame = new Student(email);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Direct to the Instructor frame.
	 */
	private void openInstructorFrame(String email) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Instructor frame = new Instructor(email);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// implementation of the interface method to return entered email
	@Override
    public String getEmail() {
        return emailTextField.getText();
    }
}