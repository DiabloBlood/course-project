package growingPlan;

public class GrowingPlan {
	//heater��ʼ�¶�
	private int heaterTempStart;
	private int heaterTempEnd;
	//sprinkler����ʱ��
	private int sprinklerTimeStart;
	private int sprinklerTimeEnd;
	//fertilizer����ʱ��
	private int fertilizerTimeStart;
	private int fertilizerTimeEnd;
	//Light����ʱ��
	private int lightTimeStart;
	private int lightTimeEnd;
	
	public GrowingPlan(int heaterTempStart, int heaterTempEnd, int sprinklerTimeStart, int sprinklerTimeEnd,
			int fertilizerTimeStart, int fertilizerTimeEnd, int lightTimeStart, int lightTimeEnd) {
		this.heaterTempStart = heaterTempStart;
		this.heaterTempEnd = heaterTempEnd;
		this.sprinklerTimeStart = sprinklerTimeStart;
		this.sprinklerTimeEnd = sprinklerTimeEnd;
		this.fertilizerTimeStart = fertilizerTimeStart;
		this.fertilizerTimeEnd = fertilizerTimeEnd;
		this.lightTimeStart = lightTimeStart;
		this.lightTimeEnd = lightTimeEnd;
	}
	//heater��ʼ�¶�
	public int heaterTempStart() {
		return heaterTempStart;
	}
	public int heaterTempEnd() {
		return heaterTempEnd;
	}
	//sprinkler����ʱ��
	public int sprinklerTimeStart() {
		return sprinklerTimeStart;
	}
	public int sprinklerTimeEnd() {
		return sprinklerTimeEnd;
	}
	//fertilizer����ʱ��
	public int fertilizerTimeStart() {
		return fertilizerTimeStart;
	}
	public int fertilizerTimeEnd() {
		return fertilizerTimeEnd;
	}
	//Light����ʱ��
	public int lightTimeStart() {
		return lightTimeStart;
	}
	public int lightTimeEnd() {
		return lightTimeEnd;
	}
}










