package Trec2016.dd_trec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ReadTopic {

	private static final File TOPIC = new File("topic_name.txt");
	
	
	public static String[][] getName(){
		String[][] re = new String[100][2];
		InputStreamReader read;
		try {
			read = new InputStreamReader(new FileInputStream(TOPIC), "utf-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			int i=0,count=0;
			String line = null;
			
			while((line=bufferedReader.readLine())!=null){
				if(i==0)
					re[count][i] = line;
				else
					re[count++][i] = line;
				i = i^1;
			}
			re[count][i] = "-1";
			
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 考虑到编码格式
		
		
		
		return re;
		
	}
	
}
