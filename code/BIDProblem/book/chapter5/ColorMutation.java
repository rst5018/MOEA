package chapter5;

import org.moeaframework.core.PRNG;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;

public class ColorMutation implements Variation {

	@Override
	public int getArity() {
		return 1;
	}

	@Override
	public Solution[] evolve(Solution[] parents) {
		Solution result = parents[0].copy();
		
		for (int i = 0; i < result.getNumberOfVariables(); i++) {
			if (result.getVariable(i) instanceof Color) {
				Color color = (Color)result.getVariable(i);
				color.setR(Math.min(255, Math.max(0, color.getR() + PRNG.nextInt(-5, 5))));
				color.setG(Math.min(255, Math.max(0, color.getG() + PRNG.nextInt(-5, 5))));
				color.setB(Math.min(255, Math.max(0, color.getB() + PRNG.nextInt(-5, 5))));
			}
		}
		
		return new Solution[] { result };
	}
	
};