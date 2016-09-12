import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Othello extends JPanel {
	
	JPanel pnlCenter = new JPanel();
	JPanel pnlEast = new JPanel();
	JPanel[][] box = new JPanel[4][4]; 
	JButton btn = new JButton();
	
	public Othello() {
		setPreferredSize(new Dimension(600,400));
		setLayout(new BorderLayout());
		
		//Panel center
		pnlCenter.setLayout(new GridLayout(4,4,2,2));
		pnlCenter.setBackground(Color.RED);
		
		//testa knapp
		btn.addActionListener(new AI());
		pnlEast.add(btn);
		
		pnlEast.setBackground(Color.GREEN);
		pnlEast.setPreferredSize(new Dimension(200,400));
		add(pnlEast, BorderLayout.EAST);
		add(pnlCenter, BorderLayout.CENTER);
		
		//add 16 JPanel into Gridlayout
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				box[i][j] = new JPanel();
				pnlCenter.add(box[i][j]);
			}
		}
	}
	
	public void changeColor(int row, int col) {
		box[row][col].setBackground(Color.BLACK);
	}
	
	private class AI implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == btn) {
				Random rand = new Random();
				int i = rand.nextInt(4);
				int j = rand.nextInt(4);
				box[i][j].setBackground(new Color((int)(Math.random() * 0x1000000)));
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Othello");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
//		frame.setBounds(50, 50, 500, 200);		//placering och storlek på skärmen
		frame.setLocation(50, 50);
//		frame.setSize(500, 200);
//		frame.setResizable(false);
		frame.add(new Othello());
		frame.pack();
		
		
		
		
	}
}
