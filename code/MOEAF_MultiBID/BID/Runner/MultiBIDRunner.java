package Runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.json.JSONObject;
import org.moeaframework.analysis.diagnostics.LaunchDiagnosticTool;
import org.moeaframework.core.Settings;
import org.moeaframework.core.Solution;
import org.moeaframework.core.spi.ProblemFactory;

import problem.MultiRoundBidObjectives;
import problem.MultiRoundBidProblem;
import problem.MultiRoundBidProblemProvider;

public class MultiBIDRunner {

	private static String pathDataFile = "";
	public static String FileName = "";
	
	public static void WriteResultToFile(Solution solution) {
		
	}
	
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
			BufferedReader br = new BufferedReader(new FileReader(pathDataFile));
		    StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        JSONObject jObj = new JSONObject(sb.toString());
	        System.out.print("Initializing... \r\n");
	        MultiRoundBidProblem.init(jObj);
	        //MultiRoundBidProblem myProblem = new MultiRoundBidProblem();	        
	        System.out.println("Complete the initialization");
	        
	        ProblemFactory.getInstance().addProvider(new MultiRoundBidProblemProvider());
	        Settings.PROPERTIES.setString(Settings.KEY_DIAGNOSTIC_TOOL_ALGORITHMS, 
	        		"NSGAIII, GDE3, eMOEA");
	        Settings.PROPERTIES.setString(Settings.KEY_DIAGNOSTIC_TOOL_PROBLEMS, "MultiBIDProblem");
	        try {
				LaunchDiagnosticTool.main(args);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
