package OBJ;

import java.util.ArrayList;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import util.DateUtil;
public class BIDProblems extends AbstractProblem {

	public BIDProblems() {
		// TODO Auto-generated constructor stub
		super(General.pubProjectBID.Packages().size() * 2, General.NUMBER_OF_OBJECTIVES);
	}
	public double realSellCostForContractor (Package currentPackage, Contractor contractor, String time) {
		double result = 0;
		for (ProductOfPackage pOp :currentPackage.Products()) {
			result += pOp.quantity() * contractor.GetProductOfContractorByID(pOp.productID()).GetSellPrice(time);
		}
		
		return result;
	}
	public double realBuyCostForContractor(Package currentPackage, Contractor contractor, String time) {
		double result = 0;
		for (ProductOfPackage pOp :currentPackage.Products()) {
			result += pOp.quantity() * contractor.GetProductOfContractorByID(pOp.productID()).buyPrice();
		}
		return result;
	}
	
	public double ProjectProfit (ArrayList<Integer> x, ArrayList<Integer> y) {
		double result = 0;
		for (int i = 0; i < General.pubProjectBID.Packages().size(); i++) {
			
			Package currentPackage = General.pubProjectBID.Packages().get(i);
			Contractor currentContractor = currentPackage.getContractorByID(x.get(i));
			String currentTime = DateUtil.add(currentPackage.from(), y.get(i));
			double inflationValue = Math.exp(General.pubProjectBID.InflationRate() * DateUtil.diff(currentTime, General.pubProjectBID.StartDate()) / 365.0);
			double currentPackageProfit = currentPackage.estimatedCost();
			currentPackageProfit = currentPackageProfit - realSellCostForContractor(currentPackage, currentContractor, currentTime);
			result += currentPackageProfit;
			result = result * inflationValue;
		}
		return result;
	}
	
	public double ProjectProfit (Solution solution) {
		double result = 0;
		for (int i = 0; i < General.pubProjectBID.Packages().size(); i++) {
			
			Package currentPackage = General.pubProjectBID.Packages().get(i);
			Contractor currentContractor = currentPackage.getContractorByID(EncodingUtils.getInt(solution.getVariable(i*2)));
			String currentTime = DateUtil.add(currentPackage.from(), EncodingUtils.getInt(solution.getVariable(i*2 + 1)));
			double inflationValue = Math.exp(General.pubProjectBID.InflationRate() * DateUtil.diff(currentTime, General.pubProjectBID.StartDate()) / 365.0);
			double currentPackageProfit = currentPackage.estimatedCost();
			currentPackageProfit = currentPackageProfit - realSellCostForContractor(currentPackage, currentContractor, currentTime);
			result += currentPackageProfit;
			result = result * inflationValue;
		}
		return result;
	}
	
	public double ContractorProfit (ArrayList<Integer> x, ArrayList<Integer> y) {		
		double result = 0;
		for (int i = 0; i < General.pubProjectBID.Packages().size(); i++) {
			Package currentPackage = General.pubProjectBID.Packages().get(i);
			Contractor currentContractor = currentPackage.getContractorByID(x.get(i));
			String currentTime = DateUtil.add(currentPackage.from(), y.get(i));
			double inflationValue = Math.exp(General.pubProjectBID.InflationRate() * DateUtil.diff(currentTime, General.pubProjectBID.StartDate()) / 365.0);
			double realBuyCost = realBuyCostForContractor(currentPackage, currentContractor, currentTime);
			double realSellCost = realBuyCostForContractor(currentPackage, currentContractor, currentTime);
			double realContractorProfit = (realSellCost - realBuyCost) * inflationValue;
			result += realContractorProfit;
		}		
		return result;
	}
	
	@Override
	public void evaluate(Solution solution) {
		// TODO Auto-generated method stub
		//Danh sach contractor duoc chon
//		ArrayList<Integer> x = new ArrayList<Integer>();
//		//Danh sach thoi gian duoc chon
//		ArrayList<Integer> y = new ArrayList<Integer>();
//		for (int i = 0; i < General.pubProjectBID.Packages().size(); i++) {
//			x.add(EncodingUtils.getInt(solution.getVariable(i*2)))	;
//			y.add(EncodingUtils.getInt(solution.getVariable(i*2 + 1)));
//		}
		//se add toan bo cac object thanh ham vao day
		solution.setObjective(0, -ProjectProfit(solution));
		//solution.setObjective(1, -ContractorProfit(x, y));
		
	}

	@Override
	public Solution newSolution() {
		// TODO Auto-generated method stub
		Solution solution = new Solution(General.pubProjectBID.Packages().size() * 2, General.NUMBER_OF_OBJECTIVES);
		for(int i =0; i < General.pubProjectBID.Packages().size(); i++) {
			solution.setVariable(i *2,	EncodingUtils.newInt(0, General.pubProjectBID.Contractors().size()));
			solution.setVariable(i*2+1, EncodingUtils.newInt(0, DateUtil.diff(General.pubProjectBID.Packages().get(i).to(), General.pubProjectBID.Packages().get(i).from())));
		}
		return solution;
	}

}
