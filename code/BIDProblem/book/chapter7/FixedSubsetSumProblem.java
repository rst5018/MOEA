package chapter7;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.Subset;
import org.moeaframework.problem.AbstractProblem;

public class FixedSubsetSumProblem extends AbstractProblem {
	
	private int k;
	
	private int[] values;

	public FixedSubsetSumProblem(int k, int[] values) {
		super(1, 1);
		this.k = k;
		this.values = values;
	}

	@Override
	public void evaluate(Solution solution) {
		int[] subset = EncodingUtils.getSubset(solution.getVariable(0));
		int sum = 0;
		
		for (int i = 0; i < subset.length; i++) {
			sum += values[subset[i]];
		}
		
		solution.setObjective(0, Math.abs(sum));
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, 1);
		solution.setVariable(0, new Subset(k, values.length));
		return solution;
	}

}
