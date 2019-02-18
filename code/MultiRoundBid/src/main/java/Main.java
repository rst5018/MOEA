import org.json.JSONObject;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import problem.Evaluator;
import problem.MultiRoundBidProblem;
import problem.MultiRoundBidObjectives;

import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Objects;

public class Main {
    static String[] ALGORITHMS = {"NSGAIII", "eMOEA", "GDE3", "PESA2", "IBEA", "SMPSO"};
    static String DATA_FOLDER = "data";
    static int LOOPS = 1;

    public static void main(String[] args) throws IOException {
        File dir = new File(DATA_FOLDER);
        for (String filename : Objects.requireNonNull(dir.list())) {
            StringBuilder resultTable = new StringBuilder("Comparison\n");
            Formatter formatter = new Formatter(resultTable);
            if (!filename.endsWith(".json")) {
                continue;
            }
            System.out.printf("Reading %s...\n", filename);
            BufferedReader br = new BufferedReader(new FileReader(DATA_FOLDER + "/" + filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            JSONObject obj = new JSONObject(sb.toString());

            System.out.print("Initializing...");
            File outFile = new File(DATA_FOLDER + "/" + filename.replace(".json", "_result.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
            MultiRoundBidProblem.init(obj);
            System.out.println("OK");

            for (String algorithm : ALGORITHMS) {
                System.out.printf("Running %s...", algorithm);
                ArrayList<Solution> solutions = new ArrayList<>();

                MultiRoundBidProblem.IGNORE_CONSTRAINTS = algorithm.equals("IBEA");
                long start_time = System.currentTimeMillis();
                for (int i = 0; i < LOOPS; ++i) {
                    NondominatedPopulation population = new Executor()
                            .withAlgorithm(algorithm)
                            .withProblemClass(MultiRoundBidProblem.class)
                            .withMaxEvaluations(10000)
                            .withProperty("bisections", 4.0D)
                            .run();
                    for (Solution solution : population) {
                        solutions.add(solution);
                    }
                }
                long time = (System.currentTimeMillis() - start_time) / LOOPS;

                Solution bestSolution = solutions.get(0);
                for (Solution solution : solutions) {
                    if (Evaluator.isBetter(solution, bestSolution)) {
                        bestSolution = solution;
                    }
                }

                formatter.format("%7s | %4f | %d\n", algorithm, Evaluator.payOff(bestSolution), time);

                bw.write("=====" + algorithm + "=====\n");
                bw.write("Time: " + time + "\n");
                bw.write(new MultiRoundBidObjectives(bestSolution).toString());
                bw.write("\n");
                bw.flush();
                System.out.println("OK");
            }
            bw.write(resultTable.toString());
            bw.close();
        }
        System.out.println("Done");
    }
}
