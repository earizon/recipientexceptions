package examples;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import recipientException.*;

/*
 * This file is an example of how to use recipientExceptions in a servlet acting as 
 * application Controller.
 * Notice the main try catch takes care of exceptions raised, while the rest of the
 * code just clasify and raises the recipient exceptions.
 */

public class servletController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static int internalState = 1; // for example purposes. 

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getParameter("formAction"); // get the task to do
                                                           boolean repeat = true;
							   while(repeat) {
							   repeat = false;
                                                           try{ //  * * * MAIN TRY-CATCH * * *
        Writer writer;
                                        try { 
	   writer = response.getWriter(); 
                                        }catch(IOException e){
           /*
	    * Final apps are not interested in IOExceptions, they are interested
	    * in administrators to be notified as soon as possible so they can 
	    * change a switch, restore routing tables, ....
	    * Let's transform it:
	    */
	    throw new administratorException(
	        "Webapp cound't answer client request due to "+    // description
		e.toString(),
		"",                                                // detail
		"See command line tools for monitoring network:\n"+// Possible Solution
		"  netstat, route, traceroute, lsof "              // (any help will make admin life
		                                                   //  much easier)
	    );
					}
        if (action == null) throw new 
            implementationException(
                "formAction GET/POST parameter NULL/EMPTY", // description
                "request:"+request.getQueryString(),        // detail
                "check web form");                          // (Possible) solution
        if("doCheckPassword".equals(action)){
            action_CheckPassword(request, response);
            // If we arrive to this point (no recipientExceptions where raised)
            // "everything" is OK.
            repeat = true;
	    action = "renderAjaxSkeleton";
	    continue; // <--- Restart main loop (repeat = true;)
        } else if ("renderAjaxSkeleton".equals(action)){
	    // Here we would write "core" AJAX.
	} else if ("letsGetStateAsJSON".equals(action)){
            /*
             * notice getInternalState() will raise the appropiate exceptions
             * and "jump" to the main catch block. So the next 
             * lines concentrate in normal flow (no exceptions) making
             * code much cleaner and easier to read/understand.
             */
            int internalState = getInternalState();
            response.setHeader("Content-Type", "text/javascript; charset=utf-8");
            response.getWriter().write(
                "({\n"+
                "'counter':10,\n"+
                "'state':"+internalState+",\n"+
                "'nextAllowedStates':{1,2,7},\n"+
                "})\n");
        } else if ("doSomeBussinessLogic".equals(action)){
            /*
             * Here we would parse input and invoque our bussiness layer
             * The bussiness layer would throw whatever recipientException it considers
             * Our bussiness layer MUST TRUST the controller is smart enough to
             * give an appropiate treatement to the recipientException.
             * That means in practice complex bussiness tasks must be
             * divided in simpler tasks that flows through the Controller...
             * And that's always a good thing to do!!!
             */
        } else {
            throw new implementationException(
                "action parameter non valid",                        // description
                "request.getParameter(\"formAction\"):" + action,   // detail
                "Check web form");                                    // (Possible) solution
        }
                                                            }catch(recipientException e){
                                                              //  * * * MAIN TRY-CATCH * * *
        /*
         * The controller decides what to do with the recipientExceptions raised
         * anywhere in our code. We can easely change the policy in a centrilized 
         * catch block.
         */                        
        if ( e instanceof userException) {
            response.getWriter().write(e.toString());
        } else if (e instanceof administratorException ) {
	    this.log(e.toString);
	    // Send a mail to the admin.
	} else if ( e instanceof implementationException){
            this.log(e.toString());
	    // Log also e stack trace if appropiate.
        } else {
            // This point must never be reached now (2007-06-25)
        }
                                                            }
							    }
    }

    static String getPasswordForUserName(String formUserName)throws recipientException{
        /*
         * We could check for malicious SQL injection code and throw an
         * administratorException if appropiate. Notice administrators are in
         * charge of security, so we would directly raise an administrator Exception
         * with descriptive security advice.
         */
        String result="";
        checkFormUserName(formUserName);   // Not implemented in example code.
        return result;
    }

    static Connection getDataBaseConnectionFromPool() throws recipientException{
        java.sql.Connection connection = null;
        String ddbbURL = "";
                                       try{ 
        // Do whatever is appropiate.
        // ...
                                        }catch(IOException e){
	throw new administratorException(
	    "Server could not fetch a database connection from Pool due to"+e.toString,
	    "ddbbURL:"+ddbbURL,
	    "Check network connection, check pool settings in server.xml"
	     );
					}
        if (connection==null) throw new administratorException(
             "Database connection closed",   // description
            "ddbb URL: "+ddbbURL,           // detail
            "Check network connection to Server"); // (possible) solution
    }

    static void action_CheckPassword(HttpServletRequest request, HttpServletResponse response) 
    throws recipientException{
        /*
         * Again the form prefix indicate the value can be wrong,
         * malicious ("SQL injection code"),....
         */
        String password = getPasswordForUserName(request.getParameter("formUserName")); 
        if ( ! password.equals(request.getParameter("formPassword")))
            throw new userException(
                 "user/password missmatch",     // description
                  "",                             // detail (if needed)
                  "Please check your entries");// Possible solution    
    }

//        # ...
//        return internalState();
    static int getInternalState() throws recipientException{
        /*
         * A user or administrator must never be in charge of non-valid
         * internal states. It's up to the soft. developer to avoid it.
         * Our main try-catch will capture this, notify the user with a polite
         * explanation and log the error so the system administrator
         * can contact the soft. developer to fix the problem.
         */
        if (internalState<0 || internalState>10) 
            throw new implementationException(
                "internalState non valid",        // description
                "internalState="+internalState,    // detail
                "check object creation code, contact mike@acme.com"); // (possible) solution
        return internalState;
    }
}
