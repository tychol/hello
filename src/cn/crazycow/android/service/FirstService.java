package cn.crazycow.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class FirstService extends Service {
	
	public class MyBinder extends Binder
	{
		public int getCount()
		{
			return count;
		}
	}

	private int count;
	
	private boolean quit = false;
	
	private MyBinder binder = new MyBinder();
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		System.out.println("Service is binded");
		return binder;
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		System.out.println("Service is unbinded");
		return super.onUnbind(intent);
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("Service is created");
		
		new Thread() {
			public void run()
			{
				while(!quit)
				{
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						
					}
					count++;
					System.out.println("count increased: " + count);
				}
			}
		}.start();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("Service is started");
		return super.onStartCommand(intent, flags, startId);
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.quit = true;
		System.out.println("Service is destoryed");
	}
	
	
	

}
