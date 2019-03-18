package chapter3;

import java.io.IOException;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.examples.ga.knapsack.Knapsack;
import org.moeaframework.util.Vector;

public class RunKnapsackProblem {

	public static void main(String[] args) throws IOException {
		NondominatedPopulation result = new Executor()
				.withProblemClass(Knapsack.class)
				.withAlgorithm("NSGAII")
				.withMaxEvaluations(10000)
				.distributeOnAllCores()
				.run();

		for (int i = 0; i < result.size(); i++) {
			Solution solution = result.get(i);
			double[] objectives = solution.getObjectives();
					
			// negate objectives to return them to their maximized form
			objectives = Vector.negate(objectives);
					
			System.out.println("Solution " + (i+1) + ":");
			System.out.println("    Sack 1 Profit: " + objectives[0]);
			System.out.println("    Sack 2 Profit: " + objectives[1]);
			System.out.println("    Binary String: " + solution.getVariable(0));
		}
	}

}
