package android.os;   
      
interface ICanbusListener {
	void actionPerformed(int actionId);
	
	//call back, so message is in-type
	void messageArrived(int msgId, in byte[] message, int length);
}