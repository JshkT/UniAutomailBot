package automail;
import java.util.*;
import strategies.Automail;

public class MailFactory{
	
	private HashMap<Integer, ArrayList<MailItem>> allMail;
	private Random random;
	private ArrayList<Robot> robots;
  
  public MailFactory(HashMap<Integer, ArrayList<MailItem>> inputMail, Random inputRandom, 
		  ArrayList<Robot> inputrobots){
	  	this.random  = inputRandom;
	  	this.allMail = inputMail;
	  	this.robots = inputrobots;
  }

  public MailItem CreateMail(){
	  int dest_floor = generateDestinationFloor();
      int priority_level = generatePriority();
      int arrival_time = generateArrivalTime();
      int weight = generateWeight();
	  if(	(random.nextInt(6) > 0) ||  // Skew towards non priority mail
	        	(allMail.containsKey(arrival_time) &&
	        	allMail.get(arrival_time).stream().anyMatch(e -> PriorityMailItem.class.isInstance(e))))
	        {
	          return new MailItem(dest_floor, arrival_time, weight);
	        } else {
	          return new PriorityMailItem(dest_floor, arrival_time, weight, priority_level, robots);
	        }
  }
  
  /**
	* @return a destination floor between the ranges of GROUND_FLOOR to FLOOR
	*/
  private int generateDestinationFloor(){
	   return Building.LOWEST_FLOOR + random.nextInt(Building.FLOORS);
  }


  /**
	* @return a random weight
	*/
  private int generateWeight(){
	final double mean = 200.0; // grams for normal item
	final double stddev = 700.0; // grams
	double base = random.nextGaussian();
	if (base < 0) base = -base;
	int weight = (int) (mean + base * stddev);
	   return weight > 5000 ? 5000 : weight;
  }

  /**
	* @return a random arrival time before the last delivery time
	*/
  private int generateArrivalTime(){
	   return 1 + random.nextInt(Clock.LAST_DELIVERY_TIME);
  }
  
private int generatePriority() {
	return random.nextInt(4) > 0 ? 10 : 100;
}




}
