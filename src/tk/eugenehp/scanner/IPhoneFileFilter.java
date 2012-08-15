package tk.eugenehp.scanner;
 
 import java.io.File;
 import java.io.FileFilter;
 
 public class IPhoneFileFilter
   implements FileFilter
 {
   public boolean accept(File pathname)
   {
	   return (getExtension(pathname) != null) && (getExtension(pathname).equalsIgnoreCase("ipa"));
   }
 
   private String getExtension(File f) {
	   String ext = null;
	   String s = f.getName();
	   int i = s.lastIndexOf('.');
 
	   if ((i > 0) && (i < s.length() - 1)) {
		   ext = s.substring(i + 1).toLowerCase();
     }
 
	   return ext;
   }
 }