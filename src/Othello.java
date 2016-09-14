import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Othello extends JPanel {
	
	private JPanel pnlCenter = new JPanel();
	private JPanel pnlEast = new JPanel();
	private JPanel pnlEastNorth = new JPanel();
	private JPanel pnlEastSouth = new JPanel();
	private JPanel pnlEastCenter = new JPanel();
	private JButton btnReset = new JButton("Reset");
	private JPanel[][] box = new JPanel[4][4];
	private JLabel lblOthello = new JLabel("OTHELLO");
	private JLabel playerScore = new JLabel("X");
	private JLabel comScore = new JLabel("Y");
	private ImageIcon imagePlayer;
	private ImageIcon imageCom;
	private JLabel jblImagePlayer;
	private JLabel jblImageCom;
	private int [][] boxOwner = new int[4][4];
	private final int EMPTY = 0, PLAYER = 1, COM = 2;
	
	public Othello() {
		
		setPreferredSize(new Dimension(600,400));
		setLayout(new BorderLayout());
		lblOthello.setFont(new Font("Serif",Font.PLAIN,40));
		//Panel center
		pnlCenter.setLayout(new GridLayout(4,4,2,2));
		pnlCenter.setBackground(new Color(0x0066cc));
		
		//Panel East
		pnlEast.setLayout(new BorderLayout());
		pnlEast.setBackground(Color.GREEN);
		pnlEast.setPreferredSize(new Dimension(200,400));
		
		//Panel East North. Contaning OTHELLO label
		pnlEastNorth.setPreferredSize(new Dimension(200,100));
//		pnlEastNorth.setBackground(Color.GRAY);
		
		//Panel East South. Containg RESET-button
		pnlEastSouth.setPreferredSize(new Dimension(200,100));
//		pnlEastSouth.setBackground(Color.GRAY);
		
		//Panel East Center. Containing Scores
		pnlEastCenter.setLayout(new GridLayout(2,2));
		imagePlayer = new ImageIcon("src/BlackCircle.png");
		imageCom = new ImageIcon("src/WhiteCircle.png");
		jblImagePlayer = new JLabel(imagePlayer);
		jblImageCom = new JLabel(imageCom);
		pnlEastCenter.add(jblImagePlayer);
		pnlEastCenter.add(playerScore);
		pnlEastCenter.add(jblImageCom);
		pnlEastCenter.add(comScore);
		pnlEastSouth.add(btnReset);
		
		//adding to Panels
		pnlEast.add(pnlEastNorth, BorderLayout.NORTH);
		pnlEast.add(pnlEastSouth, BorderLayout.SOUTH);
		pnlEast.add(pnlEastCenter, BorderLayout.CENTER);
		pnlEastNorth.add(lblOthello);
		//add 16 JPanel into Gridlayout
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				box[i][j] = new JPanel();
				pnlCenter.add(box[i][j]);
			}
		}
		add(pnlEast, BorderLayout.EAST);
		add(pnlCenter, BorderLayout.CENTER);
		
		btnReset.addActionListener(new AI());
	}
	
	//paint out window with current score
	public void render() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(boxOwner[i][j] == 0) {
					box[i][j].setBackground(new Color(00,99,33));
				} else if(boxOwner[i][j] == 1) {
					box[i][j].setBackground(Color.BLACK);
				} else {
					box[i][j].setBackground(Color.WHITE);
				}
			}
		}
	}
	public void reset() {
		boxOwner = new int[4][4];
		boxOwner[1][1] = PLAYER;
		boxOwner[2][2] = PLAYER;
		boxOwner[1][2] = COM;
		boxOwner[2][1] = COM;
		render();
		
	}
	
	public void changeColor(int row, int col) {
		box[row][col].setBackground(Color.BLACK);
	}
	
	private class AI implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == btnReset) {
				reset();
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
