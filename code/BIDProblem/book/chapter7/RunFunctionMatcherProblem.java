package chapter7;

import org.moeaframework.Executor;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.variable.Program;

public class RunFunctionMatcherProblem {
	
	public static void main(String[] args) {
		FunctionMatcherProblem problem = new FunctionMatcherProblem();
		
		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(FunctionMatcherProblem.class)
				.withMaxEvaluations(10000)
				.run();
		
		// get the resulting function
		Program program = (Program)result.get(0).getVariable(0);
		
		// print the function
		System.out.println(program);
		System.out.println("Distance: " + result.get(0).getObjective(0));
		
		// display a plot comparing the two functions
		new Plot()
				.line("Actual", problem.x, problem.y)
				.line("Estimated", problem.x, problem.calculate(program))
				.show();
	}

}
