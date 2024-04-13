package Admin;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Database.Database;
import Messages.ChooseOption;
import Messages.MarksNotAssigned;
import Messages.ResultAlreadyPublished;
import Messages.ResultPublishSuccessful;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PublishResult extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> idComboBox;
	private JComboBox<String> yearComboBox;
	private JPanel modulesPanel;
	private JLabel gradeLabel;
	Database database = new Database();
	int studentID;
	String studentEmail;
	String grade;
	String year;

	/**
	 * Create the panel.
	 */
	public PublishResult() {
		setBackground(Color.BLACK);
		setBounds(0, 0, 1048, 720);
        setLayout(null);
        
        JLabel idLabel = new JLabel("Student ID");
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        idLabel.setBounds(40, 40, 98, 51);
        add(idLabel);
        
        idComboBox = new JComboBox<>();
        idComboBox.setForeground(Color.WHITE);
        idComboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        idComboBox.setBounds(141, 54, 240, 27);
        add(idComboBox);
        getStudents();
        
        JLabel yearLabel = new JLabel("Year");
        yearLabel.setForeground(Color.WHITE);
        yearLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        yearLabel.setBounds(40, 100, 98, 51);
        add(yearLabel);
        
        String[] yearOptions = {"Choose a year", "1", "2", "3"};
        yearComboBox = new JComboBox<>(yearOptions);
        yearComboBox.setForeground(Color.BLACK);
        yearComboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        yearComboBox.setBounds(141, 114, 240, 27);
        add(yearComboBox);
        
        modulesPanel = new JPanel();
        modulesPanel.setBounds(40, 212, 450, 114);
        modulesPanel.setLayout(null);
        add(modulesPanel);
        
        JButton viewModuleBtn = new JButton("View Modules");
        viewModuleBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		getModules();
        	}
        });
        viewModuleBtn.setForeground(Color.BLACK);
        viewModuleBtn.setBounds(38, 163, 131, 37);
        add(viewModuleBtn);
        
        gradeLabel = new JLabel();
        gradeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        gradeLabel.setBounds(194, 349, 72, 37);
        add(gradeLabel);
        
        JButton calculateGradeBtn = new JButton("Calculate Grade");
        calculateGradeBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String selectedStudent = (String) idComboBox.getSelectedItem();
        		year = (String) yearComboBox.getSelectedItem();
        		
        		if (!selectedStudent.equals("Choose a student") && !year.equals("Choose a year")) {       			 
        			String[] parts = selectedStudent.split(" ");
        			studentID = Integer.parseInt(parts[0]);
        			
        			try (Connection connection = database.getConnection()) {
        				if (connection != null) {
        					String sql1 = "SELECT COUNT(*) AS count FROM Result WHERE StudentID = ? AND Year = ?";
                            PreparedStatement statement1 = connection.prepareStatement(sql1);
                            statement1.setInt(1, studentID);
                            statement1.setString(2, year);
                            ResultSet rs1 = statement1.executeQuery();
                            
                            int resultCount = 0;
                            if (rs1.next()) {
                                resultCount = rs1.getInt("count");
                            }
                            
                            if (resultCount == 0) {
	        					String sql2 = "SELECT Email FROM Student WHERE ID = ?";
	        					PreparedStatement statement2 = connection.prepareStatement(sql2);
	        					statement2.setInt(1, studentID);
	        					ResultSet rs2 = statement2.executeQuery();        					
	        			        
	        					if(rs2.next()) {
	        						studentEmail = rs2.getString("Email");
	        						String sql3 = "SELECT Marks FROM Enrollment WHERE Student_Email = ?";
	        						PreparedStatement statement3 = connection.prepareStatement(sql3);
	        						statement3.setString(1, studentEmail);
	        						ResultSet rs3 = statement3.executeQuery();
	        						
	        						int moduleCount = 0;
	        						int totalMarks = 0;
	        		                while (rs3.next()) {  
	        		                	int marks = rs3.getInt("Marks");
	        		                	
	        		                	// default value of marks is -1 in the table
	        		                	if (!rs3.wasNull()) {
	        		                		totalMarks += marks;
	        		                		moduleCount++;
	        		                	} else {
	        		                		openMarksNotAssignedPage();
	        		                		return;
	        		                	}
	        		                }
	        		                
	        		                int gradeInNumber = totalMarks / moduleCount;
	        		                grade = getGrade(gradeInNumber);	        		                
	        		                gradeLabel.setText(grade);
	        				    }
	        				} else {
	        					openAlreadyPublishedPage();
	        				}
        				}
        			} catch (SQLException e1) {
        				e1.printStackTrace();
        			}
        			
        		} else {
        			openFailedPage();
        		}
        	}
        });
        calculateGradeBtn.setForeground(Color.BLACK);
        calculateGradeBtn.setBounds(38, 349, 131, 37);
        add(calculateGradeBtn);
        
        
        
        JButton publishBtn = new JButton("Publish");
        publishBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		publishGrade(studentID, studentEmail, grade, year);
        	}
        });
        publishBtn.setForeground(Color.BLACK);
        publishBtn.setBounds(38, 457, 98, 44);
        add(publishBtn);  
	}
	
	private void getStudents() {
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		comboBoxModel.addElement("Choose a student");
		
		try (Connection connection = database.getConnection()) {
			if (connection != null) {
				String sql1 = "SELECT ID, Name FROM Student";
				PreparedStatement statement1 = connection.prepareStatement(sql1);
				ResultSet rs1 = statement1.executeQuery();
				
				while(rs1.next()) {
					String studentID = rs1.getString("ID");
					String studentName = rs1.getString("Name");
					
	                comboBoxModel.addElement(studentID + " " + studentName);	   
	            }
			}
		} catch (SQLException e) {
            e.printStackTrace();
        }
		idComboBox.setModel(comboBoxModel);
	}
	
	private void getModules() {
		String selectedStudent = (String) idComboBox.getSelectedItem();
		String year = (String) yearComboBox.getSelectedItem();
		
		if (!selectedStudent.equals("Choose a student") && !year.equals("Choose a year")) {       			 
			String[] parts = selectedStudent.split(" ");
			int studentID = Integer.parseInt(parts[0]);
			
			
			try (Connection connection = database.getConnection()) {
				if (connection != null) {
					String sql1 = "SELECT Email FROM Student WHERE ID = ?";
					PreparedStatement statement1 = connection.prepareStatement(sql1);
					statement1.setInt(1, studentID);
					ResultSet rs1 = statement1.executeQuery();
					
					// List model to hold module values
			        DefaultListModel<String> listModel = new DefaultListModel<>();
			        
					if(rs1.next()) {
						String studentEmail = rs1.getString("Email");
						String sql2 = "SELECT ModuleCode FROM Enrollment WHERE Student_Email = ?";
						PreparedStatement statement2 = connection.prepareStatement(sql2);
						statement2.setString(1, studentEmail);
						ResultSet rs2 = statement2.executeQuery();
						   			
		                while (rs2.next()) {  
		                	String moduleCode = rs2.getString("ModuleCode");
		                	String sql3 = "SELECT ModuleName FROM Module WHERE ModuleCode = ?";
							PreparedStatement statement3 = connection.prepareStatement(sql3);
							statement3.setString(1, moduleCode);
							ResultSet rs3 = statement3.executeQuery();
		                	
							if (rs3.next()) {
			                    String moduleName = rs3.getString("ModuleName");
			                    System.out.println(moduleCode + " " + moduleName);
			                    listModel.addElement(moduleCode + " " + moduleName);
		    				}
		                }
		                
		                // create a JList using the DefaultListModel
		                JList<String> moduleList = new JList<>(listModel);
		                moduleList.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		                moduleList.setFixedCellHeight(50);
		                
		                JScrollPane scrollPane = new JScrollPane(moduleList);   			                
		                scrollPane.setBounds(0, 0, 450, 130);
		                modulesPanel.removeAll();
		                modulesPanel.add(scrollPane);
		                
		                modulesPanel.revalidate();
		                modulesPanel.repaint();
				    }
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} else {
			openFailedPage();
		}
	}
	
	private void publishGrade(int studentID, String studentEmail, String grade, String year) {
		try (Connection connection = database.getConnection()) {
			if (connection != null) {
				String sql4 = "INSERT INTO Result (StudentID, Student_Email, Grade, Year) VALUES (?, ?, ?, ?)";
				PreparedStatement statement4 = connection.prepareStatement(sql4);
				statement4.setInt(1, studentID);
				statement4.setString(2, studentEmail);
				statement4.setString(3, grade);
				statement4.setString(4, year);
				
				int rows = statement4.executeUpdate();
				if(rows > 0) {
					openSuccessfulPage();
				}
			}
		} catch (SQLException e) {
            e.printStackTrace();
        }
	}
		
	private String getGrade(int marks) {
		String grade; 
		if (marks >= 70) {
			 grade = "A"; 
		} else if (marks >= 60) {
			grade = "B";
		} else if (marks >= 50) {
			grade = "C";
		} else if (marks >= 40) {
			grade = "D";
		} else {
			grade = "Fail";
		}
		return grade;
	}
	
	/**
	 * Direct to the Publish Successful frame.
	 */
	private void openSuccessfulPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	ResultPublishSuccessful frame = new ResultPublishSuccessful();
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
	 * Direct to the Already Published frame.
	 */
	private void openAlreadyPublishedPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	ResultAlreadyPublished frame = new ResultAlreadyPublished();
					frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	/**
	 * Direct to the Already Published frame.
	 */
	private void openMarksNotAssignedPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	MarksNotAssigned frame = new MarksNotAssigned();
					frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
