package terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//*********************************************************************************************
//*************************************** Parser Class ****************************************
//*********************************************************************************************
class Parser {
    //*******************************************************************
    // Class attributes *****
    //***********************
    private String commandName;
    private String[] args;
    private Map<String,Integer> cmds;
    private String homeDir = System.getProperty("user.dir");
    private String currDir = System.getProperty("user.dir");
 
    //*******************************************************************
    // Default Constructor to initialize commands *****
    //*************************************************
    Parser() {
        cmds = new HashMap<>();
        cmds.put("pwd", 0);
        cmds.put("ls", 0);
        cmds.put("ls -r", 1);
        cmds.put("cd", 1);
        cmds.put("cd ..", 1);
        cmds.put("rmdir *", 1);
        cmds.put("rmdir", 1);
        cmds.put("touch", 1);
        cmds.put("cp", 2);
        cmds.put("cp -r", 3);
        cmds.put("rm", 1);
    }
    
    //***************************************************************
    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user 
    public boolean parse(String input) {
        String[] analyze = input.split(" ");
        int length = analyze.length;
        int argsLen = length-1;
        
        commandName = analyze[0];
        args = new String[argsLen];
        for(int i=1; i<length; i++) {
            if(analyze[i].equals("-r") || analyze[i].equals("..") || analyze[i].equals("*")) {
                commandName += " " + analyze[i];
                continue;
            }
            else if((analyze[i].contains(":") || analyze[i].contains("\\")) && (commandName.equals("rm") || commandName.equals("cat"))) {
            	return false;
            }
            else if(!analyze[i].contains(":") && !commandName.equals("echo")) {
                args[i-1] = "";
                args[i-1] += currDir + "\\" + analyze[i];
                continue;
            }
            args[i-1] = analyze[i];
        }
        
        if(commandName.equals("cd") && argsLen <= cmds.get(commandName)) return true;
        else if ((commandName.equals("echo") || commandName.equals("mkdir")) && argsLen > 0) return true;
        else if(commandName.equals("cat") && argsLen <= 2 && argsLen>=1) return true;
        return cmds.get(commandName) != null && cmds.get(commandName) == argsLen;
    }
    
    //************************************************************
    // Functions for getters *****
    //****************************
    public String getCommandName() 
        {return commandName;}
    
    public String[] getArgs() 
        {return args;}
    
    public String getCurrDir()
        {return currDir;}
    
    //************************************************************
    // Functions for setters ******
    //*****************************
    public void setCurrentToHome() {
        currDir = homeDir;
    }
    public void setCurrent(String path) {
        currDir = path;
    }
}

//***********************************************************************************************
//************************************** Terminal Class *****************************************
//***********************************************************************************************
public class Terminal {
    Parser parser = new Parser();
    //Implement each command in a method, for example:
    public String pwd(){
        return parser.getCurrDir();
    }
    
    //******************************************************************************
    
    public void ls() {
    	String cmd = parser.getCommandName();
        File dir = new File(parser.getCurrDir());
        String items[] = dir.list();
        if(cmd.equals("ls")) {
            for(String item: items){
                System.out.println(item);
            }
        }
        else {
        	for(int i=items.length-1; i>=0; i--){
                System.out.println(items[i]);
            }
        }
    }
    
    //******************************************************************************
    
    public void cd() {
        String cmd = parser.getCommandName();
        String[] args = parser.getArgs();
        if(cmd.equals("cd") && args.length == 0) parser.setCurrentToHome();

        else if(cmd.equals("cd ..")) {
            String current = parser.getCurrDir();
            int index = current.lastIndexOf("\\");
            if(index == -1) {
                parser.setCurrent(current);
            }
            else {
                String path = current;
                path = current.substring(0, index);
                parser.setCurrent(path);
            }
        }

        else {
            Path path = Paths.get(args[0]);
            if(Files.exists(path)) parser.setCurrent(args[0]);
            else System.out.println(path + ": No such file");
        }
    }
    
    //******************************************************************************
    
    public void echo() {
    	String[] args = parser.getArgs();
    	for(String arg:args) {
    		System.out.print(arg + " ");
    	}
    	System.out.println();
    }
    
    //******************************************************************************
    
    public void mkdir() {
    	String[] args = parser.getArgs();
    	for(String arg:args) {
    		File file = new File(arg);
    		if(file.exists()) {
    			System.out.println("\"" + arg + "\" is already exists!");
    		}
    		else if(!file.mkdir()) {
    			System.out.println("\"" + arg + "\" cannot be created!");
    		}
    	}
    }
    
    //******************************************************************************
    
    public void rmdir() {
    	String cmd = parser.getCommandName();
    	if(cmd.equals("rmdir *")) {
    		String currDir = parser.getCurrDir();
        	File dir = new File(currDir);
            String items[] = dir.list();
            for(String item:items) {
            	File path = new File(currDir+"\\"+item);
            	String paths[] = path.list(); 
            	if(paths!=null && paths.length == 0) {
            		path.delete();
            	}
            }
    	}
    	else {
    		String[] args = parser.getArgs();
    		File dir = new File(args[0]);
            String paths[] = dir.list();
            if(paths!=null && paths.length == 0) {
        		dir.delete();
        	}
            else if(!dir.isDirectory()) {
            	System.out.println("Error: rmdir removes directories only!");
            }
            else {
            	System.out.println("\"" + args[0] + "\" is not empty!");
            }
    	}
    }
    
    //******************************************************************************
    
    public void touch() {
    	String[] args = parser.getArgs();
    	File file = new File(args[0]);
    	try {
			if(!file.createNewFile()) {
				System.out.println("\"" + args[0] + "\" already exists!");
			} 
		} 
    	catch (IOException e) {
			System.out.println("Error: \"" + args[0] + "\" is wrong path!");
		}
    }
    
    //******************************************************************************
    
    public void cpFiles(String source, String destination) {
    	Path fileIn = Paths.get(source);
    	FileOutputStream  fileOut = null;
		try {
	    	fileOut = new FileOutputStream(destination);
	    	Files.copy(fileIn, fileOut);
	    	fileOut.close();
		} 
		catch (IOException e) {
			try {
				fileOut.close();
			} catch (IOException e1) {}
			
			File file = new File(destination);
			file.delete();
			System.out.println("Error: can not copy files!");
		}
    }
    
    public void cpDirs(String source, String destination) {
    	File path1 = new File(source);
    	Path path2 = Paths.get(destination);
		if(!Files.exists(path2)) {
			File path = new File(destination);
			path.mkdir();
		}
    	for (String f : path1.list()) {
    		String dir1 = source+"\\"+f;
			String dir2 = destination+"\\"+f;
			File path = new File(dir1);
			if(path.isDirectory()) cpDirs(dir1, dir2);
			else cpFiles(dir1, dir2);
	    }
    }
    
    public void cp() {
    	String cmd = parser.getCommandName();
    	String[] args = parser.getArgs();
    	if(cmd.equals("cp")) cpFiles(args[0], args[1]);
    	else cpDirs(args[1], args[2]);
    	
    }
    
    //******************************************************************************
    
    public void rm() {
    	String[] args = parser.getArgs();
    	File file = new File(args[0]);
		if (file.isDirectory()) System.out.println("Error: can not remove directories!");
		else {
			if(file.exists()) file.delete();
			else System.out.println(args[0] + ": No such file!");
		}
    }
    
    //******************************************************************************
    
    public void cat() {
    	String[] args = parser.getArgs();
    	for(String arg:args) {
    		File file = new File(arg);
	        BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
		        String line;
		        while ((line = br.readLine()) != null) System.out.println(line);
		        br.close();
			} catch (IOException e) {
				try {
					br.close();
				} catch (IOException e1) {}
				System.out.println("Error: can not read file!");
			}
    	}
    }
    
    //******************************************************************************
    //This method will choose the suitable command method to be called
    public void chooseCommandAction() {
        String cmd = parser.getCommandName();
        if(cmd != null) {
            if(cmd.equals("cd") || cmd.equals("cd ..")) cd();
            else if(cmd.equals("ls") || cmd.equals("ls -r")) ls();
            else if(cmd.equals("pwd")) System.out.println(pwd());
            else if(cmd.equals("echo")) echo();
            else if(cmd.equals("mkdir")) mkdir();
            else if(cmd.equals("rmdir") || cmd.equals("rmdir *")) rmdir();
            else if(cmd.equals("touch")) touch();
            else if(cmd.equals("cp") || cmd.equals("cp -r")) cp();
            else if(cmd.equals("rm")) rm();
            else if(cmd.equals("cat")) cat();
        }
    }

    //*****************************************************************************************
    //************************************ CLI Main *******************************************
    //*****************************************************************************************
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        try (Scanner input = new Scanner(System.in)) {
			String cl;
			
			while(true) {
			    System.out.print("% ");
			    cl = input.nextLine();
			    
			    if(cl.equals("exit")) System.exit(0);
			    
			    boolean b = terminal.parser.parse(cl);
			    if(!b) {
			        System.out.println("Error: Command not found or invalid parameters are entered!");
			        continue;
			    }
			    
			    terminal.chooseCommandAction();
			}
		}
    }
    
}
