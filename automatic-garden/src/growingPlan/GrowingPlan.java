package growingPlan;

public class GrowingPlan {
	//heater起始温度
	private int heaterTempStart;
	private int heaterTempEnd;
	//sprinkler开关时间
	private int sprinklerTimeStart;
	private int sprinklerTimeEnd;
	//fertilizer开关时间
	private int fertilizerTimeStart;
	private int fertilizerTimeEnd;
	//Light开关时间
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
	//heater起始温度
	public int heaterTempStart() {
		return heaterTempStart;
	}
	public int heaterTempEnd() {
		return heaterTempEnd;
	}
	//sprinkler开关时间
	public int sprinklerTimeStart() {
		return sprinklerTimeStart;
	}
	public int sprinklerTimeEnd() {
		return sprinklerTimeEnd;
	}
	//fertilizer开关时间
	public int fertilizerTimeStart() {
		return fertilizerTimeStart;
	}
	public int fertilizerTimeEnd() {
		return fertilizerTimeEnd;
	}
	//Light开关时间
	public int lightTimeStart() {
		return lightTimeStart;
	}
	public int lightTimeEnd() {
		return lightTimeEnd;
	}
}










