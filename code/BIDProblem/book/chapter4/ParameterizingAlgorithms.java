package chapter4;

import org.moeaframework.Executor;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.NondominatedPopulation;

import chapter2.SchafferProblem;

public class ParameterizingAlgorithms {
	
	public static void main(String[] args) {
		NondominatedPopulation result1 = new Executor()
				.withProblemClass(SchafferProblem.class)
				.withAlgorithm("NSGAII")
				.withMaxEvaluations(10000)
				.run();
		
		NondominatedPopulation result2 = new Executor()
				.withProblemClass(SchafferProblem.class)
				.withAlgorithm("NSGAII")
				.withMaxEvaluations(10000)
				.withProperty("populationSize", 50)
				.withProperty("sbx.rate", 1.0)
				.withProperty("sbx.distributionIndex", 250.0)
				.withProperty("pm.rate", 0.0)
				.withProperty("pm.distributionIndex", 300.0)
				.run();
		
		new Plot()
				.add("NSGAII-Paramerized", result2)
				.add("NSGAII", result1)
				.show();
	}

}
