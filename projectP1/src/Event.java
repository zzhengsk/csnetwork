
public class Event 
{
	public double eventtime;
//	public double serviceTime;
	public char type;
//	Event next;
//	Event prev;
	
/*	public Event()
	{
		eventtime = serviceTime = 0;
		type = 'a';
		
	}
	
	public Event(double curtime, char t, double sertime)
	{
		eventtime = curtime;
		serviceTime = sertime;
		type = t;
	}
*/
	public Event()
	{
		eventtime = 0;
		type = 'a';
//		next = null;
//		prev = null;
	}
	
	public Event(double time, char t)
	{
		eventtime = time;
		type = t;
//		next = null;
//		prev = null;
	}
}
