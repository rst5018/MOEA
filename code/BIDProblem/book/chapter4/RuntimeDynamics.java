package chapter4;

import java.io.IOException;

import org.moeaframework.Executor;
import org.moeaframework.Instrumenter;
import org.moeaframework.analysis.collector.Accumulator;
import org.moeaframework.analysis.plot.Plot;

public class RuntimeDynamics {
	
	public static void main(String[] args) throws IOException {
		Instrumenter instrumenter = new Instrumenter()
				.withProblem("UF1")
				.withFrequency(100)
				.attachElapsedTimeCollector()
				.attachGenerationalDistanceCollector();
		
		new Executor()
				.withSameProblemAs(instrumenter)
				.withAlgorithm("NSGAII")
				.withMaxEvaluations(10000)
				.withInstrumenter(instrumenter)
				.run();
		
		Accumulator accumulator = instrumenter.getLastAccumulator();
		
		new Plot()
				.add(accumulator)
				.show();
	}

}
