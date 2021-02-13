package systemControl;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();
	private JPanel jp4 = new JPanel();
	//north Area
	private JButton button1 = new JButton("Heater ON");
	private JButton button2 = new JButton("Sprinkler ON");
	private JButton button3 = new JButton("Fertilizer ON");
	private JButton button4 = new JButton("Light ON");
	//westArea
	private JButton button5 = new JButton("Heater ON");
	private JButton button6 = new JButton("Sprinkler ON");
	private JButton button7 = new JButton("Fertilizer ON");
	private JButton button8 = new JButton("Light ON");
	//eastArea
	private JButton button9 = new JButton("Heater ON");
	private JButton button10 = new JButton("Sprinkler ON");
	private JButton button11 = new JButton("Fertilizer ON");
	private JButton button12 = new JButton("Light ON");
	//southArea
	private JButton button13 = new JButton("Heater ON");
	private JButton button14 = new JButton("Sprinkler ON");
	private JButton button15 = new JButton("Fertilizer ON");
	private JButton button16 = new JButton("Light ON");

	private JLabel label1 = new JLabel("NORTH AREA");
	private JLabel label2 = new JLabel("WEST AREA");
	private JLabel label3 = new JLabel("EAST AREA");
	private JLabel label4 = new JLabel("SOUTH AREA");
	
	public ControlPanel(){
		jp1.setLayout(new FlowLayout());
		jp1.add(button1); jp1.add(button2); jp1.add(button3); jp1.add(button4);	
		
		jp2.setLayout(new FlowLayout());
		jp2.add(button5); jp2.add(button6); jp2.add(button7); jp2.add(button8);	
		
		jp3.setLayout(new FlowLayout());
		jp3.add(button9); jp3.add(button10); jp3.add(button11); jp3.add(button12);
		
		jp4.setLayout(new FlowLayout());
		jp4.add(button13); jp4.add(button14); jp4.add(button15); jp4.add(button16);
		
		this.setLayout(new FlowLayout());
		this.add(label1); this.add(jp1);
		this.add(label2); this.add(jp2); 
		this.add(label3); this.add(jp3); 
		this.add(label4); this.add(jp4); 
				
		this.setTitle("Control Panel");
		this.setSize(480, 500);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setControlPanel();
	}
	public void buttonSwitch(boolean a){
		
	}
	public void setControlPanel(){
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button1.setText("Heater OFF");
			}
		});
	}
	public static void main(String[] args) {
		ControlPanel cp = new ControlPanel();
		/*this.setTitle("GrowingPlanGenerator");
		this.setSize(320, 630);
		this.setLocationRelativeTo(rootPane);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
	}

}
