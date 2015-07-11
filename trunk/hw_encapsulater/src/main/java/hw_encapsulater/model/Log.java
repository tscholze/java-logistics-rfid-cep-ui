package hw_encapsulater.model;

import com.alien.enterpriseRFID.tags.Tag;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Log {
	private File file;
	private HashMap<String, Integer> ids;
	
	public Log(String path){
		file = new File(path);
		ids = new HashMap<String, Integer>();
	}
	
	public void log(Tag[] tags){
		for (Tag tag : tags){
			if(!ids.containsKey(tag.getTagID())){
					ids.put(tag.getTagID(), 1);
			}
			else{
				Integer a = ids.get(tag.getTagID());
				ids.remove(tag.getTagID());
				a++;
				ids.put(tag.getTagID(), a);
			}
		}
	}
	
	public void write() throws IOException {
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		Set<String> keys = ids.keySet();
		for (String key : keys) {
			bw.write("ID: "+key+" ; COUNTS: "+ids.get(key));
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
}
