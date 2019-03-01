package problem;

import java.io.File;
import java.io.IOException;

import org.moeaframework.core.FrameworkException;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.PopulationIO;
import org.moeaframework.core.Problem;
import org.moeaframework.core.spi.ProblemProvider;

public class MultiRoundBidProblemProvider extends ProblemProvider {
	private Problem _problem;
	@Override
	public Problem getProblem(String name) {
		// TODO Auto-generated method stub
		if (name.equalsIgnoreCase("MyMultiRoundBidProblem")) {
			_problem = new MultiRoundBidProblem();
			return _problem;
		} else
		return null;
	}

	@Override
	public NondominatedPopulation getReferenceSet(String name) {
		// TODO Auto-generated method stub
		if (name.equalsIgnoreCase("MyMultiRoundBidProblem")) {			
			try {
				return new NondominatedPopulation(
						PopulationIO.readObjectives(new File("./pf/MultiRoundBid.pf")));
			} catch (IOException e) {
				// TODO: handle exception
				throw new FrameworkException(e);				
			}
		}else return null;
	}
	
	
}
