package cn.crazycow.android;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICanbusListener;
import android.os.ICanbusService;
import android.os.IHelloService;
import android.os.Person;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.crazycow.android.service.FirstService;

public class HelloActivity extends Activity {
    /** Called when the activity is first created. */
	
	FirstService.MyBinder binder;
	
	private static final String TAG = "HelloActivity";
	
    private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			System.out.println("---Service Connected-");
			binder = (FirstService.MyBinder)service;
			System.out.println("Binder is: " + binder);
			System.out.println("Count is: " + binder.getCount());
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
		}
    };
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final TextView show = (TextView)findViewById(R.id.show);
				show.setText("Hello Android: " + new java.util.Date());
			}
        });
        
        Button start = (Button)findViewById(R.id.start);
        Button stop = (Button)findViewById(R.id.stop);
        
        start.setOnClickListener(new OnClickListener(){

            
            
			@Override
			public void onClick(View v) {
				
				Intent intent =  new Intent();
	            intent.setAction("cn.crazycow.android.service.FIRST_SERVICE");
	            ComponentName comp = new ComponentName(HelloActivity.this, FirstService.class);
	            intent.setComponent(comp);
	            System.out.println("Package:" + intent.getPackage());
	            System.out.println("Package:" + intent.getComponent().getPackageName());
	            System.out.println("class:" + intent.getComponent().getClassName());
	            
				startService(intent);
			}
        });
        
        stop.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent intent =  new Intent();
	            intent.setAction("cn.crazycow.android.service.FIRST_SERVICE");
	            ComponentName comp = new ComponentName(HelloActivity.this, FirstService.class);
	            intent.setComponent(comp);
	            
				stopService(intent);
			}
        });
        
        Button bind = (Button)findViewById(R.id.bind);
        Button unbind = (Button)findViewById(R.id.unbind);
        Button getCount = (Button)findViewById(R.id.getCount);
        
        bind.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
		        final Intent intent =  new Intent();
		        intent.setAction("cn.crazycow.android.service.FIRST_SERVICE");
		        ComponentName comp = new ComponentName(HelloActivity.this, FirstService.class);
		        intent.setComponent(comp);
		        
				bindService(intent, conn, Service.BIND_AUTO_CREATE);
				System.out.println();
				

//				PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
			}
        });
        
        unbind.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				unbindService(conn);
			}
        });
        
        getCount.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				System.out.print("Count is : " + binder.getCount());
				Toast.makeText(HelloActivity.this, "Count is : " + binder.getCount(), 1000);
				final TextView show = (TextView)findViewById(R.id.show);
				show.setText("Servoce Count is: " + binder.getCount());
				
		        //print all the services
				ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);  
				
				List<ActivityManager.RunningServiceInfo> list = mActivityManager.getRunningServices (50);
				
				System.out.println("Activate service: " + list.size());  
				
				for (ActivityManager.RunningServiceInfo runServiceInfo : list) {  
					  
		            int pid = runServiceInfo.pid;
		            int uid = runServiceInfo.uid;
		            String processName = runServiceInfo.process;   
		  
		            long activeSince = runServiceInfo.activeSince;  
		  
		            int clientCount = runServiceInfo.clientCount;  
		  
		            ComponentName serviceCMP = runServiceInfo.service;  
		            String serviceName = serviceCMP.getShortClassName(); 
		            String pkgName = serviceCMP.getPackageName();
		  
		            Log.i("HelloActivity", "pid :" + pid + " pName: " + processName + " puid:"  
		                    + uid + "\n" + " service time:" + activeSince  
		                    + " clients: " + clientCount + "\n" + "component:"  
		                    + serviceName + " and " + pkgName);  
				}
		        
				
//				TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

				
				Object service = getSystemService("hello");
				if (service != null && service instanceof IBinder) {
					IHelloService hservice = IHelloService.Stub.asInterface((IBinder)service);
					System.out.println("hservice is: " + hservice);
					try {
						System.out.println("hservice's value is: " + hservice.getVal('0'));
						hservice.setVal(123, '0');
						System.out.println("hservice's new value is: " + hservice.getVal('0'));
						
						Person p = new Person();
						p.setName("jjww");
						p.setSex(10);
						
						hservice.greet(p);
						
						show.setText(show.getText() + " " + hservice.greet(p));
						
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.println("hservice is: null");
				}
				
				Object service2 = getSystemService("canbus");
				if (service2 != null && service2 instanceof IBinder) {
					ICanbusService canbusService = ICanbusService.Stub.asInterface((IBinder)service2);
					System.out.println("canbus service is: " + canbusService);
					try {
						Log.i(TAG, "canbus service: bluetooth is active: " + canbusService.getBluetooth());
						show.setText(show.getText() + " bluetooth: " + canbusService.isActiveBluetooth());
						
						Log.i(TAG, "Add listener...");
						canbusService.addListener(listener);
						show.setText(show.getText() + " bluetooth: " + canbusService.isActiveBluetooth() + " Add listener succeed");
						Log.i(TAG, "Add listener succeed");
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else {
					Log.e(TAG, "canbus service is: null");
				}
			}
        });
    }
    
    private ICanbusListener listener = new ICanbusListener.Stub() {
        public void actionPerformed(final int id) {   
        	   runOnUiThread(new Runnable() {
        	        public void run()
        	        {
        	        	Log.i(TAG, "Get message from service: comId = 0x" + Integer.toHexString(id));
        				final TextView show = (TextView)findViewById(R.id.show);
        				show.setText("Get message from service: comId = 0x" + Integer.toHexString(id));
        	        }
        	    });
        }

		@Override
		public void messageArrived(final int msgId, byte[] message, int length)
				throws RemoteException {
			
			final byte[] message2 = message;
			
     	   runOnUiThread(new Runnable() {
     		  
   	        public void run()
   	        {
   	        	Log.i(TAG, "Message arriaved: id = " + Integer.toHexString(msgId));
   				final TextView show = (TextView)findViewById(R.id.show);
   				StringBuilder sb = new StringBuilder();
   				for(int i = 0; i < message2.length; i++) {
   					sb.append(Integer.toHexString(message2[i] & 0x000000FF) + " ");
   				}
   				
   				show.setText("Message arriaved: id = " + Integer.toHexString(msgId) + " content = " + sb.toString());
   	        }
   	    });
		}
    };   
}