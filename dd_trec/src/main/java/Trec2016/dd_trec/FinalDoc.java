package Trec2016.dd_trec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FinalDoc {
	
	private static final String OUTFILE = "Rankingresult.txt";
	
	static File outFile = new File(OUTFILE);
	
	static File inFile1 = new File("LDARankingresult.txt");
	
	static File inFile2 = new File("IndriRankingresult.txt");
	
	public static void rank(String id){
		File rankFile = new File(id+"ranking.txt");
		rankFile.delete();
		outFile.delete();
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(inFile1));
			String linetext = null;
			String[] docs = new String[60];
			double[] scores = new double[60];
			int i = 0;
			while((linetext=br1.readLine())!=null){
				docs[i] = linetext.split(":")[0];
				scores[i++] = 0.3*Double.parseDouble(linetext.split(":")[1]);
			}
			br1.close();
			
			BufferedReader br2 = new BufferedReader(new FileReader(inFile2));
			boolean isFind = false;
			while((linetext=br2.readLine())!=null){
				isFind = false;
				for(int k=0;k<i;k++){
					if(docs[k].equals(linetext.split(":")[0])){
						scores[k]+= 0.7*Double.parseDouble(linetext.split(":")[1]);
						isFind = true;
						break;
					}
				}
				
				if(!isFind){
					docs[i] = linetext.split(":")[0];
					scores[i++] = Double.parseDouble(linetext.split(":")[1]);
				}
			}
			br2.close();
			
			//insertion sort
			for(int m=1;m<i;m++){
				if(scores[m-1]<scores[m]){
					double temp = scores[m];
					String tempString = docs[m];
					int n = m;
					while(n>0&&scores[n-1]<temp){
						scores[n] = scores[n-1];
						docs[n] = docs[n-1];
						n--;
					}
					scores[n] = temp;
					docs[n] = tempString;
				}
			}
			
			for(int num=0;num<30;num++){
				FinalDoc.writeFile(docs[num]+":"+scores[num],rankFile);
			}
			System.out.println(i);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private static void writeFile(String str,File file){
		try {
			FileWriter fw = new FileWriter(outFile,true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(str);
			bw.write("\n");

			bw.close();
			
			FileWriter fw2 = new FileWriter(file,true);
			BufferedWriter bw2 = new BufferedWriter(fw2);

			bw2.write(str);
			bw2.write("\n");

			bw2.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
