package automail;

import exceptions.MailAlreadyDeliveredException;
import java.util.*;


public class ReportDelivery implements IMailDelivery {
	
	private ArrayList<MailItem> MAIL_DELIVERED;
	private double total_score = 0 ;
	private static double penalty;
	
	public ReportDelivery(ArrayList<MailItem> deliveredMail) {
		MAIL_DELIVERED = deliveredMail;
	}

	/** Confirm the delivery and calculate the total score */
	public void deliver(MailItem deliveryItem){
		if(!MAIL_DELIVERED.contains(deliveryItem)){
            System.out.printf("T: %3d > Delivered     [%s]%n", Clock.Time(), deliveryItem.toString());
			MAIL_DELIVERED.add(deliveryItem);
			// Calculate delivery score
			total_score += calculateDeliveryScore(deliveryItem);
		}
		else{
			try {
				throw new MailAlreadyDeliveredException();
			} catch (MailAlreadyDeliveredException e) {
				e.printStackTrace();
			}
		}
	}

	private static double calculateDeliveryScore(MailItem deliveryItem) {  //BS THIS FUNCTION SHOULD BE IN REPORT DELIVERY. BAD ABSTRACTION
		// Penalty for longer delivery times
		//final double penalty = 1.1;
		double priority_weight = 0;
	    // Take (delivery time - arrivalTime)**penalty * (1+sqrt(priority_weight))
		if(deliveryItem instanceof PriorityMailItem){
			priority_weight = ((PriorityMailItem) deliveryItem).getPriorityLevel();
		}
	    return Math.pow(Clock.Time() - deliveryItem.getArrivalTime(),penalty)*(1+Math.sqrt(priority_weight));
	}
	
	public double getTotalScore() {
		return this.total_score;
	}
	
	
	public static void setPenalty(double Delivery_Penalty) {
		penalty = Delivery_Penalty;
	}

}
