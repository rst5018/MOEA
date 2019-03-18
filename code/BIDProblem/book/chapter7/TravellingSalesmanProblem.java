package chapter7;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class TravellingSalesmanProblem extends AbstractProblem {
	
	private double[][] cities;

	public TravellingSalesmanProblem(double[][] cities) {
		super(1, 1);
		this.cities = cities;
	}
	
	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, 1);
		solution.setVariable(0, EncodingUtils.newPermutation(cities.length));
		return solution;
	}
	
	private double distance(int i, int j) {
		return Math.sqrt(
				Math.pow(cities[i][0]-cities[j][0], 2.0) +
				Math.pow(cities[i][1]-cities[j][1], 2.0));
	}

	@Override
	public void evaluate(Solution solution) {
		int[] path = EncodingUtils.getPermutation(solution.getVariable(0));
		double totalDistance = 0.0;
		
		for (int i = 0; i < path.length; i++) {
			totalDistance += distance(path[i], path[(i+1) % path.length]);
		}
		
		solution.setObjective(0, totalDistance);
	}

}
