package Trec2016.dd_trec;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        MyIndri my = new MyIndri();
        //String[] re = my.firstsearch("government");
    	ContentCatcher cc = new ContentCatcher(my.firstsearch("government"));
    	cc.readTxtFile();
    }
}
