package Trec2016.dd_trec;

import lemurproject.indri.QueryEnvironment;
import lemurproject.indri.QueryRequest;
import lemurproject.indri.QueryResult;
import lemurproject.indri.QueryResults;

public class MyIndri{

	private final static String PATH = "D:/Indri/path/to/outputIndex2";

	public String[] firstsearch(String subTopic){
		QueryEnvironment env = new QueryEnvironment();
		String[] result = new String[30] ;
		try {
			env.addIndex(PATH);
			QueryRequest req = new QueryRequest();
			req.query = subTopic;
			req.resultsRequested = 30;
			req.startNum = 0; //starting number in the result set, eg 10 to get results starting from the 11th position in the result list
			req.options = req.TextSnippet; //alternative one is TextSnippet
			QueryResults qrets = env.runQuery(req);
			QueryResult[] rets = qrets.results;
			int i = 0;
			for(QueryResult ret : rets) { // iterate over the results.
				result[i++] = ret.documentName;
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

	public void search(String query){
		QueryEnvironment env = new QueryEnvironment();
		try {
			env.addIndex(PATH);
			QueryRequest req = new QueryRequest();
			req.query = query;
			req.resultsRequested = 5;
			req.startNum = 0; //starting number in the result set, eg 10 to get results starting from the 11th position in the result list
			req.options = req.TextSnippet; //alternative one is TextSnippet
			QueryResults qrets = env.runQuery(req);
			QueryResult[] rets = qrets.results;
			for(QueryResult ret : rets) { // iterate over the results.
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

	}
}