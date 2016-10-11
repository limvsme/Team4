package work.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import work.model.dao.MainDAO;
import work.model.dto.BudgetPaperDTO;

public class MainService {
	private MainDAO budget = MainDAO.getInstance();
	
	public ArrayList<BudgetPaperDTO> getBudget(int coupleNo){
		ArrayList<BudgetPaperDTO> before = budget.selectBudgetPaper(coupleNo);
		ArrayList<BudgetPaperDTO> after = new ArrayList<BudgetPaperDTO>();
		int var=-1;
		for (BudgetPaperDTO temp : before) {
			var = temp.getBudgetPaperNo();
			temp.setAmountSum(budget.selectAllSum(var));
			temp.setAmountYSum(budget.selectYSum(var));
			after.add(temp);
		}
		return after;
	}
	
	public HashMap<String,String> getProfile(int coupleNo){
		HashMap<String,String> result = new HashMap<String,String>();
		ArrayList<String> temp = budget.selectNameNname(coupleNo);
		String twoName = temp.get(0) +" ¢¾ "+temp.get(1);
		result.put("coupleName", budget.selectCoupleName(coupleNo));
		result.put("twoName", twoName);
		
		return result;
		
	}
}
