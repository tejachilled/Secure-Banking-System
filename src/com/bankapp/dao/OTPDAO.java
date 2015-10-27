/**
 * @author Girish Raman
 */

package com.bankapp.dao;

import com.bankapp.userexceptions.CustomException;

public interface OTPDAO {

	void storeOTP(String userName, String otp);
	boolean checkOTP(String userName,String userOTP) throws CustomException;

}