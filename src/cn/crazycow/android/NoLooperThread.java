package cn.crazycow.android;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class NoLooperThread {
//    private EventHandler mNoLooperThreadHandler;  
//    public void run() {  
//        Looper   myLooper, mainLooper;  
//        myLooper= Looper.myLooper();   //����Լ���Looper  
//            mainLooper= Looper.getMainLooper();    //����Լ���main��looper  
//            String obj;  
//            if (myLooper == null) {  
//                    mNoLooperThreadHandler = new EventHandler(mainLooper);  
//                    obj= "NoLooperThread has no looper andhandleMessage function executed in main thread!";  
//            }else  
//            {  
//                    mNoLooperThreadHandler = new EventHandler(myLooper);  
//                    obj= "This is from NoLooperThread self andhandleMessage function executed in NoLooperThread!";  
//            }  
//            mNoLooperThreadHandler.removeMessages(0);    //�����Ϣ����  
//            if (false == postRunnable) {  
//                    Message m = mNoLooperThreadHandler.obtainMessage(2, 1, 1, obj);    //������Ϣ����  
//                    mNoLooperThreadHandler.sendMessage(m);   //������Ϣ  
//                    Log.e(sTag, "NoLooperThread id:" + this.getId());  
//            }else {  
//                    mNoLooperThreadHandler.post(new Runnable() {    //���һ��Runnable�ӿڵ�ʵ�ֵ���Ϣ���У���Runnable�ӿڵ�ʵ�ֲ��ڴ�                                  �߳���ִ�У����ڽ��յ��߳���ִ�С�  
//                        public void run() {  
//                            tv.setText("update UI through handler post runnalbe mechanism!");  
//                            noLooerThread.stop();  
//                        }  
//                    });  
//            }  
}
