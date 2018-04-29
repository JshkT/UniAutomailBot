package automail;

import java.util.*;

import strategies.IMailPool;
import strategies.Automail;

/**
 * This class generates the mail.
 * Constants in this class are based on observations of typical mail arrivals
 */
public class MailGenerator {

    public final int MAIL_TO_CREATE;

    private int mailCreated;

    private final Random random;
    /** This seed is used to make the behaviour deterministic */

    private boolean complete;
    private IMailPool mailPool;
    private Automail automail;
    private ArrayList<Robot> robots;
   

    private HashMap<Integer,ArrayList<MailItem>> allMail;

    /**
     * Constructor for mail generation
     * @param mailToCreate roughly how many mail items to create
     * @param mailPool where mail items go on arrival
     * @param seed random seed for generating mail
     */
    public MailGenerator(int mailToCreate, IMailPool inputMailPool, ArrayList<Robot> inputrobots, HashMap<Boolean,Integer> seed){
        if(seed.containsKey(true)){
        	this.random = new Random((long) seed.get(true));
        }
        else{
        	this.random = new Random();
        }
        // Vary arriving mail by +/-20%
        System.out.println(mailToCreate);
        MAIL_TO_CREATE = mailToCreate*4/5 + random.nextInt(mailToCreate*2/5);
        System.out.println(MAIL_TO_CREATE);
        // System.out.println("Num Mail Items: "+MAIL_TO_CREATE);
        mailCreated = 0;
        complete = false;
        allMail = new HashMap<Integer,ArrayList<MailItem>>();
        this.robots = inputrobots;
        this.mailPool = inputMailPool; 
    }


    /**
     * This class initializes all mail and sets their corresponding values,
     */
    public void generateAllMail(){
    	MailFactory MailFac = new MailFactory(allMail, random, robots);
        while(!complete){
            MailItem newMail = MailFac.CreateMail();
            int timeToDeliver = newMail.getArrivalTime();
            /** Check if key exists for this time **/
            if(allMail.containsKey(timeToDeliver)){
                /** Add to existing array */
                allMail.get(timeToDeliver).add(newMail);
            }
            else{
                /** If the key doesn't exist then set a new key along with the array of MailItems to add during
                 * that time step.
                 */
                ArrayList<MailItem> newMailList = new ArrayList<MailItem>();
                newMailList.add(newMail);
                allMail.put(timeToDeliver,newMailList);
            }
            /** Mark the mail as created */
            mailCreated++;

            /** Once we have satisfied the amount of mail to create, we're done!*/
            if(mailCreated == MAIL_TO_CREATE){
                complete = true;
            }
        }

    }

    /**
     * While there are steps left, create a new mail item to deliver
     * @return Priority
     */
    public void addWaveToPool(){
    	// Check if there are any mail to create
        if(this.allMail.containsKey(Clock.Time())){
            for(MailItem mailItem : allMail.get(Clock.Time())){
                System.out.printf("T: %3d > new addToPool [%s]%n", Clock.Time(), mailItem.toString());
                mailPool.addToPool(mailItem);
            }
        }
    }

    public void callNotifyAll(){
      if(this.allMail.containsKey(Clock.Time())){
        for(MailItem mail: allMail.get(Clock.Time())){
          mail.notifyBots();
        }
      }
    }

}
