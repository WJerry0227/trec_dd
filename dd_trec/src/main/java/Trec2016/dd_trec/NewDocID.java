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
import java.util.Vector;

public class NewDocID {

	private static final String OUTFILE = "LDARankingresult.txt";

	private static final MyIndri mi = new MyIndri();
	
	private Vector<String> id = null;

	private String subtopic = null;

	File outFile = new File(OUTFILE);

	public NewDocID(String subtopic){
		outFile.delete();
		this.subtopic = subtopic;
	}

	public void getKeyWords(int num){
		this.id = new Vector<String>();
		File f = new File("LDAResult.txt");
		InputStreamReader read;
		try {
			read = new InputStreamReader(
			        new FileInputStream(f),"utf-8");
			BufferedReader br = new BufferedReader(read);
			String lineTxt = null;
			while((lineTxt=br.readLine())!=null){
				String[] newid = MyIndri.search(lineTxt, subtopic,num);
				
				int docnumber = 0;
				for(int i=0;i<10;i++){
					if(this.id.contains(newid[i].split(":")[0]))
					   continue;
					else{
						this.writeFile(newid[i]);
						id.add(newid[i].split(":")[0]);
						docnumber++;
						if(docnumber == 6)
							break;
					}
				}
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
