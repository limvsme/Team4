package work.model.service;


import java.util.ArrayList;

import work.model.dao.BudgetDAO;
import work.model.dao.BudgetDao2;
import work.model.dto.Budget;

public class BudgetService {
	private BudgetDAO budget = BudgetDAO.getInstance();
	private BudgetDao2 dao = BudgetDao2.getInstance();
	
	public boolean addBudget(int coupleNo, String budgetPaperName) {
		if(budget.insertBudget(coupleNo, budgetPaperName) == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Budget> selectBudget(int budgetPaperNo){
		return dao.selectBudget(budgetPaperNo);
		
	}

}
