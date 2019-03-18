package chapter8;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class MixedIntegerSrinivasProblem extends AbstractProblem {

	public MixedIntegerSrinivasProblem() {
		super(2, 2, 2);
	}

	@Override
	public void evaluate(Solution solution) {
		int x = EncodingUtils.getInt(solution.getVariable(0));
		double y = EncodingUtils.getReal(solution.getVariable(1));
		double f1 = Math.pow(x - 2.0, 2.0) + Math.pow(y - 1.0, 2.0) + 2.0;
		double f2 = 9.0*x - Math.pow(y - 1.0, 2.0);
		double c1 = Math.pow(x, 2.0) + Math.pow(y, 2.0) - 225.0;
		double c2 = x - 3.0*y + 10.0;
		
		solution.setObjective(0, f1);
		solution.setObjective(1, f2);
		solution.setConstraint(0, c1 <= 0.0 ? 0.0 : c1);
		solution.setConstraint(1, c2 <= 0.0 ? 0.0 : c2);
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(2, 2, 2);
		
		solution.setVariable(0, EncodingUtils.newBinaryInt(-20, 20));
		solution.setVariable(1, EncodingUtils.newReal(-20.0, 20.0));
		
		return solution;
	}

}
