package chapter5;
import org.moeaframework.core.Solution;
import org.moeaframework.problem.AbstractProblem;

public class ColorBlenderProblem extends AbstractProblem {
	
	private final Color target;
	
	public ColorBlenderProblem(int N, Color target) {
		super(N, 1);
		this.target = target;
	}
	
	public Color blend(Color c1, Color c2, double ratio) {
	    double invRatio = 1.0 - ratio;
	    int r = (int)((c1.getR() * invRatio) + (c2.getR() * ratio));
	    int g = (int)((c1.getG() * invRatio) + (c2.getG() * ratio));
	    int b = (int)((c1.getB() * invRatio) + (c2.getB() * ratio));

	    return new Color(r, g, b);
	}

	@Override
	public void evaluate(Solution solution) {
		Color result = new Color(255, 255, 255);
		
		for (int i = 0; i < numberOfVariables; i++) {
			result = blend(result, (Color)solution.getVariable(i), 0.5);
		}
		
		int diff = Math.abs(result.getR()-target.getR()) +
				Math.abs(result.getG()-target.getG()) +
				Math.abs(result.getB()-target.getB());
		
		solution.setObjective(0, diff);
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(numberOfVariables, numberOfObjectives);
		
		for (int i = 0; i < numberOfVariables; i++) {
			solution.setVariable(i, new Color());
		}
		
		return solution;
	}

}