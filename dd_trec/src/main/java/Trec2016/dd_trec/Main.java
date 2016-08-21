package Trec2016.dd_trec;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
//        MyIndri my = new MyIndri();
//        //String[] re = my.firstsearch("government");
//    	ContentCatcher cc = new ContentCatcher(my.firstsearch("government"));
//    	cc.readTxtFile();
    	//TopicModelBuildAndApply t = new TopicModelBuildAndApply();
    	//t.newModule("models","text.txt",5);
    	NewDocID nd = new NewDocID("government");
    	nd.getKeyWords();
    }
}
