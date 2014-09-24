KingsDomain
===========
How to split land among the King's daughters.

Overview:
This project is for CS6491 at Georgia Tech.  
The idea is how to split any shape into any arbitrary equal number of parts (in this case it's 4).

Required:
Java 1.7
Apache Ant

Running Instructions:

1.  Install Apache Ant (required to run this).
2.  In command line, you can run any one of 3 examples: 'ant TriangleExample', 'ant FourPointExample', or 'ant SixPointExample'.
3.  You can click and drag points around on the screen, and as long as your shape is convex, it should be able to find 4 equal sections.

Possible Issues:
1.  You might end up with an issue like "java.security.accesscontrolexception access denied (java.util.propertypermission user.dir read)".  I followed the instructions here and that fixed the issue when testing on another computer: http://stackoverflow.com/questions/18953573/error-java-security-accesscontrolexception-access-denied-java-util-propertyperm

Notes:
This is not perfect, sometimes it cannot find a proper cut across the shape, thus you could see 0, 1, or 2 cuts in specific situations.  Due to the time constraint on the project, we haven't been able to fix it.  The 3 videos show our app working "mostly".  You will notice some videos show only 2 cuts in certain shapes instead of 3.
