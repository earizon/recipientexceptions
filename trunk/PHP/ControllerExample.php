<?

# This file is an example of how to use recipientExceptions for day to day work.
# Notice the main try catch takes care of exceptions raised, while the rest of the
# code just clasify and raises the recipient exceptions.

require("recipientException.php");

# The prefix form is used to notice this is data sent from an outside "form".
# That means the value is untrusted, maybe wrong, null, non-sense, et ce tera,...
$action = $_REQUEST["formAction"];
						try{                         #  * * * MAIN TRY-CATCH * * *
if ($action == "letsCheckPassword"){
	# again the form prefix indicate the value can be wrong,
	# malicious ("SQL injection code"),....
	$password=getPasswordForUserName($_REQUEST["formUserName"]);
	if ($password != $_REQUEST["formPassword"])
		throw new userException(
			"user/password missmatch",	# description
			"",				# detail (if needed)
            "Please check your entries");	# Possible solution
	# If we arrive to this point (no recipientExceptions where raised)
	# "everything" is OK.
	return "OK";
} else if ($action == "letsGetStateAsAJAX"){
	# notice getInternalState() will raise the appropiate exceptions
	# and "jump" to the main catch block. So the next
	# lines concentrate in normal flow (no exceptions) making
	# code much cleaner and easier to read/understand.
	$internalState = getInternalState();
	header("Content-Type: text/javascript; charset=utf-8");
	print "({\n";
	print "'counter':10,\n";
	print "'state':".$internalState.",\n";
	print "'nextAllowedStates':{1,2,7},\n";
	print "})\n";
} else if ($action == "lestDoSomeBussinessLogic"){
	# Here we would parse input and invoque our bussiness layer
	# The bussiness layer would throw whatever recipientException it considers
	# Our bussiness layer MUST TRUST the controller is smart enough to
	# give an appropiate treatement to the recipientException.
	# That means in practice complex bussiness tasks must be
	# divided in simpler tasks that flows through the Controller...
	# And that's always a good thing to do!!!
} else{
	throw new implementationException(
		"$action not valid",	# description
		"$action:".$action,	    # detail
		"check formAction value sent ".
		"in GET/POST form requests, contact john@acme.com"); # Possible solution
}
						}catch(recipientException $e){ # * * * MAIN TRY-CATCH * * *
	# The controller decides what to do with the recipientExceptions raised
	# anywhere in our code. We can easely change the policy in a centrilized 
	# catch block. 
	if(get_class($e) == 'userException'){
		print $e;
	}else if (get_class($e) == 'administratorException' ||
	 	get_class($e) == 'implementationException'){
		print "An exception was raised. Check with your system administrator";
		error_log("".$e);
	}
						}

function getPasswordForUserName(formUserName){
	# We could check for malicious SQL injection code and throw an
	# administratorException if appropiate. Notice administrators are in
	# charge of security, so we would directly raise an administrator Exception
	# with descriptive security advice.
	checkFormUserName(formUserName);        # Not implemented in example code.
	getDataBaseConnectionFromPool();
	getPasswordForUserName(formUserName);	# Not implemented in example code.
}

function getDataBaseConnectionFromPool(){
	# Do whatever is appropiate.
	# ...
	if (!connection) throw new administratorException(
 		"Database connection closed",   # description
		"ddbb URL: "+$ddbbURL,          # detail
		"reconnect please");            # (possible) solution
}

function getInternalState(){
	#...
	# A user or administrator must never be in charge of non-valid
	# internal states. It's up to the soft. developer to avoid it.
	# Our main try-catch will capture this, notify the user with a polite
	# explanation and log the error so the system administrator
	# can contact the soft. developer to fix the problem.
	if (internalState<0 and internalState>10) 
		throw new implementationException(
		"internalState non valid",	# description
		"internalState=".$internalState,# detail
		"check object creation code, contact mike@acme.com");	# (possible) solution
	# ...
	return internalState;  # Not implemented in example code.
}

?>
