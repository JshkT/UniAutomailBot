package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;

public class Returning implements State {
	public String stateName = "RETURNING";

	@Override
	public void step(Robot wrapper) throws ExcessiveDeliveryException, ItemTooHeavyException {
		/** If its current position is at the mailroom, then the robot should change state */
        if(wrapper.getCurrent_floor() == Building.MAILROOM_LOCATION){
        	while(!wrapper.getTube().isEmpty()) {
        		MailItem mailItem = wrapper.getTube().pop();
        		wrapper.getMailPool().addToPool(mailItem);
                System.out.printf("T: %3d > old addToPool [%s]%n", Clock.Time(), mailItem.toString());
        	}
        	wrapper.setState(new Waiting());
        	wrapper.step();
        } else {
        	/** If the robot is not at the mailroom floor yet, then move towards it! */
            wrapper.moveTowards(Building.MAILROOM_LOCATION);
        }

	}

	public String getStateName() {
		return this.stateName;
	}
}
