package oppo.learn;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    static HandlerThread handlerThread = new HandlerThread("sf");
    static {
        handlerThread.start();
    }
    private Messenger mServerMessenger = null;
    Messenger mClientMessenger = new Messenger(new Handler(handlerThread.getLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                Bundle bundle = msg.getData();
                Log.d(TAG, "handleMessage: from server " + bundle.getString("server"));
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, EventBusAc.class));
        //bindMessengerService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(mServiceConnection);
        }
    }

    protected void bindMessengerService() {
        Log.d(TAG, "bindMessengerService: ");
        Intent intent = new Intent("oppo.lzc.appex.messengerservice");
        intent.setPackage("oppo.lzc.appex");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private boolean isBound = false;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            mServerMessenger = new Messenger(service);
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("client", "i'am client");
            message.setData(bundle);
            isBound = true;
            message.replyTo = mClientMessenger;
            try {
                mServerMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServerMessenger = null;
            isBound = false;
        }
    };
}
