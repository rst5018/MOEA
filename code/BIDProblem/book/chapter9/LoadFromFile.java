package chapter9;

import java.io.File;
import java.io.IOException;

import org.moeaframework.core.Population;
import org.moeaframework.core.PopulationIO;

public class LoadFromFile {
	
	public static void main(String[] args) {
		try {
			Population result = PopulationIO.read(new File("solutions.dat"));
			
			System.out.println("Read " + result.size() + " solutions!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
