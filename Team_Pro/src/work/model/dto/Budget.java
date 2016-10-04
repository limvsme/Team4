package work.model.dto;

import java.io.Serializable;

import work.util.Utility;

/** DTO  패턴적용해서 설계
 * 1.encapsulation
 * 2. 직렬화객체
 *
 *
 * @author 최준원
 * @version ver.1.0
 * @sinse JDK 1.4
 */
public class Budget implements Serializable{
	
	private int budgetPaperNo ;
	private int budgetNo ;
	private String budgetName;
	private int budgetAmount;
	private String budgetYn;
	private int categoryNo;
	private String cdate;
	private String id;
	
	public Budget(){}
	


	public Budget(int budgetPaperNo, int budgetNo,String budgetName,int budgetAmount,String budgetYn,int categoryNo,String cdate,String id){
		this.budgetPaperNo = budgetPaperNo;
		this.budgetNo = budgetNo;
		this.budgetName = budgetName;
		this.budgetAmount =budgetAmount;
		this.budgetYn =budgetYn;
		this.categoryNo= categoryNo;
		this.cdate =cdate;
		this.id=id;
	}
	public Budget( String id, int budgetPaperNo,String budgetName, int budgetAmount,int categoryNo ,int budgetNo){
		this.budgetPaperNo = budgetPaperNo;
		this.budgetName =budgetName;
		this.budgetAmount =budgetAmount;
		this.budgetNo = budgetNo;
		this.categoryNo= categoryNo;
		this.id=id;
	}
	
	public int getBudgetPaperNo() {
		return budgetPaperNo;
	}



	public void setBudgetPaperNo(int budgetPaperNo) {
		this.budgetPaperNo = budgetPaperNo;
	}



	public int getBudgetNo() {
		return budgetNo;
	}



	public void setBudgetNo(int budgetNo) {
		this.budgetNo = budgetNo;
	}



	public String getBudgetName() {
		return budgetName;
	}



	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}



	public int getBudgetAmount() {
		return budgetAmount;
	}



	public void setBudgetAmount(int budgetAmount) {
		this.budgetAmount = budgetAmount;
	}



	public String getBudgetYn() {
		return budgetYn;
	}



	public void setBudgetYn(String budgetYn) {
		this.budgetYn = budgetYn;
	}



	public int getCategoryNo() {
		return categoryNo;
	}



	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}



	public String getCdate() {
		return cdate;
	}



	public void setCdate(String cdate) {
		this.cdate = cdate;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	@Override
	public String toString() {
		return "Budget [budgetPaperNo=" + budgetPaperNo + ", budgetNo=" + budgetNo + ", budgetName=" + budgetName
				+ ", budgetAmount=" + budgetAmount + ", budgetYn=" + budgetYn + ", categoryNo=" + categoryNo
				+ ", cdate=" + cdate + ", id=" + id + "]";
	}



	

}
