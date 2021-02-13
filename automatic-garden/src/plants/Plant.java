package plants;

import environment.Environment;
import time.Clock;

public abstract class Plant {
	
	protected int height;
	protected int lifeTime;
	protected Environment env;
	protected Clock clock;
		
	public Plant(Environment env, Clock clock) {
		this.env = env;
		this.clock = clock;
		setPlant();
	}
	
	public void setPlant() {
		
	}
	
	public int lifeTime() {	
		return lifeTime;
	}
	
	public int height() {
		return height;
	}
	
	public int growth() {
		return 0;
	}
	//(lifeTime - clock.daySum() % lifeTime)
	public int restLifeTime() {
		return (lifeTime - clock.daySum() % lifeTime);
	}
}






