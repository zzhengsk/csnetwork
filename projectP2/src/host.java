import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

public class host 
{
	int MAXBUFFER = 999999;	;			
	double time; 
	int hostId;
	
	GEL gel = new GEL();
	Queue<Event> q = new LinkedList<Event>();	
	
	public host()
	{
		time = 0;
		hostId = 0;
	}
	
	public host(double lambda, int nhost, int id)
	{
	
		time = NEDT(lambda);
	    hostId = id;
		// first event 
		Event e = new Event(time, packetSize(), createDest(nhost, hostId));
			
		gel.addEvent(e);
		q.add(e);
		
		for(int i = 0; i < 1000; i++)
		{		
			// get the first event from the GEL
			// then generate the next arrival event
			if(!gel.isEmpty())
			{
				time = gel.getEvent().eventtime; 		// update the current time
					
				e = new Event(time + NEDT(lambda), packetSize(), createDest(nhost, hostId));
				gel.addEvent(e);
				q.add(e);
				gel.delEvent();
			}
		}
	}

	// generate the event	
	private static double NEDT(double rate) 
	{
	    Random rand = new Random();
	    double u = rand.nextDouble();
	    return ((-1/rate) * Math.log(1-u));
	}
	
	private static int createDest(int n, int hostId)
	{
		int destnum; 
		// make sure won't get the destination = source
		do
		{
			destnum = new Random().nextInt(n);
		}while(destnum == hostId);
		return (destnum);
	}
	
	private static int packetSize()
	{
		// packet size [64, 1518]
		int size = new Random().nextInt(1455) + 64;
		return (size);
	}
	
}
