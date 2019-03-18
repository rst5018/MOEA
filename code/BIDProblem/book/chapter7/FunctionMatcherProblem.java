package chapter7;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.Program;
import org.moeaframework.problem.AbstractProblem;
import org.moeaframework.util.tree.Add;
import org.moeaframework.util.tree.Cos;
import org.moeaframework.util.tree.Divide;
import org.moeaframework.util.tree.Environment;
import org.moeaframework.util.tree.Exp;
import org.moeaframework.util.tree.Get;
import org.moeaframework.util.tree.Log;
import org.moeaframework.util.tree.Multiply;
import org.moeaframework.util.tree.Rules;
import org.moeaframework.util.tree.Sin;
import org.moeaframework.util.tree.Subtract;

public class FunctionMatcherProblem extends AbstractProblem {
	
	public double[] x;
	
	public double[] y;
	
	private Rules rules;
	
	public FunctionMatcherProblem() {
		super(1, 1);
		
		rules = new Rules();
		rules.add(new Add());
		rules.add(new Multiply());
		rules.add(new Subtract());
		rules.add(new Divide());
		rules.add(new Sin());
		rules.add(new Cos());
		rules.add(new Exp());
		rules.add(new Log());
		rules.add(new Get(Number.class, "x"));
		rules.setReturnType(Number.class);
		rules.setMaxVariationDepth(10);
		
		x = new double[100];
		y = new double[100];
		
		for (int i = 0; i < 100; i++) {
			x[i] = 2.0*(i / 100.0) - 1.0; // range from -1 to 1
			y[i] = Math.pow(x[i], 5) - 2.0*Math.pow(x[i], 3) + x[i];
		}
	}
	
	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, 1);
		solution.setVariable(0, new Program(rules));
		return solution;
	}
	
	public double[] calculate(Program program) {
		double[] approximatedY = new double[x.length];
		
		for (int i = 0; i < x.length; i++) {
			Environment environment = new Environment();
			environment.set("x", x[i]);
			approximatedY[i] = ((Number)program.evaluate(environment)).doubleValue();
		}
		
		return approximatedY;
	}

	@Override
	public void evaluate(Solution solution) {
		Program program = (Program)solution.getVariable(0);
		
		// calculate the difference between the approximation and actual
		double[] approximatedY = calculate(program);
		double difference = 0.0;
		
		for (int i = 0; i < x.length; i++) {
			difference += Math.pow(Math.abs(y[i] - approximatedY[i]), 2.0);
		}
		
		difference = Math.sqrt(difference);
		
		// protect against NaN
		if (Double.isNaN(difference)) {
			difference = Double.POSITIVE_INFINITY;
		}

		solution.setObjective(0, difference);
	}

}
