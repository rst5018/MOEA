package problem;

import java.util.Properties;

import org.moeaframework.core.Algorithm;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.operator.RandomInitialization;
import org.moeaframework.core.spi.AlgorithmProvider;
import org.moeaframework.util.TypedProperties;

import com.sun.org.apache.bcel.internal.generic.ALOAD;

public class HyperheuristicProvider extends AlgorithmProvider {

	@Override
	public Algorithm getAlgorithm(String name, Properties properties, Problem problem) {
		// TODO Auto-generated method stub
		if(name.equalsIgnoreCase("hyperheuristic")) {
			TypedProperties typedProperties = new TypedProperties(properties);
			int populationSize = typedProperties.getInt("populationSize", 100);
			Initialization initialization = new RandomInitialization(problem, populationSize);
			Algorithm algorithm = new Hyperheuristic(problem,
					new NondominatedSortingPopulation(),
					initialization);
			return algorithm;
		}else return null;
	}

}
