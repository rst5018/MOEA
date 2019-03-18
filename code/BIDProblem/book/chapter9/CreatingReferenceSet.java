package chapter9;

import java.io.File;
import java.io.IOException;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.PopulationIO;

import chapter2.SchafferProblem;

public class CreatingReferenceSet {
	
	public static void main(String[] args) throws IOException {
		int nseeds = 25;
		String[] algorithms = new String[] { "NSGAII", "GDE3", "OMOPSO" };
		
		NondominatedPopulation referenceSet = new NondominatedPopulation();
		
		Executor executor = new Executor()
				.withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(10000);
		
		for (String algorithm : algorithms) {
			executor.withAlgorithm(algorithm);
			
			for (int i = 0; i < nseeds; i++) {
				referenceSet.addAll(executor.run());
			}
		}
		
		PopulationIO.writeObjectives(new File("Schaffer.pf"), referenceSet);
	}

}