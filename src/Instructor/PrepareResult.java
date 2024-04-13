package Instructor;

import javax.swing.JPanel;

import Database.Database;
import Messages.ChooseOption;
import Messages.EmptyFields;
import Messages.InvalidInput;
import Messages.MarksAlreadyAssigned;
import Messages.MarksAssignedSuccessful;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PrepareResult extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> moduleComboBox;
	private JComboBox<String> idComboBox;
	private JTextField marksTextField;

	/**
	 * Create the panel.
	 */
	public PrepareResult(String email) {
		setBackground(Color.BLACK);
		setBounds(0, 0, 1048, 720);
        setLayout(null);
        
        /* Student ID */
        JPanel idPanel = new JPanel();
        idPanel.setBounds(32, 133, 434, 58);
        add(idPanel);
        idPanel.setLayout(null);
        
        JLabel idLabel = new JLabel("StudentID");
        idLabel.setBackground(Color.LIGHT_GRAY);
        idLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        idLabel.setBounds(6, 6, 103, 46);
        idPanel.add(idLabel);
        
        idComboBox = new JComboBox<>();
        idComboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        idComboBox.setBounds(121, 17, 300, 27);
        idPanel.add(idComboBox);
        viewStudents(email);
        
        JPanel modulePanel = new JPanel();
        modulePanel.setLayout(null);
        modulePanel.setBounds(32, 194, 434, 58);
        add(modulePanel);
        
        /* Modules */
        JLabel moduleLabel = new JLabel("Module");
        moduleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        moduleLabel.setBounds(6, 6, 103, 46);
        modulePanel.add(moduleLabel);
        
        moduleComboBox = new JComboBox<>();
        moduleComboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        moduleComboBox.setBounds(121, 17, 300, 27);
        modulePanel.add(moduleComboBox);
        viewModuleOptions(email);        
        
        JPanel marksPanel = new JPanel();
        marksPanel.setLayout(null);
        marksPanel.setBounds(32, 255, 434, 58);
        add(marksPanel);
        
        JLabel marksLabel = new JLabel("Marks");
        marksLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        marksLabel.setBounds(6, 6, 103, 46);
        marksPanel.add(marksLabel);
        
        marksTextField = new JTextField();
        marksTextField.setBounds(125, 12, 289, 37);
        marksPanel.add(marksTextField);
        marksTextField.setColumns(10);
        
        JButton assignBtn = new JButton("Assign");
        assignBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			String marksText = marksTextField.getText().trim();
                    if (marksText.isEmpty()) {
                    	openEmptyFieldsPage();
                        System.out.println("Please enter marks.");
                        return; // exit the method if marks field is empty
                    }
                    
        		    int marks = Integer.parseInt(marksText);
        		    if (marks < 0 || marks > 100) {
                        openInvalidInputPage();
                        System.out.println("Marks should be between 0 and 100.");
                        return; // exit the method if marks are out of range
                    }
        		    
        		    String selectedStudent = (String) idComboBox.getSelectedItem();
        		    String selectedModule = (String) moduleComboBox.getSelectedItem();
        		    
        		    int studentID;
					String moduleCode;
        		    String[] parts1 = selectedStudent.split(" ");
        		    studentID = Integer.parseInt(parts1[0]);        		    
        		    String[] parts2 = selectedModule.split(" ");
        		    moduleCode = parts2[0];
        		    
       		    
        		    if (!selectedStudent.equals("Choose a student") && !selectedModule.equals("Choose a module")) {
        		    	Database database = new Database();
        				try (Connection connection = database.getConnection()) {
        					if (connection != null) {
        						String sql1 = "SELECT Email FROM Student WHERE ID = ?";
        						PreparedStatement statement1 = connection.prepareStatement(sql1);
        						statement1.setInt(1, studentID);
        						ResultSet rs1 = statement1.executeQuery();
        						
        						// check if there is a result
        						if(rs1.next()) {
        							String studentEmail = rs1.getString("Email");
        							String sql2 = "SELECT Marks FROM Enrollment WHERE Student_Email = ? AND ModuleCode = ?";
        							PreparedStatement statement2 = connection.prepareStatement(sql2);
        							statement2.setString(1, studentEmail);
        							statement2.setString(2, moduleCode);
        							ResultSet rs2 = statement2.executeQuery();
        			
        			                if (rs2.next()) {  
        			                	rs2.getInt("Marks");
        			                	if (rs2.wasNull()) {
	        								String sql3 = "UPDATE Enrollment SET Marks = ? WHERE Student_Email = ? AND ModuleCode = ?";
	        					            PreparedStatement statement3 = connection.prepareStatement(sql3);
	        					            statement3.setInt(1, marks);
	        					            statement3.setString(2, studentEmail);
	            							statement3.setString(3, moduleCode);
	        					            int rows = statement3.executeUpdate();
	        					            
	        					            if (rows > 0) {
	        					            	openSuccessfulPage();
	        					            }
        			                	} else {
        			                		openAlreadyAssignedPage();
        			                	}
        			                }
        					    }
        					}
        				} catch (SQLException e1) {
							e1.printStackTrace();
						}
                    } else {
                    	openFailedPage();
                    }

        		} catch (NumberFormatException error) {       			
        		    System.err.println("Invalid input: " + error.getMessage());
        		    openInvalidInputPage();
        		}
        	}
        });
        assignBtn.setForeground(Color.WHITE);
        assignBtn.setBounds(352, 330, 97, 46);
        add(assignBtn);
        
        JLabel headerLabel = new JLabel("Assign Marks");
        headerLabel.setBackground(new Color(238, 238, 238));
        headerLabel.setBounds(33, 18, 180, 83);
        add(headerLabel);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
	}
	
	// method to view student options
	private void viewStudents(String email) {
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		comboBoxModel.addElement("Choose a student");
		
		Set<String> studentSet = new HashSet<>(); // Set to keep track of unique students

		Database database = new Database();
		try (Connection connection = database.getConnection()) {
			if (connection != null) {
				String sql1 = "SELECT ID FROM Instructor WHERE Email = ?";
				PreparedStatement statement1 = connection.prepareStatement(sql1);
				statement1.setString(1, email);
				ResultSet rs1 = statement1.executeQuery();
				
				if(rs1.next()) {
					String instructorID = rs1.getString("ID");
					String sql2 = "SELECT ModuleCode FROM Assignment WHERE InstructorID = ?";
					PreparedStatement statement2 = connection.prepareStatement(sql2);
					statement2.setString(1, instructorID);
					ResultSet rs2 = statement2.executeQuery();
					
					while (rs2.next()) {
						String moduleCode = rs2.getString("ModuleCode");
						String sql3 = "SELECT Student_Email FROM Enrollment WHERE ModuleCode = ?";
			            PreparedStatement statement3 = connection.prepareStatement(sql3);
			            statement3.setString(1, moduleCode);
			            ResultSet rs3 = statement3.executeQuery();
		
		                while (rs3.next()) {
		                	String studentEmail = rs3.getString("Student_Email");		                	
		                	if (!studentSet.contains(studentEmail)) { // check if student already added
	                            String sql4 = "SELECT ID, Name FROM Student WHERE Email = ?";
	                            PreparedStatement statement4 = connection.prepareStatement(sql4);
	                            statement4.setString(1, studentEmail);
	                            ResultSet rs4 = statement4.executeQuery();

	                            // loop through the result set and add course panels
	                            while (rs4.next()) {
	                                String studentID = rs4.getString("ID");
	                                String studentName = rs4.getString("Name");
	                                comboBoxModel.addElement(studentID + " " + studentName);
	                                studentSet.add(studentEmail); // add student to the set
	                            }
					        }
		                }
	            	}
	            }
			}
		} catch (SQLException e) {
            e.printStackTrace();
        }
		idComboBox.setModel(comboBoxModel);
	}	
	
	// method to view module options
	private void viewModuleOptions(String email) {
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		comboBoxModel.addElement("Choose a module");
		
		Database database = new Database();
		try (Connection connection = database.getConnection()) {
			if (connection != null) {
				String sql1 = "SELECT ID FROM Instructor WHERE Email = ?";
				PreparedStatement statement1 = connection.prepareStatement(sql1);
				statement1.setString(1, email);
				ResultSet rs1 = statement1.executeQuery();
				
				// check if there is a result
				if(rs1.next()) {
					String instructorID = rs1.getString("ID");
					String sql2 = "SELECT ModuleCode FROM Assignment WHERE InstructorID = ?";
					PreparedStatement statement2 = connection.prepareStatement(sql2);
					statement2.setString(1, instructorID);
					ResultSet rs2 = statement2.executeQuery();
	
	                while (rs2.next()) {
	                	String moduleCode = rs2.getString("ModuleCode");		                
						String sql3 = "SELECT ModuleName FROM Module WHERE ModuleCode = ?";
			            PreparedStatement statement3 = connection.prepareStatement(sql3);
			            statement3.setString(1, moduleCode);
			            ResultSet rs3 = statement3.executeQuery();
		
		                while (rs3.next()) {
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
	/**
	 * Direct to the Empty Fields frame.
	 */
	private void openSuccessfulPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	MarksAssignedSuccessful frame = new MarksAssignedSuccessful();
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
	private void openAlreadyAssignedPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	MarksAlreadyAssigned frame = new MarksAlreadyAssigned();
					frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	/**
	 * Direct to the Choose an option frame.
	 */
	private void openFailedPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	ChooseOption frame = new ChooseOption();
					frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	/**
	 * Direct to the Invalid Input frame.
	 */
	private void openInvalidInputPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	InvalidInput frame = new InvalidInput();
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
