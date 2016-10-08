package work.model.service;

import java.util.HashMap;
import work.model.dao.CoupleDAO;
import work.model.dto.CoupleDTO;
import work.util.Utility;

public class MatchingService {
	private CoupleDAO couple = CoupleDAO.getInstance();


	/*
	 * ��Ȳ1. (��)Ŀ�ù�ȣ�� ������ �ְ� (1) (��)Ŀ����.
	 *  - ������ �㰡�ϰ� Ŀ�ø��� ���ǿ� ��Ƽ� �ʿ��Ҷ����� ����Ѵ�.
	 *  
	 * ��Ȳ2. (��)Ŀ�ù�ȣ�� ������ �ְ� (2) (��)Ŀ���� �ƴ�.
	 *  -> ������ȣ�� �����Ϸ� �Ѵ�. => (��)������ ������ �ִ� ������ȣ ǥ��
	 *  -> ������ȣ�� �Է��Ϸ� �Ѵ�. 
	 *  	=> (��)�Է��� ������ȣ�� �� ������ȣ�� �ƴ��� Ȯ���Ѵ�.
	 *  	=> (��)�Է��� ������ȣ�� �̹� Ŀ������ �ƴ��� Ȯ���Ѵ�.
	 *  
	 *  ��Ȳ3. Ŀ�ù�ȣ�� ����.
	 *   -> ������ȣ�� �����Ϸ� �Ѵ�. => (��)������ȣ ����
	 *   -> ������ȣ�� �Է��Ϸ� �Ѵ�.
	 *   	=> (��)�Է��� ������ȣ�� �� ������ȣ�� �ƴ��� Ȯ���Ѵ�.
	 *  	=> (��)�Է��� ������ȣ�� �̹� Ŀ������ �ƴ��� Ȯ���Ѵ�.
	 *  
	 *  ������ȣ �Է��� ������ ���
	 *  1. Ŀ�ø��� �����Ѵ�.
	 *  2. �� ȸ�������� Ŀ�ù�ȣ�� �ִ´�.
	 *  
	 *  ����� ���
	 *  1. �� Ŀ�ù�ȣ Ȯ��
	 *  2. Ŀ�ù�ȣ�� Ŀ������ Ȯ��
	 *  3. ������ ������ �ִ� ������ȣ ǥ��
	 *  4. ������ȣ ����
	 *  5. Ŀ�ø� ����
	 *  6. Ŀ�ù�ȣ�� �� ȸ�������� �ݿ�
	 *  7. Ŀ�ø� ��ȸ
	 *  
	 *  �α��ν� �� Ŀ�ù�ȣ�� ���ǿ� ������ ������.
	 */

	/**
	 * Ŀ�ÿ��θ� Ȯ���Ͽ� ��ȯ�Ѵ�.
	 * @param coupleNo Ŀ�ù�ȣ
	 * @return Ŀ���̸� true, �ƴϸ� false
	 */
	public boolean coupleYN (int coupleNo) {
		if(coupleNo != 0) {
			if(couple.selectAreYouCouple(coupleNo) == 2){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public HashMap<String, Object> makeCoupleKey (String id, int coupleNo) {
		HashMap<String, Object> temp = new HashMap<String, Object>(); 
		if(coupleNo == 0) {
			String confirmNo = Utility.getSecureCode();
			temp.put("confirmNo", confirmNo);
			int temp2 = couple.selectCoupleNoByROWID(couple.insertCoupleKey(confirmNo));
			temp.put("coupleNo", temp2);
			couple.updateMyCoupleNo(temp2, id);
			return temp;
		} else {
			temp.put("coupleNo", coupleNo);
			temp.put("confirmNo", couple.selectAlreadyNo(coupleNo));
			return temp;
		}
	}

	/**
	 * ������ȣ�� �Է��� Ŀ�ÿ����� �Ϸ��Ѵ�.
	 * ����!! ��Ʈ�ѷ����� ���� ������ȣ�� �Է��ϴ°� ���������!!
	 * @param id ȸ�����̵�
	 * @param dto ������ �Է¹��� Ŀ�ù�ȣ, ������ȣ, Ŀ���̸�
	 * @return ��������
	 */
	public boolean connectCoupleKey (String id, CoupleDTO dto) {
		if(couple.selectAreYouCouple(dto.getCoupleNo()) != 2){
			if(couple.updateCoupleName(dto) == 1 && couple.updateMyCoupleNo(dto.getCoupleNo(), id) == 1){
				return true;
			} else {
				return false;
			} 
		}
		return false;
	}
}
