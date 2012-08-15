package tk.eugenehp.scanner;

 import tk.eugenehp.errors.ErrorReporter;
 import com.dd.plist.NSDictionary;
 import com.dd.plist.PropertyListParser;
 import java.io.File;
 import java.util.Arrays;
 import java.util.Set;
 import java.util.TreeSet;
 import java.util.zip.ZipEntry;
 import java.util.zip.ZipFile;

import javax.swing.JOptionPane;
 
 public class ApplicationIdFinder
 {
   private ErrorReporter errorReporter;
 
   public ApplicationIdFinder(ErrorReporter errorReporter)
   {
	   this.errorReporter = errorReporter;
   }
 
   public String findInAppFile(File file) {
	   String applicationId = null;
     try {
    	 ZipFile zipFile = new ZipFile(file);
    	 ZipEntry zipEntry = zipFile.getEntry("iTunesMetadata.plist");
    	 NSDictionary rootDict = (NSDictionary)PropertyListParser.parse(zipFile.getInputStream(zipEntry));
    	 applicationId = rootDict.objectForKey("itemId").toString();
    	 zipFile.close();
     } catch (Exception e) {
//    	 JOptionPane.showMessageDialog(null, e.getMessage(), "IPA finder [ERROR]", 2);
    	 this.errorReporter.reportThrowable(e);
     }
     	return applicationId;
   }
 
   public Set<String> findInDirectories(Set<File> directories) {
	   Set<File> listOfFiles = new TreeSet();
	   Set applicationIds = new TreeSet();
    
	   for (File directory : directories) {
		   	listOfFiles.addAll(Arrays.asList(directory.listFiles(new IPhoneFileFilter())));
	   }
   
	   for (File file : listOfFiles) {
		   	String applicationId = findInAppFile(file);
		   	if (applicationId != null) {
//		   		JOptionPane.showMessageDialog(null, applicationId, "IPA finder", 2);
		   		applicationIds.add(applicationId);
		   	}
	   }
	   return applicationIds;
   }
 }