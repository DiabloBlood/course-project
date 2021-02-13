package environment;

import time.Clock;

public class Environment {
	private float temperature;
	private int weather;
	private int dayOrNight;
	private int condition;
	private Clock clock;
	
	public Environment(Clock clock) {
		this.clock = clock;
	}
	
	
	public int weather() {
		return weather;
	}
	
	public int dayOrNight() {
		return dayOrNight;
	}
	
	public float getTemperature() {
		return temperature;
	}
	
	public int condition() {
		return condition;
	}
	
	public void dayNightChange(){
		
		if( clock.hour() > 5 && clock.hour() < 19 ) {
			dayOrNight = 1; /* Day time */
		}
		else {
			dayOrNight = 2; /* Night time */
		}
	}
	
	public void changeWeather(){
		int i;
		if( dayOrNight == 1 ) {
			i = (int) (Math.random()*1200.0);
			if(i >= 0 && i <= 300) {
				weather = 1; /* warm */
			}
			else if(i >300 && i <= 600) {
				weather = 2; /* sunshine */
			}
			else if(i > 600 && i <= 800) {
				weather = 3; /* cloudy */
			}
			else if(i > 800 && i <= 1000){
				weather = 4; /* cold */
			}
			else {
				weather = 5; /* rain */
			}
		}
		if( dayOrNight == 2 ) {
			i = (int) (Math.random()*1200.0);
			if(i >= 0 && i <= 400) {
				weather = 1; /* warm */
			}
			else if(i > 400 && i <= 600) {
				weather = 3; /* cloudy */
			}
			else if (i > 600 && i <= 1000){
				weather = 4; /* cold */
			}
			else {
				weather = 5; /* rain */
			}
		}		
	}
	
	public void temperatureRun() {
		if( weather != 4 ) {
			if(clock.minuteSum() >= 0 && clock.minuteSum() < 360){
				temperature = (float) (55.0 - (float)clock.minuteSum()/24.0);
			}
			if(clock.minuteSum() >= 360 && clock.minuteSum() < 840){
				temperature = (float) (10.0 + (float)clock.minuteSum()/12.0);
			}
			if(clock.minuteSum() >= 840 && clock.minuteSum() < 1440){
				temperature = (float) (115.0- (float)clock.minuteSum()/24.0);
			}
		}
		if( weather == 4 ) {
			if(clock.minuteSum() >= 0 && clock.minuteSum() < 360){
				temperature = (float) (35.0 - (float)clock.minuteSum()/24.0);
			}
			if(clock.minuteSum() >= 360 && clock.minuteSum() < 840){
				temperature = (float) (-10.0 + (float)clock.minuteSum()/12.0);
			}
			if(clock.minuteSum() >= 840 && clock.minuteSum() < 1440){
				temperature = (float) (95.0- (float)clock.minuteSum()/24.0);
			}
		}		
	}
	
	public void changeCondition(){
		int i;
		i = (int) (Math.random()*1000.0);
		if(i >= 0 && i <= 600) {
			condition = 0; /* normal */
		}
		else if(i >600 && i <= 800) {
			condition = 1; /* insects attack!!! */
		}
		else {
			condition = 2; /* zombies attack!!! */
		}			
	}
	
	public void EnvironmentRun(){
		dayNightChange();
		temperatureRun();
		if( clock.hour()%8 == 0 && clock.minute() == 0 && clock.second() == 0 ){
			changeWeather();
		}
		if( clock.hour()%4 == 0 && clock.minute() == 0 && clock.second() == 0 ){
			changeCondition();
		}
	}
}







