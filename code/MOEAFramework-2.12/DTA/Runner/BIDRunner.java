package Runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import org.json.JSONObject;
import org.moeaframework.Analyzer;
import org.moeaframework.Executor;
import org.moeaframework.analysis.diagnostics.LaunchDiagnosticTool;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Settings;
import org.moeaframework.core.spi.ProblemFactory;

import BID.BIDProblem;
import BID.BIDProblemProvider;
import ObjEntity.Project;


public class BIDRunner {

	private static String pathDataFile = "";
	public static String FileName = "";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String dataFolder = System.getProperty("user.dir") + "/data";
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setCurrentDirectory(new File (dataFolder));
		int returnValue = jfc.showOpenDialog(null);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			pathDataFile = selectedFile.getAbsolutePath();		
			FileName = selectedFile.getName();
			FileName = FileName.replace(".", "_");
			System.out.println(FileName);
		}
		if(pathDataFile != "") {
			System.out.println(pathDataFile);	
		    @SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(pathDataFile));
		    StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        JSONObject jObj = new JSONObject(sb.toString());
	        Project projectBID = new Project(jObj);
	        BIDProblem myProblem = new BIDProblem(projectBID, 4);
	        ProblemFactory.getInstance().addProvider(new BIDProblemProvider(myProblem));
	        Settings.PROPERTIES.setString(Settings.KEY_DIAGNOSTIC_TOOL_ALGORITHMS, 
	        		"NSGAIII, GDE3");
	        Settings.PROPERTIES.setString(Settings.KEY_DIAGNOSTIC_TOOL_PROBLEMS, "BIDProblem");
	        try {
				LaunchDiagnosticTool.main(args);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
//	        Executor executor = new Executor()
//	        		.withProblemClass(BIDProblem.class, projectBID, 4)
//	        		.withMaxEvaluations(10000);
//	       
//	        Analyzer analyzer = new Analyzer()
//	        		.withSameProblemAs(executor)
//	        		.includeAllMetrics()
//	        		.showStatisticalSignificance();
//	        List<NondominatedPopulation> listPopulation = executor.withAlgorithm("NSGAIII").runSeeds(5);
//	        analyzer.addAll("NSGAIII", listPopulation);       
//	        analyzer.printAnalysis();
		}
	}

}
