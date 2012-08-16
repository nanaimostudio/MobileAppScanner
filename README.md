MobileAppScanner
================

Descriptions: Scans user's folder for apps.

[AppScanner Screencast](http://www.youtube.com/watch?v=Hos32MiPfSY)

\<applet code="tk.eugenehp.IPAfinder.class" archive="ipa.jar" height="70" width="600" mayscript="true"\>

\<param name="environment" value="production"><param name="host" value="http://localhost" /\>

\<param name="user" value="username" /\>\<param name="callback" value="window.location.href='/done';" /\>

\<param name="hash" value="123456" /\>

\</applet\>


callback is the webpage where you want the applet to redirect the user and there you can place some kind of session id or user id

***

NOTE:
This info is found in IPA.  Maybe it can be used in future development of this scanner.
http://a300.phobos.apple.com/us/r1000/083/Purple/v4/98/32/08/98320833-4852-5b0c-059e-9162020cf148/Icon-production.png