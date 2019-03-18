package org.moeaframework.examples.ga.knapsack;

import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.util.Vector;

public class Knapsack implements Problem {

	/**
	 * The number of sacks.
	 */
	private int nsacks = 2;

	/**
	 * The number of items.
	 */
	private int nitems = 5;

	/**
	 * Entry {@code profit[i][j]} is the profit from including item {@code j}
	 * in sack {@code i}.
	 */
	private int[][] profit = {
			{2, 5, 1, 4, 3}, // sack 1
			{1, 2, 5, 3, 3}  // sack 2
	};

	/**
	 * Entry {@code weight[i][j]} is the weight incurred from including item
	 * {@code j} in sack {@code i}.
	 */
	private int[][] weight = {
			{3, 4, 1, 5, 5}, // sack 1
			{3, 2, 5, 3, 2}  // sack 2
	};

	/**
	 * Entry {@code capacity[i]} is the weight capacity of sack {@code i}.
	 */
	private int[] capacity = { 10, 8 };

	@Override
	public void evaluate(Solution solution) {
		boolean[] d = EncodingUtils.getBinary(solution.getVariable(0));
		double[] f = new double[nsacks];
		double[] g = new double[nsacks];

		// calculate the profits and weights for the knapsacks
		for (int i = 0; i < nitems; i++) {
			if (d[i]) {
				for (int j = 0; j < nsacks; j++) {
					f[j] += profit[j][i];
					g[j] += weight[j][i];
				}
			}
		}

		// check if any weights exceed the capacities
		for (int j = 0; j < nsacks; j++) {
			if (g[j] <= capacity[j]) {
				g[j] = 0.0;
			} else {
				g[j] = g[j] - capacity[j];
			}
		}

		// negate the objectives since Knapsack is maximization
		solution.setObjectives(Vector.negate(f));
		solution.setConstraints(g);
	}

	@Override
	public String getName() {
		return "Knapsack";
	}

	@Override
	public int getNumberOfConstraints() {
		return nsacks;
	}

	@Override
	public int getNumberOfObjectives() {
		return nsacks;
	}

	@Override
	public int getNumberOfVariables() {
		return 1;
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, nsacks, nsacks);
		solution.setVariable(0, EncodingUtils.newBinary(nitems));
		return solution;
	}

	@Override
	public void close() {
		//do nothing
	}

}
