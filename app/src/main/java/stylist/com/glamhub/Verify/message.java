package stylist.com.glamhub.Verify;

/**
 * Created by OSCAR on 11/1/2017.
 */

public class message {
    String uniqueId;
    String status;

    public message() {
    }

    String msg;

    public message(String uniqueId, String status, String msg) {
        this.uniqueId = uniqueId;
        this.status = status;
        this.msg = msg;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
