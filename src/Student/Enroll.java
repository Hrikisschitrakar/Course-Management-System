package Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import Database.Database;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Enroll extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel courseLabel;
	private JComboBox<String> moduleComboBox;
	
	/**
	 * Create the panel.
	 */
	public Enroll(String email) {
		setBackground(Color.GRAY);
        setBounds(0, 0, 1048, 720);
        setLayout(null);
        
        JPanel modulePanel = new JPanel();
        modulePanel.setBackground(Color.BLACK);
        modulePanel.setBounds(28, 27, 997, 259);
        add(modulePanel);
        modulePanel.setLayout(null);
       
		courseLabel = new JLabel();
		courseLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		courseLabel.setBounds(12, 14, 933, 39);
		modulePanel.add(courseLabel);
		
		JLabel moduleLabel = new JLabel("Choose a Course:");
		moduleLabel.setForeground(Color.WHITE);
		moduleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		moduleLabel.setBounds(14, 80, 205, 39);
		modulePanel.add(moduleLabel);

        moduleComboBox = new JComboBox<String>();
        moduleComboBox.setEditable(true);
		moduleComboBox.setBounds(7, 130, 421, 39);
		moduleComboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		modulePanel.add(moduleComboBox);
		// method call to add modules the combo box from database
		viewModuleOptions(email);
		
		JPanel messagePanel = new JPanel();
		messagePanel.setBackground(Color.BLACK);
		messagePanel.setBounds(28, 352, 997, 127);
		add(messagePanel);
		messagePanel.setLayout(null);
		messagePanel.setVisible(false);
		
		JLabel messageLabel = new JLabel();
		messageLabel.setForeground(Color.WHITE);
		messageLabel.setBounds(14, 19, 965, 40);
		messageLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		messagePanel.add(messageLabel);
		
		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messagePanel.setVisible(false);
			}
		});
		okBtn.setForeground(Color.WHITE);
		okBtn.setBounds(10, 71, 76, 40);
		messagePanel.add(okBtn);
		
		JButton enrollBtn = new JButton("Enroll");
		enrollBtn.setForeground(Color.WHITE);
		enrollBtn.setBackground(Color.WHITE);
		enrollBtn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedModule = (String) moduleComboBox.getSelectedItem();
		        String selectedModuleCode = selectedModule.substring(0, 5);
		        
		        if (selectedModule.equals("Choose a module to enroll")) {
		        	messagePanel.setVisible(true);
                    String message1 = "Please choose a module.";
                    messageLabel.setText(message1);	
		        } else {
			        Database database = new Database();
			        try (Connection connection = database.getConnection()) {
			            if (connection != null) {
			                // check if already enrolled in the module
			                String checkSql = "SELECT ModuleCode FROM Enrollment WHERE Student_Email = ?";
			                try (PreparedStatement statement1 = connection.prepareStatement(checkSql)) {
			                    statement1.setString(1, email);
			                    try (ResultSet rs1 = statement1.executeQuery()) {
			                        boolean isEnrolled = false;
			                        while (rs1.next()) {
			                            String moduleCode = rs1.getString("ModuleCode");
			                            if (moduleCode.equals(selectedModuleCode)) {
			                                // the student is already enrolled in this module		                                
			                                messagePanel.setVisible(true);
			                                String message1 = "You are already enrolled in " + selectedModule + ".";
			                                messageLabel.setText(message1);		                                
			                                isEnrolled = true;
			                                break;
			                            }
			                        }
	
			                        // if not, insert the enrollment record
			                        if (!isEnrolled) {
			                            String insertSql = "INSERT INTO Enrollment (Student_Email, ModuleCode) VALUES (?, ?)";
			                            try (PreparedStatement statement2 = connection.prepareStatement(insertSql)) {
			                                statement2.setString(1, email);
			                                statement2.setString(2, selectedModuleCode);
			                                int rows = statement2.executeUpdate();
	
			                                if (rows > 0) {
			                                	messagePanel.setVisible(true);
			                                	String message2 = "You have been enrolled in " + selectedModule + " successfully.";
			                                    messageLabel.setText(message2);
			                                } else {
			                                	messagePanel.setVisible(true);
			                                	String message3 = "Failed to enroll in " + selectedModule + ".";
			                                    messageLabel.setText(message3);
			                                }
			                            }
			                        }
			                    }
			                }
			            }
			        } catch (SQLException e1) {
			            e1.printStackTrace();
			        }
		        }
		    }
		});

		enrollBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		enrollBtn.setBounds(445, 130, 91, 39);
		modulePanel.add(enrollBtn);		 
		
	}
	
	private void viewModuleOptions(String email) {
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		comboBoxModel.addElement("Choose a module to enroll");
		
		Database database = new Database();
		try (Connection connection = database.getConnection()) {
			if (connection != null) {
				// insert query
				String courseSql = "SELECT Course FROM Student WHERE Email = ?";
				PreparedStatement statement1 = connection.prepareStatement(courseSql);
				statement1.setString(1, email);
				ResultSet rs1 = statement1.executeQuery();
				
				// check if there is a result
	            if (rs1.next()) {
	                String courseCode = rs1.getString("Course");
	                
	                String courseTitleSql = "SELECT CourseName FROM Course WHERE CourseCode = ?";
		            PreparedStatement statement2 = connection.prepareStatement(courseTitleSql);
		            statement2.setString(1, courseCode);
		            ResultSet rs2 = statement2.executeQuery();
	
	                if (rs2.next()) {
	                	String title = rs2.getString("CourseName");
		                courseLabel.setText("My Course: " + courseCode + " - " + title);
		                
						String modulesSql = "SELECT ModuleCode, ModuleName FROM Module WHERE CourseCode = ?";
			            PreparedStatement statement3 = connection.prepareStatement(modulesSql);
			            statement3.setString(1, courseCode);
			            ResultSet rs3 = statement3.executeQuery();
		
		                while (rs3.next()) {
		                		String moduleCode = rs3.getString("ModuleCode");
		                		String moduleName = rs3.getString("ModuleName");		                	
		                		comboBoxModel.addElement(moduleCode + " " + moduleName);
		                }            
			        }
	            }
			}
		} catch (SQLException e) {
            e.printStackTrace();
        }
		moduleComboBox.setModel(comboBoxModel);
	}
}
