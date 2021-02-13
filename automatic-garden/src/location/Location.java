package location;

import growingPlan.GrowingPlan;
import instrument.*;

public abstract class Location {
	protected Heater heater = new Heater();;
	protected Sprinkler sprinkler = new Sprinkler();
	protected Fertilizer fertilizer = new Fertilizer();
	protected Light light = new Light();
	
	//询问四种仪器的状态
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
	
	//控制heater的开关
	public void heaterButtonOn() {
		heater.buttonOn();
	}	
	public void heaterButtonOff() {
		heater.buttonOff();
	} 
	//控制sprinkler的开关
	public void sprinklerButtonOn() {
		sprinkler.buttonOn();
	}	
	public void sprinklerButtonOff() {
		sprinkler.buttonOff();
	}
	//控制fertilizer的开关
	public void fertilizerButtonOn() {
		fertilizer.buttonOn();
	}	
	public void fertilizerButtonOff() {
		fertilizer.buttonOff();
	}
	//控制light的开关
	public void lightButtonOn() {
		light.buttonOn();
	}	
	public void lightButtonOff() {
		light.buttonOff();
	}	
}














