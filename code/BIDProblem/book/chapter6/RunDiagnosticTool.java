package chapter6;

import org.moeaframework.analysis.diagnostics.LaunchDiagnosticTool;
import org.moeaframework.core.Settings;
import org.moeaframework.core.spi.AlgorithmFactory;

import chapter5.HyperheuristicProvider;
import chapter5.RandomWalkerProvider;

public class RunDiagnosticTool {
	
	public static void main(String[] args) throws Exception {
		AlgorithmFactory.getInstance().addProvider(new RandomWalkerProvider());
		AlgorithmFactory.getInstance().addProvider(new HyperheuristicProvider());
		
		Settings.PROPERTIES.setString(
				Settings.KEY_DIAGNOSTIC_TOOL_ALGORITHMS,
				"RandomWalker, Hyperheuristic, NSGAII, GDE3");
		
		LaunchDiagnosticTool.main(args);
	}

}
