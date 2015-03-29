package apinetflix.exception;

public class UserException extends Exception {
    private String message = "User Exception";

    public UserException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
