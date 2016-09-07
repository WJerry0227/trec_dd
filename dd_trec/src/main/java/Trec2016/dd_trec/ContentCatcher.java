package Trec2016.dd_trec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class ContentCatcher {

	private static final String ENCODING = "utf-8";

	private static final String OUTFILE = "models/resultText.txt";

   private static final String EBOLAROOTFILE = "/backup/dd_trec/data/EbolaDataXML";
	
   private static final String POLAROOTFILE = "/backup/dd_trec/data/PolarDataXML";

	File outFile = new File(OUTFILE);

	File rootFile1 = new File(EBOLAROOTFILE);
	
	File rootFile2 = new File(POLAROOTFILE);

	private Vector<String> vector = new Vector<String>();

	public ContentCatcher(){
		
	}
	
	public ContentCatcher(String[] searchList){
		outFile.delete();
		this.writeFile("30");
		for(String str :searchList ){
			vector.add(str);
			//this.writeFile(str);
		}
			//System.out.println(vector.contains("ebola-62f0c08103c22775e0aa2c97ec989a43c3994645c704053f6654a0e0ba00176d"));

	}
	
	public ContentCatcher(String[] content,int i){
		outFile.delete();
		this.writeFile("200");
		for(String str:content){
			this.writeFile(str);
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
	public static void main(String[] args){
		ContentCatcher cc = new ContentCatcher();
		System.out.println(cc.getContentbyID("ebola-62f0c08103c22775e0aa2c97ec989a43c3994645c704053f6654a0e0ba00176d", 0));
	}

	/**
	 * @param id  	the ID of doc
	 * @param isPolar		0:ebola  1:polar
	 * @return    String the content of the text ;  null:did not find 
	 */
	public String getContentbyID(String id,int isPolar){
		try {
			File[] fs = null;
			if(isPolar == 0)
				fs = rootFile1.listFiles();
			else
				fs = rootFile2.listFiles();
			for (int i = 0; i < fs.length; i++) {
				File file = fs[i];
				boolean m = false;

				if (file.isFile() && file.exists()) { // 判断文件是否存在
					InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);// 考虑到编码格式
					BufferedReader bufferedReader = new BufferedReader(read);
					String lineTxt = null;
					while ((lineTxt = bufferedReader.readLine()) != null) {
						if (m == true) {
							m = false;
							return lineTxt;
						}

						if (id.equals(lineTxt)) {
							m = true;
							lineTxt = bufferedReader.readLine();
							lineTxt = bufferedReader.readLine();

						}
					}
					read.close();

				} else {
					System.out.println("找不到指定的文件");
				}
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

	public void readTxtFile(int number) {
		try {
			File[] fs = null;
			if(number == 1)
				fs = rootFile1.listFiles();
			else
				fs = rootFile2.listFiles();
			for (int i = 0; i < fs.length; i++) {
				File file = fs[i];
				boolean m = false;

				if (file.isFile() && file.exists()) { // 判断文件是否存在
					InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);// 考虑到编码格式
					BufferedReader bufferedReader = new BufferedReader(read);
					String lineTxt = null;
					while ((lineTxt = bufferedReader.readLine()) != null) {
						if (m == true) {
							m = false;
							this.writeFile(lineTxt);
						}

						if (vector.contains(lineTxt)) {
							m = true;
							lineTxt = bufferedReader.readLine();
							lineTxt = bufferedReader.readLine();

						}
					}
					read.close();

				} else {
					System.out.println("找不到指定的文件");
				}
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}

}
