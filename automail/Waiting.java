package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import strategies.IRobotBehaviour;

public class Waiting implements State {
	public String stateName = "WAITING";

	@Override
	public void step(Robot wrapper) throws ExcessiveDeliveryException, ItemTooHeavyException {
		/** Tell the sorter the robot is ready */
		wrapper.getMailPool().fillStorageTube(wrapper.getTube(), wrapper.isStrong());
        // System.out.println("Tube total size: "+tube.getTotalOfSizes());
        /** If the StorageTube is ready and the Robot is waiting in the mailroom then start the delivery */
        if(!wrapper.getTube().isEmpty()){
        	wrapper.setDeliveryCounter(0); // reset delivery counter
			wrapper.getBehaviour().startDelivery();
			wrapper.setRoute();
			wrapper.setState(new Delivering());
        }

	}
	
	public String getStateName() {
		return this.stateName;
	}


}
