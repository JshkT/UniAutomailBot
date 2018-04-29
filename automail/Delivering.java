package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;

public class Delivering implements State {
	public String stateName = "DELIVERING";
	@Override
	public void step(Robot wrapper) throws ExcessiveDeliveryException, ItemTooHeavyException {
		boolean wantToReturn = wrapper.getBehaviour().returnToMailRoom(wrapper.getTube());
		if(wrapper.getCurrent_floor() == wrapper.getDestination_floor()){ // If already here drop off either way
            /** Delivery complete, report this to the simulator! */
            wrapper.getDelivery().deliver(wrapper.getDeliveryItem());
            wrapper.setDeliveryCounter(wrapper.getDeliveryCounter());
            if(wrapper.getDeliveryCounter() > 4){
            	throw new ExcessiveDeliveryException();
            }
            /** Check if want to return or if there are more items in the tube*/
            if(wantToReturn || wrapper.getTube().isEmpty()){
            // if(tube.isEmpty()){
            	wrapper.setState(new Returning());
            }
            else{
                /** If there are more items, set the robot's route to the location to deliver the item */
                wrapper.setRoute();
                wrapper.setState(new Delivering());
            }

	}
		else {
			wrapper.moveTowards(wrapper.getDestination_floor());
		}
	}
	public String getStateName() {
		return this.stateName;
	}
}
