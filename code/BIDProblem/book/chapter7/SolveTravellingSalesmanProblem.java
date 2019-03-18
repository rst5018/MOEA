package chapter7;

import org.moeaframework.Executor;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.variable.EncodingUtils;

public class SolveTravellingSalesmanProblem {
	
	public static double[][] CITIES = {
			{ 0.0,  0.0  },
			{ 1.0,  1.0  },
			{ 0.75, 0.25 },
			{ 1.0,  0.0  },
			{ 0.95, 0.85 },
			{ 0.2,  0.5  },
			{ 0.7,  0.15 },
			{ 0.6,  0.4  },
			{ 0.15, 0.91 },
			{ 0.24, 0.8  },
			{ 0.05, 0.74 },
			{ 0.35, 0.57 }
	};
	
	public static void main(String[] args) {
		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(TravellingSalesmanProblem.class, CITIES)
				.withMaxEvaluations(10000)
				.run();
		
		int[] bestPath = EncodingUtils.getPermutation(result.get(0).getVariable(0));
		
		new Plot()
				.scatter("Cities", getCoordinate(0), getCoordinate(1))
				.line("Path", getCoordinate(0, bestPath), getCoordinate(1, bestPath))
				.show();
	}
	
	public static double[] getCoordinate(int index) {
		double[] result = new double[CITIES.length];
		
		for (int i = 0; i < CITIES.length; i++) {
			result[i] = CITIES[i][index];
		}
		
		return result;
	}
	
	public static double[] getCoordinate(int index, int[] path) {
		double[] result = new double[path.length+1];
		
		for (int i = 0; i < path.length; i++) {
			result[i] = CITIES[path[i]][index];
		}
		
		result[path.length] = CITIES[path[0]][index];
		
		return result;
	}

}
