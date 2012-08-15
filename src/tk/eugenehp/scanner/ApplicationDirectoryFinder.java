package tk.eugenehp.scanner;
import java.io.File;
 import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JOptionPane;
 
public class ApplicationDirectoryFinder
{
 	 private int platform;
 
 	 public ApplicationDirectoryFinder(int platform)
 	 {
	 	 this.platform = platform;
 	 }
 
 	 public Set<File> findDirectories()
 	 {
	 	 String[] paths;
	 	 switch (this.platform) {
	 	 case 1:
		 	paths = new String[] { "\\My Documents\\My Music\\iTunes\\Mobile Applications", "\\My Documents\\My Music\\iTunes\\iTunes Media\\Mobile Applications" };
		 	return directoriesForPathsThatExist(paths);
	 	 case 2:
	 		 paths = new String[] { "/Music/iTunes/Mobile Applications", "/Music/iTunes/iTunes Media/Mobile Applications" };
	 		 return directoriesForPathsThatExist(paths);
 	 }
	 	 return new LinkedHashSet<File>();
 	 }
 
	protected Set<File> directoriesForPathsThatExist(String[] paths)
	{
	 	 String home = System.getProperty("user.home");
	 	 Set<File> directories = new LinkedHashSet<File>();
	 	 for (int i = 0; i < paths.length; i++) {
		 	 File file = new File(home + paths[i]);
		 	 if (file.isDirectory()) {
			 	 directories.add(file);
		 	 }
	 	 }
	 	 
	 	 return directories;
 	 }
}