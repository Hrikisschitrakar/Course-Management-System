package Forms;

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
import Messages.EmptyFields;
import java.awt.Color;

public class AssignModuleForm extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField instructorIDTextField;
	private JTextField moduleIDTextField;

	/**
	 * Create the panel.
	 */
	public AssignModuleForm() {
		setBounds(0, 0, 456, 222);
		setLayout(null);
		
		JPanel formPanel = new JPanel();
		formPanel.setBackground(Color.BLACK);
		formPanel.setBounds(0, 0, 456, 222);
		add(formPanel);
		formPanel.setLayout(null);
		
		JLabel instructorIDLabel = new JLabel("Instructor ID:");
		instructorIDLabel.setForeground(Color.WHITE);
		instructorIDLabel.setBounds(30, 34, 93, 37);
		formPanel.add(instructorIDLabel);
		
		JLabel moduleIDLabel = new JLabel("Module ID:");
		moduleIDLabel.setForeground(Color.WHITE);
		moduleIDLabel.setBounds(30, 83, 93, 37);
		formPanel.add(moduleIDLabel);
		
		instructorIDTextField = new JTextField();
		instructorIDTextField.setBounds(130, 35, 305, 34);
		formPanel.add(instructorIDTextField);
		instructorIDTextField.setColumns(10);
		
		moduleIDTextField = new JTextField();
		moduleIDTextField.setColumns(10);
		moduleIDTextField.setBounds(130, 84, 305, 34);
		formPanel.add(moduleIDTextField);
				
		JButton confirmBtn = new JButton("Assign");
		confirmBtn.setForeground(Color.WHITE);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = instructorIDTextField.getText();
                String module = moduleIDTextField.getText();
                
                if (id.isEmpty() == true || module.isEmpty() == true) {
                	openEmptyFieldsPage();
                } else {
	                Database database = new Database();
	        		try (Connection connection = database.getConnection()) {
	        			if (connection != null) {
	        				// insert into Course
	        				String sql1 = "INSERT INTO Assignment (InstructorID, ModuleCode) VALUES (?, ?)";
	        	            try (PreparedStatement statement1 = connection.prepareStatement(sql1)) {
	        	                // set parameters for the prepared statement
	        	            	statement1.setString(1, id);
	        	            	statement1.setString(2, module);		        	
	
	        	                // execute the query
	        	                int rows1 = statement1.executeUpdate();
	        	                if (rows1 > 0) {
	        	                	System.out.println("Instructor added to the module.");
	        	                } else {
	        	                	System.out.println("Failed to add instructor to the module.");
	        	                }
	        	            }
	        			}
	        		} catch (SQLException e1) {
	                    e1.printStackTrace();
	                }
	                
	        		formPanel.setVisible(false);
                }
			}
		});
		confirmBtn.setBounds(245, 150, 88, 37);
		formPanel.add(confirmBtn);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setForeground(Color.WHITE);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formPanel.setVisible(false);
			}
		});
		cancelBtn.setBounds(347, 150, 88, 37);
		formPanel.add(cancelBtn);
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
