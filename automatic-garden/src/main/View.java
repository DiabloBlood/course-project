package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import environment.Environment;
import growingPlan.GrowingPlan;
import plants.*;
import systemControl.Controller;
import time.Clock;

public class View extends JPanel {
	//View 可以看到环境和时钟并且为他们绘图
	private Environment env;
	private Clock clock;
	//norhArea
	private AppleTree appleTree;
	private Eucalyptus eucalyptus;
	//westArea
	private Orchid orchid;
	private Rose rose;
	//eastArea
	private GatlingPea gatlingPea;
	private Watermelon watermelon;
	//southArea
	private Corn corn;
	private Sunflower sunflower;
	
	private Controller controller;
	private GrowingPlan growingPlan;
	
	private Font font1 = new Font("Arial", Font.BOLD,22);
	private Font font2 = new Font("Arial", Font.BOLD,18);
	private Font font5 = new Font("Arial", Font.BOLD,16);
	private Font font3 = new Font("Vladimir Script", Font.PLAIN , 48);
	private Font font4 = new Font("Times New Roman", Font.BOLD + Font.ITALIC , 36);
	

	
	
	public View(Environment env, Clock clock, AppleTree appleTree, 
			Eucalyptus eucalyptus, Orchid orchid, Rose rose,
			GatlingPea gatlingPea, Watermelon watermelon, Corn corn, 
			Sunflower sunflower, Controller controller, GrowingPlan growingPlan) {
		this.env = env;
		this.clock = clock;
		this.appleTree = appleTree;
		this.eucalyptus = eucalyptus;
		this.orchid = orchid;
		this.rose = rose;
		this.gatlingPea = gatlingPea;
		this.watermelon = watermelon;
		this.corn = corn;
		this.sunflower = sunflower;
		this.controller = controller;
		this.growingPlan = growingPlan;
	}

	@Override
	public void paintComponent(Graphics g) {	      
		//super.paintComponent(g);
		//设置字体
	    g.setFont(font1);
		//添加背景
		ImageIcon icon = new ImageIcon("./src/images/background.png");  
        Image img = icon.getImage();  
        g.drawImage(img, 200, 0, icon.getImageObserver());
        g.setColor(Color.blue);       
        g.fillRect(0, 0, 4, 720);/*左边框*/
        g.fillRect(0, 0, 900, 4);/*上边框*/
        g.fillRect(0, 688, 900, 4);/*下边框*/
        g.fillRect(890, 0, 4, 720); /*右边框*/       
        g.fillRect(197, 500, 700, 4); /*背景图下边框*/       
        g.fillRect(197, 0, 4, 500); /*背景图左边框*/       
        g.fillRect(0, 90, 200, 4); /*time下横线*/
        g.fillRect(0, 155, 200, 4); /*温度下横线*/
        g.fillRect(0, 220, 200, 4); /*weather下横线*/
        g.fillRect(0, 285, 200, 4); /*condition下横线*/
        g.fillRect(0, 375, 200, 4); /*welcome下横线*/
        //g.fillRect(0, 495, 200, 1); /*northArea下横线*/
        //g.fillRect(0, 570, 200, 1); /*appletree下横线*/
        g.fillRect(197, 500, 2, 220);/*下端分割线1*/
        g.fillRect(430, 500, 2, 220);/*下端分割线2*/
        g.fillRect(663, 500, 2, 220);/*下端分割线3*/
        //加载时钟 
	    DecimalFormat s = new DecimalFormat("00");
	    g.setColor(Color.black); 
	    g.drawString("Time: ", 10, 30);
	    if(env.dayOrNight() == 1){
	    	g.drawString("Daytime ", 75, 30);
	    }
	    if(env.dayOrNight() == 2){
	    	g.drawString("Nighttime ", 75, 30);
	    }
	    g.setColor(Color.red); 
	    g.drawString(s.format(clock.year()) + "-" + s.format(clock.month()) + "-" 
	    		+ s.format(clock.day()), 75, 56);
	    g.drawString(s.format(clock.hour()) + ":" + s.format(clock.minute()) + ":" 
	    		+ s.format(clock.second()), 75, 80);
	    //显示温度
	    DecimalFormat y = new DecimalFormat("00.00");
	    g.setColor(Color.black); 
	    g.drawString("Temperature:", 10, 120);
	    g.setColor(Color.red);
	    g.drawString(y.format(env.getTemperature()) + " °F", 75, 145);
	    //显示weather
	    g.setColor(Color.black); 
	    g.drawString("Weather:", 10, 185);
	    g.setColor(Color.red);
	    switch(env.weather()) {
	    	case 1:
	    		g.drawString("Warm", 75, 210);
	    		break;
	    	case 2:
	    		g.drawString("Sunshine", 75, 210);
	    		break;
	    	case 3:
	    		g.drawString("Cloudy", 75, 210);
	    		break;
	    	case 4:
	    		g.drawString("Cold", 75, 210);
	    		break;
	    	case 5:
	    		g.drawString("Rain", 75, 210);
	    		break;
	    }
	    //显示condition
	    g.setColor(Color.black); 
	    g.drawString("Emergency:", 10, 250);
	    g.setColor(Color.red);
	    g.setFont(font2);
	    switch(env.condition()){
	    	case 0:
	    		g.drawString("Normal and trivial!", 35, 275);
	    		break;
	    	case 1:
	    		g.drawString("Insects Attack!!!", 35, 275);
	    		break;
	    	case 2:
	    		g.drawString("Zombies Attack!!!", 35, 275);
	    		break;
	    }
	    //显示welcome！
	    g.setFont(font3);
	    g.setColor(Color.magenta); 
	    g.drawString("Welcome", 5, 345);
	    g.setFont(font4);
	    g.drawString("!!!", 155, 345);
	    //显示northArea
	    g.setFont(font1);
	    g.setColor(Color.black);
	    g.drawString("North Area:", 10, 405);
	    g.setFont(font2);
	    g.drawString("Heater", 30, 425);
	    g.drawString("Sprinkler", 30, 445);
	    g.drawString("Fertilizer", 30, 465);
	    g.drawString("Light", 30, 485);	    
	    if(controller.northArea.isHeaterOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 150, 425);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 150, 425);
	    }
	    if(controller.northArea.isSprinklerOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 150, 445);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 150, 445);
	    }
	    if(controller.northArea.isFertilizerOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 150, 465);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 150, 465);
	    }
	    if(controller.northArea.isLightOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 150, 485);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 150, 485);
	    }
	    //显示Appletree
	    g.setFont(font1);
	    g.setColor(Color.black);
	    g.drawString("AppleTree:", 10, 520);
	    g.setFont(font2);
	    g.drawString("Height", 20, 540);
	    g.drawString("RestTime", 20, 560);
	    g.setColor(Color.red);
	    g.drawString(s.format(appleTree.growth()), 120, 540);
	    g.drawString("cm", 160, 540);
	    g.drawString(s.format(appleTree.restLifeTime()), 120, 560);
	    g.drawString("Days", 150, 560);
	    //显示Eucalyptus
	    g.setFont(font1);
	    g.setColor(Color.black);
	    g.drawString("Eucalyptus:", 10, 595);
	    g.setFont(font2);
	    g.drawString("Height", 20, 615);
	    g.drawString("RestTime", 20, 635);
	    g.setColor(Color.red);
	    g.drawString(s.format(eucalyptus.growth()), 120, 615);
	    g.drawString("m", 150, 615);
	    g.drawString(s.format(eucalyptus.restLifeTime()), 120, 635);
	    g.drawString("Days", 150, 635);
	    //显示westArea
	    g.setFont(font2);
	    g.setColor(Color.black);
	    g.drawString("West Area:", 205, 525);
	    g.setFont(font5);
	    g.drawString("Heater", 210, 545);
	    g.drawString("Sprinkler", 210, 565);
	    g.drawString("Fertilizer", 210, 585);
	    g.drawString("Light", 210, 605);
	    if(controller.westArea.isHeaterOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 285, 545);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 285, 545);
	    }
	    if(controller.westArea.isSprinklerOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 285, 565);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 285, 565);
	    }
	    if(controller.westArea.isFertilizerOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 285, 585);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 285, 585);
	    }
	    if(controller.westArea.isLightOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 285, 605);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 285, 605);
	    }
	    //显示Orchid 
	    g.setFont(font2);
	    g.setColor(Color.black);
	    g.drawString("Orchid: ", 330, 525);
	    g.setFont(font5);
	    g.drawString("Height", 340, 540);
	    g.drawString("RestTime", 340, 570);
	    g.setColor(Color.red);
	    g.drawString(s.format(orchid.growth()), 350, 555);
	    g.drawString("cm", 380, 555);
	    g.drawString(s.format(orchid.restLifeTime()), 350, 585);
	    g.drawString("Days", 380, 585);
	    //显示Rose
	    g.setFont(font2);
	    g.setColor(Color.black);
	    g.drawString("Rose: ", 330, 605);
	    g.setFont(font5);
	    g.drawString("Height", 340, 620);
	    g.drawString("RestTime", 340, 650);
	    g.setColor(Color.red);
	    g.drawString(s.format(rose.growth()), 350, 635);
	    g.drawString("cm", 380, 635);
	    g.drawString(s.format(rose.restLifeTime()), 350, 665);
	    g.drawString("Days", 380, 665);
	    
	    //显示eastArea
	    g.setFont(font2);
	    g.setColor(Color.black);
	    g.drawString("East Area:", 435, 525);
	    g.setFont(font5);
	    g.drawString("Heater", 440, 545);
	    g.drawString("Sprinkler", 440, 565);
	    g.drawString("Fertilizer", 440, 585);
	    g.drawString("Light", 440, 605);
	    if(controller.eastArea.isHeaterOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 515, 545);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 515, 545);
	    }
	    if(controller.eastArea.isSprinklerOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 515, 565);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 515, 565);
	    }
	    if(controller.eastArea.isFertilizerOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 515, 585);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 515, 585);
	    }
	    if(controller.eastArea.isLightOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 515, 605);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 515, 605);
	    }
	    //显示GatlingPea 
	    g.setFont(font2);
	    g.setColor(Color.black);
	    g.drawString("GatlingPea: ", 560, 525);
	    g.setFont(font5);
	    g.drawString("Height", 570, 540);
	    g.drawString("RestTime", 570, 570);
	    g.setColor(Color.red);
	    g.drawString(s.format(gatlingPea.growth()), 580, 555);
	    g.drawString("cm", 610, 555);
	    g.drawString(s.format(gatlingPea.restLifeTime()), 580, 585);
	    g.drawString("Days", 610, 585);
	    //显示Watermelon
	    g.setFont(font2);
	    g.setColor(Color.black);
	    g.drawString("Watermelon: ", 550, 605);
	    g.setFont(font5);
	    g.drawString("Weight", 570, 620);
	    g.drawString("RestTime", 570, 655);
	    g.setColor(Color.red);
	    g.drawString(s.format(watermelon.growth()), 580, 635);
	    g.drawString("g", 620, 635);
	    g.drawString(s.format(watermelon.restLifeTime()), 580, 670);
	    g.drawString("Days", 610, 670);
	    
	    //显示southArea
	    g.setFont(font2);
	    g.setColor(Color.black);
	    g.drawString("South Area:", 668, 525);
	    g.setFont(font5);
	    g.drawString("Heater", 670, 545);
	    g.drawString("Sprinkler", 670, 565);
	    g.drawString("Fertilizer", 670, 585);
	    g.drawString("Light", 670, 605);
	    if(controller.southArea.isHeaterOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 745, 545);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 745, 545);
	    }
	    if(controller.southArea.isSprinklerOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 745, 565);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 745, 565);
	    }
	    if(controller.southArea.isFertilizerOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 745, 585);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 745, 585);
	    }
	    if(controller.southArea.isLightOnOff() == true) {
	    	g.setColor(Color.red);
	    	g.drawString("On", 745, 605);
	    }
	    else {
	    	g.setColor(Color.red);
	    	g.drawString("Off", 745, 605);
	    }
	    //显示Corn
	    g.setFont(font2);
	    g.setColor(Color.black);
	    g.drawString("Corn: ", 790, 525);
	    g.setFont(font5);
	    g.drawString("Height", 800, 540);
	    g.drawString("RestTime", 800, 570);
	    g.setColor(Color.red);
	    g.drawString(s.format(corn.growth()), 810, 555);
	    g.drawString("cm", 840, 555);
	    g.drawString(s.format(corn.restLifeTime()), 810, 585);
	    g.drawString("Days", 840, 585);
	    //显示sunflower
	    g.setFont(font2);
	    g.setColor(Color.black);
	    g.drawString("Sunflower: ", 780, 605);
	    g.setFont(font5);
	    g.drawString("Height", 800, 620);
	    g.drawString("RestTime", 800, 655);
	    g.setColor(Color.red);
	    g.drawString(s.format(sunflower.growth()), 810, 635);
	    g.drawString("cm", 840, 635);
	    g.drawString(s.format(sunflower.restLifeTime()), 810, 670);
	    g.drawString("Days", 840, 670);
	}	    		
}






