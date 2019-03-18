package chapter8;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

public class RunIntegerSchafferProblems {
	
	public static void main(String[] args) {
		NondominatedPopulation result1 = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(BinaryIntegerSchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();
		
		NondominatedPopulation result2 = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(RealIntegerSchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();

		System.out.println("Binary Integer Encoding:");
		for (Solution solution : result1) {
			System.out.printf("  %d => %.5f, %.5f\n",
					EncodingUtils.getInt(solution.getVariable(0)),
					solution.getObjective(0),
					solution.getObjective(1));
		}
		
		System.out.println();
		System.out.println("Real Integer Encoding:");
		for (Solution solution : result2) {
			System.out.printf("  %d => %.5f, %.5f\n",
					EncodingUtils.getInt(solution.getVariable(0)),
					solution.getObjective(0),
					solution.getObjective(1));
		}
	}

}
