/**
 * @author Girish Raman
 */

package com.bankapp.dao;

import com.bankapp.userexceptions.CustomException;

public interface OTPDAO {

	void storeOTP(String userName, Long otp);
	boolean checkOTP(String userName,Long userOTP) throws CustomException;

}