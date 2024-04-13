package Forms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Database.Database;
import Messages.CourseDoesNotExist;
import Messages.EmptyFields;

public class ViewModulesForm extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField codeTextField;

	/**
	 * Create the panel.
	 */
	public ViewModulesForm() {
		setBackground(Color.BLACK);
		setBounds(0, 0, 628, 273);
		setLayout(null);
		
		JLabel deleteModuleLabel = new JLabel("View modules of a course");
		deleteModuleLabel.setForeground(Color.WHITE);
		deleteModuleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		deleteModuleLabel.setBounds(47, 20, 224, 42);
		add(deleteModuleLabel);
		
		JLabel courseCode = new JLabel("Course Code:");
		courseCode.setForeground(Color.WHITE);
		courseCode.setBounds(47, 84, 113, 30);
		add(courseCode);
		
		codeTextField = new JTextField();
		codeTextField.setBounds(156, 84, 413, 30);
		add(codeTextField);
		codeTextField.setColumns(10);
		
		JButton viewBtn = new JButton("View");
		viewBtn.setForeground(Color.WHITE);
		viewBtn.setBounds(356, 175, 95, 42);
		add(viewBtn);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setForeground(Color.WHITE);
		cancelBtn.setBounds(474, 175, 95, 42);
		add(cancelBtn);
		
		// action listener for Confirm button
		viewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				String code = codeTextField.getText();
				if (code.isEmpty() == true) {
					openEmptyFieldsPage();
				} else {
					Database database = new Database();
	        		try (Connection connection = database.getConnection()) {
	        			if (connection != null) {
                            // check if the row with the given code exists in the database
                            String sql = "SELECT ModuleCode, ModuleName FROM Module WHERE CourseCode = ?";
                            PreparedStatement statement = connection.prepareStatement(sql);
                            statement.setString(1, code);
                            ResultSet rs = statement.executeQuery();
                                      
                            removeAll();
                            // List model to hold module values
                            DefaultListModel<String> listModel = new DefaultListModel<>();
                            listModel.addElement("Module List of " + code + ":");
                            
	        	    		while (rs.next()) {
	        	    			String moduleCode = rs.getString("ModuleCode");
	        	    			String moduleName = rs.getString("ModuleName");
	        	    			
	        	    			listModel.addElement(moduleCode + " " + moduleName);
	        	    		}	
	                            
	        	    		// create a JList using the DefaultListModel
                            JList<String> moduleList = new JList<>(listModel);
                            moduleList.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                            moduleList.setFixedCellHeight(50);                           

                            JScrollPane scrollPane = new JScrollPane(moduleList);
                            scrollPane.setBounds(0, 0, 628, 273);
                            add(scrollPane);

                            revalidate();
                            repaint();
                            } else {
                            	openFailedPage();
                            }    
	        				
        				} catch (SQLException e1) {
        					e1.printStackTrace();
        				}
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
	 * Direct to the Module Added Failed frame.
	 */
	private void openFailedPage() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	CourseDoesNotExist frame = new CourseDoesNotExist();
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

