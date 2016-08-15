package Trec2016.dd_trec;

import jgibblda.Estimator;
import jgibblda.Inferencer;
import jgibblda.LDACmdOption;
/**
 * apply LDA Topic model to achieve the classification of the articles
 * @author Wenzao Wang
 * 2016-08-15
 */
public class TopicModelBuildAndApply {
	private static double alpha = 0.5;
	private static double beta = 0.1;
	private static int niters = 1000;
	
	/**
	 * 根据语料库建立估计模型，模型最终名称"model-final"，
	 * @param directory语料目录
	 * @param fileName语料文件名如newdoc.dat
	 */
	public static void newModule(String directory, String fileName,int classNumber) {
		
		LDACmdOption ldaOption = new LDACmdOption();   
	    ldaOption.est = true;  
	    ldaOption.estc = false;  
	    ldaOption.modelName = "model-final";  
	    ldaOption.dir = directory; 
	    ldaOption.dfile = fileName;  
	    ldaOption.alpha = alpha;  
	    ldaOption.beta = beta;  
	    ldaOption.K =  classNumber;  
	    ldaOption.niters = niters;  
	 //   topicNum = ldaOption.K;  
	    Estimator estimator = new Estimator();  
	    estimator.init(ldaOption);  
	    estimator.estimate(); 
	    Inferencer inferencer = new Inferencer(); 
        inferencer.init(ldaOption);	
	}
	
	public static void main(String[] args) {
		//newModule("models/casestudy-en","newdocs.dat");
		newModule("models","text.txt",5);
	}

}
