package chapter10;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;

import chapter2.SchafferProblem;

public class ComparingCInterface {
	
	public static void main(String[] args) {
		long startTime;
		
		// run within Java
		startTime = System.currentTimeMillis();
		
		new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();
		
		System.out.println("Pure Java: " + (System.currentTimeMillis() - startTime));
		
		// run using Java Native Access
		startTime = System.currentTimeMillis();
		
		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(JNASchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();
		
		System.out.println("Via JNA: " + (System.currentTimeMillis() - startTime));
		
		// run using standard I/O as an ExternalProblem
		startTime = System.currentTimeMillis();
		
		new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(StdioSchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();
		
		System.out.println("Via Standard I/O: " + (System.currentTimeMillis() - startTime));
	}

}
