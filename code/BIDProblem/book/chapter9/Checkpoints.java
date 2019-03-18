package chapter9;

import java.io.File;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;

import chapter2.SchafferProblem;

public class Checkpoints {
	
	public static void main(String[] args) {
		File checkpointFile = new File("checkpoint.dat");
		long start = System.currentTimeMillis();
		
		if (checkpointFile.exists()) {
			System.out.println("Checkpoint file exists, will resume from prior run!");
		}
		
		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(1000000)
				.withCheckpointFrequency(10000)
				.withCheckpointFile(checkpointFile)
				.run();
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - start) / 1000 + "s");
	}

}
