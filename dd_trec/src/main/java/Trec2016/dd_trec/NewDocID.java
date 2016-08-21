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

public class NewDocID {

	private static final String OUTFILE = "result.txt";

	private static final MyIndri mi = new MyIndri();

	private String subtopic = null;

	File outFile = new File(OUTFILE);

	public NewDocID(String subtopic){
		outFile.delete();
		this.subtopic = subtopic;
	}

	public void getKeyWords(){
		File f = new File("LDAResult.txt");
		InputStreamReader read;
		try {
			read = new InputStreamReader(
			        new FileInputStream(f),"utf-8");
			BufferedReader br = new BufferedReader(read);
			String lineTxt = null;
			while((lineTxt=br.readLine())!=null){
				this.writeFile(mi.search(lineTxt, subtopic));
			}
			read.close();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//考虑到编码格式
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void writeFile(String str){
		try {
			FileWriter fw = new FileWriter(outFile,true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(str);
			bw.write("\n");

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
