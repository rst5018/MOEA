package OBJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import util.DateUtil;

public class test {
	static String DATA_FOLDER = "DTA_BID/data";
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JSONObject jObj = new JSONObject();
		 File dir = new File(DATA_FOLDER);
		 System.out.println(dir.getAbsolutePath());
		 for (String filename : Objects.requireNonNull(dir.list())) {
			 System.out.println(filename);
			 BufferedReader br = new BufferedReader(new FileReader(DATA_FOLDER + "/" + filename));
	         StringBuilder sb = new StringBuilder();
	         String line = br.readLine();
	         while (line != null) {
	        	 //System.out.println(line);
	             sb.append(line);
	             line = br.readLine();
	         }
	         jObj = new JSONObject(sb.toString());
	     }		 
        ProjectBID projectBID = new ProjectBID(jObj);
        General.pubProjectBID = projectBID;
        BIDProblems curBID = new BIDProblems();
        
//        Package currentPackage = General.pubProjectBID.Packages().get(0);
//		Contractor currentContractor = currentPackage.getContractorByID(1);
//		String currentTime = DateUtil.add(currentPackage.from(), 5);
//        
//        double cost = curBID.realSellCostForContractor(currentPackage, currentContractor, currentTime);
//        
//        System.out.println(cost);
        
        NondominatedPopulation result = new Executor()
        		.withProblemClass(BIDProblems.class)
        		.withAlgorithm("GDE3")
        		.withMaxEvaluations(10000)
        		.run();

	}

}
