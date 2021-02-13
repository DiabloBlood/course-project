package instrument;

public abstract class Instrument {
	protected boolean button = false;

	public boolean isOnOff() {
		return button;
	}
	
	public void buttonOn() {
		button = true;
	}
	
	public void buttonOff() {
		button = false;
	}
	
}
