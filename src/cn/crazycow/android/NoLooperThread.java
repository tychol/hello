package cn.crazycow.android;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class NoLooperThread {
//    private EventHandler mNoLooperThreadHandler;  
//    public void run() {  
//        Looper   myLooper, mainLooper;  
//        myLooper= Looper.myLooper();   //获得自己的Looper  
//            mainLooper= Looper.getMainLooper();    //获得自己的main的looper  
//            String obj;  
//            if (myLooper == null) {  
//                    mNoLooperThreadHandler = new EventHandler(mainLooper);  
//                    obj= "NoLooperThread has no looper andhandleMessage function executed in main thread!";  
//            }else  
//            {  
//                    mNoLooperThreadHandler = new EventHandler(myLooper);  
//                    obj= "This is from NoLooperThread self andhandleMessage function executed in NoLooperThread!";  
//            }  
//            mNoLooperThreadHandler.removeMessages(0);    //清空消息队列  
//            if (false == postRunnable) {  
//                    Message m = mNoLooperThreadHandler.obtainMessage(2, 1, 1, obj);    //生成消息对象  
//                    mNoLooperThreadHandler.sendMessage(m);   //发送消息  
//                    Log.e(sTag, "NoLooperThread id:" + this.getId());  
//            }else {  
//                    mNoLooperThreadHandler.post(new Runnable() {    //添加一个Runnable接口的实现到消息队列，此Runnable接口的实现不在此                                  线程中执行，会在接收的线程中执行。  
//                        public void run() {  
//                            tv.setText("update UI through handler post runnalbe mechanism!");  
//                            noLooerThread.stop();  
//                        }  
//                    });  
//            }  
}
