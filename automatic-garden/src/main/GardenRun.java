package main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import javax.swing.JFrame;

import environment.Environment;
import growingPlan.GrowingPlan;
import growingPlan.PlanGenerator;
import plants.*;
import systemControl.Controller;
import time.Clock;

public class GardenRun {
	
	public static void main(String[] args) throws IOException {
		//由用户设置GrowingPlan
		PlanGenerator pg = new PlanGenerator();
		GrowingPlan growingPlan = new GrowingPlan(pg.hs(), pg.he(), pg.ss(), pg.se(),
					pg.fs(), pg.fe(), pg.ls(), pg.le());
		//GrowingPlan growingPlan = new GrowingPlan(45, 55, 8, 12,
				//9, 15, 21, 23);
		File writename = new File("./src/log/log.txt"); 
        writename.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        DecimalFormat s = new DecimalFormat("00");
        out.write("**************************************************************************\n");
        out.write("Growing Plan Generated!\n");
        out.write("\tHeater Temperature Start at:\t" + s.format(pg.hs()) + "°F\n");
        out.write("\tHeater Temperature End at:\t\t" + s.format(pg.he()) + "°F\n");
        out.write("\tSprinkler Time Start at:\t" + s.format(pg.ss()) + "\n");
        out.write("\tSprinkler Time End at:\t\t" + s.format(pg.se()) + "\n");
        out.write("\tFertilizer Time Start at:\t" + s.format(pg.fs()) + "\n");
        out.write("\tFertilizer Time End at:\t\t" + s.format(pg.fe()) + "\n");
        out.write("\tLight Time Start at:\t" + s.format(pg.ls()) + "\n");
        out.write("\tLight Time End at:\t\t" + s.format(pg.le()) + "\n");
		//建立标尺
		Clock clock = new Clock();
		Environment env = new Environment(clock);
		//建立控制
		Controller controller = new Controller(growingPlan, env, clock);
		//建立8个植物				
		AppleTree appleTree = new AppleTree(env, clock); /* northArea */
		Eucalyptus eucalyptus = new Eucalyptus(env, clock);		
		Orchid orchid = new Orchid(env, clock); 				/* westArea */
		Rose rose = new Rose(env, clock);		
		GatlingPea gatlingPea = new GatlingPea(env, clock); /* eastArea */
		Watermelon watermelon = new Watermelon(env, clock);		
		Corn corn = new Corn(env, clock);						/* southArea */
		Sunflower sunflower = new Sunflower(env, clock);
		
		//建立图像
		JFrame frame = new JFrame("Display Monitor");
		View view = new View(env, clock, appleTree, 
				eucalyptus, orchid, rose, 
				gatlingPea, watermelon, corn, 
				sunflower, controller, growingPlan);
		
        frame.setResizable(false);
        frame.setTitle("Display Monitor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,720);
        frame.setVisible(true);	 
		frame.getContentPane().add(view);  
        view.setVisible(true);
        frame.validate();
            
        while(true){
        	if( clock.minute()%20 == 0 && clock.second() == 0){
        		try {          
                    out.write("**************************************************************************\n");
                    //显示日期和温度
                    out.write("Date: " + clock.year() + "-" + clock.month() + "-" + clock.day()
                    			+"  " + clock.hour() + ":" + clock.minute() + ":" + clock.second() + "\n");
                    out.write("Temperature:  " + env.getTemperature() + " °F\n");
                    //显示天气
                    out.write("Weather:  ");           
                    switch(env.weather()) {
        	    	case 1:
        	    		out.write("Warm\n");
        	    		break;
        	    	case 2:
        	    		out.write("Sunshine\n");
        	    		break;
        	    	case 3:
        	    		out.write("Cloudy  All the Lights be compulsively turned on!\n");
        	    		break;
        	    	case 4:
        	    		out.write("Cold   All the Heaters be compulsively turned on!\n");
        	    		break;
        	    	case 5:
        	    		out.write("Rain All the Sprinklers be compulsively turned off!\n");
        	    		break;
                    }
                    //显示condition
                    out.write("Emergency:\n");
                    switch(env.condition()){
        	    	case 0:
        	    		out.write("\tNormal and trivial! Don't worry!\n");
        	    		break;
        	    	case 1:
        	    		out.write("\tWarning: Insects Attack!!! Turn on the pesticide!\n");
        	    		break;
        	    	case 2:
        	    		out.write("\tWarning: Zombies Attack!!! Turn on the GatlingPea!\n");
        	    		break;
                    }
                    //显示northArea
                    out.write("------------------------------------------------------------\n");
                    out.write("North Area:\n");
                    out.write("\tHeater:\t\t");
                    if(controller.northArea.isHeaterOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc1() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");
                    
                    out.write("\tSprinkler:\t");
                    if(controller.northArea.isSprinklerOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc2() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");                       
                            
                    out.write("\tFertilizer: ");
                    if(controller.northArea.isFertilizerOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc3() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");
                   
                    out.write("\tLight:\t\t");	
                    if(controller.northArea.isLightOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc4() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");
                    out.write("\n");
                    //显示AppleTree            
                    out.write("\tAppleTree:\n");
                    out.write("\t\tHeight:\t");
                    out.write(s.format(appleTree.growth()) + " cm\n");
                    out.write("\t\tRest Life Time:\t");
                    out.write(s.format(appleTree.restLifeTime()) + " Days\n");
                    //显示Eucalyptus
                    out.write("\tEucalyptus:\n");
                    out.write("\t\tHeight:\t");
                    out.write(s.format(eucalyptus.growth()) + " m\n");
                    out.write("\t\tRest Life Time:\t");
                    out.write(s.format(eucalyptus.restLifeTime()) + " Days\n");
                    //显示westArea
                    out.write("------------------------------------------------------------\n");
                    out.write("West Area:\n");
                    out.write("\tHeater:\t\t");
                    if(controller.westArea.isHeaterOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc5() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");
                    
                    out.write("\tSprinkler:\t");
                    if(controller.westArea.isSprinklerOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc6() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");                       
                            
                    out.write("\tFertilizer: ");
                    if(controller.westArea.isFertilizerOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc7() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");
                   
                    out.write("\tLight:\t\t");	
                    if(controller.westArea.isLightOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc8() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");
                    out.write("\n");
                    //显示Orchid             
                    out.write("\tOrchid:\n");
                    out.write("\t\tHeight:\t");
                    out.write(s.format(orchid.growth()) + " cm\n");
                    out.write("\t\tRest Life Time:\t");
                    out.write(s.format(orchid.restLifeTime()) + " Days\n");
                    //显示Rose
                    out.write("\tRose:\n");
                    out.write("\t\tHeight:\t");
                    out.write(s.format(rose.growth()) + " m\n");
                    out.write("\t\tRest Life Time:\t");
                    out.write(s.format(rose.restLifeTime()) + " Days\n");
                    //显示eastArea
                    out.write("------------------------------------------------------------\n");
                    out.write("East Area:\n");
                    out.write("\tHeater:\t\t");
                    if(controller.eastArea.isHeaterOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc9() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");
                    
                    out.write("\tSprinkler:\t");
                    if(controller.eastArea.isSprinklerOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc10() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");                       
                            
                    out.write("\tFertilizer: ");
                    if(controller.eastArea.isFertilizerOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc11() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");
                   
                    out.write("\tLight:\t\t");	
                    if(controller.eastArea.isLightOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc12() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");
                    out.write("\n");
                    //显示GatlingPea             
                    out.write("\tGatlingPea:\n");
                    out.write("\t\tHeight:\t");
                    out.write(s.format(gatlingPea.growth()) + " cm\n");
                    out.write("\t\tRest Life Time:\t");
                    out.write(s.format(gatlingPea.restLifeTime()) + " Days\n");
                    //显示Watermelon
                    out.write("\tWatermelon:\n");
                    out.write("\t\tWeight:\t");
                    out.write(s.format(watermelon.growth()) + " g\n");
                    out.write("\t\tRest Life Time:\t");
                    out.write(s.format(watermelon.restLifeTime()) + " Days\n");
                    //显示southArea
                    out.write("------------------------------------------------------------\n");
                    out.write("South Area:\n");
                    out.write("\tHeater:\t\t");
                    if(controller.southArea.isHeaterOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc13() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");
                    
                    out.write("\tSprinkler:\t");
                    if(controller.southArea.isSprinklerOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc14() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");                       
                            
                    out.write("\tFertilizer: ");
                    if(controller.southArea.isFertilizerOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc15() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");
                   
                    out.write("\tLight:\t\t");	
                    if(controller.southArea.isLightOnOff() == true) 
                    	out.write("On\t");
            	    else
            	    	out.write("Off\t");
                    if(controller.acc16() == false)
                    	out.write("GrowingPlan hold the access!\n");
                    else
                    	out.write("Gardener hold the access!\n");
                    out.write("\n");
                    //显示Corn             
                    out.write("\tCorn:\n");
                    out.write("\t\tHeight:\t");
                    out.write(s.format(corn.growth()) + " cm\n");
                    out.write("\t\tRest Life Time:\t");
                    out.write(s.format(corn.restLifeTime()) + " Days\n");
                    //显示Sunflower
                    out.write("\tSunflower:\n");
                    out.write("\t\tHeight:\t");
                    out.write(s.format(sunflower.growth()) + " cm\n");
                    out.write("\t\tRest Life Time:\t");
                    out.write(s.format(sunflower.restLifeTime()) + " Days\n");           
                    out.flush(); // 把缓存区内容压入文件    
                } 
                catch (Exception e) {  
                    e.printStackTrace();  
                }
        	}
        	       	
        	clock.start();
        	env.EnvironmentRun();
        	controller.setController();
        	controller.setControlPanel();
        	try {
				Thread.sleep((long) 16.7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	frame.repaint();
        }
        //out.close();
	}
}


