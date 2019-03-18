package chapter6;

import java.io.File;
import java.io.IOException;

import org.moeaframework.core.FrameworkException;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.PopulationIO;
import org.moeaframework.core.Problem;
import org.moeaframework.core.spi.ProblemProvider;

import chapter2.SchafferProblem;

public class SchafferProblemProvider extends ProblemProvider {

	@Override
	public Problem getProblem(String name) {
		if (name.equalsIgnoreCase("MySchafferProblem")) {
			return new SchafferProblem();
		} else {
			return null;
		}
	}

	@Override
	public NondominatedPopulation getReferenceSet(String name) {
		if (name.equalsIgnoreCase("MySchafferProblem")) {
			try {
				return new NondominatedPopulation(
						PopulationIO.readObjectives(new File("./pf/Schaffer.pf")));
			} catch (IOException e) {
				throw new FrameworkException(e);
			}
		} else {
			return null;
		}
	}

}
