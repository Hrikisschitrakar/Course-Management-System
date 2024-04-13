package Forms;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EditInstructor extends JPanel {

	private static final long serialVersionUID = 1L;
	JPanel formPanel;

	/**
	 * Create the panel.
	 */
	public EditInstructor() {
		setBackground(Color.BLACK);
		setBounds(0, 0, 770, 234);
		setLayout(null);
		
		formPanel = new JPanel();
		formPanel.setBackground(Color.GRAY);
		formPanel.setBounds(265, 6, 456, 222);
		add(formPanel);
		formPanel.setLayout(null);
		
		JButton assignButton = new JButton("Assign a module");
		assignButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewFormPanel(new AssignModuleForm());				
			}
		});
		assignButton.setForeground(Color.WHITE);
		assignButton.setBounds(42, 54, 173, 50);
		add(assignButton);
		
		JButton removeButton = new JButton("Remove from a module");
		removeButton.setForeground(Color.WHITE);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewFormPanel(new RemoveFromModuleForm());
			}
		});
		removeButton.setBounds(42, 132, 173, 50);
		add(removeButton);							
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
        formPanel.revalidate();
        formPanel.repaint();
    }
}
