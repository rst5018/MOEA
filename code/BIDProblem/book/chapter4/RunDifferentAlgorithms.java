package chapter4;

import java.io.IOException;

import org.moeaframework.Executor;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.NondominatedPopulation;
import chapter2.SchafferProblem;

public class RunDifferentAlgorithms {
	
	public static void main(String[] args) throws IOException {
		NondominatedPopulation result1 = new Executor()
				.withProblemClass(SchafferProblem.class)
				.withAlgorithm("NSGAII")
				.withMaxEvaluations(10000)
				.run();
		
		NondominatedPopulation result2 = new Executor()
				.withProblemClass(SchafferProblem.class)
				.withAlgorithm("GDE3")
				.withMaxEvaluations(10000)
				.run();
		
		new Plot()
				.add("NSGAII", result1)
				.add("GDE3", result2)
				.show();
	}

}
