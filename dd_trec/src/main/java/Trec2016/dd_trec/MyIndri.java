package Trec2016.dd_trec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import lemurproject.indri.QueryEnvironment;
import lemurproject.indri.QueryRequest;
import lemurproject.indri.QueryResult;
import lemurproject.indri.QueryResults;

public class MyIndri{

	private final static String EBOLAPATH = "/backup/dd_trec/data/collection1Ebola";
	
	private final static String POLARPATH = "/backup/dd_trec/data/collection2Polar";
	
	private static final String OUTFILE = "IndriRankingresult.txt";

	File outFile = new File(OUTFILE);
	
	public void deleteMyIndriFile(){
		outFile.delete();
	}
	
	public String[] getContent(String subTopic,int num){
		QueryEnvironment env = new QueryEnvironment();
		String[] result = new String[20] ;
		try {
			if(num == 0)
				env.addIndex(EBOLAPATH);
			else
				env.addIndex(POLARPATH);
			QueryRequest req = new QueryRequest();
			req.query = "#combine("+subTopic+")";
			req.resultsRequested = 20;
			req.startNum = 0; //starting number in the result set, eg 10 to get results starting from the 11th position in the result list
			req.options = req.TextSnippet; //alternative one is TextSnippet
			QueryResults qrets = env.runQuery(req);
			QueryResult[] rets = qrets.results;
			int i = 0;
			for(QueryResult ret : rets) { // iterate over the results.
				result[i++] = ret.snippet;
				System.out.println(
						//ret.docid + " " + // internal ID
						ret.documentName + " " +
						ret.score + " " +
						ret.snippet //.replace("\n", "").replace("\r", "") // add this to remove the newline...
						);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // open an Indri index

		return result;
	}
	
	public void directSearch(String subTopic,int num){
		QueryEnvironment env = new QueryEnvironment();
		try {
			if(num == 0)
				env.addIndex(EBOLAPATH);
			else
				env.addIndex(POLARPATH);
			QueryRequest req = new QueryRequest();
			req.query = "#combine("+subTopic+")";
			req.resultsRequested = 5;
			req.startNum = 0; //starting number in the result set, eg 10 to get results starting from the 11th position in the result list
			req.options = req.TextSnippet; //alternative one is TextSnippet
			QueryResults qrets = env.runQuery(req);
			QueryResult[] rets = qrets.results;
			for(QueryResult ret : rets) { // iterate over the results.
				System.out.println(ret.documentName);
				this.writeFile(ret.documentName+":"+ret.score);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // open an Indri index
	}
	
	public List<String> search(String query,int number,int database){
		QueryEnvironment env = new QueryEnvironment();
		List<String> re = new ArrayList<String>();
		try {
			if(database == 0)
				env.addIndex(EBOLAPATH);
			else
				env.addIndex(POLARPATH);
			QueryRequest req = new QueryRequest();
			req.query = "#combine("+query+")";
			req.resultsRequested = number;
			req.startNum = 0; //starting number in the result set, eg 10 to get results starting from the 11th position in the result list
			req.options = req.TextSnippet; //alternative one is TextSnippet
			QueryResults qrets = env.runQuery(req);
			QueryResult[] rets = qrets.results;
			for(QueryResult ret : rets) { // iterate over the results.
				re.add(ret.documentName+":"+ret.score);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} // open an Indri index
		return re;
	}
	
	public String[] firstsearch(String subTopic,int num){
		QueryEnvironment env = new QueryEnvironment();
		String[] result = new String[30] ;
		try {
			if(num == 0)
				env.addIndex(EBOLAPATH);
			else
				env.addIndex(POLARPATH);
			QueryRequest req = new QueryRequest();
			req.query = "#combine("+subTopic+")";
			req.resultsRequested = 30;
			req.startNum = 0; //starting number in the result set, eg 10 to get results starting from the 11th position in the result list
			req.options = req.TextSnippet; //alternative one is TextSnippet
			QueryResults qrets = env.runQuery(req);
			QueryResult[] rets = qrets.results;
			double maxScore = rets[0].score;
			double minScore = rets[29].score;
			double fencha = maxScore - minScore;
			int i = 0;
			for(QueryResult ret : rets) { // iterate over the results.
				System.out.println(ret.documentName);
				result[i++] = ret.documentName;
				this.writeFile(ret.documentName+":"+(ret.score-minScore)/fencha);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // open an Indri index

		return result;
	}

	public static String[] search(String keywords,String topic,int num){
		QueryEnvironment env = new QueryEnvironment();
		String[] result = new String[10] ;
		try {
			if(num == 0)
				env.addIndex(EBOLAPATH);
			else
				env.addIndex(POLARPATH);
			QueryRequest req = new QueryRequest();
			req.query = "#combine("+topic+" "+keywords+")";
			
			req.resultsRequested = 10;
			req.startNum = 0; //starting number in the result set, eg 10 to get results starting from the 11th position in the result list
			req.options = req.TextSnippet; //alternative one is TextSnippet
			QueryResults qrets = env.runQuery(req);
			QueryResult[] rets = qrets.results;
			double maxScore = rets[0].score;
			double minScore = rets[9].score;
			double fencha = maxScore - minScore;
			int i = 0;
			for(QueryResult ret : rets) { // iterate over the results.
				result[i++] = ret.documentName+":"+(ret.score-minScore)/fencha;
			System.out.println(
			//ret.docid + " " + // internal ID
			ret.documentName + " " +
			ret.score + " " +
			ret.snippet //.replace("\n", "").replace("\r", "") // add this to remove the newline...
			);
			}
			return result;
		} catch (Exception e) {
			QueryRequest req = new QueryRequest();
			req.query = "#combine("+topic+")";
			
			req.resultsRequested = 10;
			req.startNum = 0; //starting number in the result set, eg 10 to get results starting from the 11th position in the result list
			req.options = req.TextSnippet; //alternative one is TextSnippet
			QueryResults qrets = null;
			try {
				qrets = env.runQuery(req);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			QueryResult[] rets = qrets.results;
			int i = 0;
			for(QueryResult ret : rets) { // iterate over the results.
				result[i++] = ret.documentName+":"+ret.score;
			}
			return result;
		} // open an Indri index

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