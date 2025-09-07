package pl.mirekgab.task1.exception;

public class AppRuntimeException extends RuntimeException{
    private int httpStatus;
    private String message;

    public AppRuntimeException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
