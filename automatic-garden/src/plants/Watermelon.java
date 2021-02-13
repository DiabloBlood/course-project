package plants;

import environment.Environment;
import time.Clock;

public class Watermelon extends Plant {
	
	public Watermelon(Environment env, Clock clock) {
		super(env, clock);
	}

	private int weight;	
	
	@Override
	public void setPlant() {
		//��ʼ�߶�30����,������
		weight = 20;
		//����15��
		lifeTime = 10;
	}

	@Override
	public int growth() {
		if( clock.hourSum()!=0 && clock.hourSum()%240 == 0 ){
			weight = 20;
		}
		else {
			weight = 20 + (clock.hourSum()%240)*30;
		}
		return weight;
	}
}
