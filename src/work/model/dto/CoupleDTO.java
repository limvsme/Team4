package work.model.dto;

public class CoupleDTO {
	private int coupleNo;
	private String confirmNo;
	private String coupleName;
	
	public CoupleDTO() {}
	
	public CoupleDTO(int coupleNo, String confirmNo, String coupleName) {
		super();
		this.coupleNo = coupleNo;
		this.confirmNo = confirmNo;
		this.coupleName = coupleName;
	}

	public int getCoupleNo() {
		return coupleNo;
	}

	public void setCoupleNo(int coupleNo) {
		this.coupleNo = coupleNo;
	}

	public String getConfirmNo() {
		return confirmNo;
	}

	public void setConfirmNo(String confirmNo) {
		this.confirmNo = confirmNo;
	}

	public String getCoupleName() {
		return coupleName;
	}

	public void setCoupleName(String coupleName) {
		this.coupleName = coupleName;
	}
	
}
