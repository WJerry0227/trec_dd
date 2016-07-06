import org.apache.poi.hwpf.model.Sttb;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.*;

/**
 * Created by yuminchen on 16/5/30.
 */
public class HTMLTagFilter {

    public static void Html2Doc(String fileName , String outfile){

        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream in = null;
        FileOutputStream out = null;
        ParseContext parseContext = new ParseContext();

        try {
            in = new FileInputStream(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        HtmlParser parser = new HtmlParser();

        if (in != null) try {
            parser.parse(in, handler, metadata, parseContext);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }

        File outFile = new File(outfile);
        try {

            out = new FileOutputStream(outFile);
            if(!outFile.exists()){
                outFile.createNewFile();
            }

            String[] metaNames = metadata.names();
            out.write("contents of metadata : \n".getBytes());
            for (String name : metaNames){
                out.write((name+ "  :  "+ metadata.get(name)+"\n").getBytes());
            }
            out.write("\n".getBytes());

            String headerContent = handler.toString();
            out.write("contents of document : \n".getBytes());
            out.write(headerContent.getBytes());
            out.write("\n".getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }


    }

    public static void main(String[] args) {
        HTMLTagFilter.Html2Doc("doc/test.html","doc/result.txt");
    }
}
