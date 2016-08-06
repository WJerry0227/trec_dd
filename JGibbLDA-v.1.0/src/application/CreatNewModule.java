package application;

import jgibblda.Estimator;
import jgibblda.Inferencer;
import jgibblda.LDACmdOption;
import jgibblda.Model;

/*creat by 文藻
 */


public class CreatNewModule {
	private static double alpha = 0.5;
	private static double beta = 0.1;
	private static int  title = 100;
	private static int niters = 1000;
	
	/**
	 * 根据语料库建立估计模型，模型最终名称"model-final"，
	 * @param directory语料目录
	 * @param fileName语料文件名如newdoc.dat
	 */
	public static void newModule(String directory, String fileName) {
		
		LDACmdOption ldaOption = new LDACmdOption();   
	    ldaOption.est = true;  
	    ldaOption.estc = false;  
	    ldaOption.modelName = "model-final";  
	    ldaOption.dir = directory; 
	    ldaOption.dfile = fileName;  
	    ldaOption.alpha = alpha;  
	    ldaOption.beta = beta;  
	    ldaOption.K =  title;  
	    ldaOption.niters = niters;  
	 //   topicNum = ldaOption.K;  
	    Estimator estimator = new Estimator();  
	    estimator.init(ldaOption);  
	    estimator.estimate(); 
	    Inferencer inferencer = new Inferencer(); 
        inferencer.init(ldaOption);
		
	}

	
	 /**
	  * 新文档的主题分布
	  * @param resultDir
	  * @param fileName
	 * @return 
	  * @return
	  */
	public static String docsTopic(String resultDir,String fileName) {
		   
	        LDACmdOption ldaOption = new LDACmdOption();   
	        ldaOption.inf = true;  
	        ldaOption.estc = false;  
	        ldaOption.dir = resultDir;   
	        ldaOption.modelName = "model-final";  
	        ldaOption.dfile = fileName; 
	        ldaOption.niters =30;
	        Inferencer inferencer = new Inferencer();   
	        inferencer.init(ldaOption);  
	        Model newModel = inferencer.inference();
		
		    return "" ;
	}
	
	public static void main(String[] args) {
		//newModule("models/casestudy-en","newdocs.dat");
		docsTopic("models/casestudy-en","mydocs.dat");
	}

}
