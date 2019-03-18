package chapter5;

import java.util.Properties;

import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;
import org.moeaframework.core.spi.OperatorProvider;

public class ColorProvider extends OperatorProvider {

	@Override
	public String getMutationHint(Problem problem) {
		Solution solution = problem.newSolution();
		
		for (int i = 0; i < problem.getNumberOfVariables(); i++) {
			if (solution.getVariable(i) instanceof Color) {
				return "colormutation";
			}
		}
		
		return null;
	}

	@Override
	public String getVariationHint(Problem problem) {
		return getMutationHint(problem);
	}

	@Override
	public Variation getVariation(String name, Properties properties,
			Problem problem) {
		if (name.equalsIgnoreCase("colormutation")) {
			return new ColorMutation();
		}
		
		return null;
	}

}