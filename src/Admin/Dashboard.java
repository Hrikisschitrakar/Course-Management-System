package Admin;

import Database.Database;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Dashboard extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel infoPanel;

	/**
	 * Create the panel.
	 */
	public Dashboard() {
		setBackground(Color.BLACK);
		setBounds(0, 0, 1048, 720);
        setLayout(null);
        
        /* Date */
     	// get current date
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        // format the date into the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        String formattedDate = dateFormat.format(date);

        JLabel dateLabel = new JLabel(formattedDate);
        dateLabel.setBackground(Color.WHITE);
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Bodoni 72", Font.PLAIN, 20));
        dateLabel.setBounds(6, 6, 186, 29);
        add(dateLabel);
        
        /* Information */
        infoPanel = new JPanel();
        infoPanel.setBackground(Color.BLACK);
        infoPanel.setBounds(0, 47, 1072, 656);
        add(infoPanel);
        infoPanel.setLayout(null);
        
        // Courses Information
        JPanel coursesPanel = new JPanel();
        coursesPanel.setBackground(Color.GRAY);
        coursesPanel.setBounds(6, 26, 295, 419);
        infoPanel.add(coursesPanel);
        coursesPanel.setLayout(null);
        
        JLabel coursesIndex = new JLabel("Total Courses");
        coursesIndex.setForeground(new Color(255, 255, 255));
        coursesIndex.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
        coursesIndex.setBounds(76, 33, 143, 45);
        coursesPanel.add(coursesIndex);
        
        JLabel coursesCount = new JLabel(getCount("Course", "CourseCode"));
        coursesCount.setForeground(new Color(255, 255, 255));
        coursesCount.setFont(new Font("Lucida Grande", Font.PLAIN, 99));
        coursesCount.setBounds(112, 93, 63, 115);
        coursesPanel.add(coursesCount);
        
        // Students Information
        JPanel studentsPanel = new JPanel();
        studentsPanel.setBackground(Color.GRAY);
        studentsPanel.setBounds(370, 26, 295, 419);
        infoPanel.add(studentsPanel);
        studentsPanel.setLayout(null);
        
        JLabel stucentsIndex = new JLabel("Total Students");
        stucentsIndex.setForeground(new Color(255, 255, 255));
        stucentsIndex.setBounds(67, 33, 148, 45);
        studentsPanel.add(stucentsIndex);
        stucentsIndex.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
        
        JLabel studentsCount = new JLabel(getCount("Student", "ID"));
        studentsCount.setBounds(83, 90, 126, 115);
        studentsPanel.add(studentsCount);
        studentsCount.setForeground(Color.WHITE);
        studentsCount.setFont(new Font("Lucida Grande", Font.PLAIN, 99));
        
        // Instructors Information
        JPanel instructorsPanel = new JPanel();
        instructorsPanel.setBackground(Color.GRAY);
        instructorsPanel.setBounds(731, 26, 295, 419);
        infoPanel.add(instructorsPanel);
        instructorsPanel.setLayout(null);
        
        JLabel instructorsIndex = new JLabel("Total Instructors");
        instructorsIndex.setBounds(63, 33, 171, 45);
        instructorsIndex.setForeground(new Color(255, 255, 255));
        instructorsPanel.add(instructorsIndex);
        instructorsIndex.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
        
        JLabel instructorsCount = new JLabel(getCount("Instructor", "ID"));
        instructorsCount.setBounds(83, 90, 126, 115);
        instructorsCount.setForeground(Color.WHITE);
        instructorsCount.setFont(new Font("Lucida Grande", Font.PLAIN, 99));
        instructorsPanel.add(instructorsCount);        
    }
	
	/**
	 * Get Counts from Database
	 */
	private String getCount(String tableName, String column) {
		Database database = new Database();
		int count = 0;
		try (Connection connection = database.getConnection()) {
			if (connection != null) {
				// select query for given table name and column name
	            String sql = "SELECT COUNT(" + column + ") AS count FROM " + tableName;
	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                // execute the query and store the result set
	                ResultSet resultSet = statement.executeQuery();
	                // If there is a result, fetch the count
	                if (resultSet.next()) {
	                    count = resultSet.getInt("count");
	                } else {
                        System.out.println("Failed");
                    }
				}
			}
		} catch (SQLException e) {
            e.printStackTrace();
        }
		return Integer.toString(count);
	}
}
