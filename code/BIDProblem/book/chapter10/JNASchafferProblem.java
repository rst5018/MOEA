package chapter10;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import com.sun.jna.Native;

public class JNASchafferProblem extends AbstractProblem {
	
	public static native double schaffer(double[] vars, double[] objs);
	
	static {
		System.setProperty("jna.library.path", "./book/chapter10");
		Native.register("schaffer");
	}

	public JNASchafferProblem() {
		super(1, 2);
	}

	@Override
	public void evaluate(Solution solution) {
		double[] vars = EncodingUtils.getReal(solution);
		double[] objs = new double[solution.getNumberOfObjectives()];
		
		schaffer(vars, objs);
		
		solution.setObjectives(objs);
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, 2);
		solution.setVariable(0, EncodingUtils.newReal(-10.0, 10.0));
		return solution;
	}
	
}
