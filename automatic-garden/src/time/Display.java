package time;


public class Display {
	private int value = 0;
	private int limit = 0;
	
	public Display(int limit, int value)
	{
		this.limit = limit;
		this.value = value;
	}

	public void increase()
	{
		value++;
		if(value == limit) {
			value = 0;
		}
	}
	
	public void increaseTwo(){
		value++;
		if(value == limit +1) {
			value = 1;
		}
	}
	public int getValue()
	{
		return value;
	}
}