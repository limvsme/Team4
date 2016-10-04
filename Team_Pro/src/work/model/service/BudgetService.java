package work.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import work.model.dao.BudgetDao;
import work.model.dto.Budget;
import work.util.Utility;

/**렌트 시스템에대한 서비스 클래스
 * --렌트 시스템의 기능들에 대한 business logic 클래스
 * 
 * */

public class BudgetService {
	/**렌트DAO 클래스*/
	private BudgetDao dao = BudgetDao.getInstance();
	
	
	public ArrayList<Budget> selectBudget(String id){
		return dao.selectBudget(id);
		
	}
	
//	public ArrayList<Rent> selectAllRent(){
//		return dao.selectAllRent();
//	}
	
//	public ArrayList<Rent> selectOneRent(String userId){
//		return dao.selectOneRent(userId);
//	}
	

}
