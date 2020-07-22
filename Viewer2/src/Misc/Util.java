package Misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import Main.Address;
import Main.Viewer;

public class Util {
	final static String domain = "database/temp/";
	public static File file = new File(domain+"DataSplit0");
	
	public static void parseDataBase(File file,String domain,int maxLinePerFile) {
		int lineCounter = 0;
		int fileNum = 0;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			File wFile = new File(domain+"DataSplit"+fileNum++);
			PrintWriter writer = new PrintWriter(wFile, "UTF-8");
			
			NumberFormat myFormat = NumberFormat.getInstance();
			myFormat.setGroupingUsed(true);
			
			while (line != null) {
				writer.println(line);
				lineCounter++;
				if(wFile.length()>maxLinePerFile & line.charAt(0)!=' ') {
					lineCounter = 0;
					writer.close();
					wFile = new File(domain+"DataSplit"+fileNum++);
					writer = new PrintWriter(wFile, "UTF-8");
					System.out.println( myFormat.format(fileNum) );
				}
				//if(lineCounter%1000==0) System.out.println( myFormat.format(lineCounter) );
				//System.out.println(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void parseDataBase(File file,int maxLinePerFile) {
		parseDataBase(file,domain,maxLinePerFile);
	}
	public static void reverseFile(File file) {
		File outputFile = new File(file.getName()+"r");
		try {
			outputFile.createNewFile();
			Scanner myReader = new Scanner(file);
			FileWriter writer = new FileWriter(outputFile); 
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				//System.out.println(data);
				writer.write(data+"\n");
				writer.flush();
			}
			writer.close();
			myReader.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		file = outputFile;
	}
	public static void findEntry(String query) {
		String contents[] = new File(domain).list();
		int maxThreads = 10;
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for(int x=0;x<contents.length;) {
			if(threads.size()<maxThreads) {
				Thread t = new Search(domain+contents[x],query);
				t.start();
				threads.add( t );
				x++;
			}
			for(int z=0;z<threads.size();z++)
				if(!threads.get(z).isAlive()) {
					threads.remove(threads.get(z));
					z--;
				}
		}
	}
	public static void nextFile() {
		File directoryPath = new File(domain);
		String contents[] = directoryPath.list();
		Arrays.sort(contents);
		for(int i=contents.length; i>0; i--) {
			if( file.getName().equals(contents[i-1]) ) {
				System.out.println(contents.length+" "+i);
				if(i==contents.length)
					file = new File(domain+contents[0]);
				else
					file = new File(domain+contents[i]);
			}
			
		}
		loadFile();
	}
	public static void printDir() {
		File directoryPath = new File(domain);
		String contents[] = directoryPath.list();
		Arrays.sort(contents);
		for(int x=0;x<contents.length;x++) {
			System.out.println(contents[x]);
		}
		loadFile();
	}
	public static void lastFile() {
		File directoryPath = new File(domain);
		String contents[] = directoryPath.list();
		for(int i=0; i<contents.length-1; i++) {
			if( file.getName().equals(contents[i+1]) ) {
				file = new File(domain+contents[i]);
			}
		}
		loadFile();
	}
	public static void loadFile() {
//		System.out.println("Loading file");
		Viewer.list = new ArrayList<Address>();
		for(String s:Util.getSet(0)) 
			Viewer.list.add(new Address(s));
		//Collections.reverse(Viewer.list);
//		System.out.println("Loaded file");
	}
	public static void sel() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(new JFrame()); //Where frame is the parent component
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    file = fc.getSelectedFile();
		    //Now you have your file to do whatever you want to do
		} else {
		    //User did not choose a valid file
		}
	}
	
	public static ArrayList<String> getSet(int depth) {
		try {
			ArrayList<String> re = new ArrayList<String>();
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = br.readLine()) != null) {
					// process the line.
					if(line.charAt(0)!=' ')
						re.add(line);
				}
			}
			return re;
		}catch(Exception e) { return null; }
	}
	
	public static ArrayList<String> getAddress(String address) {
		try {
			boolean a = false;
			ArrayList<String> re = new ArrayList<String>();
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = br.readLine()) != null) {
					// process the line.
					if(line.charAt(0)!=' ')
						if(line.equals(address)) {
							a = true;
						}
					if(a & line.charAt(0)==' ') {
						re.add(line);
					}
					if(a)
						if(!line.equals(address) & line.charAt(0)!=' ')
							a = false;
				}
			}
			return re;
		}catch(Exception e) { return null; }
	}
	
	public static ArrayList<String> getList(String ip, String name) {
		String s = getValue(ip, name);
		ArrayList<String> re = new ArrayList<String>();
		if(s!=null)
			while(s.contains(" ")) {
				re.add(s.substring(0,s.indexOf(" ")));
				s = s.substring(s.indexOf(" ")+1);
			}
		return re;
	}
	
	public static String getValue(String ip, String name) {
		try {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				boolean a = false;
				while ((line = br.readLine()) != null) {
					// process the line.
					if(line.charAt(0)!=' ') {
						if(line.equals(ip))
							a = true;
					}
					if(a)
						if(line.substring(0,name.length()).contains(name))
							return line.substring(name.length()+1);
				}
			}
			return null;
		}catch(Exception e) { return null; }
	}
}
class Search extends Thread{
	File file;
	String query;
	public Search(String loc,String query) {
		//System.out.println(loc);
		file = new File(loc);
		this.query = query;
	}
	public void run() {
		try {
			Scanner s = new Scanner(file);
			int lineNum = 0;
			while(s.hasNext()) {
				String line = s.nextLine(); lineNum++;
				if(line.contains(query)) 
					System.out.println(file.getName()+" #"+lineNum+" "+line);
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
