package chapter5;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.spi.AlgorithmFactory;
import chapter2.SchafferProblem;

public class RunHyperheuristic {
	
	public static void main(String[] args) {
		AlgorithmFactory.getInstance().addProvider(new HyperheuristicProvider());
		
		NondominatedPopulation result = new Executor()
				.withAlgorithm("hyperheuristic")
				.withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();
	}

}
