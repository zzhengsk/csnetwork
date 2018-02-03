import java.util.ArrayList;

public class GEL 
{
	public ArrayList<Event> gel;
	
	public GEL()
	{
		gel = new ArrayList<Event>();
		gel.clear();
	}
	
	public void addEvent(Event newe)
	{
		if(gel.isEmpty())
		{
			gel.add(0, newe);
		}
		else 
		{	
			int i = 0;
			while(i < gel.size())
			{
				if(gel.get(i).eventtime >= newe.eventtime)
					break;
				i++;
			}
					
			gel.add(i, newe);
			
		}
	}
	
	public void delEvent()
	{
		gel.remove(0);
	}
	
	public Event getEvent()
	{
		return gel.get(0);
	}
	
	public boolean isEmpty()
	{
		if(gel.isEmpty())
			return true;
		else
			return false;
	}
}
