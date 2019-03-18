package problem;

import ObjEntity.ConstractorProduct;
import ObjEntity.Contractor;
import ObjEntity.Package;
import ObjEntity.PackageProduct;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import util.DateUtil;

import java.util.Formatter;
import java.util.HashMap;

public class MultiRoundBidObjectives {
    private Solution solution;

    private HashMap<Package, String> packageTime = new HashMap<>();
    private HashMap<Package, Contractor> packageContractor = new HashMap<>();
    private HashMap<Contractor, Double> contractorsProfit = new HashMap<>();

    private double ownerProfit = 0;
    private double projectQuality = 0;
    private double sumContractorsProfit = 0;
    private double balanceContractorsProfit = 0;


    public MultiRoundBidObjectives(Solution solution) {
        this.solution = solution;
        evaluate();
    }

    private void evaluate() {
        for (int i = 0; i < MultiRoundBidProblem.packages.size(); ++i) {
            Package p = MultiRoundBidProblem.packages.get(i);
            packageContractor.put(p, MultiRoundBidProblem.contractors.get(EncodingUtils.getInt(solution.getVariable(2 * i))));
            packageTime.put(p,
                    DateUtil.add(p.getTimeline().from(), EncodingUtils.getInt(solution.getVariable(2 * i + 1))));
        }

        for (Contractor contractor : MultiRoundBidProblem.contractors) {
            contractorsProfit.put(contractor, 0.0);
        }

        for (Package p : MultiRoundBidProblem.packages) {
            Contractor contractor = packageContractor.get(p);
            projectQuality += contractor.getQuality();
            String time = packageTime.get(p);
            double inflationValue = Math.exp(MultiRoundBidProblem.inflationRate * DateUtil.diff(time, MultiRoundBidProblem.startDate) / 365.0);
            for (PackageProduct packageProduct : p.getPackageProducts()) {
                ConstractorProduct constractorProduct =
                        contractor.getConstractorProduct(packageProduct.getProduct().getProductId());
                double realPrice = constractorProduct.getSellPriceAt(time);
                ownerProfit += (p.getEstimated_cost() - packageProduct.getQuantity() * realPrice) * MultiRoundBidProblem.inflationRate;
                double newContractorProfit = packageProduct.getQuantity() *
                        (realPrice - constractorProduct.getBuyPrice()) * inflationValue;
                contractorsProfit.put(contractor,
                        contractorsProfit.get(contractor) + newContractorProfit);
            }
        }

        sumContractorsProfit = MultiRoundBidProblem.contractors.parallelStream()
                .mapToDouble(contractor -> contractorsProfit.get(contractor)).sum();

        for (Contractor c1 : MultiRoundBidProblem.contractors) {
            for (Contractor c2 : MultiRoundBidProblem.contractors) {
                balanceContractorsProfit += Math.abs(c1.getRelationship() * contractorsProfit.get(c1) -
                        c2.getRelationship() * contractorsProfit.get(c2));
            }
        }
        balanceContractorsProfit /= 2.0;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        formatter.format("Pay-off: %.4f\n", Evaluator.payOff(getSolution()));
        formatter.format("Owner Profit: %.0f\n", getOwnerProfit());
        formatter.format("Sum Contractors Profit: %.0f\n", getSumContractorsProfit());
        formatter.format("Balance Contractors Profit: %.0f\n", getBalanceContractorsProfit());
        formatter.format("Project Quality: %.2f\n", getProjectQuality());
        for (int i = 0; i < MultiRoundBidProblem.packages.size(); ++i) {
            formatter.format("Package %d: %s\n" +
                            "           %s\n", i,
                    getPackageContractor().get(MultiRoundBidProblem.packages.get(i)).getDescription(),
                    getPackageTime().get(MultiRoundBidProblem.packages.get(i)));
        }
        return builder.toString();
    }

    public Solution getSolution() {
        return solution;
    }

    public HashMap<Package, String> getPackageTime() {
        return packageTime;
    }

    public HashMap<Package, Contractor> getPackageContractor() {
        return packageContractor;
    }

    public HashMap<Contractor, Double> getContractorsProfit() {
        return contractorsProfit;
    }

    public double getOwnerProfit() {
        return ownerProfit;
    }

    public double getProjectQuality() {
        return projectQuality;
    }

    public double getSumContractorsProfit() {
        return sumContractorsProfit;
    }

    public double getBalanceContractorsProfit() {
        return balanceContractorsProfit;
    }
}
