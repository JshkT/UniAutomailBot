package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;

public interface State {
	 public void step(Robot wrapper) throws ExcessiveDeliveryException, ItemTooHeavyException;
	 public String getStateName();

}
