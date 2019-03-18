package BID;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.json.JSONObject;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.PopulationIO;

import ObjEntity.Project;
import Runner.BIDRunner;

public class BIDReferenceSet {

	public static void main (String[] args) throws IOException{
		int nseeds = 25;
		String pathDataFile = "";
		String saveFileName = "";
		String dataFolder = System.getProperty("user.dir") + "/data";
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setCurrentDirectory(new File (dataFolder));
		int returnValue = jfc.showOpenDialog(null);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			pathDataFile = selectedFile.getAbsolutePath();		
			saveFileName = selectedFile.getName();
			saveFileName = saveFileName.replace(".", "_");
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
	        long startTime = System.currentTimeMillis();
	        JSONObject jObj = new JSONObject(sb.toString());
	        Project projectBID = new Project(jObj);
	        String[] algorithms = new String[] {"GDE3", "NSGAIII"};
			NondominatedPopulation referenceSet = new NondominatedPopulation();
			Executor executor = new Executor()
					.withProblemClass(BIDProblem.class, projectBID, 4)
					.withMaxEvaluations(10000);
			for(String algorithm : algorithms) {
				executor.withAlgorithm(algorithm);
				for (int i = 0; i < nseeds; i++) {
					referenceSet.addAll(executor.run());
				}
			}
			String pfFile = "./pf/BID" + saveFileName + ".pf";
			PopulationIO.writeObjectives(new File(pfFile), referenceSet);
			long endTime = System.currentTimeMillis();
			System.out.println("Done");
			System.out.println("Thoi gian thuc thi = " + (endTime - startTime));
		
	}
}
}
