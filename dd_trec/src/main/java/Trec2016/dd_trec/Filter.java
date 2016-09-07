package Trec2016.dd_trec;


import java.util.Vector;

/**
 * Created by yuminchen on 16/7/5.
 * modified by Wenzao Wang on 16/8/16
 */
public class Filter {
    Vector<String> nonsenseWords;

    public Filter(Vector<String> nonsenseWords) {
        this.nonsenseWords = nonsenseWords;
    }

    public Filter() {
        nonsenseWords = new Vector<String>();
        fillVector();
    }

    public boolean filter(String word){
      
//        System.out.print(word);
        if (nonsenseWords.contains(word)){
            return false;
        }
        return true;

    }

    private void fillVector(){
    	  nonsenseWords.add("'s");
    	  nonsenseWords.add("|");   
        nonsenseWords.add("var");
        nonsenseWords.add("...");
        nonsenseWords.add("/");
        nonsenseWords.add("get");
        nonsenseWords.add("``");
        nonsenseWords.add("��");
        nonsenseWords.add("`");
        nonsenseWords.add("<");
        nonsenseWords.add(">");
        nonsenseWords.add("http");
        nonsenseWords.add("--");
        nonsenseWords.add("class=");
        nonsenseWords.add("–");
        nonsenseWords.add("-");
        nonsenseWords.add("{");
        nonsenseWords.add("}");
        nonsenseWords.add("''");
        nonsenseWords.add("'");
        nonsenseWords.add("n't");
        nonsenseWords.add("rel=");
        nonsenseWords.add("===");
        nonsenseWords.add("href=");
        nonsenseWords.add("/a");
        nonsenseWords.add("src=");
        nonsenseWords.add("br/");
        nonsenseWords.add("\"");
        nonsenseWords.add("…");
        nonsenseWords.add("05:03:39");
        nonsenseWords.add("05:03:21");
        nonsenseWords.add("07:03:34");
        nonsenseWords.add("月");
        nonsenseWords.add("//newsblogged.com/");
        nonsenseWords.add("09:03:59");
        nonsenseWords.add("=");
        nonsenseWords.add("a.");
        nonsenseWords.add("d.");
        nonsenseWords.add("r.");
        nonsenseWords.add("e.");
        nonsenseWords.add("m.");
        nonsenseWords.add("s.");
        nonsenseWords.add("c.");
        nonsenseWords.add("j.");
        nonsenseWords.add("r.j.");
        nonsenseWords.add("snow/ic");
        nonsenseWords.add("•");
        nonsenseWords.add("//hotair.com/");
        nonsenseWords.add("..");
        nonsenseWords.add("e.g.");
        nonsenseWords.add("12:00:00");
        nonsenseWords.add("p");
        nonsenseWords.add("/p");
        nonsenseWords.add("alt=");
        nonsenseWords.add("width=");
        nonsenseWords.add("img");
        nonsenseWords.add("li");
        nonsenseWords.add("data-position=");
        nonsenseWords.add("data-poid=");
        nonsenseWords.add("data-post-type=");
        nonsenseWords.add("u.s.");
        nonsenseWords.add("dr.");
        nonsenseWords.add("mr.");
        nonsenseWords.add("strong");
        nonsenseWords.add("/strong");
        nonsenseWords.add("target=");
        nonsenseWords.add("_blank");
        nonsenseWords.add("em");
        nonsenseWords.add("/em");
        // todo the fill vector

    }

    public static void main(String[] args) {
    }
}

