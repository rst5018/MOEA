package chapter5;

import org.moeaframework.core.Initialization;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.util.sequence.LatinHypercube;

public class LatinHypercubeInitialization implements Initialization {
	
	private Problem problem;
	
	private int size;
	
	public LatinHypercubeInitialization(Problem problem, int size) {
		super();
		this.problem = problem;
		this.size = size;
	}

	@Override
	public Solution[] initialize() {
		LatinHypercube lhs = new LatinHypercube();
		double[][] samples = lhs.generate(size, problem.getNumberOfObjectives());
		Solution[] result = new Solution[size];
		
		for (int i = 0; i < size; i++) {
			Solution solution = problem.newSolution();
			
			for (int j = 0; j < problem.getNumberOfVariables(); j++) {
				RealVariable variable = (RealVariable)solution.getVariable(j);
				variable.setValue(samples[i][j] * (variable.getUpperBound()-variable.getLowerBound())
						+ variable.getLowerBound());
			}
			
			result[i] = solution;
		}
		
		return result;
	}
	
}