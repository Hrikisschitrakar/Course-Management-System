package Forms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
import Messages.CourseDeleteFail;
import Messages.CourseDeleteSuccessful;
import Messages.EmptyFields;

public class DeleteCourseForm extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField codeTextField;

	/**
	 * Create the panel.
	 */
	public DeleteCourseForm() {
		setBackground(Color.BLACK);
		setBounds(0, 0, 628, 273);
		setLayout(null);
		
		JLabel deleteModuleLabel = new JLabel("Delete a course");
		deleteModuleLabel.setForeground(Color.WHITE);
		deleteModuleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		deleteModuleLabel.setBounds(47, 20, 142, 42);
		add(deleteModuleLabel);
		
		JLabel courseCode = new JLabel("Course Code:");
		courseCode.setForeground(Color.WHITE);
		courseCode.setBounds(47, 84, 113, 30);
		add(courseCode);
		
		codeTextField = new JTextField();
		codeTextField.setBounds(156, 84, 413, 30);
		add(codeTextField);
		codeTextField.setColumns(10);
		
		JButton confirmBtn = new JButton("Confirm");
		confirmBtn.setForeground(Color.WHITE);
		confirmBtn.setBounds(356, 175, 95, 42);
		add(confirmBtn);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setForeground(Color.WHITE);
		cancelBtn.setBounds(474, 175, 95, 42);
		add(cancelBtn);
		
		// action listener for Confirm button
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = codeTextField.getText();
				if (code.isEmpty() == true) {
					openEmptyFieldsPage();
				} else {
					Database database = new Database();
	        		try (Connection connection = database.getConnection()) {
	        			if (connection != null) {
	        				// first delete the modules of the course
	        				String sql = "DELETE FROM Module WHERE CourseCode = ?";
	        				try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        	            	statement.setString(1, code);
	        	                int rows = statement.executeUpdate();
	        	                
	        	                if (rows > 0) {
	        	                	// then delete course
	        	                	sql = "DELETE FROM Course WHERE CourseCode = ?";
	        	                	try (PreparedStatement statement1 = connection.prepareStatement(sql)) {
	    	        	            	statement1.setString(1, code);
	    	        	            	rows = statement1.executeUpdate();
	    	        	            	
	    	        	            	if (rows > 0) {
	    	        	            		openSuccessfulPage();
	    	        	            	}
	        	                	}		        	                	
		        	            } else {
		        	            	openFailedPage();
		        	            }  
	        				}        	                	
	        			}
	        		} catch (SQLException e1) {
	                    e1.printStackTrace();
	                }
	        		
	        		setVisible(false);
				}
 			}
		});
		
		// action listener for Cancel button
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}
	
	/**
	 * Direct to the Module Added Successful frame.
	 */
	private void openSuccessfulPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	CourseDeleteSuccessful frame = new CourseDeleteSuccessful();;
					frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	/**
	 * Direct to the Module Added Failed frame.
	 */
	private void openFailedPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	CourseDeleteFail frame = new CourseDeleteFail();
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
