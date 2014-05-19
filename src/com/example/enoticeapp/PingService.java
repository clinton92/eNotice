package com.example.enoticeapp;



/*import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;*/
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.app.Service;


public class PingService extends Service{

private Handler handler;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		Log.d("Service","Service Created");
		Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
		/*NotificationManager nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		Notification noti=new Notification(android.R.drawable.stat_notify_chat,"1 Message",System.currentTimeMillis());
		
		Intent intent=new Intent();
		PendingIntent pending=PendingIntent.getActivity(this,0, intent,0 );
		
		noti.setLatestEventInfo(this,"Urgent Message","Hello,how r u", pending);
		nm.notify(0, noti);*/
		
		//--------------------------------
		/*Intent intent = new Intent(this, NotificationReceiverActivity.class);
	    PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

	    // Build notification
	    // Actions are just fake
	    Notification noti = new Notification.Builder(this)
	        .setContentTitle("New mail from " + "test@gmail.com")
	        .setContentText("Subject").setSmallIcon(R.drawable.ic_launcher)
	        .setContentIntent(pIntent)
	        .addAction(R.drawable.ic_launcher, "Call", pIntent)
	        .addAction(R.drawable.ic_launcher, "More", pIntent)
	        .addAction(R.drawable.ic_launcher, "And more", pIntent).build();
	    
	    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    // hide the notification after its selected
	    noti.flags |= Notification.FLAG_AUTO_CANCEL;

	    notificationManager.notify(0, noti);
	    
	    handler=new Handler();*/
	}

	@Override
	public void onDestroy() {
		Thread.interrupted();
		Log.d("Service","Service Destroyed");
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	
	}
	

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		Log.d("Service","Service Started");
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		new Thread(new Task()).start();
	}
	
	class Task implements Runnable
	{

		@Override
		public void run() {
			for(int i=0;i<=20;i++)
			{
				final int value=i;
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						
						Toast.makeText(PingService.this,""+value,Toast.LENGTH_LONG).show();
						
					}
				});
			}
			
		}
		
	}
	
}
