package OBJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

public class test {
	static String DATA_FOLDER = "DTA_BID/data";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		 File dir = new File(DATA_FOLDER);
		 System.out.println(dir.getAbsolutePath());
		 for (String filename : Objects.requireNonNull(dir.list())) {
			 System.out.println(filename);
			 BufferedReader br = new BufferedReader(new FileReader(DATA_FOLDER + "/" + filename));
	         StringBuilder sb = new StringBuilder();
	         String line = br.readLine();
	         while (line != null) {
	        	 //System.out.println(line);
	             sb.append(line);
	             line = br.readLine();
	         }
	         JSONObject jObj = new JSONObject(sb.toString());
	         ProjectBID myProject = new ProjectBID(jObj);
	         ArrayList<Product> myProduct = myProject.getProducts();
	         for (Product product : myProduct) {
				System.out.println(product.Description());
			}
		 }
		 
		 
	}

}
