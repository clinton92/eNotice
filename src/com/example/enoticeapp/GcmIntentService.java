package com.example.enoticeapp;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class GcmIntentService extends IntentService{
	Context context;
	public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public static final String TAG = "GCM Demo";
    private String title=null;
    private String description=null;

    private JSONObject json = null;
    

	public GcmIntentService() {
		super("GcmIntentService");
		// TODO Auto-generated constructor stublll
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();
		String msg = intent.getStringExtra("message");
		Log.d("Message Received",""+msg);
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);
		
		 if (!extras.isEmpty()) {
			 
			 if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
	                sendNotification("Send error: " + extras.toString());
	            } else if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_DELETED.equals(messageType)) {
	                sendNotification("Deleted messages on server: " +
	                        extras.toString());
	            // If it's a regular GCM message, do some work.
	            } else if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
	                // This loop represents the service doing some work.
	                for (int i=0; i<5; i++) {
	                    Log.i(TAG, "Working... " + (i+1)
	                            + "/5 @ " + SystemClock.elapsedRealtime());
	                    try {
	                        Thread.sleep(500);
	                    } catch (InterruptedException e) {
	                    }
	                }
	                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
	                // Post notification of received message.
	                //sendNotification("Received: " + extras.toString());
	                sendNotification(msg);
	                Log.i(TAG, "Received: " + extras.toString());
	            }
	        }
		 GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
	private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        
        Intent myintent = new Intent(this, DashBoard.class);
        myintent.putExtra("message", msg);
        Log.d("message to be processed",""+msg);
        processJSON(msg);
        Uri sound = Uri.parse("android.resource://com.example.enoticeapp/"+R.raw.notify);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        		myintent, PendingIntent.FLAG_UPDATE_CURRENT);
       
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_enotice_small)
        .setContentTitle(title)
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(description))
        .setContentText(description)
        .setTicker(description)
        .setVibrate(new long[] { 1000, 1000, 1000 })
        .setLights(Color.RED, 3000, 3000)
        .setSound(sound);
       // mBuilder.sound = 

        mBuilder.setContentIntent(contentIntent);
        
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
	
	private void processJSON(String msg){
		try{
			json = new JSONObject(msg);
			title = json.getString("title");
			description = json.getString("description");
		}
		catch(JSONException e){
			Log.d("GCMIntentService","Exception in JSON: ");
			e.printStackTrace();
		}
	}
}