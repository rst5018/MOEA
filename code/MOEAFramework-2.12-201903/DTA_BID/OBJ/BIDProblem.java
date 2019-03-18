package OBJ;

import java.util.ArrayList;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import util.DateUtil;
public class BIDProblem extends AbstractProblem {

	ProjectBID _projectBID;
	int _numberOfObj;
	
	double[] _availableObjective;
	final int AVAILABLEOBJECTIVE = 4;
	
	public BIDProblem(ProjectBID projectBID, int numObjective) {
		// TODO Auto-generated constructor stub
		super(projectBID.Packages().size() * 2, numObjective);
		_projectBID = projectBID;
		_numberOfObj = numObjective;
		_availableObjective = new double[AVAILABLEOBJECTIVE];
	}
	
	public int numberOfObjective() {
		return (_numberOfObj <= AVAILABLEOBJECTIVE)? _numberOfObj: AVAILABLEOBJECTIVE;
	}
	
	double realSellCostForContractor (Package currentPackage, Contractor contractor, String time) {
		double result = 0;
		//System.out.println(contractor.description());
		for (ProductOfPackage pOp :currentPackage.Products()) {
			result += pOp.quantity() * contractor.GetProductOfContractorByID(pOp.productID()).GetSellPrice(time);
		}
		
		return result;
	}
	double realBuyCostForContractor(Package currentPackage, Contractor contractor, String time) {
		double result = 0;
		for (ProductOfPackage pOp :currentPackage.Products()) {
			result += pOp.quantity() * contractor.GetProductOfContractorByID(pOp.productID()).buyPrice();
		}
		return result;
	}
	/**
	 * Tinh loi nhuan cua chu dau tu
	 * duoc tinh bang gia du kien tru di gia chi thuc	
	 * @param x mang chua thu tu trong joinedContractor the hien Contractor do duoc chon
	 * @param y mang chua thong tin thoi gian duoc chon la so ngay sau khi package duoc kich hoat
	 * @return Loi nhuan cua chu dau tu
	 */
	double ProjectProfit (int[] x, int[] y) {
		 double result = 0;
		 for (int i = 0; i < _projectBID.Packages().size(); i++) {
			//System.out.printf("Package thu %d \r\n", i);			
			Package currentPackage = _projectBID.Packages().get(i);
			//System.out.println(x[i]);
			Contractor currentContractor = currentPackage.getContractorByIndex(x[i]);
			String currentTime = DateUtil.add(currentPackage.from(), y[i]);			
			//System.out.println(currentPackage.ID());
			//System.out.println(currentContractor.ID());
			double inflationValue = Math.exp(_projectBID.InflationRate() * DateUtil.diff(currentTime, _projectBID.StartDate()) / 365.0);
			double currentPackageProfit = currentPackage.estimatedCost();
			currentPackageProfit = currentPackageProfit - realSellCostForContractor(currentPackage, currentContractor, currentTime);
			result += currentPackageProfit;
			result = result * inflationValue;
		}
		return result;
	}
	/**
	 * Tinh loi nhuan cua nha thau
	 * @param x mang chua thu tu trong joinedContractor the hien Contractor do duoc chon
	 * @param y mang chua thong tin thoi gian duoc chon la so ngay sau khi package duoc kich hoat
	 * @return Loi nhuan cua tat ca cac nha thau
	 */
	double ContractorProfit (int[] x, int[] y) {		
		double result = 0;
		for (int i = 0; i < _projectBID.Packages().size(); i++) {
			Package currentPackage = _projectBID.Packages().get(i);
			Contractor currentContractor = currentPackage.getContractorByIndex(x[i]);
			String currentTime = DateUtil.add(currentPackage.from(), y[i]);
			double inflationValue = Math.exp(_projectBID.InflationRate() * DateUtil.diff(currentTime, _projectBID.StartDate()) / 365.0);
			double realBuyCost = realBuyCostForContractor(currentPackage, currentContractor, currentTime);
			double realSellCost = realSellCostForContractor(currentPackage, currentContractor, currentTime);
			double realContractorProfit = (realSellCost - realBuyCost) * inflationValue;
			result += realContractorProfit;
		}		
		return result;
	}
	/**
	 * Tinh loi nhuan cua mot nha thau trong du an
	 * @param contractor Nha thau can duoc tinh loi nhuan
	 * @param x mang chua thu tu trong joinedContractor the hien Contractor do duoc chon
	 * @param y mang chua thong tin thoi gian duoc chon la so ngay sau khi package duoc kich hoat
	 * @return Loi nhuan cua nha thau can tinh
	 */
	double ContractorProfit (Contractor contractor, int[] x, int y[]) {
		double result = 0;
		for (int i = 0; i < _projectBID.Packages().size(); i++) {
			Package currentPackage = _projectBID.Packages().get(i);
			Contractor currentContractor = currentPackage.getContractorByIndex(x[i]);
			if (contractor.ID() == currentContractor.ID()) {
				String currentTime = DateUtil.add(currentPackage.from(), y[i]);
				double inflationValue = Math.exp(_projectBID.InflationRate() * DateUtil.diff(currentTime, _projectBID.StartDate()) / 365.0);
				double realBuyCost = realBuyCostForContractor(currentPackage, currentContractor, currentTime);
				double realSellCost = realSellCostForContractor(currentPackage, currentContractor, currentTime);
				double realContractorProfit = (realSellCost - realBuyCost) * inflationValue;
				result += realContractorProfit;
			}
		}
		return result;
	}
	/**
	 * Ham tra ve so chenh lech ve loi nhuan giua cac nha thau
	 * Duoc tinh bang tong chenh lech cua tung nha thau voi nhau
	 * Co tinh them he so quan he relationship
	 * @param x mang chua thong tin thoi gian duoc chon la so ngay sau khi package duoc kich hoat
	 * @param y mang chua thong tin thoi gian duoc chon la so ngay sau khi package duoc kich hoat
	 * @return Chenh lech ve loi nhuan giua cac nha thau
	 */
	double KeepContractorRelationship(int[] x, int[] y) {
		double result = 0;
		for (Contractor contractor1 : _projectBID.Contractors()) {
			for (Contractor contractor2 : _projectBID.Contractors()) {
				result += Math.abs(ContractorProfit(contractor1, x, y) *contractor1.relationship() - ContractorProfit(contractor2, x, y) * contractor2.relationship());
			}		
		}
		return result;
	}
	/**
	 * Ham tra ve chat luong cua du an
	 * La tong chat luong cua cac nha thau duoc chon
	 * @param x mang chua thong tin thoi gian duoc chon la so ngay sau khi package duoc kich hoat
	 * @param y mang chua thong tin thoi gian duoc chon la so ngay sau khi package duoc kich hoat
	 * @return
	 */
	double ProjectQuality(int[] x, int[] y) {
		double result = 0;
		for (int i = 0; i < _projectBID.Packages().size(); i++) {
			Package currentPackage = _projectBID.Packages().get(i);
			Contractor currentContractor = currentPackage.getContractorByIndex(x[i]);
			result = currentContractor.quality();
		}
		
		return result;
	}
	
	@Override
	public void evaluate(Solution solution) {
		// TODO Auto-generated method stub
		//Danh sach nha thau duoc chon
		int[] x = new int[_projectBID.Packages().size()];
		//Danh sach thoi gian duoc chon
		int[] y = new int[_projectBID.Packages().size()];
		
		
		for (int i = 0; i < _projectBID.Packages().size(); i++) {
			x[i] = EncodingUtils.getInt(solution.getVariable(i*2));
			y[i] = EncodingUtils.getInt(solution.getVariable(i*2 + 1));
		}
		// Danh sach tat cac cac objective co san
		_availableObjective[0] = -ProjectProfit(x,y);
		_availableObjective[1] = -ContractorProfit(x,y);
		_availableObjective[2] = KeepContractorRelationship(x,y);
		_availableObjective[3] = -ProjectQuality(x,y);
		//se add toan bo cac object thanh ham vao day
		for (int i = 0; i < numberOfObjective(); i++) {
			solution.setObjective(i, _availableObjective[i]);
		}
	}

	@Override
	public Solution newSolution() {
		// TODO Auto-generated method stub
		Solution solution = new Solution(_projectBID.Packages().size() * 2, _numberOfObj);
		for(int i =0; i < _projectBID.Packages().size(); i++) {
			solution.setVariable(i *2,	EncodingUtils.newInt(0, _projectBID.Packages().get(i).joinedContractors().size() -1));
			solution.setVariable(i*2+1, EncodingUtils.newInt(0, DateUtil.diff(_projectBID.Packages().get(i).to(), _projectBID.Packages().get(i).from())));
		}
		return solution;
	}

}
