package chapter6;

import org.moeaframework.analysis.diagnostics.LaunchDiagnosticTool;
import org.moeaframework.core.Settings;
import org.moeaframework.core.spi.AlgorithmFactory;
import org.moeaframework.core.spi.ProblemFactory;

import chapter5.HyperheuristicProvider;
import chapter5.RandomWalkerProvider;

public class CustomProblem {
	
	public static void main(String[] args) throws Exception {
		AlgorithmFactory.getInstance().addProvider(new RandomWalkerProvider());
		AlgorithmFactory.getInstance().addProvider(new HyperheuristicProvider());
		
		ProblemFactory.getInstance().addProvider(new SchafferProblemProvider());
		
		Settings.PROPERTIES.setString(
				Settings.KEY_DIAGNOSTIC_TOOL_ALGORITHMS,
				"RandomWalker, Hyperheuristic, NSGAII, GDE3");
		
		Settings.PROPERTIES.setString(
				Settings.KEY_DIAGNOSTIC_TOOL_PROBLEMS,
				"MySchafferProblem");
		
		LaunchDiagnosticTool.main(args);
	}

}
