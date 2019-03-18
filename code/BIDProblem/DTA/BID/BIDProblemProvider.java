package BID;

import java.io.File;
import java.io.IOException;

import org.moeaframework.core.FrameworkException;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.PopulationIO;
import org.moeaframework.core.Problem;
import org.moeaframework.core.spi.ProblemProvider;

import ObjEntity.Project;
import Runner.BIDRunner;

public class BIDProblemProvider extends ProblemProvider{

	private Problem _problem;
	private Project _projectBID;
	int _numberOfObj;
	public BIDProblemProvider(Project projectBID, int numberOfObjectives ) {
		// TODO Auto-generated constructor stub
		super();
		_projectBID = projectBID;
		_numberOfObj = numberOfObjectives;
		
	}
	public BIDProblemProvider(BIDProblem var) {
		// TODO Auto-generated constructor stub
		super();
		_problem = var;
		
	}
	
	@Override
	public Problem getProblem(String name) {
		// TODO Auto-generated method stub
		if (name.equalsIgnoreCase("BIDProblem")) {
			//_problem =  new BIDProblem(_projectBID, _numberOfObj);
			return _problem;
		} else
		return null;
	}

	@Override
	public NondominatedPopulation getReferenceSet(String name) {
		// TODO Auto-generated method stub
		if (name.equalsIgnoreCase("BIDProblem")) {			
			try {
				String pfFile = "./pf/BID" + BIDRunner.FileName + ".pf";
				return new NondominatedPopulation(
						PopulationIO.readObjectives(new File(pfFile)));
			} catch (IOException e) {
				// TODO: handle exception
				throw new FrameworkException(e);				
			}
		}else return null;
	}

}