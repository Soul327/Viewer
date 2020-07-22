package Main;

import java.io.File;

import Misc.Util;

public class Test {
	public static void main(String args[]) {
		File file = new File("database\\database-please-no.txt");
		Util.parseDataBase(file,"temp\\",10_000_000);
	}
}