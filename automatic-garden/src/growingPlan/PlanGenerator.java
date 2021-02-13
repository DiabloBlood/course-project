package growingPlan;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

//: gui/ComboBoxes.java
//Using drop-down lists.
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlanGenerator extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//框架
	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();
	private JPanel jp4 = new JPanel();
	private JPanel jp5 = new JPanel();
	private JPanel jp6 = new JPanel();
	private JPanel jp7 = new JPanel();
	private JPanel jp8 = new JPanel();
	private JPanel jp9 = new JPanel();
	//输入文本框
	private JTextField t1 = new JTextField(15);
	private JTextField t2 = new JTextField(15);
	private JTextField t3 = new JTextField(15);
	private JTextField t4 = new JTextField(15);
	private JTextField t5 = new JTextField(15);
	private JTextField t6 = new JTextField(15);
	private JTextField t7 = new JTextField(15);
	private JTextField t8 = new JTextField(15);
	private JTextField t9 = new JTextField(25);
	//加下拉菜单
	private JComboBox<Integer> c1 = new JComboBox<Integer>();
	private JComboBox<Integer> c2 = new JComboBox<Integer>();
	private JComboBox<String> c3 = new JComboBox<String>();
	private JComboBox<Integer> c4 = new JComboBox<Integer>();
	private JComboBox<String> c5 = new JComboBox<String>();
	private JComboBox<Integer> c6 = new JComboBox<Integer>();
	private JComboBox<Integer> c7 = new JComboBox<Integer>();
	private JComboBox<Integer> c8 = new JComboBox<Integer>();
	//8个提示
	private JLabel label1 = new JLabel("HeaterON   Temperature");
	private JLabel label2 = new JLabel("HeaterOFF  Temperature");
	private JLabel label3 = new JLabel("Sprinkler        Start Time");
	private JLabel label4 = new JLabel("Sprinkler        end   Time");
	private JLabel label5 = new JLabel("Fertilizer       Start Time");
	private JLabel label6 = new JLabel("Fertilizer       End   Time");
	private JLabel label7 = new JLabel("Light            Start Time");
	private JLabel label8 = new JLabel("Light            End   Time");
	//8个选择button
	private JButton button1 = new JButton("Select");
	private JButton button2 = new JButton("Select");
	private JButton button3 = new JButton("Select");
	private JButton button4 = new JButton("Select");
	private JButton button5 = new JButton("Select");
	private JButton button6 = new JButton("Select");
	private JButton button7 = new JButton("Select");
	private JButton button8 = new JButton("Select");
	private JButton button9 = new JButton("GardenRun");
	private int hs;	
	private int he;
	private int ss;	
	private int se;
	private int fs;	
	private int fe;
	private int ls;	
	private int le;
	private boolean gr = false;
	
	public int hs(){
		return hs;
	}
	public int he(){
		return he;
	}
	public int ss(){
		return ss;
	}
	public int se(){
		return se;
	}
	public int fs(){
		return fs;
	}
	public int fe(){
		return fe;
	}
	public int ls(){
		return ls;
	}
	public int le(){
		return le;
	}
	public PlanGenerator() {
		int i;
		DecimalFormat s = new DecimalFormat("00");
		//setHeaterStart();
		for(i = 0; i < 20; i++)
			c1.addItem(i+40);
		for(i = 0; i < 20; i++)
			c2.addItem(i+60);
		//setsprinkler		
		for(i = 0; i < 5; i++)
			c3.addItem(s.format(i+8));
		for(i = 0; i < 9; i++)
			c4.addItem(i+13);
		//fertilizer
		for(i = 0; i < 7; i++)
			c5.addItem(s.format(i+6));
		for(i = 0; i < 6; i++)
			c6.addItem(i+13);
		//setLight
		for(i = 0; i < 3; i++)
			c7.addItem(i+18);
		for(i = 0; i < 3; i++)
			c8.addItem(i+21);
		this.setLayout(new FlowLayout());				
		jp1.setLayout(new FlowLayout()); 
		jp1.add(t1); jp1.add(c1); jp1.add(button1);
		this.add(label1); this.add(jp1); t1.setEditable(false); 
		jp2.setLayout(new FlowLayout()); 
		jp2.add(t2); jp2.add(c2); jp2.add(button2);
		this.add(label2); this.add(jp2); t2.setEditable(false);
		jp3.setLayout(new FlowLayout()); 
		jp3.add(t3); jp3.add(c3); jp3.add(button3);
		this.add(label3); this.add(jp3); t3.setEditable(false);
		jp4.setLayout(new FlowLayout()); 
		jp4.add(t4); jp4.add(c4); jp4.add(button4);
		this.add(label4); this.add(jp4); t4.setEditable(false);
		jp5.setLayout(new FlowLayout()); 
		jp5.add(t5); jp5.add(c5); jp5.add(button5);
		this.add(label5); this.add(jp5); t5.setEditable(false);
		jp6.setLayout(new FlowLayout()); 
		jp6.add(t6); jp6.add(c6); jp6.add(button6);
		this.add(label6); this.add(jp6); t6.setEditable(false);
		jp7.setLayout(new FlowLayout()); 
		jp7.add(t7); jp7.add(c7); jp7.add(button7);
		this.add(label7); this.add(jp7); t7.setEditable(false);
		jp8.setLayout(new FlowLayout()); 
		jp8.add(t8); jp8.add(c8); jp8.add(button8);
		this.add(label8); this.add(jp8); t8.setEditable(false);
		this.add(t9); t9.setEditable(false);
		jp9.add(button9);this.add(jp9);
		
		this.setTitle("GrowingPlanGenerator");
		this.setSize(320, 630);
		this.setLocationRelativeTo(rootPane);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getData();
	}
	
	public void getData() {
		//heater start温度
		c1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t1.setText(""+ ((JComboBox)e.getSource()).getSelectedItem());
			}
		});
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hs = Integer.parseInt(t1.getText());
				t1.setText("Success！");
			}
		});
		//heater end温度
		c2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t2.setText(""+ ((JComboBox)e.getSource()).getSelectedItem());
			}
		});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				he = Integer.parseInt(t2.getText());
				t2.setText("Success！");
			}
		});
		//sprinkler start时间
		c3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t3.setText(""+ ((JComboBox)e.getSource()).getSelectedItem());
			}
		});
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ss = Integer.parseInt(t3.getText());
				t3.setText("Success！");
			}
		});
		//sprinkler end时间
		c4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t4.setText(""+ ((JComboBox)e.getSource()).getSelectedItem());
			}
		});
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				se = Integer.parseInt(t4.getText());
				t4.setText("Success！");
			}
		});
		//fertilizer start时间
		c5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t5.setText(""+ ((JComboBox)e.getSource()).getSelectedItem());
			}
		});
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fs = Integer.parseInt(t5.getText());
				t5.setText("Success！");
			}
		});
		//fertilizer end时间
		c6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t6.setText(""+ ((JComboBox)e.getSource()).getSelectedItem());
			}
		});
		button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fe = Integer.parseInt(t6.getText());
				t6.setText("Success！");
			}
		});
		//light start时间
		c7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t7.setText(""+ ((JComboBox)e.getSource()).getSelectedItem());
			}
		});
		button7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ls = Integer.parseInt(t7.getText());
				t7.setText("Success！");
			}
		});
		//light end时间
		c8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t8.setText(""+ ((JComboBox)e.getSource()).getSelectedItem());
			}
		});
		button8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				le = Integer.parseInt(t8.getText());
				t8.setText("Success！");
			}
		});
		//gardenRun按钮
		button9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( hs == 0 || he == 0 || ss == 0 || se == 0 || fs == 0 || fe == 0 || ls == 0 || le == 0){
					t9.setText("Please set your Growing Plan ! ! ! ! ! ! ! !");
				}
				else {
					t9.setText("Growing Plan setting Success!");
					gr = true;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException o) {
						o.printStackTrace();
					}
				}
			}
		});
		
		while( true ){
			if( hs == 0 || he == 0 || ss == 0 || se == 0 || fs == 0 || fe == 0 || ls == 0 || le == 0
					|| gr == false){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else 
				break;
		}
	}
} 



	
	
	

	
	
	
	
	
	
	
	
	
	
	
	