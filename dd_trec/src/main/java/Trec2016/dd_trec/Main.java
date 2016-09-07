package Trec2016.dd_trec;

/**
 * Hello world!
 *
 */
public class Main {
	public static void main(String[] args) {
		Main m=new Main();
		m.ldaMethod();
	}
	
	public void directIndri(){
		String[][] re = ReadTopic.getName();
		for (int i = 0; re[i][0] != "-1"; i++) {
			String initQuery = re[i][1];
			MyIndri my = new MyIndri();
			if(i<27)
				my.directSearch(initQuery,0);
			else
				my.directSearch(initQuery,1);
			CreateQuery.createQ(re[i][0],"123");
		}
	}
	public void ldaMethod_indriContent(){
		String[][] re = ReadTopic.getName();
		//for (int i = 0; i<8; i++) {
			int i=0;
			String initQuery = re[i][1];
			MyIndri my = new MyIndri();
			ContentCatcher cc = null;
			if(i<27){
				cc = new ContentCatcher(my.getContent(initQuery,0),0);
				cc.readTxtFile(1);
			}
			else{
				cc = new ContentCatcher(my.getContent(initQuery,1),0);
				cc.readTxtFile(2);
			}
			//TopicModelBuildAndApply t = new TopicModelBuildAndApply();
			TopicModelBuildAndApply.classify("models", "resultText.txt", 5,8);
			NewDocID nd = new NewDocID(initQuery);
			if(i<27)
				nd.getKeyWords(0);
			else
				nd.getKeyWords(1);
			CreateQuery.createQ(re[i][0],"33");
		//}
	}
	
	public void ldaMethod(){
		String[][] re = ReadTopic.getName();
		for (int i = 0; i<53; i++) {
			String initQuery = re[i][1];
			MyIndri my = new MyIndri();
			my.deleteMyIndriFile();
			ContentCatcher cc = null;
			if(i<27){
				cc = new ContentCatcher(my.firstsearch(initQuery,0));
				cc.readTxtFile(1);
			}
			else{
				cc = new ContentCatcher(my.firstsearch(initQuery,1));
				cc.readTxtFile(2);
			}
			//TopicModelBuildAndApply t = new TopicModelBuildAndApply();
			TopicModelBuildAndApply.classify("models", "resultText.txt", 5,8);
			NewDocID nd = new NewDocID(initQuery);
			if(i<27)
				nd.getKeyWords(0);
			else
				nd.getKeyWords(1);
			FinalDoc.rank(re[i][0]);
			CreateQuery.createQ(re[i][0],"LDA_Indri73");
		}
	}
}
