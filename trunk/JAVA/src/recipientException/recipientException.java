package recipientException;

public class recipientException extends Exception {

//	 By enrique.arizonbenito@gmail.com
//	 recipientExceptions are designed to make code cleaner and easier to read.
//	 As soon as a new exception or error is triggered we will try to trap it
//	 and classify as a a user, administrator or implementation Exception and
//	 propagate it "up the stack" to the proper layer:
//	 - User Exceptions are those originated by an "external" user error, like sending non-correct
//	   or empty values. We will try to return such exceptions to the "external" user.
//	   Our app is not responsible of user error and will take no further acctions.
//	 - Administrator Exceptions are those originated by a network problem, server shutdown,
//	   or any other problem that must be solved by the proper system administrator.
//	   Our app will try to restablish connection or resources after a "sensible" time and
//	   (ideally) send an message/email to the system administrator.
//	 - Implementation Exceptions are those originated by an error in our code, ussually
//	   a non-valid state for a variable, or an impossible logical condition.
//	   (can be related to "assert" checks). Such Exceptions ought never be raised in final
//	   code, just beta/non-finished one.
//
//	   Recipient Exceptions must be used for final app code, not base libraries, since there
//	   is no way "a priory" for a general purpose library to known, for example, whether an 
//	   empty string is caused by an user mistake or a implementation/programming mistake. Base 
//	   libraries must use the normal (origin) exceptions, (I/O Error, "null exceptions",...).
//	   Final apps (production code) must generate recipientExceptions.

	private static final long serialVersionUID = 1L;

    public Throwable t;
    public String description;
    public String details;
    public String solution;

    public recipientException(String description, String details, String solution) {
        this.description = description;
        this.details = details;
        this.solution = solution;
    }

    public recipientException(String description, String details, String solution, Throwable t) {
    	this.description = description;
        this.details = details;
        this.solution = solution;
        this.t = t;
    }

    // While member fields are public we try to use getter so api doesn't
    // change if code grows more complex.

    public String getThrowableText() {
        return ("Received throwable with Message: "+ t.getMessage());
    }

    public String getDescriptionText() { return description; }

    public String getDetails(){ return details; }

    public String getSolution(){ return solution; }

    public String toString(){
    	String result = this.getClass().toString();
    	result += "\n description: "+ this.getDescriptionText();
    	result += "\n detail: "+ this.getDetails();
    	result += "\n solution: " + this.getSolution();
    	return result;
    }
}

