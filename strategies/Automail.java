package strategies;

import automail.IMailDelivery;
import automail.Robot;
import java.util.*;

public class Automail {
	      
    public Robot robot1, robot2;
    public IMailPool mailPool;
    
    public Automail(IMailDelivery delivery, Properties prop) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	//// Swap the next line for the one below
    	mailPool = new WeakStrongMailPool();
    	
        /** Initialize the RobotAction */
    	boolean weak = false;  // Can't handle more than 2000 grams
    	boolean strong = true; // Can handle any weight that arrives at the building

    	if(prop.getProperty("Robot_Type_1").equals("weak")) {
    		System.out.println("Nani");
    		IRobotBehaviour robotBehaviourW = new MyRobotBehaviour(weak);
    		robot1 = new Robot(robotBehaviourW, delivery, mailPool, weak); /* shared behaviour because identical and stateless */
    	}
    	else {
    		System.out.println("Nani1");
    		IRobotBehaviour robotBehaviourS = new MyRobotBehaviour(strong);
    		robot1 = new Robot(robotBehaviourS, delivery, mailPool, strong); 
    	}
    	
    	if(prop.getProperty("Robot_Type_2").equals("weak")) {
    		System.out.println("Nani2");
    		IRobotBehaviour robotBehaviourW = new MyRobotBehaviour(weak);
    		robot2 = new Robot(robotBehaviourW, delivery, mailPool, weak); /* shared behaviour because identical and stateless */
    	}
    	else {
    		System.out.println("Nani3");
    		IRobotBehaviour robotBehaviourS = new MyRobotBehaviour(strong);
    		robot2 = new Robot(robotBehaviourS, delivery, mailPool, strong);
    	}
    	
    }
    
}
