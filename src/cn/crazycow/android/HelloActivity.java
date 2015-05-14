package cn.crazycow.android;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CanbusManager;
import android.os.CanbusManager.CarBasicInfoListener;
import android.os.CanbusManager.CarRadarListener;
import android.os.CanbusManager.SteeringWheelKeyCode;
import android.os.IBinder;
import android.os.ICanbusListener;
import android.os.ICanbusService;
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
				Log.i(TAG, "---Service Connected-");
				binder = (FirstService.MyBinder)service;
				Log.i(TAG, "Binder is: " + binder);
				Log.i(TAG, "Count is: " + binder.getCount());
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
//	            Log.i(TAG, "Package:" + intent.getPackage());
	            Log.i(TAG, "Package:" + intent.getComponent().getPackageName());
	            Log.i(TAG, "class:" + intent.getComponent().getClassName());
	            
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
				
				Log.i(TAG, "Activate service: " + list.size());
				
				for (ActivityManager.RunningServiceInfo runServiceInfo : list) {  
					  
		            int pid = runServiceInfo.pid;
//		            int uidd = runServiceInfo.uid;
		            int uidd = 0;
		            String processName = runServiceInfo.process;   
		  
		            long activeSince = runServiceInfo.activeSince;  
		  
		            int clientCount = runServiceInfo.clientCount;  
		  
		            ComponentName serviceCMP = runServiceInfo.service;  
		            String serviceName = serviceCMP.getShortClassName(); 
		            String pkgName = serviceCMP.getPackageName();
		  
		            Log.i("HelloActivity", "pid :" + pid + " pName: " + processName + " puid:"  
		                    + uidd + "\n" + " service time:" + activeSince  
		                    + " clients: " + clientCount + "\n" + "component:"  
		                    + serviceName + " and " + pkgName);  
				}
		        
				
//				TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				
//				Object service = getSystemService("hello");
//				if (service != null && service instanceof IBinder) {
//					IHelloService hservice = IHelloService.Stub.asInterface((IBinder)service);
//					Log.i(TAG, "hservice is: " + hservice);
//					try {
//						Log.i(TAG, "hservice's value is: " + hservice.getVal('0'));
//						hservice.setVal(123, '0');
//						Log.i(TAG, "hservice's new value is: " + hservice.getVal('0'));
//						
//						Person p = new Person();
//						p.setName("jjww");
//						p.setSex(10);
//						
//						hservice.greet(p);
//						
//						show.setText(show.getText() + " " + hservice.greet(p));
//						
//					} catch (RemoteException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				} else {
//					Log.i(TAG, "hservice is: null");
//				}
				
				Object service2 = getSystemService("canbus");
				if (service2 != null && service2 instanceof IBinder) {
					ICanbusService canbusService = ICanbusService.Stub.asInterface((IBinder)service2);
					Log.i(TAG, "canbus service is: " + canbusService);
					try {
						CanbusManager manager = new CanbusManager(canbusService, HelloActivity.this);
						//Log.i(TAG, "canbus service: bluetooth is active: " + canbusService.getBluetooth());
						//show.setText(show.getText() + " bluetooth: " + canbusService.isActiveBluetooth());
						
						Log.i(TAG, "Add listener 2222 ...");
						
						//canbusService.addListener(listener);
						
						//canbusService.addListener2(0x72, listener2);
						
						byte[] msg = canbusService.queryMessage(0x72, new byte[0]);
						Log.i(TAG, "msg.len: " + msg.length);
						
//						canbusService.removeListener(listener);
//						canbusService.removeListener2(0x72, listener2);
						
						//*****************************
						manager.registerCarRadarListener(mCarRadarListener);
						
						show.setText("Add CarRadarListener succeed");
						Log.i(TAG, "Add CarRadarListener succeed");
						
						manager.registerCarBasciInfoListener(mCarBasicInfoListener);
						
						show.setText("Add CarBasicInfoListener succeed");
						Log.i(TAG, "Add CarBasicInfoListener succeed");
						
						show.setText("Add listener 2222 succeed");
						Log.i(TAG, "Add listener 2222 succeed");
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else if (service2 != null && service2 instanceof CanbusManager) {
					CanbusManager manager = (CanbusManager)service2;
					
					manager.registerCarRadarListener(mCarRadarListener);
					
					show.setText("Add CarRadarListener succeed");
					Log.i(TAG, "Add CarRadarListener succeed");
					
					manager.registerCarBasciInfoListener(mCarBasicInfoListener);
					
					show.setText("Add CarBasicInfoListener succeed");
					Log.i(TAG, "Add CarBasicInfoListener succeed");
					
				} else {
					Log.e(TAG, "canbus service is: null");
				}
			}
        });
    }
    
    private ICanbusListener listener = new ICanbusListener.Stub() {

				@Override
				public void messageArrived(final int msgId, byte[] message, int length)
						throws RemoteException {
					
					Log.i(TAG, "Message arriaved to listner1(Outter): id = " + Integer.toHexString(msgId));
					
					final byte[] message2 = message;
					
		     	   runOnUiThread(new Runnable() {
		     		  
		   	        public void run()
		   	        {
		   	        	Log.i(TAG, "Message arriaved to listner1(Inner): id = " + Integer.toHexString(msgId));
		   	        	final TextView show = (TextView)findViewById(R.id.show);
		   	        	StringBuilder sb = new StringBuilder();
		   	        	for(int i = 0; i < message2.length; i++) {
		   	        		sb.append(Integer.toHexString(message2[i] & 0x000000FF) + " ");
		   	        	}
		   				
		   	        	show.setText("Message arriaved to listner1(inner): id = " + Integer.toHexString(msgId) + " content = " + sb.toString());
		   	        }
		   	    });
				}
		};
		
    private ICanbusListener listener2 = new ICanbusListener.Stub() {

				@Override
				public void messageArrived(final int msgId, byte[] message, int length)
						throws RemoteException {
					
					Log.i(TAG, "Message arriaved to listner2(Outter): id = " + Integer.toHexString(msgId));
					
					final byte[] message2 = message;
					
		     	   runOnUiThread(new Runnable() {
		     		  
		   	        public void run()
		   	        {
		   	        	Log.i(TAG, "Message arriaved to listner2(Inner): id = " + Integer.toHexString(msgId));
		   	        	final TextView show = (TextView)findViewById(R.id.show);
		   	        	StringBuilder sb = new StringBuilder();
		   	        	for(int i = 0; i < message2.length; i++) {
		   	        		sb.append(Integer.toHexString(message2[i] & 0x000000FF) + " ");
		   	        	}
		   	        	show.setText("Message arriaved to listner2(Inner): id = " + Integer.toHexString(msgId) + " content = " + sb.toString());
		   	        }
		   	    });
				}
		};
		
		private CarRadarListener mCarRadarListener = new CarRadarListener(){

			@Override
			public void leftAngel(final int angel) {
				// TODO Auto-generated method stub
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): leftAngel = " + angel);
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): leftAngel = " + angel);
			   	        }
			   	    });
			}

			@Override
			public void rightAngel(final int angel) {
				// TODO Auto-generated method stub
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): rightAngel = " + angel);
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): rightAngel = " + angel);
			   	        }
			   	    });
				
			}

			@Override
			public void radarState(final int[] state) {
				// TODO Auto-generated method stub
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): radarState = " + state);
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): radarState = " + state);
			   	        }
			   	    });
				
			}

			@Override
			public void radarAssitState(final int[] state) {
				// TODO Auto-generated method stub
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): radarAssitState = " + state);
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): radarAssitState = " + state);
			   	        }
			   	    });
				
			}
			
		};
		
		private CarBasicInfoListener mCarBasicInfoListener = new CarBasicInfoListener(){

			@Override
			public void accOn(final boolean state) {
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): accOn = " + state);
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): accOn = " + state);
			   	        }
			   	    });
				
			}

			@Override
			public void illOn(final boolean state) {
				// TODO Auto-generated method stub
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): illOn = " + state);
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): illOn = " + state);
			   	        }
			   	    });
				
			}

			@Override
			public void revOn(final boolean state) {
				// TODO Auto-generated method stub
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): revOn = " + state);
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): revOn = " + state);
			   	        }
			   	    });
				
			}

			@Override
			public void parkOn(final boolean state) {
				// TODO Auto-generated method stub
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): parkOn = " + state);
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): parkOn = " + state);
			   	        }
			   	    });
				
			}

			@Override
			public void keyIn(final boolean state) {
				// TODO Auto-generated method stub
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): keyIn = " + state);
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): keyIn = " + state);
			   	        }
			   	    });
				
			}

			@Override
			public void raderAvailable(final boolean state) {
				// TODO Auto-generated method stub
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): raderAvailable = " + state);
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): raderAvailable = " + state);
			   	        }
			   	    });
				
			}

			@Override
			public void bluetoothAvailable(final boolean state) {
				// TODO Auto-generated method stub
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): bluetoothAvailable = " + state);
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): bluetoothAvailable = " + state);
			   	        }
			   	    });
				
			}

			@Override
			public void steeringWheelBtn(final SteeringWheelKeyCode keyCode) {
				// TODO Auto-generated method stub
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): steeringWheelBtn = " + keyCode.name());
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): steeringWheelBtn = " + keyCode.name());
			   	        }
			   	    });
				
			}

			@Override
			public void illValue(final int value) {
				// TODO Auto-generated method stub
		     	   runOnUiThread(new Runnable() {
			     		  
			   	        public void run()
			   	        {
			   	        	Log.i(TAG, "Message arriaved to listner2(Inner): illValue = " + value);
			   	        	final TextView show = (TextView)findViewById(R.id.show);
			   	        	show.setText("Message arriaved to listner2(Inner): illValue = " + value);
			   	        }
			   	    });
				
			}
			
		};
}