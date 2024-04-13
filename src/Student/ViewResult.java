package Student;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Database.Database;

public class ViewResult extends JPanel {

	private static final long serialVersionUID = 1L;
	Database database = new Database();
	JLabel statusLabel;
	JPanel modulesPanel;
	JPanel resultPanel;
	JPanel resultNotFoundPanel;
	private JTable modulesTable;

	/**
	 * Create the panel.
	 */
	public ViewResult(String email) {
		setBackground(Color.BLACK);
		setBounds(0, 0, 1048, 720);
        setLayout(null);
        
        resultPanel = new JPanel();
        resultPanel.setBackground(Color.DARK_GRAY);
        resultPanel.setBounds(257, 84, 537, 595);
        add(resultPanel);
        resultPanel.setLayout(null);
        resultPanel.setVisible(false);
        
        resultNotFoundPanel = new JPanel();
        resultNotFoundPanel.setBackground(new Color(255, 255, 255));
        resultNotFoundPanel.setBounds(395, 281, 351, 60);
        add(resultNotFoundPanel);
        resultNotFoundPanel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Result Slip is not published yet.");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        lblNewLabel.setBounds(59, 21, 226, 16);
        resultNotFoundPanel.add(lblNewLabel);
        resultNotFoundPanel.setVisible(false);
        
        /* Result Slip Indices */
        
        JLabel idIndex = new JLabel("Student ID");
        idIndex.setBounds(25, 28, 76, 39);
        idIndex.setForeground(Color.WHITE);
        idIndex.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        resultPanel.add(idIndex);
        
        JLabel nameIndex = new JLabel("Name");
        nameIndex.setBounds(25, 79, 76, 39);
        nameIndex.setForeground(Color.WHITE);
        nameIndex.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        resultPanel.add(nameIndex);
        
        JLabel yearIndex = new JLabel("Year");
        yearIndex.setBounds(25, 130, 76, 39);
        yearIndex.setForeground(Color.WHITE);
        yearIndex.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        resultPanel.add(yearIndex);
        
        JLabel courseIndex = new JLabel("Course");
        courseIndex.setBounds(25, 181, 76, 39);
        courseIndex.setForeground(Color.WHITE);
        courseIndex.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        resultPanel.add(courseIndex);
        
        JLabel gradeIndex = new JLabel("Grade");
        gradeIndex.setBounds(25, 508, 76, 39);
        gradeIndex.setForeground(Color.WHITE);
        gradeIndex.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        resultPanel.add(gradeIndex);
        
        JLabel statusIndex = new JLabel("Status");
        statusIndex.setBounds(304, 508, 76, 39);
        statusIndex.setForeground(Color.WHITE);
        statusIndex.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        resultPanel.add(statusIndex);
        
        /* Modules Marks Table */
        modulesPanel = new JPanel();
        modulesPanel.setBounds(25, 228, 488, 268);
        resultPanel.add(modulesPanel);
        modulesPanel.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 488, 268);
		modulesPanel.add(scrollPane);
		
		modulesTable = new JTable();
		scrollPane.setViewportView(modulesTable);
		modulesTable.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		modulesTable.setRowHeight(40);
		
		JTableHeader tableHeader = modulesTable.getTableHeader();
		tableHeader.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		tableHeader.setForeground(new Color(70, 130, 180));
		tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 40));
        
		/* Result Slip Labels */
        JLabel idLabel = new JLabel("");
        idLabel.setBackground(Color.WHITE);
        idLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        idLabel.setBounds(141, 28, 372, 39);
        resultPanel.add(idLabel);
        
        JLabel nameLabel = new JLabel("");
        nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        nameLabel.setBounds(141, 79, 372, 39);
        resultPanel.add(nameLabel);
        
        JLabel yearLabel = new JLabel("");
        yearLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        yearLabel.setBounds(141, 130, 372, 39);
        resultPanel.add(yearLabel);
        
        JLabel courseLabel = new JLabel("");
        courseLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        courseLabel.setBounds(141, 181, 372, 39);
        resultPanel.add(courseLabel);
        
        JLabel gradeLabel = new JLabel("");
        gradeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        gradeLabel.setBounds(123, 508, 121, 39);
        resultPanel.add(gradeLabel);
        
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        statusLabel.setBounds(392, 508, 121, 39);
        resultPanel.add(statusLabel);

        /* Result Slip Button */
        JButton resultSlipBtn = new JButton("Result Slip");
        resultSlipBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try (Connection connection = database.getConnection()) {
        			if (connection != null) {
        				String sql = "SELECT Grade FROM Result WHERE Student_Email = ?";
        				PreparedStatement statement = connection.prepareStatement(sql);
        				statement.setString(1, email);
        				ResultSet rs = statement.executeQuery();
        				
        				if (rs.next()) {
        					resultPanel.setVisible(true);
	        				String sql1 = "SELECT ID, Name, Year, Course FROM Student WHERE Email = ?";
	        				PreparedStatement statement1 = connection.prepareStatement(sql1);
	        				statement1.setString(1, email);
	        				ResultSet rs1 = statement1.executeQuery();
	        				
	        		        if (rs1.next()) {
	                            String id = rs1.getString("ID");
	                            String name = rs1.getString("Name");
	                            String year = rs1.getString("Year");
	                            String course = rs1.getString("Course");
	                            
	                            idLabel.setText(id);
	                            nameLabel.setText(name);
	                            yearLabel.setText(year);
	                            courseLabel.setText(course);
	
	            		        String sql2 = "SELECT Grade FROM Result WHERE StudentID = ?";
	            				PreparedStatement statement2 = connection.prepareStatement(sql2);
	            				statement2.setString(1, id);
	            				ResultSet rs2 = statement2.executeQuery();
	            				
	            		        if (rs2.next()) {
	                                String grade = rs2.getString("Grade");                            
	                                gradeLabel.setText(grade);
	                                
	                                getMarkList(email);
	                                getStatus(grade);
	                            }
	                        }
        				} else {
        					resultNotFoundPanel.setVisible(true);
        				}
        			}
        		} catch (SQLException e1) {
                    e1.printStackTrace();
                }
        	
        	}
        });
        resultSlipBtn.setForeground(Color.WHITE);
        resultSlipBtn.setBounds(33, 31, 140, 44);
        add(resultSlipBtn);  
	}
	
	private void getMarkList(String email) {
		Database database = new Database();
		try (Connection connection = database.getConnection()) {
			if (connection != null) {
				String sql1 = "SELECT ModuleCode, Marks FROM Enrollment WHERE Student_Email = ?";
				PreparedStatement statement1 = connection.prepareStatement(sql1);
				statement1.setString(1, email);
				ResultSet rs1 = statement1.executeQuery();
				ResultSetMetaData rsmd = rs1.getMetaData();
				DefaultTableModel model = (DefaultTableModel) modulesTable.getModel();
				
				int cols = rsmd.getColumnCount();
				// array of column names
				String[] colName = new String[cols];
				for(int i = 0; i < cols; i++) {
					colName[i] = rsmd.getColumnName(i+1);
				}
				
				model.setColumnIdentifiers(colName);
				
				// loop through the result set and add course panels
		        while (rs1.next()) {
		        	String moduleCode = rs1.getString("ModuleCode");
		            String marks = rs1.getString("Marks");
		            
		            
		            String[] row = {moduleCode, marks};
		            model.addRow(row);
		        }

		        statement1.close();
		        connection.close();
			}    				
		} catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	private void getStatus(String grade) {
		if (grade.equals("Fail")) {
			statusLabel.setText("NOT PROMOTED");
		} else {
			statusLabel.setText("PROMOTED");
		}
	}
}
