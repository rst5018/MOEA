package chapter9;

import java.io.File;
import java.io.IOException;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.PopulationIO;

import chapter2.SchafferProblem;

public class SaveToFile {

	public static void main(String[] args) {
		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();
		
		try {
			PopulationIO.write(new File("solutions.dat"), result);
			System.out.println("Saved " + result.size() + " solutions!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
