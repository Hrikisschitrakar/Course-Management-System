package Forms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Database.Database;
import Messages.CourseAddedSuccessful;
import Messages.EmptyFields;


public class AddCourseForm extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField codeTextField;
    private JTextField titleTextField;
    private JTextField module1TextField;
    private JTextField module2TextField;
    private JTextField module3TextField;
    private JTextField module4TextField;

	/**
	 * Create the panel.
	 */
	public AddCourseForm() {
		setBackground(Color.BLACK);
		setBounds(0, 0, 628, 273);
		setLayout(null);
		
		JLabel courseCode = new JLabel("Course Code:");
		courseCode.setForeground(Color.WHITE);
		courseCode.setBounds(6, 6, 113, 30);
		add(courseCode);
		
		JLabel courseTitle = new JLabel("Course Title:");
		courseTitle.setForeground(Color.WHITE);
		courseTitle.setBounds(6, 48, 113, 30);
		add(courseTitle);
		
		codeTextField = new JTextField();
		codeTextField.setBounds(131, 6, 491, 30);
		add(codeTextField);
		codeTextField.setColumns(10);
		
		titleTextField = new JTextField();
		titleTextField.setColumns(10);
		titleTextField.setBounds(131, 48, 491, 30);
		add(titleTextField);
		
		JLabel module1 = new JLabel("Module 1:");
		module1.setForeground(Color.WHITE);
		module1.setBackground(Color.WHITE);
		module1.setBounds(6, 90, 113, 30);
		add(module1);
		
		JLabel module2 = new JLabel("Module 2:");
		module2.setBounds(331, 90, 113, 30);
		add(module2);
		
		JLabel module3 = new JLabel("Module 3:");
		module3.setForeground(Color.WHITE);
		module3.setBounds(6, 151, 113, 30);
		add(module3);
		
		JLabel module4 = new JLabel("Module 4:");
		module4.setBounds(331, 151, 113, 30);
		add(module4);
		
		module1TextField = new JTextField();
		module1TextField.setColumns(10);
		module1TextField.setBounds(6, 120, 293, 30);
		add(module1TextField);
		
		module2TextField = new JTextField();
		module2TextField.setColumns(10);
		module2TextField.setBounds(329, 120, 293, 30);
		add(module2TextField);		
		
		module3TextField = new JTextField();
		module3TextField.setColumns(10);
		module3TextField.setBounds(6, 183, 293, 30);
		add(module3TextField);
		
		module4TextField = new JTextField();
		module4TextField.setColumns(10);
		module4TextField.setBounds(329, 183, 293, 30);
		add(module4TextField);
		
		JButton confirmBtn = new JButton("Confirm");
		confirmBtn.setForeground(Color.WHITE);
		confirmBtn.setBounds(408, 225, 95, 42);
		add(confirmBtn);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setForeground(Color.WHITE);
		cancelBtn.setBounds(527, 225, 95, 42);
		add(cancelBtn);
		

		// action listener for confirm button
        confirmBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String code = codeTextField.getText();
                String title = titleTextField.getText();
                String module1 = module1TextField.getText();
                String module2 = module2TextField.getText();
                String module3 = module3TextField.getText();
                String module4 = module4TextField.getText();
                String []modules = new String[4];
                modules[0] = module1;
                modules[1] = module2;
                modules[2] = module3;
                modules[3] = module4;
                
                if (code.isEmpty() == true || title.isEmpty() == true || module1.isEmpty() == true || module2.isEmpty() == true ||
                		module3.isEmpty() == true || module4.isEmpty() == true) {
                	openEmptyFieldsPage();
                } else {
	                Database database = new Database();
	        		try (Connection connection = database.getConnection()) {
	        			if (connection != null) {
	        				boolean isAdded = false;
	        				
	        				// insert into Course
	        				String sql1 = "INSERT INTO Course (CourseCode, CourseName, Duration) VALUES (?, ?, ?)";
	        	            try (PreparedStatement statement1 = connection.prepareStatement(sql1)) {
	        	                // set parameters for the prepared statement
	        	            	statement1.setString(1, code);
	        	            	statement1.setString(2, title);
	        	            	statement1.setInt(3, 3);
	
	        	                // execute the query
	        	                int rows1 = statement1.executeUpdate();
	        	                if (rows1 > 0) {
	        	                	String sql2 = "INSERT INTO Module (ModuleCode, ModuleName, CourseCode) VALUES (?, ?, ?)";
	        	                	try(PreparedStatement statement2 = connection.prepareStatement(sql2)) {	
	        	                		for(int i = 0; i < 4; i++) {
			        	                	String currentModule = modules[i];
			        	                	String moduleCode = currentModule.substring(0, 5);
			        	                    String moduleName = currentModule.substring(5).trim();		        	                    		
		        	                			        	               	        	                		        	                
				        	                statement2.setString(1, moduleCode);
				        	            	statement2.setString(2, moduleName);
				        	            	statement2.setString(3, code);
				        	            	
				        	            	int rows2 = statement2.executeUpdate();
				        	            	if (rows2 == 0) {
				        	            		isAdded = false;
		                                        break;
				        	            	} else {
		                                        isAdded = true;
		                                    }
			        	                }
		        	                }
	        	                } 	        	                	        	              	        	                
	        	            }
	        	            if (isAdded) {
        	                	openSuccessfulPage();
        	                    System.out.println("New course added to the database.");	               	                    
        	                } else {
        	                	System.out.println("Failed to add new course to the database.");
        	                }
	        			}
	        		} catch (SQLException e1) {
	                    e1.printStackTrace();
	                }
	                
	                setVisible(false);
                }
            }
        });

        // action listener for cancel button
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // hide the form panel on cancel
                setVisible(false);
            }
        });
	}
	
	/**
	 * Direct to the Course Added Successful frame.
	 */
	private void openSuccessfulPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	CourseAddedSuccessful frame = new CourseAddedSuccessful();
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
}
