package automail;

public class Building {
	
	
    /** The number of floors in the building **/
    public static int FLOORS;
    
    /** Represents the ground floor location */
    public static final int LOWEST_FLOOR = 1;
    
    /** Represents the mailroom location */
    public static final int MAILROOM_LOCATION = 1;
    
    
   public static void setNumFloors(int numFloors) {
	   FLOORS = numFloors;
   }

}
