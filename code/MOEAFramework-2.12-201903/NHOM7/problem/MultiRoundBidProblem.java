package problem;

import models.Package;
import models.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.moeaframework.Executor;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;
import util.DateUtil;

import java.util.ArrayList;

public class MultiRoundBidProblem extends AbstractProblem {

    private static final int NUMBER_OF_OBJECTIVES = 4;
    public static boolean IGNORE_CONSTRAINTS = true;

    public static int projectId;
    public static double inflationRate;
    public static String startDate;
    public static ArrayList<Package> packages = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Contractor> contractors = new ArrayList<>();

    static final String[] OBJECTIVES = {"OWNER_PROFIT", "SUMCONTRACTORS_PROFIT", "BALANCE_CONTRACTORS_PROFIT", "PROJECT_QUALITY"};
    static String objective;
    static String optimize;

    public MultiRoundBidProblem() {
        super(packages.size() * 2, NUMBER_OF_OBJECTIVES);
    }

    public static void init(JSONObject obj) {
        projectId = obj.getInt("project_id");
        inflationRate = obj.getDouble("inflation_rate");
        startDate = obj.getString("start_date");

        JSONArray productsArray = obj.getJSONArray("product");
        for (int i = 0; i < productsArray.length(); ++i) {
            products.add(new Product(productsArray.getJSONObject(i)));
        }

        JSONArray contractorsArray = obj.getJSONArray("contractors");
        for (int i = 0; i < contractorsArray.length(); ++i) {
            JSONObject contractorObject = contractorsArray.getJSONObject(i);
            ArrayList<ConstractorProduct> constractorProducts = new ArrayList<>();
            JSONArray constractorProductsArray = contractorObject.getJSONArray("products");
            for (int j = 0; j < constractorProductsArray.length(); ++j) {
                JSONObject constractorProductObject = constractorProductsArray.getJSONObject(j);
                for (Product product : products) {
                    if (product.getProductId() == constractorProductObject.getInt("product_id")) {
                        constractorProducts.add(new ConstractorProduct(product, constractorProductObject));
                        break;
                    }
                }
            }
            contractors.add(new Contractor(contractorObject, constractorProducts));
        }

        JSONArray packagesArray = obj.getJSONArray("packages");
        for (int i = 0; i < packagesArray.length(); ++i) {
            JSONObject packageObject = packagesArray.getJSONObject(i);
            ArrayList<Contractor> joinedContractors = new ArrayList<>();
            JSONArray joinedContractorsArray = packageObject.getJSONArray("joined_contractors");
            for (int j = 0; j < joinedContractorsArray.length(); ++j) {
                for (Contractor contractor : contractors) {
                    if (contractor.getContractorId() == joinedContractorsArray.getInt(j)) {
                        joinedContractors.add(contractor);
                        break;
                    }
                }
            }
            ArrayList<PackageProduct> packageProducts = new ArrayList<>();
            JSONArray packageProductsArray = packageObject.getJSONArray("products");
            for (int j = 0; j < packageProductsArray.length(); ++j) {
                JSONObject packageProductObject = packageProductsArray.getJSONObject(j);
                for (Product product : products) {
                    if (product.getProductId() == packageProductObject.getInt("product_id")) {
                        packageProducts.add(new PackageProduct(product, packageProductObject.getInt("quantity")));
                        break;
                    }
                }
            }

            packages.add(new Package(packageObject, joinedContractors, packageProducts));
        }

        for (String item : OBJECTIVES) {
            for (String type : new String[] {"MIN", "MAX"}) {
                objective = item;
                optimize = type;
                Solution solution = new Executor()
                        .withAlgorithm("eMOEA")
                        .withProblemClass(MultiRoundBidProblem.class)
                        .withMaxEvaluations(10000)
                        .run()
                        .get(0);
                for (int i = 0; i < NUMBER_OF_OBJECTIVES; ++i) {
                    Evaluator.PROPERTIES.put(optimize + "_" + objective, Math.abs(solution.getObjective(i)));
                    if (solution.getObjective(i) != 0) {
                        break;
                    }
                }
            }
        }

        objective = null;
    }

    @Override
    public Solution newSolution() {
        Solution solution;
        if (IGNORE_CONSTRAINTS) {
            solution = new Solution(packages.size() * 2, NUMBER_OF_OBJECTIVES);
        } else {
            solution = new Solution(packages.size() * 2, NUMBER_OF_OBJECTIVES, 2);
        }
        for (int i = 0; i < packages.size(); ++i) {
            Timeline t = packages.get(i).getTimeline();
            solution.setVariable(2 * i, EncodingUtils.newInt(0, contractors.size() - 1));
            solution.setVariable(2 * i + 1, EncodingUtils.newInt(0, DateUtil.diff(t.to(), t.from())));
        }
        return solution;
    }

    @Override
    public void evaluate(Solution solution) {
        MultiRoundBidObjectives multiRoundBidObjectives = new MultiRoundBidObjectives(solution);
        double[] objectives = {0, 0, 0, 0};
        objectives[0] = (objective == null) ? - multiRoundBidObjectives.getOwnerProfit() :
                        !objective.equals(OBJECTIVES[0]) ? 0 :
                        optimize.equals("MIN") ? multiRoundBidObjectives.getOwnerProfit() :
                                - multiRoundBidObjectives.getOwnerProfit();
        objectives[1] = (objective == null) ? - multiRoundBidObjectives.getSumContractorsProfit() :
                !objective.equals(OBJECTIVES[1]) ? 0 :
                        optimize.equals("MIN") ? multiRoundBidObjectives.getSumContractorsProfit() :
                                - multiRoundBidObjectives.getSumContractorsProfit();
        objectives[2] = (objective == null) ? multiRoundBidObjectives.getBalanceContractorsProfit() :
                !objective.equals(OBJECTIVES[2]) ? 0 :
                        optimize.equals("MIN") ? multiRoundBidObjectives.getBalanceContractorsProfit() :
                                - multiRoundBidObjectives.getBalanceContractorsProfit();
        objectives[3] = (objective == null) ? - multiRoundBidObjectives.getProjectQuality() :
                !objective.equals(OBJECTIVES[3]) ? 0 :
                        optimize.equals("MIN") ? multiRoundBidObjectives.getProjectQuality() :
                                - multiRoundBidObjectives.getProjectQuality();

        solution.setObjectives(objectives);
        if (objective == null && solution.getNumberOfConstraints() > 0) {
            solution.setConstraint(0, multiRoundBidObjectives.getOwnerProfit() >= 0 ? 0 : 1);
            solution.setConstraint(1, 0);
            for (Contractor contractor : contractors) {
                if (multiRoundBidObjectives.getContractorsProfit().get(contractor) < 0) {
                    solution.setConstraint(1, 1);
                    break;
                }
            }
        }
    }

}
