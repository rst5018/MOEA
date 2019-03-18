package chapter5;

import org.moeaframework.Executor;
import org.moeaframework.Instrumenter;
import org.moeaframework.analysis.collector.Accumulator;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.spi.AlgorithmFactory;

public class PlotDynamicsWithProvider {

	public static void main(String[] args) {
		AlgorithmFactory.getInstance().addProvider(new RandomWalkerProvider());

		Instrumenter instrumenter = new Instrumenter()
				.withProblem("UF1")
				.withFrequency(100)
				.addAllowedPackage("chapter5")
				.attachGenerationalDistanceCollector();

		new Executor()
				.withSameProblemAs(instrumenter)
				.withAlgorithm("RandomWalker")
				.withMaxEvaluations(10000)
				.withInstrumenter(instrumenter)
				.run();

		Accumulator accumulator = instrumenter.getLastAccumulator();

		new Plot().add(accumulator).show();
	}

}
