package chapter8;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

public class RunMixedIntegerSrinivasProblem {
	
	public static void main(String[] args) {
		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(MixedIntegerSrinivasProblem.class)
				.withMaxEvaluations(10000)
				.withProperty("operator", "sbx+hux+pm+bf")
				.run();

		for (Solution solution : result) {
			if (!solution.violatesConstraints()) {
				System.out.format("%3d %7.3f => %7.3f %7.3f%n",
						EncodingUtils.getInt(solution.getVariable(0)),
						EncodingUtils.getReal(solution.getVariable(1)),
						solution.getObjective(0),
						solution.getObjective(1));
			}
		}
	}

}
