package stylist.com.glamhub.Feedback;

/**
 * Created by OSCAR on 9/10/2017.
 */

public class Reviews {
    String time,message,userKey,userId,username,status;

    public Reviews() {
    }


    public Reviews(String time, String message, String userKey, String userId, String username) {
        this.time = time;
        this.message = message;
        this.userKey = userKey;
        this.userId = userId;
        this.username = username;
    }

    public Reviews(String message, String userKey, String status) {
        this.message = message;
        this.userKey = userKey;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
