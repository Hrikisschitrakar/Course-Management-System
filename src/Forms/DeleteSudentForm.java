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
import java.awt.Font;

public class DeleteSudentForm extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField instructorIDTextField;

	/**
	 * Create the panel.
	 */
	public DeleteSudentForm() {
		setBounds(0, 0, 456, 222);
		setLayout(null);
		
		JPanel formPanel = new JPanel();
		formPanel.setBackground(Color.BLACK);
		formPanel.setBounds(0, 0, 456, 222);
		add(formPanel);
		formPanel.setLayout(null);
		
		JLabel instructorIDLabel = new JLabel("Student ID");
		instructorIDLabel.setForeground(Color.WHITE);
		instructorIDLabel.setBounds(28, 85, 93, 37);
		formPanel.add(instructorIDLabel);
		
		instructorIDTextField = new JTextField();
		instructorIDTextField.setBounds(133, 86, 305, 34);
		formPanel.add(instructorIDTextField);
		instructorIDTextField.setColumns(10);
		
		JButton confirmBtn = new JButton("Delete");
		confirmBtn.setForeground(Color.WHITE);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = instructorIDTextField.getText();
				
				if (id.isEmpty() == true) {
                	openEmptyFieldsPage();
                } else {
	                Database database = new Database();
	        		try (Connection connection = database.getConnection()) {
	        			if (connection != null) {
	        				// insert into Course
	        				String sql1 = "DELETE FROM Student WHERE ID = ?";
	        	            try (PreparedStatement statement1 = connection.prepareStatement(sql1)) {
	        	                // set parameters for the prepared statement
	        	            	statement1.setString(1, id);		        	
	
	        	                // execute the query
	        	                int rows1 = statement1.executeUpdate();
	        	                if (rows1 > 0) {
	        	                	System.out.println("Student deleted.");
	        	                } else {
	        	                	System.out.println("Failed to delete.");
	        	                }
	        	            }
	        			}
	        		} catch (SQLException e1) {
	                    e1.printStackTrace();
	                }
                }
				formPanel.setVisible(false);
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
		
		JLabel lblNewLabel = new JLabel("Remove Student");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(28, 28, 123, 19);
		formPanel.add(lblNewLabel);
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
