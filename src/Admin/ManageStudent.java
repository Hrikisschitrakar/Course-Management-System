package Admin;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import Database.Database;
import Forms.DeleteSudentForm;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManageStudent extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField searchTextField;
	private final JPanel tablePanel = new JPanel();
	private JTable studentsTable;
	private JPanel formPanel = new JPanel();;

	/**
	 * Create the panel.
	 */
	public ManageStudent() {
		setBackground(Color.BLACK);
		setBounds(0, 0, 1048, 720);
		setLayout(null);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(Color.BLACK);
		searchPanel.setBounds(0, 20, 1020, 57);
		add(searchPanel);
		searchPanel.setLayout(null);
		
		/* Search Student */
		JLabel searchLabel = new JLabel("Search Student");
		searchLabel.setForeground(Color.WHITE);
		searchLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		searchLabel.setBounds(6, 12, 121, 28);
		searchPanel.add(searchLabel);
		
		searchTextField = new JTextField();
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel model = (DefaultTableModel) studentsTable.getModel();
				TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
				studentsTable.setRowSorter(sorter);
				// sort table according to texts entered at text field
				sorter.setRowFilter(RowFilter.regexFilter(searchTextField.getText()));
			}
		});
		searchTextField.setBounds(137, 6, 340, 40);
		searchPanel.add(searchTextField);
		searchTextField.setColumns(10);
		
		/* Form Panel */
		formPanel.setBounds(129, 468, 770, 234);
		add(formPanel);
		formPanel.setLayout(null);
		
		/* Delete Student */
		JButton deleteStudentBtn = new JButton("Delete Student");
		deleteStudentBtn.setForeground(Color.WHITE);
		deleteStudentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewFormPanel(new DeleteSudentForm());
			}
		});
		deleteStudentBtn.setBounds(723, 9, 130, 36);
		searchPanel.add(deleteStudentBtn);
		
		/* View Progress */
		JButton viewProgressBtn = new JButton("View Progress");
		viewProgressBtn.setForeground(new Color(70, 130, 180));
		viewProgressBtn.setBounds(865, 9, 130, 36);
		searchPanel.add(viewProgressBtn);
		
		JLabel headerLabel = new JLabel("Students Record");
		headerLabel.setBounds(6, 93, 163, 38);
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
		
		studentsTable = new JTable();
		scrollPane.setViewportView(studentsTable);
		getStudentInfo();	
		studentsTable.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		studentsTable.setRowHeight(40);
		
		JTableHeader tableHeader = studentsTable.getTableHeader();
		tableHeader.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		tableHeader.setForeground(new Color(70, 130, 180));
		tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 50));		
	}
	
	/**
	 * Fetch course info from the database.  
	 */
	private void getStudentInfo() {
		Database database = new Database();
	    try (Connection connection = database.getConnection()) {
	    	// select query to fetch course info
			String sql = "SELECT ID, Name, Phone, Email, Course FROM Student";
			PreparedStatement statement = connection.prepareStatement(sql);
			// execute the query and get the ResultSet
			ResultSet rs = statement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			DefaultTableModel model = (DefaultTableModel) studentsTable.getModel();
			
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
	
	/*
	 * Method to add delete student form to the formPanel 
	 * when delete student button is pressed.
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
