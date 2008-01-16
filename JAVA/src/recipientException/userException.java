package recipientException;

public class userException extends recipientException {

	private static final long serialVersionUID = 1L;

	public userException(String description, String details, String solution) {
        super(description, details, solution);
    }

    public userException(String description, String details, String solution, Throwable t) {
    	super(description, details, solution, t);
    }
}

