package com.bankapp.userexceptions;

public class UserAccountExist extends Exception
{

    private static final long serialVersionUID = 1997753363232807008L;

		public UserAccountExist()
		{
		}

		public UserAccountExist(String message)
		{
			super(message);
		}

		public UserAccountExist(Throwable cause)
		{
			super(cause);
		}

		public UserAccountExist(String message, Throwable cause)
		{
			super(message, cause);
		}

		public UserAccountExist(String message, Throwable cause, 
                                           boolean enableSuppression, boolean writableStackTrace)
		{
			super(message, cause, enableSuppression, writableStackTrace);
		}

}
