package chapter10;

import java.io.IOException;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.ExternalProblem;

public class StdioSchafferProblem extends ExternalProblem {

	public StdioSchafferProblem() throws IOException {
		super("python", "book/chapter10/schaffer.py");
	}

	@Override
	public String getName() {
		return "ExternalSchafferProblem";
	}

	@Override
	public int getNumberOfConstraints() {
		return 0;
	}

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}

	@Override
	public int getNumberOfVariables() {
		return 1;
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, 2);
		solution.setVariable(0, EncodingUtils.newReal(-10.0, 10.0));
		return solution;
	}
	
}
