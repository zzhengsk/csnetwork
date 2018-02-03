public class Event 
{
	public double eventtime;
	public int size, dest;

	public Event()
	{
		eventtime = 0;
		size = 0;
		dest = -1;
	}
	
	public Event(double time, int s, int destination)
	{
		eventtime = time;
		size = s;
		dest = destination;
	}
}
