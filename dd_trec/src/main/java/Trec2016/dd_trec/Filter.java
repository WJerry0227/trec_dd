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
        
        // todo the fill vector

    }

    public static void main(String[] args) {
    }
}

