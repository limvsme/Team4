package work.model.service;

import java.util.ArrayList;

import work.model.dao.MainDAO;
import work.model.dto.BudgetPaperDTO;

public class MainService {
	private MainDAO budget = MainDAO.getInstance();
	
	public ArrayList<BudgetPaperDTO> getBudget(int coupleNo){
		return budget.selectBudgetPaper(coupleNo);
	}
}
