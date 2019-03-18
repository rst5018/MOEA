package chapter2;

import org.moeaframework.Executor;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.NondominatedPopulation;

public class PlotSchafferProblem {
	
	public static void main(String[] args) {
		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();
		
		new Plot()
				.add("NSGAII", result)
				.show();
	}

}
