package chapter10;

import org.moeaframework.Executor;

public class TestTiming {
	
	public static void main(String[] args) {
		long startTime;
		
		// run without multithreading
		startTime = System.currentTimeMillis();
		
		new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(ExpensiveSchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();
		
		System.out.println("Single threaded: " + (System.currentTimeMillis()-startTime));
		
		// run with mulithreading
		startTime = System.currentTimeMillis();
		
		new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(ExpensiveSchafferProblem.class)
				.withMaxEvaluations(10000)
				.distributeOnAllCores()
				.run();
		
		System.out.println("Multithreading: " + (System.currentTimeMillis()-startTime));
	}

}
