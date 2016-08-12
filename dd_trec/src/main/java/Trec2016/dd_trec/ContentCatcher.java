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

	private static final String OUTFILE = "result.txt";

	private static final String INFILE = "D:/Indri/path/to/collection2/hello.txt";

	File outFile = new File(OUTFILE);

	File inFile = new File(INFILE);

	private Vector<String> vector = new Vector<String>();

	public ContentCatcher(String[] searchList){
		outFile.delete();
		for(String str :searchList ){
			vector.add(str);
			//this.writeFile(str);
		}
			//System.out.println(vector.contains("ebola-62f0c08103c22775e0aa2c97ec989a43c3994645c704053f6654a0e0ba00176d"));

	}

	public void writeFile(String str){
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



	public void readTxtFile(){
        try {

                File file = new File(INFILE);
                boolean m = false;

                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),ENCODING);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	if(m == true){
                    		m = false;
                    		this.writeFile(lineTxt);
                    	}

                        if(vector.contains(lineTxt)){
                        	m = true;
                        	lineTxt = bufferedReader.readLine();
                        	lineTxt = bufferedReader.readLine();

                        }
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }

}
