package Runner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
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
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Settings;
import org.moeaframework.core.Solution;
import org.moeaframework.core.spi.ProblemFactory;

import BID.BIDProblem;
import BID.BIDProblemProvider;
import ObjEntity.Project;

public class BIDRunner {

	private static String pathDataFile = "";
	public static String FileName = "";
	public static BIDProblem pubBIDProblem = null;
	@SuppressWarnings("resource")
	public static void WriteSolution(Solution solution, int idSolution, String AlgorithmName, int numberOfseeds, long runtime, int idOfNDP) throws IOException {	
		File outFile = new File("Result/" + FileName.replace("_json", "_result.txt"));
		if(!outFile.exists()) outFile.createNewFile();
		FileWriter fr = new FileWriter(outFile, true);
        BufferedWriter bw = new BufferedWriter(fr);      
        if(idOfNDP == 0 && idSolution == 0) {
        	bw.write("=====   " + AlgorithmName + "   ===== \r\n");        	
        	bw.write("Number of seeds: " + idOfNDP  + ", run time: " + runtime);
        }
        if(idSolution == 0) {
        	bw.write("\r\n ***** Seed: " + idOfNDP + " *****\r\n");
        }
        bw.write("Solution: " + idSolution + "\r\n");
        bw.write(pubBIDProblem.toString(solution));
        bw.flush();
        bw.close();      
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
			//Xoa file ket qua truoc khi chay
			File outFile = new File("Result/" + FileName.replace("_json", "_result.txt"));
			if(outFile.exists())
				outFile.delete();	       
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
	        pubBIDProblem = myProblem;       
	        	        
	        ProblemFactory.getInstance().addProvider(new BIDProblemProvider(myProblem));
	        Settings.PROPERTIES.setString(Settings.KEY_DIAGNOSTIC_TOOL_ALGORITHMS, 
	        		"NSGAIII, GDE3, eMOEA");
	        Settings.PROPERTIES.setString(Settings.KEY_DIAGNOSTIC_TOOL_PROBLEMS, "BIDProblem");
	        try {
				LaunchDiagnosticTool.main(args);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	        

		}
	}

}
//Doan code in ket qua theo tung analyzer
//String[] algorithms = new String[] {"GDE3", "NSGAIII", "eMOEA"};
//Executor executor = new Executor()
//		.withProblemClass(BIDProblem.class, projectBID, 4)
//		.withMaxEvaluations(10000);
//
//for (String algorithm : algorithms) {
//	Analyzer analyzer = new Analyzer()
//    		.withSameProblemAs(executor)
//    		.includeAllMetrics()
//    		.showStatisticalSignificance();
//	long startTime = System.currentTimeMillis();
//	analyzer.addAll(algorithm, executor.withAlgorithm(algorithm).runSeeds(1));
//	long endTime = System.currentTimeMillis();
//	System.out.printf("========= %s : runtime = %d\r\n", algorithm, (endTime - startTime));
//	analyzer.printAnalysis();
//	long endTime2 = System.currentTimeMillis();
//	System.out.println("Thoi gian in ket qua: " + (endTime2 - endTime) );
//}
