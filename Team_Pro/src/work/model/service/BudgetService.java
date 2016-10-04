package work.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import work.model.dao.BudgetDao;
import work.model.dto.Budget;
import work.util.Utility;

/**��Ʈ �ý��ۿ����� ���� Ŭ����
 * --��Ʈ �ý����� ��ɵ鿡 ���� business logic Ŭ����
 * 
 * */

public class BudgetService {
	/**��ƮDAO Ŭ����*/
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
