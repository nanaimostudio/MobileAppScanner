/*
 * August 2012
 * http:/www.eugenehp.tk
 * http://www.youtube.com/user/EugeneHPEugene
 * */

package tk.eugenehp;

import tk.eugenehp.errors.ErrorReporter;
import tk.eugenehp.errors.ErrorReporterFactory;
import tk.eugenehp.scanner.ApplicationDirectoryFinder;
import tk.eugenehp.scanner.ApplicationIdFinder;
import tk.eugenehp.scanner.PlatformDetector;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

public class IPAfinder extends JApplet
  implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private Set<File> appDirectories;
  private int fNumOfApps = 0;
  private Set<String> applicationIds;
  private URL fServerURL;
  private String fCallBack;
  private ErrorReporter errorReporter;
  private JLabel fStatusLabel;
  private JButton fRightButton;
  private JButton fLeftButton;
  private ImageIcon fStartIcon;
  private ImageIcon fYesIcon;
  private ImageIcon fSelectIcon;
  
  public void init()
  {
    this.errorReporter = new ErrorReporterFactory(getParameter("environment")).errorReporter();
    try
    {
      EventQueue.invokeAndWait(new Runnable() {
        public void run() {
          IPAfinder.this.initComponents();
        } } );
    } catch (Throwable throwable) {
      this.errorReporter.reportThrowable(throwable);
    }

    //TODO: add host and sha1 variables

	  this.fRightButton.setIcon(this.fStartIcon);
	  this.fRightButton.setActionCommand("start");
	  ResizeButtons();
	  this.fRightButton.setVisible(true);
	  this.fRightButton.requestFocusInWindow();
  }

  private void initComponents() {
    this.fStatusLabel = new JLabel();
    this.fRightButton = new JButton();
    this.fLeftButton = new JButton();
    try
    {
      this.fStartIcon = new ImageIcon(getClass().getResource("start.gif"));
      this.fSelectIcon = new ImageIcon(getClass().getResource("select.gif"));
      this.fYesIcon = new ImageIcon(getClass().getResource("yes.gif"));
    } catch (Exception e) {
      this.errorReporter.reportThrowable(e);
    }

    getContentPane().setLayout(null);

    this.fStatusLabel.setText("");
    getContentPane().add(this.fStatusLabel);
    this.fStatusLabel.setBounds(10, 10, 580, 17);

    this.fRightButton.setText("");
    this.fRightButton.setVisible(false);
    this.fRightButton.setBorderPainted(false);
    this.fRightButton.addActionListener(this);
    this.fRightButton.setBounds(455, 30, 130, 29);
    getContentPane().add(this.fRightButton);

    this.fLeftButton.setText("");
    this.fLeftButton.setVisible(false);
    this.fLeftButton.setBorderPainted(false);
    this.fLeftButton.addActionListener(this);
    this.fLeftButton.setBounds(381, 30, 60, 29);
    getContentPane().add(this.fLeftButton);

    getContentPane().setBackground(Color.WHITE);
  }

  public void finished() {
    this.fLeftButton.setVisible(false);
    this.fRightButton.setVisible(false);

    this.fStatusLabel.setText("Finished! Sent " + this.fNumOfApps + " apps to the server.");
    try
    {
      JSObject.getWindow(this).eval(this.fCallBack);
    } catch (JSException e) {
      this.errorReporter.reportThrowable(e);
    }
  }

  public void actionPerformed(ActionEvent event) {
    if (event.getActionCommand().equals("start")) {
      int platform = new PlatformDetector().detectedPlatform(); 
      this.appDirectories = autoDetectedAppDirectories(platform);
      
      if (!this.appDirectories.isEmpty())
      {
        this.fStatusLabel.setText("We detected an app folder at the default location. Do you want to use this folder?");

        this.fLeftButton.setIcon(this.fYesIcon);
        this.fLeftButton.setActionCommand("send");

        this.fRightButton.setIcon(this.fSelectIcon);
        this.fRightButton.setActionCommand("browse");
        ResizeButtons();
        this.fLeftButton.setVisible(true);
        this.fLeftButton.requestFocusInWindow();
      } else {
        this.fStatusLabel.setText("We could not auto-detect your app folder. Please manually select an apps folder.");
        this.fRightButton.setIcon(this.fSelectIcon);
        this.fRightButton.setActionCommand("browse");
        ResizeButtons();
      }
    } else if (event.getActionCommand().equals("browse")) {
      this.appDirectories = chooseAppDirectories();
      if (!this.appDirectories.isEmpty())
      {
        this.fLeftButton.setVisible(false);
        this.fRightButton.setVisible(false);
        this.fRightButton.requestFocusInWindow();
        this.fStatusLabel.setText("Scanning Apps...");

        GetScanWorker().start();
      }
    } else if (event.getActionCommand().equals("send"))
    {
      this.fLeftButton.setVisible(false);
      this.fRightButton.setVisible(false);
      this.fRightButton.requestFocusInWindow();
      this.fStatusLabel.setText("Scanning Apps...");

      GetScanWorker().start();
    }
  }

  private Set<File> autoDetectedAppDirectories(int platform)
  {
    return new ApplicationDirectoryFinder(platform).findDirectories();
  }

  private Set<File> chooseAppDirectories()
  {
    while (true)
    {
      JFileChooser fc;
      if (this.appDirectories.isEmpty()) {
        fc = new JFileChooser();
      }
      else {
        File defaultDirectory = null;
        for (File appDirectory : this.appDirectories) {
          defaultDirectory = appDirectory;
        }
        System.out.print(defaultDirectory);
        fc = new JFileChooser(defaultDirectory);
      }

      fc.setFileSelectionMode(1);

      int dialogReturn = fc.showOpenDialog(null);

      if (dialogReturn == 0) {
        File selectedFile = fc.getSelectedFile();

        if (!selectedFile.isDirectory()) {
          JOptionPane.showMessageDialog(null, "The folder you selected is not valid, please try again.", "IPA finder", 2);
        } else {
          Set<File> chosenDirectories = new TreeSet<File>();
          chosenDirectories.add(selectedFile);
          return chosenDirectories;
        }
      } else {
        return new TreeSet<File>();
      }
    }
  }

  private void GetApps() {
    this.applicationIds = new ApplicationIdFinder(this.errorReporter).findInDirectories(this.appDirectories);
    this.fNumOfApps = this.applicationIds.size();
  }

  private Thread GetScanWorker()
  {
    return new Thread() {
      public void run() {
        IPAfinder.this.GetApps();

        if (IPAfinder.this.fNumOfApps > 0)
        {
        	IPAfinder.this.fLeftButton.setVisible(false);
        	IPAfinder.this.fRightButton.setVisible(false);
        	IPAfinder.this.fStatusLabel.setText("Sending Apps...");

        	IPAfinder.this.GetSendWorker().start();
        } else {
        	IPAfinder.this.fLeftButton.setVisible(false);

        	IPAfinder.this.fRightButton.setIcon(IPAfinder.this.fSelectIcon);
        	IPAfinder.this.fRightButton.setActionCommand("browse");
        	IPAfinder.this.ResizeButtons();
        	IPAfinder.this.fRightButton.setVisible(true);

        	IPAfinder.this.fStatusLabel.setText("No apps were found in that folder.");
        }
      } } ;
  }

  private boolean SendApps() {
    String queryString = "";
    String output = "";
    boolean success = false;
    
    for (String applicationId : this.applicationIds) {
      output = output + applicationId + "\n"; 
    }
    
    JOptionPane.showMessageDialog(null, output, "IPA finder", 2);
    try
    {
      HttpURLConnection server = (HttpURLConnection)this.fServerURL.openConnection();
      server.setRequestMethod("POST");
      server.setDoOutput(true);

      OutputStreamWriter out = new OutputStreamWriter(server.getOutputStream());

      queryString = "apps=" + URLEncoder.encode(output, "UTF-8");																// + '&' + "user=" + URLEncoder.encode(this.fUser, "UTF-8");

      out.write(queryString);
      out.flush();

      if (server.getResponseCode() == 200)
        success = true;
      else {
        success = false;
      }
      out.close();

      return success;
    } catch (Exception e) {
      this.errorReporter.reportThrowable(e);
      JOptionPane.showMessageDialog(null, e.getMessage());
    }return false;
  }

  private Thread GetSendWorker()
  {
    return new Thread() {
      public void run() {
    	  IPAfinder.this.SendApps();
    	  IPAfinder.this.finished();
      }
    };
  }

  private void ResizeButtons()
  {
    int appletWidth = getWidth();
    int appletHeight = getHeight();
    try
    {
      int iconHeight;
      int iconWidth;
	if (this.fRightButton.getIcon() == this.fStartIcon) {
        iconWidth = this.fStartIcon.getIconWidth();
        iconHeight = this.fStartIcon.getIconHeight();
      } else {
        iconWidth = this.fSelectIcon.getIconWidth();
        iconHeight = this.fSelectIcon.getIconHeight();
      }

      int iconX = appletWidth - (iconWidth + 10);
      int iconY = appletHeight - (iconHeight + 10);
      this.fRightButton.setBounds(iconX, iconY, iconWidth, iconHeight);

      iconWidth = this.fYesIcon.getIconWidth();
      iconHeight = this.fYesIcon.getIconHeight();
      iconX -= iconWidth + 10;

      this.fLeftButton.setBounds(iconX, iconY, iconWidth, iconHeight);
    } catch (Exception e) {
      this.errorReporter.reportThrowable(e);
    }
  }
}