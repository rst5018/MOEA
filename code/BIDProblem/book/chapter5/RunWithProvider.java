package chapter5;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.spi.AlgorithmFactory;

import chapter2.SchafferProblem;

public class RunWithProvider {
	
	public static void main(String[] args) {
		AlgorithmFactory.getInstance().addProvider(new RandomWalkerProvider());
		
		NondominatedPopulation result = new Executor()
				.withAlgorithm("RandomWalker")
				.withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();
	}

}
