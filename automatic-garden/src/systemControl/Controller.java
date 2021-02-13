package systemControl;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import environment.Environment;
import growingPlan.GrowingPlan;
import location.*;
import time.Clock;

public class Controller extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NorthArea northArea = new NorthArea();
	public WestArea westArea = new WestArea();
	public EastArea eastArea = new EastArea ();
	public SouthArea southArea = new SouthArea();
	private GrowingPlan growingPlan;	
	private Environment env;
	private Clock clock;
	
	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();
	private JPanel jp4 = new JPanel();
	//northArea
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
	private JButton button17 = new JButton("Growing Plan Access Recovery");

	private JLabel label1 = new JLabel("NORTH AREA");
	private JLabel label2 = new JLabel("WEST AREA");
	private JLabel label3 = new JLabel("EAST AREA");
	private JLabel label4 = new JLabel("SOUTH AREA");
	private JTextField t = new JTextField(35);
	//b代表button和growing plan control设置开关的控制权在谁那里
	private boolean acc1 = false;
	private boolean acc2 = false;
	private boolean acc3 = false;
	private boolean acc4 = false;
	
	private boolean acc5 = false;
	private boolean acc6 = false;
	private boolean acc7 = false;
	private boolean acc8 = false;
	
	private boolean acc9 = false;
	private boolean acc10 = false;	
	private boolean acc11 = false;
	private boolean acc12 = false;
	
	private boolean acc13 = false;
	private boolean acc14 = false;
	private boolean acc15 = false;
	private boolean acc16 = false;
	
	public Controller(GrowingPlan growingPlan, Environment env, Clock clock) {
		this.growingPlan = growingPlan;
		this.env = env;
		this.clock = clock;
		
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
		this.add(t); t.setEditable(false); this.add(button17);
		
		this.setTitle("Control Panel");
		this.setSize(480, 370);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		env.EnvironmentRun(); /*先知道初始化的天气*/
		setController();/*根据天气初始化各个instrument*/
		setButtonTitle();
		setControlPanel();
	}
	
	public void setButtonTitle() {
		//northArea
		if(northArea.isHeaterOnOff() == true){
			button1.setText("Heater ON");
		}
		else 
			button1.setText("Heater OFF");
		if(northArea.isSprinklerOnOff() == true){
			button2.setText("Sprinkler ON");
		}
		else 
			button2.setText("Sprinkler OFF");
		if(northArea.isFertilizerOnOff() == true){
			button3.setText("Fertilizer ON");
		}
		else 
			button3.setText("Fertilizer OFF");
		if(northArea.isLightOnOff() == true){
			button4.setText("Light ON");
		}
		else 
			button4.setText("Light OFF");
		//westArea		
		if(westArea.isHeaterOnOff() == true){
			button5.setText("Heater ON");
		}
		else 
			button5.setText("Heater OFF");
		if(westArea.isSprinklerOnOff() == true){
			button6.setText("Sprinkler ON");
		}
		else 
			button6.setText("Sprinkler OFF");
		if(westArea.isFertilizerOnOff() == true){
			button7.setText("Fertilizer ON");
		}
		else 
			button7.setText("Fertilizer OFF");
		if(westArea.isLightOnOff() == true){
			button8.setText("Light ON");
		}
		else 
			button8.setText("Light OFF");		
		//eastArea
		if(eastArea.isHeaterOnOff() == true){
			button9.setText("Heater ON");
		}
		else 
			button9.setText("Heater OFF");
		if(eastArea.isSprinklerOnOff() == true){
			button10.setText("Sprinkler ON");
		}
		else 
			button10.setText("Sprinkler OFF");
		if(eastArea.isFertilizerOnOff() == true){
			button11.setText("Fertilizer ON");
		}
		else 
			button11.setText("Fertilizer OFF");
		if(eastArea.isLightOnOff() == true){
			button12.setText("Light ON");
		}
		else 
			button12.setText("Light OFF");
		//southArea
		if(southArea.isHeaterOnOff() == true){
			button13.setText("Heater ON");
		}
		else 
			button13.setText("Heater OFF");
		if(southArea.isSprinklerOnOff() == true){
			button14.setText("Sprinkler ON");
		}
		else 
			button14.setText("Sprinkler OFF");
		if(southArea.isFertilizerOnOff() == true){
			button15.setText("Fertilizer ON");
		}
		else 
			button15.setText("Fertilizer OFF");
		if(southArea.isLightOnOff() == true){
			button16.setText("Light ON");
		}
		else 
			button16.setText("Light OFF");		
	}
	
	public void buttonSwitch(boolean a){
		if(a == true) {
			a = false;
		}
		else 
			a = true;
	}
	
	public void setControlPanel(){
		//北部heater
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc1 = true;
				t.setText("NORTH AREA HEATER GET THE CONTROL ACCESS!");
				if(northArea.isHeaterOnOff() == true){
					northArea.heaterButtonOff();
					button1.setText("Heater OFF");					
				}
				else {
					northArea.heaterButtonOn();
					button1.setText("Heater ON");
				}
			}
		});
		//北部sprinkler
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc2 = true;
				t.setText("NORTH AREA SPRINKLER GET THE CONTROL ACCESS!");
				if(northArea.isSprinklerOnOff() == true){
					northArea.sprinklerButtonOff();
					button2.setText("Sprinkler OFF");
				}
				else {
					northArea.sprinklerButtonOn();
					button2.setText("Sprinkler ON");
				}
			}
		});
		//北部fertilizer
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc3 = true;
				t.setText("NORTH AREA FERTILIZER GET THE CONTROL ACCESS!");
				if(northArea.isFertilizerOnOff() == true){
					northArea.fertilizerButtonOff();
					button3.setText("Fertilizer OFF");
				}
				else {
					northArea.fertilizerButtonOn();
					button3.setText("Fertilizer ON");
				}
			}
		});
		//北部Light
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc4 = true;
				t.setText("NORTH AREA LIGHT GET THE CONTROL ACCESS!");
				if(northArea.isLightOnOff() == true){
					northArea.lightButtonOff();
					button4.setText("Light OFF");
				}
				else {
					northArea.lightButtonOn();
					button4.setText("Light ON");
				}
			}
		});		
		//西部heater
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc5 = true;
				t.setText("WEST AREA HEATER GET THE CONTROL ACCESS!");
				if(westArea.isHeaterOnOff() == true){
					westArea.heaterButtonOff();
					button5.setText("Heater OFF");					
				}
				else {
					westArea.heaterButtonOn();
					button5.setText("Heater ON");
				}
			}
		});
		//西部sprinkler
		button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc6 = true;
				t.setText("WEST AREA SPRINKLER GET THE CONTROL ACCESS!");
				if(westArea.isSprinklerOnOff() == true){
					westArea.sprinklerButtonOff();
					button6.setText("Sprinkler OFF");
				}
				else {
					westArea.sprinklerButtonOn();
					button6.setText("Sprinkler ON");
				}
			}
		});
		//西部fertilizer
		button7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc7 = true;
				t.setText("WEST AREA FERTILIZER GET THE CONTROL ACCESS!");
				if(westArea.isFertilizerOnOff() == true){
					westArea.fertilizerButtonOff();
					button7.setText("Fertilizer OFF");
				}
				else {
					westArea.fertilizerButtonOn();
					button7.setText("Fertilizer ON");
				}
			}
		});
		//西部Light
		button8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc8 = true;
				t.setText("WEST AREA LIGHT GET THE CONTROL ACCESS!");
				if(westArea.isLightOnOff() == true){
					westArea.lightButtonOff();
					button8.setText("Light OFF");
				}
				else {
					westArea.lightButtonOn();
					button8.setText("Light ON");
				}
			}
		});
		
		//东部heater
		button9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc9 = true;
				t.setText("EAST AREA HEATER GET THE CONTROL ACCESS!");
				if(eastArea.isHeaterOnOff() == true){
					eastArea.heaterButtonOff();
					button9.setText("Heater OFF");					
				}
				else {
					eastArea.heaterButtonOn();
					button9.setText("Heater ON");
				}
			}
		});
		//东部sprinkler
		button10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc10 = true;
				t.setText("EAST AREA SPRINKLER GET THE CONTROL ACCESS!");
				if(eastArea.isSprinklerOnOff() == true){
					eastArea.sprinklerButtonOff();
					button10.setText("Sprinkler OFF");
				}
				else {
					eastArea.sprinklerButtonOn();
					button10.setText("Sprinkler ON");
				}
			}
		});
		//东部fertilizer
		button11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc11 = true;
				t.setText("EAST AREA FERTILIZER GET THE CONTROL ACCESS!");
				if(eastArea.isFertilizerOnOff() == true){
					eastArea.fertilizerButtonOff();
					button11.setText("Fertilizer OFF");
				}
				else {
					eastArea.fertilizerButtonOn();
					button11.setText("Fertilizer ON");
				}
			}
		});
		//东部Light
		button12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc12 = true;
				t.setText("EAST AREA LIGHT GET THE CONTROL ACCESS!");
				if(eastArea.isLightOnOff() == true){
					eastArea.lightButtonOff();
					button12.setText("Light OFF");
				}
				else {
					eastArea.lightButtonOn();
					button12.setText("Light ON");
				}
			}
		});
		//南部heater
		button13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc13 = true;
				t.setText("SOUTH AREA HEATER GET THE CONTROL ACCESS!");
				if(southArea.isHeaterOnOff() == true){
					southArea.heaterButtonOff();
					button13.setText("Heater OFF");					
				}
				else {
					southArea.heaterButtonOn();
					button13.setText("Heater ON");
				}
			}
		});
		//南部sprinkler
		button14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc14 = true;
				t.setText("SOUTH AREA SPRINKLER GET THE CONTROL ACCESS!");
				if(southArea.isSprinklerOnOff() == true){
					southArea.sprinklerButtonOff();
					button14.setText("Sprinkler OFF");
				}
				else {
					southArea.sprinklerButtonOn();
					button14.setText("Sprinkler ON");
				}
			}
		});
		//南部fertilizer
		button15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc15 = true;
				t.setText("SOUTH AREA FERTILIZER GET THE CONTROL ACCESS!");
				if(southArea.isFertilizerOnOff() == true){
					southArea.fertilizerButtonOff();
					button15.setText("Fertilizer OFF");
				}
				else {
					southArea.fertilizerButtonOn();
					button15.setText("Fertilizer ON");
				}
			}
		});
		//东部Light
		button16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc16 = true;
				t.setText("SOUTH AREA LIGHT GET THE CONTROL ACCESS!");
				if(southArea.isLightOnOff() == true){
					southArea.lightButtonOff();
					button16.setText("Light OFF");
				}
				else {
					southArea.lightButtonOn();
					button16.setText("Light ON");
				}
			}
		});
		//恢复growingPlan
		button17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acc1 = false; acc2 = false; acc3 = false; acc4 = false;
				acc5 = false; acc6 = false; acc7 = false; acc8 = false;
				acc9 = false; acc10 = false; acc11 = false; acc12 = false;
				acc13 = false; acc14 = false; acc15 = false; acc16 = false;
				t.setText("NOW GROWING PALN GET THE CONTROL ACCESS");
				setController();
				setButtonTitle();
			}
		});
	}
	
	//heater起始温度
	public int heaterTempStart() {
		return growingPlan.heaterTempStart();
	}
	public int heaterTempEnd() {
		return growingPlan.heaterTempEnd();
	}
	//sprinkler开关时间
	public int sprinklerTimeStart() {
		return growingPlan.sprinklerTimeStart();
	}
	public int sprinklerTimeEnd() {
		return growingPlan.sprinklerTimeEnd();
	}
	//fertilizer开关时间
	public int fertilizerTimeStart() {
		return growingPlan.fertilizerTimeStart();
	}
	public int fertilizerTimeEnd() {
		return growingPlan.fertilizerTimeEnd();
	}
	//Light开关时间
	public int lightTimeStart() {
		return growingPlan.lightTimeStart();
	}
	public int lightTimeEnd() {
		return growingPlan.lightTimeEnd();
	}
	
	//setHeater
	public void setHeater() {
		//check if weather is cold
		if( env.weather() == 4 ) {
			if(acc1 == false)
				northArea.heaterButtonOn();
			if(acc5 == false)
				westArea.heaterButtonOn();
			if(acc9 == false)
				eastArea.heaterButtonOn();
			if(acc13 == false)
				southArea.heaterButtonOn();
		}
		else {
			//set northArea acc为true代表权限在控制板那里，为false代表在growingPlan这里
			if((env.getTemperature() <= heaterTempStart()) && acc1 == false) {
				northArea.heaterButtonOn();
			}
			if((env.getTemperature() >= heaterTempEnd()) && acc1 == false) {
				northArea.heaterButtonOff();
			}
			//set westArea
			if((env.getTemperature() <= heaterTempStart()) && acc5 == false ) {
				westArea.heaterButtonOn();
			}
			if((env.getTemperature() >= heaterTempEnd()) && acc5 == false ) {
				westArea.heaterButtonOff();
			}
			//set eastArea
			if((env.getTemperature() <= heaterTempStart()) && acc9 == false ) {
				eastArea.heaterButtonOn();
			}
			if((env.getTemperature() >= heaterTempEnd()) && acc9 == false ) {
				eastArea.heaterButtonOff();
			}
			//set southArea
			if((env.getTemperature() <= heaterTempStart()) && acc13 == false ) {
				southArea.heaterButtonOn();
			}
			if((env.getTemperature() >= heaterTempEnd()) && acc13 == false) {
				southArea.heaterButtonOff();
			}
		}	
	}
		
	//set Sprinkler
	public void setSprinkler() {
		//check weather if was rain
		if( env.weather() == 5 ) {
			if( acc2 == false )
				northArea.sprinklerButtonOff();
			if( acc6 == false )
				westArea.sprinklerButtonOff();
			if( acc10 == false )
				eastArea.sprinklerButtonOff();
			if( acc14 == false )
				southArea.sprinklerButtonOff();			
		}
		else {
			//set northArea
			if( acc2 == false ) {
				if(clock.hour()>= sprinklerTimeStart() && clock.hour() <= sprinklerTimeEnd()) {
					northArea.sprinklerButtonOn();
				}
				else
					northArea.sprinklerButtonOff();
			}			
			//set westArea
			if( acc6 == false ) {
				if(clock.hour()>= sprinklerTimeStart() && clock.hour() <= sprinklerTimeEnd()) {
					westArea.sprinklerButtonOn();
				}
				else
					westArea.sprinklerButtonOff();
			}			
			//set eastArea
			if( acc10 == false ) {
				if(clock.hour()>= sprinklerTimeStart() && clock.hour() <= sprinklerTimeEnd()) {
					eastArea.sprinklerButtonOn();
				}
				else
					eastArea.sprinklerButtonOff();
			}			
			//set southArea
			if( acc14 = false ) {
				if(clock.hour()>= sprinklerTimeStart() && clock.hour() <= sprinklerTimeEnd()) {
					southArea.sprinklerButtonOn();
				}
				else
					southArea.sprinklerButtonOff();
			}			
		}
	}	
	//set fertilizer
	public void setFertilizer(){
		//set northArea
		if( acc3 == false ) {
			if(clock.hour()>= fertilizerTimeStart() && clock.hour() <= fertilizerTimeEnd()) {
				northArea.fertilizerButtonOn();
			}
			else
				northArea.fertilizerButtonOff();
		}		
		//set westArea
		if( acc7 == false ) {
			if(clock.hour()>= fertilizerTimeStart() && clock.hour() <= fertilizerTimeEnd()) {
				westArea.fertilizerButtonOn();
			}
			else
				westArea.fertilizerButtonOff();
		}		
		//set eastArea
		if( acc11 == false) {
			if(clock.hour()>= fertilizerTimeStart() && clock.hour() <= fertilizerTimeEnd()) {
				eastArea.fertilizerButtonOn();
			}
			else
				eastArea.fertilizerButtonOff();
		}		
		//set southArea
		if( acc15 == false) {
			if(clock.hour()>= fertilizerTimeStart() && clock.hour() <= fertilizerTimeEnd()) {
				southArea.fertilizerButtonOn();
			}
			else
				southArea.fertilizerButtonOff();
		}		
	}
	//set light
	public void setLight() {
		if( env.weather() == 3 ) {
			if( acc4 == false )
				northArea.lightButtonOn();
			if( acc8 == false )
				westArea.lightButtonOn();
			if( acc12 == false )
				eastArea.lightButtonOn();
			if( acc16 == false )
				southArea.lightButtonOn();
		}
		else {
			//set northArea
			if( acc4 == false) {
				if(clock.hour()>= lightTimeStart() && clock.hour() <= lightTimeEnd()) {
					northArea.lightButtonOn();
				}
				else
					northArea.lightButtonOff();
			}			
			//set westArea
			if( acc8 == false) {
				if(clock.hour()>= lightTimeStart() && clock.hour() <= lightTimeEnd()) {
					westArea.lightButtonOn();
				}
				else
					westArea.lightButtonOff();
			}			
			//set eastArea
			if( acc12 == false) {
				if(clock.hour()>= lightTimeStart() && clock.hour() <= lightTimeEnd()) {
					eastArea.lightButtonOn();
				}
				else
					eastArea.lightButtonOff();
			}			
			//set southArea
			if( acc16 == false) {
				if(clock.hour()>= lightTimeStart() && clock.hour() <= lightTimeEnd()) {
					southArea.lightButtonOn();
				}
				else
					southArea.lightButtonOff();	
			}					
		}
	}
	
	public void setController(){
		setHeater();
		setSprinkler();
		setFertilizer();
		setLight();
	}
	
	public boolean acc1(){
		return acc1;
	}
	public boolean acc2(){
		return acc2;
	}
	public boolean acc3(){
		return acc3;
	}
	public boolean acc4(){
		return acc4;
	}
	public boolean acc5(){
		return acc5;
	}
	public boolean acc6(){
		return acc6;
	}
	public boolean acc7(){
		return acc7;
	}
	public boolean acc8(){
		return acc8;
	}
	public boolean acc9(){
		return acc9;
	}
	public boolean acc10(){
		return acc10;
	}
	public boolean acc11(){
		return acc11;
	}
	public boolean acc12(){
		return acc12;
	}
	public boolean acc13(){
		return acc13;
	}
	public boolean acc14(){
		return acc14;
	}
	public boolean acc15(){
		return acc15;
	}
	public boolean acc16(){
		return acc16;
	}
}










