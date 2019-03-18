package chapter7;

import java.util.Arrays;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

public class SolveFixedSubsetSumProblem {
	
	private static int[] values = { -1, 1, 3, 5, 9, -4, -2, -8, 17, 24, 18, -16, -20 };
	
	public static void main(String[] args) {
		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(FixedSubsetSumProblem.class, 6, values)
				.withMaxEvaluations(10000)
				.run();
		
		
		
		for (Solution solution : result) {
			int[] subset = EncodingUtils.getSubset(solution.getVariable(0));
			System.out.println(Arrays.toString(toValues(subset, values)) +
					" => " + solution.getObjective(0));
		}
	}
	
	public static int[] toValues(int[] subset, int[] values) {
		int[] result = new int[subset.length];
		
		for (int i = 0; i < subset.length; i++) {
			result[i] = values[subset[i]];
		}
		
		return result;
	}

}
