package chapter5;

import org.moeaframework.core.Algorithm;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Variation;
import org.moeaframework.core.operator.RandomInitialization;
import org.moeaframework.core.operator.real.PM;
import chapter2.SchafferProblem;

public class RunRandomWalker {
	
	public static void main(String[] args) {
		Problem problem = new SchafferProblem();
		
		Initialization initialization = new RandomInitialization(
				problem,
				100);
		
		Variation variation = new PM(
				1.0 / problem.getNumberOfVariables(),
				30.0);
		
		Algorithm algorithm = new RandomWalker(
				problem,
				initialization,
				variation);
		
		while (algorithm.getNumberOfEvaluations() < 10000) {
			algorithm.step();
		}
		
		NondominatedPopulation result = algorithm.getResult();
	}

}
