package Student;

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
import java.awt.Color;

public class MyModules extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel courseLabel;
	private JPanel headerPanel;
	private JPanel modulePanel;
	private JPanel instructorPanel;
	
	/**
	 * Create the panel.
	 */
	public MyModules(String email) {
		setBackground(Color.BLACK);
		setBounds(0, 0, 1048, 720);
        setLayout(null);
//      System.out.println(email);
        
        headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setBounds(28, 27, 1003, 137);
        add(headerPanel);
        headerPanel.setLayout(null);
       
		courseLabel = new JLabel();
		getMyCourse(email);
		courseLabel.setForeground(new Color(70, 130, 180));
		courseLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		courseLabel.setBounds(12, 18, 933, 39);
		headerPanel.add(courseLabel);
		
		JLabel moduleLabel = new JLabel("Enrolled Modules: ");
		moduleLabel.setForeground(Color.WHITE);
		moduleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		moduleLabel.setBounds(12, 92, 205, 39);
		headerPanel.add(moduleLabel);
		
		/* My Modules List */
		modulePanel = new JPanel();
		modulePanel.setBackground(Color.GRAY);
        modulePanel.setBounds(28, 164, 640, 250);
        add(modulePanel);
        modulePanel.setLayout(null);
        
        getMyModules(email);
        
        /* My Instructors List */
        instructorPanel = new JPanel();
        instructorPanel.setBackground(Color.GRAY);
        instructorPanel.setBounds(40, 473, 628, 205);
        add(instructorPanel);
        instructorPanel.setLayout(null);
        
        JLabel instructorsLabel = new JLabel("Tutors:");
        instructorsLabel.setBackground(Color.WHITE);
        instructorsLabel.setForeground(Color.WHITE);
        instructorsLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        instructorsLabel.setBounds(40, 422, 205, 39);
        add(instructorsLabel);
        
        getMyInstructors(email);
	}
	
	private void getMyCourse(String email) {
		Database database = new Database();
		try (Connection connection = database.getConnection()) {
			if (connection != null) {
				// insert query
				String courseSql = "SELECT Course FROM Student WHERE Email = ?";
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
				String sql1 = "SELECT ModuleCode FROM Enrollment WHERE Student_Email = ?";
				PreparedStatement statement1 = connection.prepareStatement(sql1);
				statement1.setString(1, email);
				ResultSet rs1 = statement1.executeQuery();
				
				// List model to hold module values
		        DefaultListModel<String> listModel = new DefaultListModel<>();
				
		        while (rs1.next()) {
                    String moduleCode = rs1.getString("ModuleCode");
                    
                    String sql2 = "SELECT ModuleName FROM Module WHERE ModuleCode = ?";
    				PreparedStatement statement2 = connection.prepareStatement(sql2);
    				statement2.setString(1, moduleCode);
    				ResultSet rs2 = statement2.executeQuery();
    				
    				if (rs2.next()) {
	                    String moduleName = rs2.getString("ModuleName");
	                    listModel.addElement(moduleCode + " " + moduleName);
    				}
                }

		        
		        // create a JList using the DefaultListModel
                JList<String> moduleList = new JList<>(listModel);
                moduleList.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
                moduleList.setFixedCellHeight(50);
                
                JScrollPane scrollPane = new JScrollPane(moduleList);
                scrollPane.setBounds(0, 0, 628, 250);
                modulePanel.add(scrollPane);
			}    				
		} catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	private void getMyInstructors(String email) {
		Database database = new Database();
		try (Connection connection = database.getConnection()) {
			if (connection != null) {
				// insert query
				String sql1 = "SELECT ModuleCode FROM Enrollment WHERE Student_Email = ?";
				PreparedStatement statement1 = connection.prepareStatement(sql1);
				statement1.setString(1, email);
				ResultSet rs1 = statement1.executeQuery();
				
				// List model to hold module values
		        DefaultListModel<String> instructorsListModel = new DefaultListModel<>();
				
	            while (rs1.next()) {
	                String moduleCode = rs1.getString("ModuleCode");
	                
	                String sql2 = "SELECT InstructorID FROM Assignment WHERE ModuleCode = ?";
		            PreparedStatement statement2 = connection.prepareStatement(sql2);
		            statement2.setString(1, moduleCode);
		            ResultSet rs2 = statement2.executeQuery();		            		            
	
	                if (rs2.next()) {
	                	String instructorID = rs2.getString("InstructorID");
	                	System.out.println("Instructor ID: " + instructorID);
	                	String sql3 = "SELECT Name FROM Instructor WHERE ID = ?";
			            PreparedStatement statement3 = connection.prepareStatement(sql3);
			            statement3.setString(1, instructorID);
			            ResultSet rs3 = statement3.executeQuery();
			            
			            if (rs3.next()) {
			            	String instructorName = rs3.getString("Name");
			            	System.out.println("Instructor Name: " + instructorName);
			            	instructorsListModel.addElement(moduleCode + " - " + instructorName);
			            }
	                }
	            }
	            
	            // create a JList using the DefaultListModel
                JList<String> instructorList = new JList<>(instructorsListModel);
                instructorList.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
                instructorList.setFixedCellHeight(50);
                
                JScrollPane scrollPane = new JScrollPane(instructorList);
                scrollPane.setBounds(0, 0, 628, 205);
                instructorPanel.add(scrollPane);
			}
		} catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
