package Admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import Database.Database;
import Forms.DeleteInstructorForm;
import Forms.EditInstructor;

public class ManageInstructor extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField searchTextField;
	private JPanel tablePanel = new JPanel();
	private JTable instructorsTable;
	private JPanel formPanel = new JPanel();;

	/**
	 * Create the panel.
	 */
	public ManageInstructor() {
		setBackground(Color.BLACK);
		setBounds(0, 0, 1048, 720);
		setLayout(null);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(Color.BLACK);
		searchPanel.setBounds(0, 20, 1020, 57);
		add(searchPanel);
		searchPanel.setLayout(null);
		
		/* Search Student */
		JLabel searchLabel = new JLabel("Search Instructor");
		searchLabel.setForeground(Color.WHITE);
		searchLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		searchLabel.setBounds(6, 12, 131, 28);
		searchPanel.add(searchLabel);
		
		searchTextField = new JTextField();
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel model = (DefaultTableModel) instructorsTable.getModel();
				TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
				instructorsTable.setRowSorter(sorter);
				// sort table according to texts entered at text field
				sorter.setRowFilter(RowFilter.regexFilter(searchTextField.getText()));
			}
		});
		searchTextField.setBounds(149, 7, 340, 40);
		searchPanel.add(searchTextField);
		searchTextField.setColumns(10);
		
		/* Form Panel */
		formPanel.setBounds(129, 468, 770, 234);
		add(formPanel);
		formPanel.setLayout(null);
		
		/* Edit Instructor Button */
		JButton editInstructorBtn = new JButton("Edit Instructor");
		editInstructorBtn.setForeground(Color.WHITE);
		editInstructorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewFormPanel(new EditInstructor());
			}
		});
		editInstructorBtn.setBounds(723, 9, 130, 36);
		searchPanel.add(editInstructorBtn);
		
		/* Delete Instructor Button */
		JButton deleteInstructorBtn = new JButton("Delete Instructor");
		deleteInstructorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewFormPanel(new DeleteInstructorForm());
			}
		});
		deleteInstructorBtn.setForeground(new Color(70, 130, 180));
		deleteInstructorBtn.setBounds(865, 9, 130, 36);
		searchPanel.add(deleteInstructorBtn);
		
		JLabel headerLabel = new JLabel("Instructors Record");
		headerLabel.setBounds(6, 93, 172, 38);
		add(headerLabel);
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));		
		
		/* Students Table */
		tablePanel.setBounds(0, 143, 1020, 313);
		add(tablePanel);
		tablePanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 0, 1020, 313);
		tablePanel.add(scrollPane);
		
		instructorsTable = new JTable();
		scrollPane.setViewportView(instructorsTable);
		getInstructorInfo();	
		instructorsTable.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		instructorsTable.setRowHeight(40);	
		
		JTableHeader tableHeader = instructorsTable.getTableHeader();
		tableHeader.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		tableHeader.setForeground(new Color(70, 130, 180));
		tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 50));
	}
	
	/**
	 * Fetch course info from the database.  
	 */
	private void getInstructorInfo() {
		Database database = new Database();
	    try (Connection connection = database.getConnection()) {
	    	// select query to fetch course info
			String sql = "SELECT ID, Name, Phone, Email, Course FROM Instructor";
			PreparedStatement statement = connection.prepareStatement(sql);
			// execute the query and get the ResultSet
			ResultSet rs = statement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			DefaultTableModel model = (DefaultTableModel) instructorsTable.getModel();
			
			int cols = rsmd.getColumnCount();
			// array of column names
			String[] colName = new String[cols];
			for(int i = 0; i < cols; i++) {
				colName[i] = rsmd.getColumnName(i+1);
			}
			
			model.setColumnIdentifiers(colName);
			
			// loop through the result set and add course panels
	        while (rs.next()) {
	        	String id = rs.getString("ID");
	            String name = rs.getString("Name");
	            String phone = rs.getString("Phone");
	            String email = rs.getString("Email");
	            String course = rs.getString("Course");
	            
	            String[] row = {id, name, phone, email, course};
	            model.addRow(row);
	        }

	        statement.close();
	        connection.close();

		} catch (SQLException e) {
            e.printStackTrace();
		}
	}
	
	/**
	 * Method to add delete instructor form to the formPanel 
	 * when delete instructor button is pressed.
	 */
    private void viewFormPanel(JPanel panel) {
        // clear existing content
        formPanel.removeAll();
        formPanel.setVisible(true);
        // add new content
        formPanel.add(panel);       
        formPanel.revalidate();
        formPanel.repaint();
    }
}
