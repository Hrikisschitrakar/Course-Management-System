package Admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Database.Database;
import Forms.AddCourseForm;
import Forms.AddModuleForm;
import Forms.DeleteCourseForm;
import Forms.ViewModulesForm;

public class ManageCourse extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel formPanel;
	private JPanel coursesPanel;
	private JTable coursesTable;
	
	/**
	 * Create the panel.
	 */
	public ManageCourse() {
		setBounds(0, 0, 1048, 720);
		setLayout(null);
		
		/* Courses Info */
		coursesPanel = new JPanel();
		coursesPanel.setBackground(Color.BLACK);
		coursesPanel.setBounds(2, 2, 1040, 325);
		add(coursesPanel);
		coursesPanel.setLayout(null);
		
		// Courses Table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 6, 900, 313);
		coursesPanel.add(scrollPane);
		
		coursesTable = new JTable();
		scrollPane.setViewportView(coursesTable);
		getCourseInfo();	
		coursesTable.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		coursesTable.setRowHeight(60);
		
		JTableHeader tableHeader = coursesTable.getTableHeader();
		tableHeader.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		tableHeader.setForeground(new Color(70, 130, 180));
		tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 50));
		
		// Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.BLACK);
		buttonsPanel.setBounds(0, 383, 1048, 337);
		add(buttonsPanel);
		buttonsPanel.setLayout(null);
		
		formPanel = new JPanel();
		formPanel.setBounds(305, 22, 628, 273);
		buttonsPanel.add(formPanel);
		formPanel.setLayout(null);
		formPanel.setVisible(false); // initially not visible
		
		/* View Modules of a Course */
		JButton viewModuleBtn = new JButton("View modules");
		viewModuleBtn.setBounds(25, 19, 199, 60);
		buttonsPanel.add(viewModuleBtn);
		viewModuleBtn.setForeground(Color.BLACK);
		viewModuleBtn.setFont(new Font("Lucida Grande", Font.BOLD, 17));              
			
		/* Add New Course */
		JButton addNewCourseBtn = new JButton("Add new course");
		addNewCourseBtn.setBounds(25, 91, 199, 60);
		addNewCourseBtn.setForeground(Color.BLACK);
		addNewCourseBtn.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		buttonsPanel.add(addNewCourseBtn);
		
				
		/* Add Module to a Course */
		JButton addModuleBtn = new JButton("Add module");
		addModuleBtn.setForeground(Color.BLACK);
		addModuleBtn.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		addModuleBtn.setBounds(25, 163, 199, 60);
		buttonsPanel.add(addModuleBtn);
		
		/* Delete a Course */
		JButton deleteCourseBtn = new JButton("Delete a course");
		deleteCourseBtn.setForeground(new Color(215, 30, 34));
		deleteCourseBtn.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		deleteCourseBtn.setBounds(25, 235, 199, 60);
		buttonsPanel.add(deleteCourseBtn);
		
		// action listener for Add New Course button
		viewModuleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewFormPanel(new ViewModulesForm());
				
		    }
		});		
		
		// action listener for Add New Course button
		addNewCourseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewFormPanel(new AddCourseForm());
				
		    }
		});
		
		// action listener for Add Module button
		addModuleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewFormPanel(new AddModuleForm());
			}
		});
		
		// action listener for Delete Course button
		deleteCourseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewFormPanel(new DeleteCourseForm());
			}
		});
		
	}
	
	/**
	 * Fetch course info from the database.  
	 */
	private void getCourseInfo() {
		Database database = new Database();
	    try (Connection connection = database.getConnection()) {
	    	// select query to fetch course info
			String sql = "SELECT CourseCode, CourseName FROM Course";
			PreparedStatement statement = connection.prepareStatement(sql);
			// execute the query and get the ResultSet
			ResultSet rs = statement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			DefaultTableModel model = (DefaultTableModel) coursesTable.getModel();
			
			int cols = rsmd.getColumnCount();
			// array of column names
			String[] colName = new String[cols];
			for(int i = 0; i < cols; i++) {
				colName[i] = rsmd.getColumnName(i+1);
			}
			
			model.setColumnIdentifiers(colName);
			
			// loop through the result set and add course panels
	        while (rs.next()) {
	        	String code = rs.getString("CourseCode");
	            String name = rs.getString("CourseName");
	            
	            String[] row = {code, name};
	            model.addRow(row);
	        }

	        statement.close();
	        connection.close();

		} catch (SQLException e) {
            e.printStackTrace();
		}
	}
	
	/**
	 * Method to add view specific form to the formPanel 
	 * when specific button is pressed.
	 */
    private void viewFormPanel(JPanel panel) {
        // clear existing content
        formPanel.removeAll();
        formPanel.setVisible(true);
        // add new content
        formPanel.add(panel);       
        // repaint
        formPanel.revalidate();
        formPanel.repaint();
    }
}