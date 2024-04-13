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
import Messages.EmptyFields;
import Messages.ModuleAddedFail;
import Messages.ModuleAddedSuccessful;

public class AddModuleForm extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField moduleCodeTextField;
    private JTextField moduleTitleTextField;
    private JTextField courseCodeTextField;

	/**
	 * Create the panel.
	 */
	public AddModuleForm() {
		setBackground(Color.BLACK);
		setBounds(0, 0, 628, 273);
		setLayout(null);
		
		JLabel addModuleLabel = new JLabel("Add module to a course");
		addModuleLabel.setForeground(Color.WHITE);
		addModuleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		addModuleLabel.setBounds(27, 6, 204, 42);
		add(addModuleLabel);
		
		JLabel addModuleCourse = new JLabel("Course Code:");
		addModuleCourse.setForeground(Color.WHITE);
		addModuleCourse.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		addModuleCourse.setBounds(27, 55, 100, 42);
		add(addModuleCourse);
		
		courseCodeTextField = new JTextField();
		courseCodeTextField.setColumns(10);
		courseCodeTextField.setBounds(151, 59, 452, 36);
		add(courseCodeTextField);
		
		JLabel addModuleCode = new JLabel("Module Code:");
		addModuleCode.setForeground(Color.WHITE);
		addModuleCode.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		addModuleCode.setBounds(27, 101, 100, 42);
		add(addModuleCode);
		
		moduleCodeTextField = new JTextField();
		moduleCodeTextField.setBounds(151, 105, 452, 36);
		add(moduleCodeTextField);
		moduleCodeTextField.setColumns(10);
		
		JLabel addModuleTitle = new JLabel("Module Title:");
		addModuleTitle.setForeground(Color.WHITE);
		addModuleTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		addModuleTitle.setBounds(27, 149, 100, 42);
		add(addModuleTitle);
		
		moduleTitleTextField = new JTextField();
		moduleTitleTextField.setColumns(10);
		moduleTitleTextField.setBounds(151, 153, 452, 36);
		add(moduleTitleTextField);

		JButton confirmBtn = new JButton("Confirm");
		confirmBtn.setForeground(Color.WHITE);
		confirmBtn.setBounds(390, 211, 95, 42);
		add(confirmBtn);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setForeground(Color.WHITE);
		cancelBtn.setBounds(508, 211, 95, 42);
		add(cancelBtn);
		
		// action listener for Confirm button
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String courseCode = courseCodeTextField.getText();
				String moduleCode = moduleCodeTextField.getText();
				String moduleName = moduleTitleTextField.getText();
				
				if (courseCode.isEmpty() == true || moduleCode.isEmpty() == true || moduleName.isEmpty() == true) {
				    openEmptyFieldsPage();
				} else {				
					Database database = new Database();
					try (Connection connection = database.getConnection()) {
						if (connection != null) {
							String sql =  "INSERT INTO Module (ModuleCode, ModuleName, CourseCode) VALUES (?, ?, ?)" ;
			                    	
							PreparedStatement statement = connection.prepareStatement(sql);
							statement.setString(1, moduleCode);
							statement.setString(2, moduleName);
		                    statement.setString(3, courseCode);    				                    	
		
				            int rows = statement.executeUpdate();
				            if (rows > 0) {
		                        openSuccessfulPage();
		                    } else {
		                    	openFailedPage();
		                    }        
			                connection.close();
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
                	ModuleAddedSuccessful frame = new ModuleAddedSuccessful();
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
                	ModuleAddedFail frame = new ModuleAddedFail();
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

