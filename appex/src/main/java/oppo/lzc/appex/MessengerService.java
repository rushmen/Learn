package oppo.lzc.appex;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {
    private static final String TAG = "MessengerService";
    static HandlerThread handlerThread = new HandlerThread("my", Process.THREAD_PRIORITY_BACKGROUND);
    static {
        handlerThread.start();
    }
    public MessengerService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: intent " + intent);
        Messenger messenger = new Messenger(new Handler(handlerThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                if (msg != null) {
                    Bundle bundle = msg.getData();
                    Log.d(TAG, "handleMessage: receive from client " + bundle.getString("client"));

                    bundle.clear();
                    bundle.putString("server", "i'am server");
                    Message replayMsg = Message.obtain();
                    replayMsg.setData(bundle);

                    if (msg.replyTo != null) {
                        try {
                            msg.replyTo.send(replayMsg);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        return messenger.getBinder();
    }
}
