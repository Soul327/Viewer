package Main;

import java.util.ArrayList;

import Misc.Util;

public class Address {
	public boolean updated = false;
	public String name, ip;
	public ArrayList<String> ports, test;
	
	public Address(String ip) {
		name = ip;
		this.ip = ip;
		/*//Test output
		for(int z=0;z<Math.random()*1000+10;z++) {
			String s = "";
			for(int y=0;y<Math.random()*1000;y++)
				s += (char)((int)(Math.random()*255));
			test.add(s);
		}
		//*/
	}
	public void getValues() {
		//System.out.println("Updating");
		new Thread() {
			public void run(){
				test = new ArrayList<String>();
				if(Main.ezViewMode == 0) {
					ArrayList<String> address = Util.getAddress(name);
					for(String a:address)
						test.add(a);
				}
				if(Main.ezViewMode == 1) {
					test.add("Name: "+Util.getValue(ip, " name:"));
					test.add("OS: "+Util.getValue(ip, " OS:"));
					
					ArrayList<String> temp = Util.getList(ip, " ports:");
					for(String s:temp) 
						if(s.contains("tcp"))
							test.add(s);
					for(String s:temp) 
						if(s.contains("udp"))
							test.add(s);
					for(String s:temp) 
						if(!s.contains("tcp") & !s.contains("udp"))
							test.add(s);
				}
			}
		}.start();
		updated = true;
	}
}
