package Runner_BID;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import org.json.JSONObject;

import OBJ.ProjectBID;

public class RunBIDProblem {

	static String[] ALGORITHMS = {"NSGAIII", "eMOEA", "GDE3", "PESA2", "IBEA", "SMPSO"};
    static String DATA_FOLDER = "DTA_BID/data";
    static String RESULT_FOLDER = "DTA_BID/result";
    static int LOOPS = 1;
	public static void main(String[] args){
		// TODO Auto-generated method stub
		File dirData = new File(DATA_FOLDER);
		File dirResult = new File (RESULT_FOLDER);
		if(!dirResult.exists()) {
			dirResult.mkdir();
		}
		for (String filename : Objects.requireNonNull(dirData.list())) {
			if (!filename.endsWith(".json")) {
                continue;
            }
			System.out.println(filename);
			String outFilename = RESULT_FOLDER + "/" + filename.replace(".json", "_result.txt");
			System.out.println(outFilename);			
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(DATA_FOLDER + "/" + filename));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Error in BufferedReader of file: " + filename);
				e.printStackTrace();
			}
	        StringBuilder sb = new StringBuilder();
	        String line="";
			try {
				line = br.readLine();
				while (line != null) {
					sb.append(line);
		            line = br.readLine();					
		         }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error in reading file: " + filename);
				e.printStackTrace();
			}
			JSONObject jObj = new JSONObject(sb.toString());
			ProjectBID projectBID = new ProjectBID(jObj);
			
		}

	}

}
