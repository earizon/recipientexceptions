package recipientException;

public class implementationException extends recipientException {

	private static final long serialVersionUID = 1L;

	public implementationException(String description, String details, String solution) {
        super(description, details, solution);
    }

    public implementationException(String description, String details, String solution, Throwable t) {
    	super(description, details, solution, t);
    }
}

