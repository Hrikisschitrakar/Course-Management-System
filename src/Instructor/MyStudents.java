package Instructor;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Database.Database;

public class MyStudents extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel courseLabel;
	private JPanel headerPanel;
	private JPanel modulePanel;
	private JPanel studentPanel;
	
	/**
	 * Create the panel.
	 */
	public MyStudents(String email) {
		setBackground(Color.BLACK);
		setBounds(0, 0, 1048, 720);
        setLayout(null);
        
        headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setLayout(null);
        headerPanel.setBounds(23, 21, 1003, 137);
        add(headerPanel);
        
        courseLabel = new JLabel();
        courseLabel.setForeground(new Color(70, 130, 180));
        courseLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        courseLabel.setBounds(12, 18, 933, 39);
        headerPanel.add(courseLabel);
        
        getMyCourse(email);
        
        JLabel modulesLabel = new JLabel("My Modules: ");
        modulesLabel.setForeground(Color.WHITE);
        modulesLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        modulesLabel.setBounds(6, 18, 205, 39);
        headerPanel.add(modulesLabel);
        
        modulePanel = new JPanel();
        modulePanel.setBackground(Color.GRAY);
        modulePanel.setLayout(null);
        modulePanel.setBounds(23, 158, 638, 131);
        add(modulePanel);
        getMyModules(email);
        
        JLabel studentsLabel = new JLabel("My Students:");
        studentsLabel.setForeground(Color.WHITE);
        studentsLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        studentsLabel.setBounds(23, 316, 205, 39);
        add(studentsLabel);

        /* Students List */
        studentPanel = new JPanel();
        studentPanel.setBackground(Color.GRAY);
        studentPanel.setBounds(23, 367, 628, 295);
        studentPanel.setLayout(null);
		add(studentPanel);

		getMyStudents(email);
	}
	
	private void getMyCourse(String email) {
		Database database = new Database();
		try (Connection connection = database.getConnection()) {
			if (connection != null) {
				// insert query
				String courseSql = "SELECT Course FROM Instructor WHERE Email = ?";
				PreparedStatement statement1 = connection.prepareStatement(courseSql);
				statement1.setString(1, email);
				ResultSet rs1 = statement1.executeQuery();
				
				// Check if there is a result
	            if (rs1.next()) {
	                String code = rs1.getString("Course");
	                
	                String courseTitleSql = "SELECT CourseName FROM Course WHERE CourseCode = ?";
		            PreparedStatement statement2 = connection.prepareStatement(courseTitleSql);
		            statement2.setString(1, code);
		            ResultSet rs2 = statement2.executeQuery();
	
	                if (rs2.next()) {
	                	String title = rs2.getString("CourseName");
		                courseLabel.setText("My Course: " + code + " - " + title);
	                }
	            }
			}
		} catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private void getMyModules(String email) {
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
					
					// List model to hold module values
			        DefaultListModel<String> listModel = new DefaultListModel<>();
					
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
	                moduleList.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	                moduleList.setFixedCellHeight(50);
	                
	                JScrollPane scrollPane = new JScrollPane(moduleList);
	                scrollPane.setBounds(0, 0, 628, 131);
	                modulePanel.add(scrollPane);
				}
			}    				
		} catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	private void getMyStudents(String email) {
		Database database = new Database();
		try (Connection connection = database.getConnection()) {
			if (connection != null) {
				String sql1 = "SELECT ID FROM Instructor WHERE Email = ?";
				PreparedStatement statement1 = connection.prepareStatement(sql1);
				statement1.setString(1, email);
				ResultSet rs1 = statement1.executeQuery();
				
				DefaultListModel<String> listModel = new DefaultListModel<>();
				listModel.addElement("ID - " + "Student Name"  + " - " + "Module Code");
				
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
		                	String sql4 = "SELECT ID, Name FROM Student WHERE Email = ?";
				            PreparedStatement statement4 = connection.prepareStatement(sql4);
				            statement4.setString(1, studentEmail);
				            ResultSet rs4 = statement4.executeQuery();
				            
							// loop through the result set and add course panels
					        while (rs4.next()) {
					        	String studentID = rs4.getString("ID");
				            	String studentName = rs4.getString("Name");
				            	listModel.addElement(studentID + "  -  " + studentName  + "  -  " + moduleCode);
					        }
		                }
	            	}
				}
				// create a JList using the DefaultListModel
                JList<String> instructorList = new JList<>(listModel);
                instructorList.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
                instructorList.setFixedCellHeight(50);
                
                JScrollPane scrollPane = new JScrollPane(instructorList);
                scrollPane.setBounds(0, 0, 628, 295);
                studentPanel.add(scrollPane);
			}
		} catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
