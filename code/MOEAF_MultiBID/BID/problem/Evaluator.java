package problem;

import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

import java.util.HashMap;

public class Evaluator {
    public static HashMap<String, Double> PROPERTIES = new HashMap<>();

    public static double payOff(Solution solution) {
        double[] objectives = solution.getObjectives();
        double payOff = 0;
        payOff += (PROPERTIES.get("MAX_OWNER_PROFIT") - Math.abs(objectives[0])) /
                (PROPERTIES.get("MAX_OWNER_PROFIT") - PROPERTIES.get("MIN_OWNER_PROFIT"));
        payOff += (PROPERTIES.get("MAX_SUMCONTRACTORS_PROFIT") - Math.abs(objectives[1])) /
                (PROPERTIES.get("MAX_SUMCONTRACTORS_PROFIT") - PROPERTIES.get("MIN_SUMCONTRACTORS_PROFIT"));
        payOff += (Math.abs(objectives[2]) - PROPERTIES.get("MIN_BALANCE_CONTRACTORS_PROFIT")) /
                (PROPERTIES.get("MAX_BALANCE_CONTRACTORS_PROFIT") - PROPERTIES.get("MIN_BALANCE_CONTRACTORS_PROFIT"));
        payOff += (PROPERTIES.get("MAX_PROJECT_QUALITY") - Math.abs(objectives[3])) /
                (PROPERTIES.get("MAX_PROJECT_QUALITY") - PROPERTIES.get("MIN_PROJECT_QUALITY"));
        payOff /= 4.0;
        return payOff;
    }

    public static double payOff(Solution solution, NondominatedPopulation population) {
    	double result = 0;
    	
    	return result;
    }
    public static boolean isBetter(Solution solution1, Solution solution2) {
        return payOff(solution1) < payOff(solution2);
    }
}
