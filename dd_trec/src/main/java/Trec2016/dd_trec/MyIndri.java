package Trec2016.dd_trec;

import lemurproject.indri.QueryEnvironment;
import lemurproject.indri.QueryRequest;
import lemurproject.indri.QueryResult;
import lemurproject.indri.QueryResults;

public class MyIndri{

	public void search(){
		QueryEnvironment env = new QueryEnvironment();
		try {
			env.addIndex("D:/Indri/path/to/outputIndex2");
			QueryRequest req = new QueryRequest();
			req.query = "government";
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