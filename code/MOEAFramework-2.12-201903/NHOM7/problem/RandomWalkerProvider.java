package problem;

import java.util.Properties;

import org.moeaframework.core.Algorithm;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Variation;
import org.moeaframework.core.operator.RandomInitialization;
import org.moeaframework.core.spi.AlgorithmProvider;
import org.moeaframework.core.spi.OperatorFactory;
import org.moeaframework.util.TypedProperties;

public class RandomWalkerProvider extends AlgorithmProvider{

	@Override
	public Algorithm getAlgorithm(String name, Properties properties, Problem problem) {
		// TODO Auto-generated method stub
		if(name.equalsIgnoreCase("RandomWalker")) {
			TypedProperties typedProperties = new TypedProperties(properties);
			int populationSize = typedProperties.getInt("populationSize", 100);
			Initialization initialization = new RandomInitialization(problem, populationSize);
			Variation variation = OperatorFactory.getInstance().getVariation("pm", properties, problem);
			return new RandomWalker(problem, initialization, variation);
		}else 
		return null;
	}

}
