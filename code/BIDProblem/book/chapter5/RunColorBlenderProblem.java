package chapter5;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.spi.OperatorFactory;

public class RunColorBlenderProblem {
	
	public static void main(String[] args) {
		OperatorFactory.getInstance().addProvider(new ColorProvider());
		
		NondominatedPopulation result = new Executor()
				.withProblemClass(ColorBlenderProblem.class, 3, new Color(127, 127, 127))
				.withAlgorithm("NSGAII")
				.withMaxEvaluations(10000)
				.distributeOnAllCores()
				.run();
		
		for (Solution solution : result) {
			System.out.print(solution.getObjective(0));
			
			for (int i = 0; i < solution.getNumberOfVariables(); i++) {
				System.out.print(" ");
				System.out.print(solution.getVariable(i));
			}
		}
	}

}
