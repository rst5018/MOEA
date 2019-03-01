package OBJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.jfree.chart.plot.PieLabelDistributor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.moeaframework.Analyzer;
import org.moeaframework.Executor;
import org.moeaframework.analysis.plot.Plot;
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
			 if (!filename.endsWith(".json")) {
	                continue;
	            }
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

        Executor executor = new Executor()
        		.withProblemClass(BIDProblem.class, projectBID, 4)
        		.withMaxEvaluations(10000);
       
        Analyzer analyzer = new Analyzer()
        		.withSameProblemAs(executor)
        		.includeHypervolume()
        		.showStatisticalSignificance();
        analyzer.addAll("NSGAIII", executor.withAlgorithm("NSGAIII").runSeeds(10));
        analyzer.printAnalysis();
        new Plot().add(analyzer).show();         
        

	}

}
