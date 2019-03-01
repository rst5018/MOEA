package RunnerNHOM7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.Instrument;

import org.json.JSONObject;
import org.moeaframework.Analyzer;
import org.moeaframework.Executor;
import org.moeaframework.Instrumenter;
import org.moeaframework.analysis.collector.Accumulator;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.spi.AlgorithmFactory;
import org.moeaframework.core.spi.AlgorithmProvider;
import org.moeaframework.analysis.diagnostics.LaunchDiagnosticTool;
import org.moeaframework.core.Settings;
import org.moeaframework.core.spi.AlgorithmFactory;
import org.moeaframework.core.spi.ProblemFactory;

import com.sun.org.apache.bcel.internal.generic.ALOAD;

import jmetal.core.Solution;
import problem.HyperheuristicProvider;
import problem.MultiRoundBidProblem;
import problem.MultiRoundBidProblemProvider;
import problem.RandomWalkerProvider;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	    String[] ALGORITHMS = {"NSGAIII", "eMOEA", "GDE3", "PESA2", "IBEA", "SMPSO"};
	    String filename = "DTA_BID/data/data1.json";
	    BufferedReader br = new BufferedReader(new FileReader(filename));
	    StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
       	 //System.out.println(line);
            sb.append(line);
            line = br.readLine();
        }
        JSONObject obj = new JSONObject(sb.toString());
        MultiRoundBidProblem.init(obj);
        
        AlgorithmFactory.getInstance().addProvider(new RandomWalkerProvider());
        AlgorithmFactory.getInstance().addProvider(new HyperheuristicProvider());
        ProblemFactory.getInstance().addProvider(new MultiRoundBidProblemProvider());
        Settings.PROPERTIES.setString(Settings.KEY_DIAGNOSTIC_TOOL_ALGORITHMS, 
        		"RandomWalker, Hyperheuristic, NSGAIII, GDE3");
        Settings.PROPERTIES.setString(Settings.KEY_DIAGNOSTIC_TOOL_PROBLEMS, "MyMultiRoundBidProblem");
        LaunchDiagnosticTool.main(args);
       
//        Executor executor = new Executor()
//                .withAlgorithm(ALGORITHMS[0])
//                .withProblemClass(MultiRoundBidProblem.class)
//                .withMaxEvaluations(10000)
//                .withProperty("bisections", 4.0D);
//        NondominatedPopulation population = executor.run();
//        for (int i =0; i < population.size(); i++) {
//        	org.moeaframework.core.Solution solution = population.get(i);
//			System.out.printf("%.5f %.5f %.5f %.5f \n", solution.getObjective(0), solution.getObjective(1),solution.getObjective(2), solution.getObjective(3) );
//		}
//        Analyzer analyzer = new Analyzer()
//        		.withSameProblemAs(executor)
//        		.includeAllMetrics()
//        		.showStatisticalSignificance();
//        analyzer.addAll("NSGAIII", executor.runSeeds(10));
//        analyzer.printAnalysis();
//        new Plot()
//        .add(analyzer)
//        .show();
        System.out.println("Done");

	}

}
