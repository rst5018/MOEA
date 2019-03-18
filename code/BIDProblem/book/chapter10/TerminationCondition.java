package chapter10;

import org.moeaframework.Executor;

public class TerminationCondition {
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		
		new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(ExpensiveSchafferProblem.class)
				.withMaxTime(30000) // 30 seconds
				.run();
		
		double runtime = (System.currentTimeMillis() - startTime) / 1000.0;
		System.out.printf("Terminated after %.1f seconds!%n", runtime);
	}

}
