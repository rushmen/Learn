package oppo.lzc.appex;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    LocalService mService = null;
    LocalService.LocalBinder localBinder;
    boolean mBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
        startMessengerService();
        //startBindLocal();

    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            Log.d(TAG, "onStop: unBindService");
            mBound = false;
        }
    }

    protected  void startMessengerService() {
        Log.d(TAG, "startMessengerService: ");
        startService(new Intent(this, MessengerService.class));
    }
    protected  void startBindLocal() {
        Log.d(TAG, "startBindLocal: ");
        bindService(new Intent(this, LocalService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: name " + name + ", service " + service);
            localBinder = (LocalService.LocalBinder)service;
            mService = localBinder.getService();
            Log.d(TAG, "onServiceConnected: " + localBinder.getName());
            Log.d(TAG, "onServiceConnected: " + localBinder.setName("new name"));
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            mBound = false;
        }
    };

}
