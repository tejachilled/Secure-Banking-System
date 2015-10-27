package com.bankapp.userexceptions;

public class UserNameExists extends Exception
{

    private static final long serialVersionUID = 1997753363232807009L;

		public UserNameExists()
		{
		}

		public UserNameExists(String message)
		{
			super(message);
		}

		public UserNameExists(Throwable cause)
		{
			super(cause);
		}

		public UserNameExists(String message, Throwable cause)
		{
			super(message, cause);
		}

		public UserNameExists(String message, Throwable cause, 
                                           boolean enableSuppression, boolean writableStackTrace)
		{
			super(message, cause, enableSuppression, writableStackTrace);
		}


}
