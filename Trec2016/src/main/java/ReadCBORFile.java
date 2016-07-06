import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

public class ReadCBORFile{

	static void readCbor(String[] names)  throws IOException {
		CBORFactory f = new CBORFactory();
		ObjectMapper mapper = new ObjectMapper(f);

		for (String infile : names) {
			InputStream fp = new FileInputStream(infile);
			if (infile.endsWith(".gz")) {
				fp = new GZIPInputStream(fp);
			}

			ObjectReader r = mapper.reader(Map.class);
			MappingIterator<Map> it = r.readValues(fp);
			while (it.hasNextValue()) {
				Map obj = it.nextValue();
				System.out.println(obj.get("key"));
			}
		}
	}

	public static void main(String[] args) throws IOException{

		String[] names = {""};
		ReadCBORFile.readCbor(names);
			// Or, use a schema...
			/*
			ObjectReader r = mapper.reader(TargetModel.class);
			MappingIterator<TargetModel> it = r.readValues(fp);
			while (it.hasNextValue()) {
				TargetModel obj = it.nextValue();
				System.out.println(obj);
			}
			*/
	}
}
