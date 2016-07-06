package filterNonsense;

import java.util.Vector;

/**
 * Created by yuminchen on 16/7/5.
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
        char firstAhl = word.charAt(0);
        if(firstAhl==','||firstAhl=='.'||firstAhl=='?'){
            word = word.substring(2);
        }
        else if (firstAhl==' '){
            word = word.substring(1);
        }
//        System.out.print(word);
        if (nonsenseWords.contains(word)){
            return false;
        }
        return true;

    }

    private void fillVector(){
        nonsenseWords.add("a");
        nonsenseWords.add("the");
        nonsenseWords.add("are");
        nonsenseWords.add("be");
        nonsenseWords.add("you");
        nonsenseWords.add("own");
        nonsenseWords.add("an");
        nonsenseWords.add("of");
        nonsenseWords.add("in");
        nonsenseWords.add("out");
        nonsenseWords.add("var");
        nonsenseWords.add("as");
        nonsenseWords.add("like");
        nonsenseWords.add("by");
        nonsenseWords.add("kind");
        nonsenseWords.add("will");
        nonsenseWords.add("do");
        nonsenseWords.add("did");
        nonsenseWords.add("does");
        nonsenseWords.add("is");
        nonsenseWords.add("can");
        nonsenseWords.add("to");
        nonsenseWords.add("your");
        nonsenseWords.add("should");
        nonsenseWords.add("such");
        nonsenseWords.add("so");
        nonsenseWords.add("if");
        nonsenseWords.add("we");
        nonsenseWords.add("for");
        nonsenseWords.add("while");
        nonsenseWords.add("want");
        nonsenseWords.add("us");
        nonsenseWords.add("or");
        nonsenseWords.add("and");
        nonsenseWords.add("either");
        nonsenseWords.add("about");
        nonsenseWords.add("about");
        nonsenseWords.add("main");
        nonsenseWords.add("what");
        nonsenseWords.add("how");
        nonsenseWords.add("me");
        nonsenseWords.add("who");
        nonsenseWords.add("it");
        nonsenseWords.add("but");
        nonsenseWords.add("at");
        nonsenseWords.add("their");
        nonsenseWords.add("some");
        nonsenseWords.add("any");
        nonsenseWords.add("from");
        nonsenseWords.add("on");
        nonsenseWords.add("have");
        nonsenseWords.add("my");
        nonsenseWords.add("into");
        // todo the fill vector

    }

    public static void main(String[] args) {
    }
}
