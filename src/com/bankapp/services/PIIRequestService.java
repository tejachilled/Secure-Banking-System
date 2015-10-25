package com.bankapp.services;

public interface PIIRequestService {

	boolean sendNewRequest(String externalUserName, String internalUserName);

}