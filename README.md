Simulation of Token Passing Protocol (Taking Turn Protocol) based
Local-Area Network 

Phase1:
Simulate how a single server with a finite buffer to haddle packets.
The server can only handle one packet at one time.
Packets arrive to the server in a random time. There are two situations.
1: No packet in the buffer. Server will immediately handle the packet.
2: At least one packet in the buffer. The arrival packet will be stored 
into the buffer, and then the server will handle the packets in a FIFO order.

Phase2:
Simulate how a Token Passing Protocol work for the LAN.
There are multiple server, but there only one token exist at whole time. 
The server can only handle packets if and only if the server has the token. 
The time of holding the token is determined by a negative exponential distribution.
When the server done with handling packets or the token time up, token 
must be passed to the next server. 
