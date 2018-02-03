import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

public class phaseONE 
{
	public static void main(String[] args) 
	{
		// mu = 1, lambda = 0.1, 0.25, 0.4, 0.55, 0.65, 0.8, 0.9, infinite buffer
		test(0.1, 1, 99999);
		test(0.25, 1, 99999);
		test(0.4, 1, 99999);
		test(0.55, 1, 99999);
		test(0.65, 1, 99999);
		test(0.8, 1, 99999);
		test(0.9, 1, 99999);
		
		// mu = 1, lambda = 0.2, 0.4, 0.6, 0.8, 0.9, buffer = 1, 20, 50
		test(0.2, 1, 1);
		test(0.2, 1, 20);
		test(0.2, 1, 50);
		
		test(0.4, 1, 1);
		test(0.4, 1, 20);
		test(0.4, 1, 50);
		
		test(0.6, 1, 1);
		test(0.6, 1, 20);
		test(0.6, 1, 50);
		
		test(0.8, 1, 1);
		test(0.8, 1, 20);
		test(0.8, 1, 50);
		
		test(0.9, 1, 1);
		test(0.9, 1, 20);
		test(0.9, 1, 50);
	}
		

	// generate the event	
	private static double NEDT(double rate) 
	{
	    Random rand = new Random();
	    double u = rand.nextDouble();
	    return ((-1/rate) * Math.log(1-u));
	}
	
	
	private static void test(double lambda, double mu, int MAXBUFFER)
	{
		//int MAXBUFFER = 10;			// 1, 20, 50
		//	double lambda = .9;		// .1, .25, .4, .55, .65, .8, .9
		//	double mu = 1;				// .2, .4, .6, .8, .9
		double length = 0, lengtharea = 0;
		double time = 0, prevTime = 0;
		double usage = 0;
		int loss = 0;

		GEL gel = new GEL();
		Queue<Event> q = new LinkedList<Event>();		// server queue
		
		Event e = new Event(0, 'a');
		
		// first event arrival time
		gel.addEvent(e);
	
		for(int i = 0; i < 100000; i++)
		{
			prevTime = time;
			
			// get the first event from the GEL
			// if is an arrival event, then process-arrival-event;
			if(gel.getEvent().type == 'a')
			{
				time = gel.getEvent().eventtime; 		// update the current time
				
				e = new Event(time + NEDT(lambda), 'a');
				gel.addEvent(e);
				
				if(length == 0)
				{
					length++;
					e = new Event(time+NEDT(mu), 'd');
					gel.addEvent(e);
				}
				
				else if( (length-1) < MAXBUFFER)
				{
					q.add(e);
					length++;
					usage += (time - prevTime);
				}
				
				else 
				{
					loss++;
					usage += (time - prevTime);
				}
				
				gel.delEvent();
			}
			
			// if is a departure event, then process-service-completion
			else
			{
				time = gel.getEvent().eventtime; 		// update the current time
				length--;
				
				usage += (time - prevTime);
				
				if(length > 0)
				{
					q.remove();								// dequeue the event
					e = new Event(time + NEDT(mu), 'd');					
					gel.addEvent(e);

				}
				gel.delEvent();
			}
			

			lengtharea += length * (time - prevTime);
		}
		
		System.out.println("lambda: " + lambda + "  mu: " + mu + "  BUFFERSIZE: " + MAXBUFFER);
		System.out.println("packet loss: " + loss);
		System.out.println("server usage: " + usage/time);
		System.out.println("meanlength: " + lengtharea/time + "\n");
	}
}
