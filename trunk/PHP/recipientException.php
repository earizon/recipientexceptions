<?php
// By enrique.arizonbenito@gmail.com
// recipientExceptions are designed to make code cleaner and easier to read.
// As soon as a new exception or error is triggered we will try to trap it
// and classify as a a user, administrator or implementation Exception and
// propagate it "up the stack" to the proper layer:
// - User Exceptions are those originated by an "external" user error, like sending non-correct
//   or empty values. We will try to return such exceptions to the "external" user.
//   Our app is not responsible of user error and will take no further acctions.
// - Administrator Exceptions are those originated by a network problem, server shutdown,
//   or any other problem that must be solved by the proper system administrator.
//   Our app will try to restablish connection or resources after a "sensible" time and
//   (ideally) send an message/email to the system administrator.
// - Implementation Exceptions are those originated by an error in our code, ussually
//   a non-valid state for a variable, or an impossible logical condition.
//   (can be related to "assert" checks). Such Exceptions ought never be raised in final
//   code, just beta/non-finished one.
//
//   Recipient Exceptions must be used for final app code, not base libraries, since there
//   is no way "a priory" for a general purpose library to known, for example, whether an 
//   empty string is caused by an user mistake or a implementation/programming mistake. Base 
//   libraries must use the normal (origin) exceptions, (I/O Error, "null exceptions",...).
//   Final apps (production code) must generate recipientExceptions.

class recipientException extends Exception {
	protected $description;
	protected $detail;
	protected $solution;

	function __construct($description, $detail, $solution) {
		$this->description = $description;
		$this->detail      = $detail;
		$this->solution    = $solution;
	}

	public function __toString() {
		return 	"description: ".$this->description.",".
			"detail: ".$this->detail.",".
			"solution: ".$this->solution;
	}
}

class implementationException extends recipientException{
}

class administratorException extends recipientException{
}

class userException extends recipientException{
}

?> 
