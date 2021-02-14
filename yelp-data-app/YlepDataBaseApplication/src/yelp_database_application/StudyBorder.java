package yelp_database_application;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class StudyBorder {
	//all the fonts
		public final static Font TAHOMA_FONT = new Font("Tahoma", Font.BOLD, 20);
		
		public final static int FRAME_WIDTH = 800;
		public final static int FRAME_HEIGHT = 600;
		

		static JFrame frame = new JFrame();
		//set all the labels
		static JLabel labelBusiness;
		
	public static void main(String[] args) {
		
		frame.getContentPane().setBackground(Color.white);
		frame.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		labelBusiness = new JLabel("Business");
		labelBusiness.setFont(TAHOMA_FONT);
		labelBusiness.setBounds(0, 0, 300, 40);
		labelBusiness.setBackground(Color.PINK);
		labelBusiness.setOpaque(true);
		
		
		Border blackline, raisedetched, loweredetched,
	       raisedbevel, loweredbevel, empty;
		blackline = BorderFactory.createLineBorder(Color.black);
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		raisedbevel = BorderFactory.createRaisedBevelBorder();
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		empty = BorderFactory.createEmptyBorder();
		
		labelBusiness.setBorder(BorderFactory.createLoweredBevelBorder());
		frame.getContentPane().add(labelBusiness);

	}

}
