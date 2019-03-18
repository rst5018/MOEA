package chapter9;

import java.io.IOException;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import chapter2.SchafferProblem;

public class PrintFormattedSolutions {
	
	public static void main(String[] args) throws IOException {
		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();

		for (int i = 0; i < result.size(); i++) {
			Solution solution = result.get(i);
			System.out.print("Solution " + i + ":");
			
			for (int j = 0; j < solution.getNumberOfVariables(); j++) {
				System.out.printf(" %.3f", EncodingUtils.getReal(solution.getVariable(j)));
			}
			
			System.out.print(" =>");
			
			for (int j = 0; j < solution.getNumberOfObjectives(); j++) {
				System.out.printf(" %.3f", solution.getObjective(j));
			}
			
			System.out.println();
		}
	}

}
