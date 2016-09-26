package work.model.service;

import java.util.HashMap;
import work.model.dao.CoupleDAO;
import work.model.dto.CoupleDTO;
import work.util.Utility;

public class MatchingService {
	private CoupleDAO couple = CoupleDAO.getInstance();


	/*
	 * 상황1. (일)커플번호를 가지고 있고 (1) (이)커플임.
	 *  - 접속을 허가하고 커플명을 세션에 담아서 필요할때마다 써야한다.
	 *  
	 * 상황2. (일)커플번호를 가지고 있고 (2) (이)커플이 아님.
	 *  -> 인증번호를 발행하려 한다. => (삼)기존에 가지고 있던 인증번호 표출
	 *  -> 인증번호를 입력하려 한다. 
	 *  	=> (일)입력한 인증번호가 내 인증번호가 아닌지 확인한다.
	 *  	=> (이)입력한 인증번호가 이미 커플인지 아닌지 확인한다.
	 *  
	 *  상황3. 커플번호가 없음.
	 *   -> 인증번호를 발행하려 한다. => (사)인증번호 발행
	 *   -> 인증번호를 입력하려 한다.
	 *   	=> (일)입력한 인증번호가 내 인증번호가 아닌지 확인한다.
	 *  	=> (이)입력한 인증번호가 이미 커플인지 아닌지 확인한다.
	 *  
	 *  인증번호 입력을 성공할 경우
	 *  1. 커플명을 설정한다.
	 *  2. 내 회원정보에 커플번호를 넣는다.
	 *  
	 *  도출된 기능
	 *  1. 내 커플번호 확인
	 *  2. 커플번호가 커플인지 확인
	 *  3. 기존에 가지고 있는 인증번호 표출
	 *  4. 인증번호 발행
	 *  5. 커플명 설정
	 *  6. 커플번호를 내 회원정보에 반영
	 *  7. 커플명 조회
	 *  
	 *  로그인시 내 커플번호를 세션에 담으면 좋을듯.
	 */

	/**
	 * 커플여부를 확인하여 반환한다.
	 * @param coupleNo 커플번호
	 * @return 커플이면 true, 아니면 false
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
	 * 인증번호를 입력해 커플연결을 완료한다.
	 * 주의!! 컨트롤러에서 본인 인증번호를 입력하는걸 막아줘야함!!
	 * @param id 회원아이디
	 * @param dto 폼에서 입력받은 커플번호, 인증번호, 커플이름
	 * @return 성공여부
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
