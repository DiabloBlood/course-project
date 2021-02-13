package time;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.Thread;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Clock {
	private Display second = new Display(60, 0);
	private Display minute = new Display(60, 0);
	private Display hour = new Display(24, 0);
	private Display day = new Display(30, 1);
	private Display month = new Display(12, 3);
	private Display year = new Display(2206, 2016);
	private int minuteSum = 0;
	private int hourSum = 0;
	private int daySum = 0;
	
	public void start() {
		second.increase();
		if(second.getValue() == 0) {
			minute.increase();
			minuteSum++;
			if(minuteSum == 1440){
				minuteSum = 0;
			}
			if(minute.getValue() == 0){
				hour.increase();
				hourSum++;
				if(hour.getValue() == 0) {
					day.increaseTwo();
					daySum++;
					if(day.getValue() == 1) {
						month.increaseTwo();
						if(month.getValue() == 1){
							year.increase();
						}
					}
				}
			}
		}
		/*System.out.printf("%04d-%02d-%02d-%02d:%02d\n", 
				year.getValue(), month.getValue(),day.getValue(), 
				hour.getValue(), minute.getValue());*/	
	}
	
	public int year() {
		return year.getValue();
	}
	
	public int month() {
		return month.getValue();
	}
	
	public int day() {
		return day.getValue();
	}
	
	public int hour() {
		return hour.getValue();
	}
	
	public int minute() {
		return minute.getValue();
	}
	
	public int second() {
		return second.getValue();
	}
	
	public int minuteSum() {
		return minuteSum;
	}
	
	public int hourSum() {
		return hourSum;
	}
	
	public int daySum(){
		return daySum;
	}
	
	/*public void paintComponent(Graphics g) {
	      
		super.paintComponent(g);   
	    //»­Êý×ÖÊ±ÖÓ
	    g.setColor(Color.blue);
	    DecimalFormat s = new DecimalFormat("00");
	    DecimalFormat y = new DecimalFormat("0000");
	    g.drawString(y.format(year.getValue()) + "-" + s.format(month.getValue()) + "-" 
	    		+ s.format(day.getValue()) + "  " + s.format(hour.getValue()) + ":" +
	    		s.format(minute.getValue()), 10, 20);
	      
	  }*/
}
