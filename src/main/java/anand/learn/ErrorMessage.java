package anand.learn;

import java.time.LocalDateTime;

public class ErrorMessage {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorMessage() {
    }

    public ErrorMessage(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
