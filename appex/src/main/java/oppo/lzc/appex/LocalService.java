package oppo.lzc.appex;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {
    private static final String TAG = "LocalService";
    private IBinder iBinder;
    public LocalService() {
        Log.d(TAG, "LocalService: ");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        iBinder = new LocalBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        iBinder = null;
        super.onDestroy();
    }

    class LocalBinder extends Binder {
        private static final String TAG = "LocalBinder";
        LocalService getService() {
            return LocalService.this;
        }
        String getName() {
            return "LocalService binder";
        }
        boolean setName(String name) {
            Log.d(TAG, "setName: " + name);
            return true;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: intent " + intent);
        return iBinder;
    }
    
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: ");
        super.onRebind(intent);
    }
}
