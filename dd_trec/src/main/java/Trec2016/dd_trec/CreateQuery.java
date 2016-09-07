package Trec2016.dd_trec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class CreateQuery {
	
	private static final File RESULT = new File("Rankingresult.txt");
	
	public static void createQ(String id,String name){
		InputStreamReader read;
		String re = "cd /backup/dd_trec/jig/trec-dd-jig-master; python jig/jig.py -runid "+name+" -topic "+id+" -docs ";
		try {
			read = new InputStreamReader(new FileInputStream(RESULT), "utf-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			String line = bufferedReader.readLine();
			int i = 0;
			while(i<5){
				re+=line.replace("\n", "");
				re+=" ";
				i++;
				line = bufferedReader.readLine();
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		re+=" >"+id+"result0.txt";
		System.out.println(re);
		CreateQuery.executeConsoleCommand(re);
		
	}
	
	public static void executeConsoleCommand(String command){
		try {
			Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",command});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void writeSh(String str,String id){
		try {
			File outFile = new File(id+"final.sh");
			outFile.delete();
			FileWriter fw = new FileWriter(outFile,true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(str);
			bw.write(" >"+id+"result.txt");

			bw.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
