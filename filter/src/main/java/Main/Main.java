package Main;

import classify.Stemmer;
import filterNonsense.Filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by yuminchen on 16/7/6.
 */
public class Main {

    Filter filter = new Filter();
    Stemmer s = new Stemmer();
    String path_1 = Main.class.getResource("1.txt").getPath();
    String path_2 = Main.class.getResource("2.txt").getPath();
    final  String[] fineNames = {path_1,path_2};


    public void execute(){
        char[] w = new char[501];
        for (int i = 0; i < fineNames.length; i++)
            try
            {
                FileInputStream in = new FileInputStream(fineNames[i]);

                try
                { while(true)

                {  int ch = in.read();
                    if (Character.isLetter((char) ch))
                    {
                        int j = 0;
                        while(true)
                        {  ch = Character.toLowerCase((char) ch);
                            w[j] = (char) ch;
                            if (j < 500) j++;
                            ch = in.read();
                            if (!Character.isLetter((char) ch))
                            {
                       /* to test add(char ch) */
                                for (int c = 0; c < j; c++) s.add(w[c]);

                       /* or, to test add(char[] w, int j) */
                       /* s.add(w, j); */

                                s.stem();
                                {  String u;

                          /* and now, to test toString() : */
                                    u = s.toString();

                          /* to test getResultBuffer(), getResultLength() : */
                          /* u = new String(s.getResultBuffer(), 0, s.getResultLength()); */
//                                    System.out.println(u);
                                    if(filter.filter(u)) {
//                                        System.out.println(filter.filter(u));
                                        System.out.print(u);
                                    }
                                }
                                break;
                            }
                        }
                    }
                    if (ch < 0) break;
                    if (filter.filter(ch+""))
                        System.out.print((char)ch);
                }
                }
                catch (IOException e)
                {  System.out.println("error reading " + fineNames[i]);
                    break;
                }
            }
            catch (FileNotFoundException e)
            {  System.out.println("file " + fineNames[i] + " not found");
                break;
            }
    }
    public static void main(String[] args) {

        new Main().execute();
    }
}
