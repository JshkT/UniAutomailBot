package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import strategies.IMailPool;
import strategies.IRobotBehaviour;

/**
 * The robot delivers mail!
 */
public class Robot {

	StorageTube tube;
    IRobotBehaviour behaviour;
    IMailDelivery delivery;
    protected final String id;
    
    public void setTube(StorageTube tube) {
		this.tube = tube;
	}

	public void setBehaviour(IRobotBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	public void setDelivery(IMailDelivery delivery) {
		this.delivery = delivery;
	}


	public void setCurrent_floor(int current_floor) {
		this.current_floor = current_floor;
	}

	public void setDestination_floor(int destination_floor) {
		this.destination_floor = destination_floor;
	}

	public void setMailPool(IMailPool mailPool) {
		this.mailPool = mailPool;
	}

	public void setStrong(boolean strong) {
		this.strong = strong;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public void setDeliveryItem(MailItem deliveryItem) {
		this.deliveryItem = deliveryItem;
	}

	public void setDeliveryCounter(int deliveryCounter) {
		this.deliveryCounter = deliveryCounter;
	}

	private int current_floor;
    public StorageTube getTube() {
		return tube;
	}

	public IRobotBehaviour getBehaviour() {
		return behaviour;
	}

	public IMailDelivery getDelivery() {
		return delivery;
	}

	public String getId() {
		return id;
	}


	public int getCurrent_floor() {
		return current_floor;
	}

	public int getDestination_floor() {
		return destination_floor;
	}

	public IMailPool getMailPool() {
		return mailPool;
	}

	public boolean isStrong() {
		return strong;
	}

	public State getCurrentState() {
		return currentState;
	}

	public MailItem getDeliveryItem() {
		return deliveryItem;
	}

	public int getDeliveryCounter() {
		return deliveryCounter;
	}

	private int destination_floor;
    private IMailPool mailPool;
    private boolean strong;
    private State currentState;
    
    private MailItem deliveryItem;
    
    private int deliveryCounter;
    

    /**
     * Initiates the robot's location at the start to be at the mailroom
     * also set it to be waiting for mail.
     * @param behaviour governs selection of mail items for delivery and behaviour on priority arrivals
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     * @param strong is whether the robot can carry heavy items
     */
    public Robot(IRobotBehaviour behaviour, IMailDelivery delivery, IMailPool mailPool, boolean strong){
    	id = "R" + hashCode();
    	currentState = new Returning();
        current_floor = Building.MAILROOM_LOCATION;
        tube = new StorageTube();
        this.behaviour = behaviour;
        this.delivery = delivery;
        this.mailPool = mailPool;
        this.strong = strong;
        this.deliveryCounter = 0;
    }

    /**
     * This is called on every time step
     * @throws ExcessiveDeliveryException if robot delivers more than the capacity of the tube without refilling
     */
    public void step() throws ExcessiveDeliveryException, ItemTooHeavyException{
    	currentState.step(this); 
    }

    
    
    
    public void setState(State s) {
    	if(s.getStateName() != currentState.getStateName()) {
    		System.out.printf("T: %3d > %11s changed from %s to %s%n", Clock.Time(), id, currentState.getStateName(), s.getStateName());
    	}
    	currentState = s;
    	if(s.getStateName().equals("DELIVERING")) {
    		System.out.printf("T: %3d > %11s-> [%s]%n", Clock.Time(), id, deliveryItem.toString());
    	}
   
    }
    
    
    /**
     * Sets the route for the robot
     */
    public void setRoute() throws ItemTooHeavyException{
        /** Pop the item from the StorageUnit */
        deliveryItem = tube.pop();
        if (!strong && deliveryItem.weight > 2000) throw new ItemTooHeavyException(); 
        /** Set the destination floor */
        destination_floor = deliveryItem.getDestFloor();
    }

    /**
     * Generic function that moves the robot towards the destination
     * @param destination the floor towards which the robot is moving
     */
    public void moveTowards(int destination){
        if(current_floor < destination){
            current_floor++;
        }
        else{
            current_floor--;
        }
    }
    
    

}
