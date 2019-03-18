package chapter9;

import java.io.File;
import java.io.IOException;

import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.Population;
import org.moeaframework.core.PopulationIO;

public class PlotReferenceSet {
	
	public static void main(String[] args) throws IOException {
		Population withEpsilons = PopulationIO.readObjectives(
				new File("Schaffer_Epsilon.pf"));
		
		Population withoutEpsilons = PopulationIO.readObjectives(
				new File("Schaffer.pf"));
		
		new Plot()
				.add("With Epsilons", withEpsilons)
				.add("Without Epsilons", withoutEpsilons)
				.show();
	}

}
