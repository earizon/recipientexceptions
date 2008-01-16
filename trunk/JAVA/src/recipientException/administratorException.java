package recipientException;

public class administratorException extends recipientException {

	private static final long serialVersionUID = 1L;

	public administratorException(String description, String details, String solution) {
        super(description, details, solution);
    }

    public administratorException(String description, String details, String solution, Throwable t) {
    	super(description, details, solution, t);
    }
}

