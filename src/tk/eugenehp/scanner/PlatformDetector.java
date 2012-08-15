package tk.eugenehp.scanner;
 import java.io.File;
 
 public class PlatformDetector
 {
   public static final int kWindowsPlatform = 1;
   public static final int kUnixPlatform = 2;
 
   public int detectedPlatform()
   {
	   if (File.separatorChar == '\\') {
		   return 1;
     }
	   return 2;
   }
 }