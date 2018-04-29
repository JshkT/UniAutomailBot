package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import exceptions.MailAlreadyDeliveredException;
import strategies.Automail;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.io.FileNotFoundException;

/**
 * This class simulates the behaviour of AutoMail
 */
public class Simulation {
/*test*/
    /** Constant for the mail generator */

    private static ArrayList<MailItem> MAIL_DELIVERED;
    private static ReportDelivery reportDelivery;
    private static ArrayList<Robot> robots;

    public static void main(String[] args) { //throws IOException {
    	
        MAIL_DELIVERED = new ArrayList<MailItem>();

        /** Used to see whether a seed is initialized or not */
        HashMap<Boolean, Integer> seedMap = new HashMap<>();
        Properties prop = readProperties();			
       
        
        if(prop.getProperty("Seed") != null) {
        	seedMap.put(true, Integer.parseInt(prop.getProperty("Seed")));
        	System.out.println(prop.getProperty("Seed") + "sEED");
        }
        else {
        	seedMap.put(false, 0);
        }
        
        Building.setNumFloors(Integer.parseInt(prop.getProperty("Number_of_Floors")));
        System.out.println(Building.FLOORS);
        ReportDelivery.setPenalty(Double.parseDouble(prop.getProperty("Delivery_Penalty")));
        Clock.setLastDeliveryTime(Integer.parseInt(prop.getProperty("Last_Delivery_Time")));
        System.out.println(Clock.LAST_DELIVERY_TIME);
        
        
        reportDelivery = new ReportDelivery(MAIL_DELIVERED);
        Automail automail = new Automail(reportDelivery, prop);
        robots = new ArrayList<Robot>();
        robots.add(automail.robot1);
        robots.add(automail.robot2);
        int mail_to_create = Integer.parseInt(prop.getProperty("Mail_to_Create"));
        MailGenerator generator = new MailGenerator(mail_to_create, automail.mailPool, robots, seedMap);

        /** Initiate all the mail */
        generator.generateAllMail();
        while(MAIL_DELIVERED.size() != generator.MAIL_TO_CREATE) {
          generator.callNotifyAll();
          generator.addWaveToPool();
            try {
				automail.robot1.step();
				automail.robot2.step();
			} catch (ExcessiveDeliveryException|ItemTooHeavyException e) {
				e.printStackTrace();
				System.out.println("Simulation unable to complete.");
				System.exit(0);
			}
            Clock.Tick();
        }
        printResults();
    }

    public static void printResults(){
        System.out.println("T: "+Clock.Time()+" | Simulation complete!");
        System.out.println("Final Delivery time: "+Clock.Time());
        System.out.printf("Final Score: %.2f%n", reportDelivery.getTotalScore());
    }
    
    public static Properties readProperties()  {
    	Properties simulationProperties= new Properties();
		FileReader inStream = null;
		try {
			inStream = new FileReader("automail.properties");
			simulationProperties.load(inStream);
		} 
		catch(FileNotFoundException e){
			System.out.print("Lul");
		}
		catch(IOException e){
			System.out.print("Lul");
		}
		finally {
			 if (inStream != null) {
				 try {
	                inStream.close();
				 }
				 catch(IOException e) {
					 System.out.print("Lul");
				 }
	            }
		}
		return simulationProperties; 

    }
}
