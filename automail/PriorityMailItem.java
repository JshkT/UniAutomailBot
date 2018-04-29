package automail;
import java.util.*;
import strategies.Automail;

public class PriorityMailItem extends MailItem{

	/** The priority of the mail item from 1 low to 100 high */
    private final int PRIORITY_LEVEL;
    private ArrayList<Robot> robots;

	public PriorityMailItem(int input_destination, int input_arrival, int input_weight, 
			                int input_priority, ArrayList<Robot> inputRobots) {
		super(input_destination, input_arrival, input_weight);
		PRIORITY_LEVEL = input_priority;
        this.robots = inputRobots;
	}

  @Override
  public void notifyBots(){
	  for(Robot robot : robots) {
		  robot.behaviour.priorityArrival(this.PRIORITY_LEVEL, this.weight);
	  }
  }

    /**
    *
    * @return the priority level of a mail item
    */
   public int getPriorityLevel(){
       return PRIORITY_LEVEL;
   }

   @Override
   public String toString(){
       return super.toString() + String.format(" | Priority: %3d", PRIORITY_LEVEL);
   }


}
