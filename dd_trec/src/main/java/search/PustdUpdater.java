package search;

import db.DBReader;
import db.DBReader2;
import db.DBWriter;
import query.Doc2Mysql;

import java.util.*;

/**
 * Created by cat on 16/8/22.
 */
public class PustdUpdater {

    private static DBReader dbReader = new DBReader();

    public static void update(Set<String> DPre,Set<String> qPre, Set<String> qNow, SearchSession searchSession){

        Set<String> qTheme = new HashSet<String>();
        for (String q: qPre){
            if (qNow.contains(q)){
                qTheme.add(q);
            }
        }

        Set<String> qRemoved = new TreeSet<String>(qPre);
        qRemoved.removeAll(qTheme);

        Set<String> qAdded = new TreeSet<String>(qNow);
        qAdded.removeAll(qTheme);

        Set<String> qAll = new TreeSet<String>(qPre);
        qAll.addAll(qNow);

        ArrayList<String> termList = new ArrayList<String>(1);
        
        termList.add("");

        String lastBestDoc = searchSession.lastBestDoc();

        for (String t: qAll){
        	
            termList.set(0,t);
            HashMap<String, Double> pustdMap = new HashMap<String, Double>(1);
            ArrayList<String> docs = dbReader.getRelatedDocs(termList);
            
            if(docs.size()==0){
            	continue;
            }
            
            Map<String, Double> updatedPustdMap = dbReader.getUpdatedPustds(t, docs);
            
            Map<String, Double> docOldPustdMap = dbReader.getPustds(t, docs);
            
            for(String key: docOldPustdMap.keySet()){
            	if(updatedPustdMap.containsKey(key)){
            		docOldPustdMap.put(key, updatedPustdMap.get(key));
            	}
            }

            if(DPre.contains(t)){

                double pustdLastBest = dbReader.getPustd(t, lastBestDoc);

                for (String d: docs) {
                	if(!updatedPustdMap.containsKey(d) && docOldPustdMap.containsKey(d)){
                    double pustdOld = docOldPustdMap.get(d);

                    double pustdNew = Math.exp(1-pustdLastBest)*pustdOld;

                    pustdMap.put(d,pustdNew);
                	}
                }

            }else if(!DPre.contains(t) && qAdded.contains(t)){
                double idfT = dbReader.getIdf(t);

                for (String d: docs) {
                	if(!updatedPustdMap.containsKey(d) && docOldPustdMap.containsKey(d)){
                    double pustdOld = docOldPustdMap.get(d);

                    double pustdNew = Math.exp(1 + idfT) * pustdOld;

                    pustdMap.put(d, pustdNew);
                	}
                }

            }else if(qTheme.contains(t)){

                double pustdLastBest = dbReader.getPustd(t, lastBestDoc);

                for (String d: docs) {
                	if(!updatedPustdMap.containsKey(d) && docOldPustdMap.containsKey(d)){
                    double pustdOld = docOldPustdMap.get(d);

                    double pustdNew = Math.exp(1+(1-pustdLastBest))*pustdOld;

                    pustdMap.put(d, pustdNew);
                	}
                }

            }else{
            	continue;
            }

            if(pustdMap.size()>0)
            	DBWriter.updatePustdByTerm(t, pustdMap);
        }
    }
}
