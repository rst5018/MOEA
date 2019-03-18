package problem;

import java.io.File;
import java.io.IOException;

import org.moeaframework.core.FrameworkException;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.PopulationIO;
import org.moeaframework.core.Problem;
import org.moeaframework.core.spi.ProblemProvider;

import Runner.MultiBIDRunner;

public class MultiRoundBidProblemProvider extends ProblemProvider {

	//private Problem _problem;
//	public MultiRoundBidProblemProvider(MultiRoundBidProblem var) {
//		// TODO Auto-generated constructor stub
//		super();
//		_problem = var;
//		
//	}
	@Override
	public Problem getProblem(String name) {
		// TODO Auto-generated method stub
		if (name.equalsIgnoreCase("MultiBIDProblem")) {
			//_problem =  new BIDProblem(_projectBID, _numberOfObj);
			return new MultiRoundBidProblem();
		} else
		return null;
	}

	@Override
	public NondominatedPopulation getReferenceSet(String name) {
		// TODO Auto-generated method stub
		if (name.equalsIgnoreCase("MultiBIDProblem")) {			
			try {
				String pfFile = "./pf/MultiBID" + MultiBIDRunner.FileName + ".pf";
				return new NondominatedPopulation(
						PopulationIO.readObjectives(new File(pfFile)));
			} catch (IOException e) {
				// TODO: handle exception
				throw new FrameworkException(e);				
			}
		}else return null;
	}

}
