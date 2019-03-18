package chapter5;

import org.moeaframework.algorithm.AbstractEvolutionaryAlgorithm;
import org.moeaframework.algorithm.GDE3;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Variation;
import org.moeaframework.core.comparator.ChainedComparator;
import org.moeaframework.core.comparator.CrowdingComparator;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.operator.GAVariation;
import org.moeaframework.core.operator.TournamentSelection;
import org.moeaframework.core.operator.real.DifferentialEvolution;
import org.moeaframework.core.operator.real.DifferentialEvolutionSelection;
import org.moeaframework.core.operator.real.PM;
import org.moeaframework.core.operator.real.SBX;

public class Hyperheuristic extends AbstractEvolutionaryAlgorithm {
	
	private NSGAII nsgaii;
	
	private GDE3 gde3;
	
	private int iteration;

	public Hyperheuristic(Problem problem, NondominatedSortingPopulation population,
			Initialization initialization) {
		super(problem, population, null, initialization);

		// define our NSGAII instance
		TournamentSelection selection = new TournamentSelection(2, 
				new ChainedComparator(
						new ParetoDominanceComparator(),
						new CrowdingComparator()));

		Variation variation = new GAVariation(
				new SBX(1.0, 25.0),
				new PM(1.0 / problem.getNumberOfVariables(), 30.0));

		nsgaii = new NSGAII(
				problem,
				population,
				null, // no archive
				selection,
				variation,
				initialization);
		
		// define our GDE3 instance
		gde3 = new GDE3(problem,
				population,
				new ParetoDominanceComparator(),
				new DifferentialEvolutionSelection(),
				new DifferentialEvolution(0.1, 0.9),
				initialization);
	}

	@Override
	protected void iterate() {
		if (iteration % 2 == 0) {
			nsgaii.iterate();
		} else {
			gde3.iterate();
		}
		
		numberOfEvaluations = nsgaii.getNumberOfEvaluations() +
				gde3.getNumberOfEvaluations();
		
		iteration++;
	}

}