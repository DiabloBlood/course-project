package location;

import growingPlan.GrowingPlan;
import instrument.*;

public abstract class Location {
	protected Heater heater = new Heater();;
	protected Sprinkler sprinkler = new Sprinkler();
	protected Fertilizer fertilizer = new Fertilizer();
	protected Light light = new Light();
	
	//ѯ������������״̬
	public boolean isHeaterOnOff(){
		return heater.isOnOff();
	}
	
	public boolean isSprinklerOnOff(){
		return sprinkler.isOnOff();
	}
	
	public boolean isFertilizerOnOff(){
		return fertilizer.isOnOff();
	}
	
	public boolean isLightOnOff(){
		return light.isOnOff();
	}
	
	//����heater�Ŀ���
	public void heaterButtonOn() {
		heater.buttonOn();
	}	
	public void heaterButtonOff() {
		heater.buttonOff();
	} 
	//����sprinkler�Ŀ���
	public void sprinklerButtonOn() {
		sprinkler.buttonOn();
	}	
	public void sprinklerButtonOff() {
		sprinkler.buttonOff();
	}
	//����fertilizer�Ŀ���
	public void fertilizerButtonOn() {
		fertilizer.buttonOn();
	}	
	public void fertilizerButtonOff() {
		fertilizer.buttonOff();
	}
	//����light�Ŀ���
	public void lightButtonOn() {
		light.buttonOn();
	}	
	public void lightButtonOff() {
		light.buttonOff();
	}	
}














