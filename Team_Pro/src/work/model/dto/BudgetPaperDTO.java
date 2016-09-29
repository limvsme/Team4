package work.model.dto;

public class BudgetPaperDTO {
	private int budgetPaperNo;
	private String budgetPaperName;
	private int amountSum;
	private int amountYSum;
	
	public BudgetPaperDTO() {}
	
	public BudgetPaperDTO(int budgetPaperNo, String budgetPaperName) {
		super();
		this.budgetPaperNo = budgetPaperNo;
		this.budgetPaperName = budgetPaperName;
	}

	public BudgetPaperDTO(int budgetPaperNo, String budgetPaperName, int amountSum, int amountYSum) {
		super();
		this.budgetPaperNo = budgetPaperNo;
		this.budgetPaperName = budgetPaperName;
		this.amountSum = amountSum;
		this.amountYSum = amountYSum;
	}

	public int getBudgetPaperNo() {
		return budgetPaperNo;
	}

	public void setBudgetPaperNo(int budgetPaperNo) {
		this.budgetPaperNo = budgetPaperNo;
	}

	public String getBudgetPaperName() {
		return budgetPaperName;
	}

	public void setBudgetPaperName(String budgetPaperName) {
		this.budgetPaperName = budgetPaperName;
	}

	public int getAmountSum() {
		return amountSum;
	}

	public void setAmountSum(int amountSum) {
		this.amountSum = amountSum;
	}

	public int getAmountYSum() {
		return amountYSum;
	}

	public void setAmountYSum(int amountYSum) {
		this.amountYSum = amountYSum;
	}
	
}
