package oppo.learn;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusAc extends AppCompatActivity {

    private static final String TAG = "EventBusAc";
    @BindView(R.id.button1) Button button1;
    @BindView(R.id.button2) Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                EventBus.getDefault().post(new MessageEvent("1-1"));
                break;
            case R.id.button2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new MessageEvent("2-1"));
                    }
                }).start();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void OnMessage1(MessageEvent event) {
        Log.d(TAG, "OnMessage1: " + event.getMessage());
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void OnMessage2(MessageEvent event) {
        Log.d(TAG, "OnMessage2: " + event.getMessage());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnMessage3(MessageEvent event) {
        Log.d(TAG, "OnMessage3: " + event.getMessage());
    }
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void OnMessage4(MessageEvent event) {
        Log.d(TAG, "OnMessage4: " + event.getMessage());
    }
}
