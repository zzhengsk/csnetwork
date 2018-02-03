// ECS 152A 
// group members:  Jiahong Ou,   Zhanhong Zheng

import java.text.DecimalFormat;

public class phaseTwo
{	
	public static void main(String[] args) 
	{
		System.out.println("Part A\n");
		test(0.01, 10);
		test(0.05, 10);
		test(0.1, 10);
		test(0.2, 10);
		test(0.3, 10);
		test(0.5, 10);
		test(0.6, 10);
		test(0.7, 10);
		test(0.8, 10);
		test(0.9, 10);
		
		System.out.println("Part B\n");
		test(0.01, 25);
		test(0.05, 25);
		test(0.1, 25);
		test(0.2, 25);
		test(0.3, 25);
		test(0.5, 25);
		test(0.6, 25);
		test(0.7, 25);
		test(0.8, 25);
		test(0.9, 25);	
	}
	
	
	public static void test(double lambda, int N)
	{
		int token;		// record the position for the token 
		int frameSize = 0, total_send = 0, packetcnt = 0;
		double Tprop = 0.00001, R = 100*1024*1024; 
		double q_delay = 0, pt_delay = 0;		// q_delay --- queue delay for each packet
												// pt_delay--- transmission delay for each packet
		double total_delay = 0;
		double tokenTime, throughput, avg_delay;


		DecimalFormat df = new DecimalFormat("0.000");
		
		tokenTime = 1 / lambda;
	
		host[] hosts = new host[N];
		host temp = new host();
		for(int i=0; i<N; i++)
			hosts[i] = new host(lambda, N, i);
		
		//pass the free token 10000000 times		
		for(int i=0; i<10; i++)
		{
			token = i % N;  // update the token position
			frameSize = 0;
			q_delay = 0;
			pt_delay = 0;
			
			// calculate the frame size and update the total packets has been send
			while(!hosts[token].q.isEmpty() && hosts[token].q.peek().eventtime <= tokenTime)
			{
				temp.q.add(hosts[token].q.peek());
				
				frameSize += (hosts[token].q.peek().size);
				packetcnt++;
				
				hosts[token].q.remove();
			}
			
			// frame size = 0 means at this time, this host has nothing to send
			// then just pass the free token to the next host
			if(frameSize == 0)
			{
				tokenTime += Tprop;
				continue;
			}

			// calculate the delay for each packet, also record total delay and total send
			else
			{
				while( !temp.q.isEmpty() && temp.q.peek().eventtime <= tokenTime)
				{
					// queuing delay for this packet
					q_delay = tokenTime - temp.q.peek().eventtime;		
					
					// transmission delay for this packet
					pt_delay = ((N - token + temp.q.peek().dest) % N) * (frameSize*8/R + Tprop);
					
					total_delay += (pt_delay + q_delay); 
					temp.q.remove();
				}
				
				total_send += frameSize;
				tokenTime += (N * (frameSize*8/R + Tprop) + Tprop);
			}
		}
				
		throughput = total_send / tokenTime;
		avg_delay = total_delay / packetcnt;
				
		System.out.println("lambda: " + lambda + "     Hosts Number N: " + N);
		System.out.println("Thoughput: "+ df.format(throughput) + " byte/second"); 
		System.out.println("Average packet delay: "+ df.format(avg_delay) + " s\n");
	}
}



