import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UD_Installer {
  public static void main(String[] args) {
	  
	//Creates Java GUI for the sake of ease. Simple UI since it's a work program
    final JComboBox<String> systems = new JComboBox<>();
    systems.addItem("System1");
    systems.addItem("System2");
    systems.addItem("System5");
    systems.addItem("System6");
    systems.addItem("System7");
    systems.addItem("System9");
    systems.addItem("System10");
    systems.addItem("System12");
    systems.addItem("System14");
    systems.addItem("System15");
    systems.addItem("System16");
    systems.addItem("System17");
    systems.addItem("System18");
    systems.addItem("System19");
    systems.addItem("System20");
    systems.addItem("System21");
    systems.addItem("System22");
    systems.addItem("System24");
    systems.addItem("System30");
    systems.addItem("System34");
    systems.setEditable(true);
    JPanel steps = new JPanel();
    JPanel firstStep = new JPanel();
    JPanel secondStep = new JPanel();
    final JFrame frame = new JFrame("Multi Universal Desktop Installer 1.0");
    frame.setSize(1200, 300);
    frame.setDefaultCloseOperation(3);
    frame.setVisible(true);
    frame.setLayout(new GridLayout(3, 1));
    frame.add(steps);
    frame.add(firstStep);
    frame.add(secondStep);
    Button go = new Button("Go");
    Button go2 = new Button("Go");
    Label stepInstruction = new Label("Please do each step in order to complete the process. First step requires you install Universal desktop for your system. Second step requires your username to find Universal desktop install");
    Label instruction = new Label("Step 1: Please select a system to download from");
    Label instruction2 = new Label("Step 2: Please enter your computer's username");
    final JTextField username = new JTextField("");
    username.setPreferredSize(new Dimension(150, 20));
    steps.add(stepInstruction);
    firstStep.add(instruction);
    firstStep.add(systems);
    firstStep.add(go);
    secondStep.add(instruction2);
    secondStep.add(username);
    secondStep.add(go2);
    
    //Pulls up install page for UD. Version 1.1 was able to install the files automatically but that code was lost to a hard drive crash (the .exe exists still)
    go.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            String selectedSystem = (String)systems.getSelectedItem();
            System.out.println(selectedSystem);
            try {
              Runtime.getRuntime().exec("C:\\Program Files\\Internet Explorer\\iexplore.exe https://" + 
                  selectedSystem + ".pos.infogenesisasp.com/infogenesis/install/universaldesktop/");
            } catch (Exception e) {
              e.printStackTrace();
            } 
            frame.validate();
          }
        });
    
    //Second Go which starts the process of transfer folders and creates shortcut
    go2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            String selectedSystem = (String)systems.getSelectedItem();
            String Username = username.getText();
            File directoryPath = new File("C:\\Users\\lynns02\\AppData\\Local\\Apps\\2.0");
            Path Install = null;
            Path Install2 = null;
            Path Documents = Paths.get("C:\\Users\\" + Username + "\\Universal Desktop\\" + selectedSystem, new String[0]);
            String tion = "";
            String exe = "";
            String[] contents = directoryPath.list();
            for (int i = 0; i < contents.length; i++) {
              if (contents[i].contains(".")) {
                directoryPath = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Apps\\2.0\\" + contents[i]);
                File random = directoryPath;
                String[] contents2 = directoryPath.list();
                directoryPath = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Apps\\2.0\\" + contents[i] + "\\" + contents2[0]);
                File random2 = directoryPath;
                String[] contents3 = directoryPath.list();
                for (int b = 0; b < contents3.length; b++) {
                  if (contents3[b].contains("exe")) {
                    exe = contents3[b];
                    Install2 = Paths.get("C:\\Users\\" + Username + "\\AppData\\Local\\Apps\\2.0\\" + contents[i] + "\\" + contents2[0] + "\\" + contents3[b], new String[0]);
                  } 
                  if (contents3[b].contains("tion")) {
                    tion = contents3[b];
                    Install = Paths.get("C:\\Users\\" + Username + "\\AppData\\Local\\Apps\\2.0\\" + contents[i] + "\\" + contents2[0] + "\\" + contents3[b], new String[0]);
                    directoryPath = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Apps\\2.0\\" + contents[i] + "\\" + contents2[0]);
                    b = contents3.length;
                  } 
                } 
                try {
                  UD_Installer.createNewFolder(Username, selectedSystem, tion);
                  UD_Installer.createNewFolder(Username, selectedSystem, exe);
                } catch (IOException e1) {
                  e1.printStackTrace();
                } 
                try {
                  UD_Installer.copyDirectory(Install.toString(), Documents + "\\" + tion);
                  UD_Installer.copyDirectory(Install2.toString(), Documents + "\\" + exe);
                  UD_Installer.fileDelete(Install.toString(), true);
                  File temp = new File(Install.toString());
                  String[] tempList = temp.list();
                  if (tempList != null)
                    for (int a = 0; a < tempList.length; a++)
                      UD_Installer.fileDelete(Install + "\\" + tempList[a], false);  
                  UD_Installer.fileDelete(Install2.toString(), true);
                  for (int j = 0; j < contents3.length; j++) {
                    if (!contents3[j].contains("tion") && !contents3[j].contains("exe"))
                      UD_Installer.fileDelete(directoryPath + "\\" + contents3[j], false); 
                  } 
                  UD_Installer.fileDelete(random2.toString(), false);
                  UD_Installer.fileDelete(random.toString(), false);
                  File Shortcut = new File(Documents + "\\" + tion);
                  UD_Installer.createShortcut(Shortcut + "\\UniversalDesktop.exe", "C:\\Users\\" + Username + "\\Desktop\\" + selectedSystem + ".lnk");
                } catch (IOException e) {
                  e.printStackTrace();
                } 
                i = contents.length;
              } 
            } 
          }
        });
  }
  
  //Creates new folders in user section to store versions of UD
  public static void createNewFolder(String user, String system, String folderName) throws IOException {
    String fileName = "C:\\Users\\" + user + "\\Universal Desktop";
    String fileName2 = "C:\\Users\\" + user + "\\Universal Desktop\\" + system;
    String fileName3 = "C:\\Users\\" + user + "\\Universal Desktop\\" + system + "\\" + folderName;
    Path path = Paths.get(fileName, new String[0]);
    Path path2 = Paths.get(fileName2, new String[0]);
    Path path3 = Paths.get(fileName3, new String[0]);
    if (!Files.exists(path, new java.nio.file.LinkOption[0])) {
      Files.createDirectory(path, (FileAttribute<?>[])new FileAttribute[0]);
      if (!Files.exists(path2, new java.nio.file.LinkOption[0])) {
        Files.createDirectory(path2, (FileAttribute<?>[])new FileAttribute[0]);
        if (!Files.exists(path3, new java.nio.file.LinkOption[0]))
          Files.createDirectories(path3, (FileAttribute<?>[])new FileAttribute[0]); 
      } 
    } else if (!Files.exists(path2, new java.nio.file.LinkOption[0])) {
      Files.createDirectory(path2, (FileAttribute<?>[])new FileAttribute[0]);
      if (!Files.exists(path3, new java.nio.file.LinkOption[0]))
        Files.createDirectories(path3, (FileAttribute<?>[])new FileAttribute[0]); 
    } else if (!Files.exists(path3, new java.nio.file.LinkOption[0])) {
      Files.createDirectories(path3, (FileAttribute<?>[])new FileAttribute[0]);
    } else {
      fileDelete(path3.toString(), true);
    } 
  }
  
  //Copies installed UD files to new folders. Allowing more versions of UD to be installed
  public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
    /*Files.walk(Paths.get(sourceDirectoryLocation, new String[0]), new java.nio.file.FileVisitOption[0])
      .forEach(source -> {
          Path destination = Paths.get(paramString1, new String[] { source.toString().substring(paramString2.length()) });
          try {
            Files.copy(source, destination, new java.nio.file.CopyOption[0]);
          } catch (IOException iOException) {}
        });*/
  }
  
  //Deletes files as UD requires it
  public static void fileDelete(String folder, boolean HelpImages) {
    File index = new File(folder);
    File indexHelp = null, indexImages = null;
    if (HelpImages) {
      indexHelp = new File(String.valueOf(folder) + "\\Help");
      indexImages = new File(String.valueOf(folder) + "\\Images");
      String[] entries3 = indexHelp.list();
      if (entries3 != null) {
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = entries3).length, b = 0; b < i; ) {
          String s = arrayOfString[b];
          File currentFile = new File(indexHelp.getPath(), s);
          currentFile.delete();
          b++;
        } 
      } 
      String[] entries2 = indexImages.list();
      if (entries2 != null) {
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = entries2).length, b = 0; b < i; ) {
          String s = arrayOfString[b];
          File currentFile = new File(indexImages.getPath(), s);
          currentFile.delete();
          b++;
        } 
      } 
      indexHelp.delete();
      indexImages.delete();
    } 
    String[] entries = index.list();
    if (entries != null) {
      byte b;
      int i;
      String[] arrayOfString;
      for (i = (arrayOfString = entries).length, b = 0; b < i; ) {
        String s = arrayOfString[b];
        File currentFile = new File(index.getPath(), s);
        currentFile.delete();
        b++;
      } 
    } 
    index.delete();
  }
  
  //creates Shortcut from new folders. Helps user as it makes the process a 1 click process
  public static void createShortcut(String folder, String system) throws IOException {
    //ShellLink.createLink(folder, system);
  }
}