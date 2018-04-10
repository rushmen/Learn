package oppo.learn;

/**
 * Created by 80185996 on 2018/4/10.
 */

public class MessageEvent {
    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

}
