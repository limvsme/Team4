package work.model.service;


import work.model.dao.BudgetDAO;

public class BudgetService {
	private BudgetDAO budget = BudgetDAO.getInstance();
	
	public boolean addBudget(int coupleNo, String budgetPaperName) {
		if(budget.insertBudget(coupleNo, budgetPaperName) == 1) {
			return true;
		} else {
			return false;
		}
	}

}
