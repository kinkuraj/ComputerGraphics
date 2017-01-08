package in.bits.assignment;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class ApplicationFrame {

	JFrame mainFrame;
	JPanel buttonPanel;
	JPanel showPanel;
	public ApplicationFrame() {
		this("ApplicationFrame v1.0");
	}

	public ApplicationFrame(String title) {
		createUI(title);
		System.out.println("Construct ApplicationFrame");
	}

	protected void createUI(String title) {
		
		mainFrame = new JFrame(title);
		mainFrame.setSize(1000, 700);
		mainFrame.setLayout(new GridLayout(1, 2));
		mainFrame.setVisible(true);
		showPanel = new JPanel();
		showPanel.setMinimumSize(new Dimension(300, 600));
		showPanel.setLayout(new BoxLayout(showPanel, BoxLayout.Y_AXIS));
		
		JButton draw = new JButton("Draw");
		draw.setActionCommand("Draw");
		draw.addActionListener(new ButtonClickListener());
		showPanel.setMinimumSize(new Dimension(300, 600));
		
		
		buttonPanel = new JPanel();
		buttonPanel.setMinimumSize(new Dimension(250, 600));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(draw);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				buttonPanel,showPanel);
		splitPane.setOneTouchExpandable(true);

		
		mainFrame.add(splitPane);
		mainFrame.setVisible(true);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("Closing Window");
				System.exit(0);
			}
		});
	}
	
	private class ButtonClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent action) {
			String command = action.getActionCommand();
			if (command.equals("Draw")) {
				showPanel.removeAll();
				showPanel.repaint();
				showPanel.revalidate();
				paint(mainFrame.getGraphics());

			}
		}
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
