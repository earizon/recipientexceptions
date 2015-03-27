Have you ever worked with Web MVC frameworks? One important feature of such frameworks is that them all have a centralized entry-exit point that takes cares of common tasks (usually named "controller").

Recipient Exceptions is all about using a similar architecture and "something like similar" design patterns to work with program exceptions in parallel with normal control-flow. The idea is difficult to express with common english (especially for a non-native english speaker like me) but example code makes it really easy to understand.

Noneless, recipient exceptions can be applied to any final application, not just Web applications. Any application/framework supporting exception capture with a common entry-exit point. A controller in the MVC parlance or a main-loop in the Desktop GUI parlance, or a multithread application with a set of unique control points for each thread is probably a suitable aplication for recipient exceptions.

As the documentation explains, recipient exceptions are not suitable for base/generic libraries, just for final apps that run inside a "real context". The "real context" terms means there will be final users, probably computer agnostic, using the final application.

It consists of a micro-library (some tens/hundred lines of code depending on the language implementation) that contains the "kernel" api an example working code and an easy to understand tutorial to show how it works in real world circumstances. Implementations exists for Java and PHP, since those are the languages I'm used to. Still implementing it for other languages is just straight-forward.

More importantly, with a "bit of trainning" the recipient exception micro-library framework can be gradually integrated into existing code. It requires no more efforts that integrating a logging library. It can be integrated step-by-step.

Postcript:
> As of April 2008 I'm studying to write a wrap-library around the C POSIX signal mechanism that mimics the human-friendly recipient Exception mechanism. Still it's not a priority in my TODO list. So it means I could do it tomorrow ... or never.