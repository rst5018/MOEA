package problem;

import org.moeaframework.algorithm.AbstractEvolutionaryAlgorithm;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.PRNG;
import org.moeaframework.core.Population;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;

public class RandomWalker extends AbstractEvolutionaryAlgorithm {

	private final Variation variation;
	public RandomWalker(Problem problem, Initialization initialization, Variation variation) {
		super(problem, new NondominatedSortingPopulation(), null, initialization);
		this.variation = variation;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void iterate() {
		// TODO Auto-generated method stub
		NondominatedSortingPopulation population = (NondominatedSortingPopulation) getPopulation();
		int index = PRNG.nextInt(population.size());
		Solution parent = population.get(index);
		Solution offspring = variation.evolve(new Solution[] {parent})[0];
		evaluate(offspring);
		population.add(offspring);
		population.truncate(population.size() - 1);
	}

}
