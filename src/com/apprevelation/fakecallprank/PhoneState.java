package com.apprevelation.fakecallprank;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PhoneState extends Service {
	private CallStateListener mCallStateListener = new CallStateListener();
	private TelephonyManager mTelephonyManager;
	private int mCallState;

	@Override
	public void onCreate() {
		super.onCreate();
		mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		mCallState = mTelephonyManager.getCallState();
		mTelephonyManager.listen(mCallStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public void onDestroy() {
		Log.d("onDestroy", "onDestroy");
		mTelephonyManager.listen(mCallStateListener, PhoneStateListener.LISTEN_NONE);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null; //-- not a bound service--
	}

	private final class CallStateListener extends PhoneStateListener {
		
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			switch (mCallState) {

				case TelephonyManager.CALL_STATE_IDLE:
					if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
						Toast.makeText(getApplicationContext(),"idle --> off hook = new outgoing call", Toast.LENGTH_SHORT).show();
						Log.d("state", "idle --> off hook = new outgoing call");
						// idle --> off hook = new outgoing call
						//triggerSenses(Sense.CallEvent.OUTGOING);
					} else if (state == TelephonyManager.CALL_STATE_RINGING) {

						//change the incoming number here 
						//change the log as well
						Toast.makeText(getApplicationContext(),"call from "+ incomingNumber, Toast.LENGTH_SHORT).show();
						Toast.makeText(getApplicationContext(),"idle --> ringing = new incoming call", Toast.LENGTH_SHORT).show();
						Log.d("state", "idle --> ringing = new incoming call");
						// idle --> ringing = new incoming call
						//triggerSenses(Sense.CallEvent.INCOMING);
					}
					break;

				case TelephonyManager.CALL_STATE_OFFHOOK:
					if (state == TelephonyManager.CALL_STATE_IDLE) {

						//change here as well

						Toast.makeText(getApplicationContext(),"off hook --> idle  = disconnected", Toast.LENGTH_SHORT).show();
						Log.d("state", "off hook --> idle  = disconnected");
						// off hook --> idle  = disconnected
						//triggerSenses(Sense.CallEvent.ENDED);
					} else if (state == TelephonyManager.CALL_STATE_RINGING) {

						//change here too

						Toast.makeText(getApplicationContext(), "off hook --> ringing = another call waiting", Toast.LENGTH_SHORT).show();
						Log.d("state", "off hook --> ringing = another call waiting");
						// off hook --> ringing = another call waiting
						//triggerSenses(Sense.CallEvent.WAITING);
					}
					Toast.makeText(getApplicationContext(),"CALL_STATE_OFFHOOK", Toast.LENGTH_SHORT).show();
					Log.d("CALL_STATE_OFFHOOK", String.valueOf(state));
					break;

				case TelephonyManager.CALL_STATE_RINGING:
					if (state == TelephonyManager.CALL_STATE_OFFHOOK) {

						//change here to

						Toast.makeText(getApplicationContext(),"ringing --> off hook = received", Toast.LENGTH_SHORT).show();
						Log.d("state", "ringing --> off hook = received");
						// ringing --> off hook = received
						//triggerSenses(Sense.CallEvent.RECEIVED);
					} else if (state == TelephonyManager.CALL_STATE_IDLE) {

						//change log
						//change notification bar
						//change call screen

						Toast.makeText(getApplicationContext(),"ringing --> idle = missed call", Toast.LENGTH_SHORT).show();
						Log.d("state", "ringing --> idle = missed call");
						// ringing --> idle = missed call
						//triggerSenses(Sense.CallEvent.MISSED);
					}
					break;
			}

			mCallState = state;
		}
	}

	public static void init(Context c) {
		c.startService(new Intent(c, PhoneState.class));
		Log.d("Service enabled","Service enabled: " + true);
	}
}