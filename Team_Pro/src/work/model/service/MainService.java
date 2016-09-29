package work.model.service;

import java.util.ArrayList;

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
}
