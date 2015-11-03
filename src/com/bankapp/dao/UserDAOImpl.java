package com.bankapp.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import com.bankapp.jdbc.UserRowMapper;
import com.bankapp.jdbc.UseraccountsRowMapper;
import com.bankapp.model.UserInfo;
import com.bankapp.model.Useraccounts;
import com.bankapp.userexceptions.CustomException;
import com.bankapp.userexceptions.UserAccountExist;
import com.bankapp.userexceptions.UserNameExists;

public class UserDAOImpl implements UserDAO {

	@Autowired
	DataSource dataSource;
	private Connection conn = null;
	private static final String INTERNAL_USER = "I"; 
	private static final String EXTERNAL_USER = "U"; 
	private static final String MERCHANT = "M";

	public void insert(UserInfo user){
		String sql = "INSERT INTO test_table "
				+ "(firstname,lastname) VALUES (?, ?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql,
				new Object[] { user.getFirstName(), user.getLastName()});

	}
	
	
	
	@Override
	public List<UserInfo> getExternalUserList() {
		List<UserInfo> userList = new ArrayList<>();

		String sql = "select * from tbl_external_users";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		userList = jdbcTemplate.query(sql, new UserRowMapper());
		return userList;
	}
	
	
	@Override
	public void updateLoginTable(String password, String userName) {
		// TODO Auto-generated method stub
		String sql = "update tbl_login set pwd_hash= ? , first_time = ?  where user_name= ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, password);
			ps.setBoolean(2, false);
			ps.setString(3, userName);
			ps.executeUpdate();			
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	@Override
	public boolean isFirstLogin(String name) {
		return getLoginInfo(name).isFirstLogin();
	}
	
	private UserInfo getLoginInfo(String username){
		String sql = "select * from tbl_login where user_name= ?";
		UserInfo user = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new UserInfo(
						rs.getString("user_name"),
						rs.getString("pwd_hash"), 
						rs.getString("user_type"),
						rs.getBoolean("first_time")
						);
			}
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return user;
	}
	
	@Override
	public UserInfo findUserByUsername(String username) {
		UserInfo user = null;
		String password = "";		
		user = getLoginInfo(username);
		
		if(user!=null && ( user.getRole().equalsIgnoreCase(INTERNAL_USER))){
			password = user.getPassword();
			user = getInternalUser(user.getUserName());
			user.setPassword(password);
		}else if(user!=null && (user.getRole().equalsIgnoreCase(EXTERNAL_USER) 
				||  user.getRole().equalsIgnoreCase(MERCHANT))){
			password = user.getPassword();
			user = getExternalUser(user.getUserName());
			user.setPassword(password);
		}
		return user;
	}
	@Override
	public void registerNewInternalUser(UserInfo userInfo,String role) throws CustomException, UserNameExists {
		/*
		 * 1 = successfully updated
		 * 2 = duplicate entry
		 * 3 = some other error
		 * */
		try{
			userInfo.setRole(INTERNAL_USER);
			int val = insertToLoginTable(userInfo,true);
			System.out.println("registerNewInternalUser return val : "+val);
			switch(val){
			case 1:{
				userInfo.setRole(role);
				insertToInternalUserTable(userInfo); break;
			}
			case 2:{
				throw new UserNameExists("User name exists. Please choose another user name"); 
			}
			}
		}catch(SQLException exp){
			throw new CustomException(exp);
		} catch (UserNameExists exp) {
			throw exp;
		}
	}

	@Override
	public Long registerNewUserAccount(UserInfo userInfo, Useraccounts account) throws UserAccountExist, UserNameExists, CustomException {
		/*
		 * 1 = successfully updated
		 * 2 = duplicate entry
		 * 3 = some other error
		 * */
		int val = 0;

		try{
			val = insertToLoginTable(userInfo,true);
			System.out.println("registerNewUserAccount Insert to login table value : "+val);
			switch(val){
			case 1:{
				insertToExternalUserTable(userInfo);
				Long uniqueAccountNumber =  insertToAccountsTable(userInfo,account);
				return uniqueAccountNumber;
			}
			case 2:{
				if(checkIfUserSame(userInfo)){
					String accountType = getAccountType(userInfo.getUserName());
					if(!accountType.equalsIgnoreCase(account.getAccountType())) {
						Long uniqueAccountNumber =  insertToAccountsTable(userInfo,account);
						return uniqueAccountNumber;			
					}else{
						throw new UserAccountExist("This user already have the account you are trying to create");
					}
				}
			}
			case 3:
				throw new CustomException("Error! Please try again");
			}
			Default: throw new CustomException("Error! Please try again");

		}catch(UserAccountExist exp){
			throw exp;
		}catch(SQLException exp){
			throw new CustomException(exp);
		} catch (UserNameExists exp) {
			// TODO Auto-generated catch block
			throw exp;
		}
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {

		String sql = "UPDATE tbl_external_users SET  email_id = ? , phone_number = ? , add_l1 = ? , add_l2 = ?"
				+ " WHERE user_name = ?";
		PreparedStatement preparedStatement = null;

		try {
			conn = dataSource.getConnection();
			preparedStatement= conn.prepareStatement(sql);

			preparedStatement.setString(5, userInfo.getUserName());
			preparedStatement.setString(1, userInfo.getEmaiID());
			preparedStatement.setLong(2, userInfo.getPhoneNumber());
			preparedStatement.setString(3, userInfo.getAddress1());
			preparedStatement.setString(4, userInfo.getAddress2());
			// execute update SQL stetement
			System.out.println("update statement : "+preparedStatement.toString());
			preparedStatement.executeUpdate();

			System.out.println("Record is updated to External users table table!");
			preparedStatement.close();
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}


	}

	@Override
	public void updateInternalUserInfo(UserInfo userInfo) {

		String sql = "UPDATE tbl_internal_users SET  email_id = ? , phone_number = ? , add_l1 = ? , add_l2 = ?"
				+ " WHERE user_name = ?";
		PreparedStatement preparedStatement = null;

		try {
			conn = dataSource.getConnection();
			preparedStatement= conn.prepareStatement(sql);
			preparedStatement.setString(5, userInfo.getUserName());
			preparedStatement.setString(1, userInfo.getEmaiID());
			preparedStatement.setLong(2, userInfo.getPhoneNumber());
			preparedStatement.setString(3, userInfo.getAddress1());
			preparedStatement.setString(4, userInfo.getAddress2());
			// execute update SQL stetement
			System.out.println("update statement : "+preparedStatement.toString());
			preparedStatement.executeUpdate();
			System.out.println("Record is updated to Internal users table table!");
			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


	@Override
	public void deleteUserInfo(UserInfo userInfo) {
		deleteFromTables(userInfo.getUserName());
	}
	private void deleteFromTables(String userName) {
		// TODO Auto-generated method stub
		String sql = "DELETE from tbl_login"
				+ " WHERE user_name = ?";
		PreparedStatement preparedStatement = null;

		try {
			conn = dataSource.getConnection();
			preparedStatement= conn.prepareStatement(sql);
			preparedStatement.setString(1, userName);
			// execute update SQL stetement
			System.out.println("update statement : "+preparedStatement.toString());
			preparedStatement.executeUpdate();
			System.out.println("Record is deleted to Internal users table table!");
			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public UserInfo getUserAndAccuntInfobyUserName(String userName) {
		UserInfo userInfo = getExternalUser(userName);
		List<Useraccounts> userAccounts= getAccountsInfobyUserName(userName);
		userInfo.setAccount(userAccounts);
		return userInfo;
	}
	@Override
	public String getUserRole(String username) {

		return findUserByUsername(username).getRole();
	}




	private String getAccountType(String userName) throws SQLException, UserAccountExist {
		String sql = "select * from tbl_accounts where user_name= ?";
		String accountType = ""; int count= 0;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				accountType = rs.getString("type");
				count++;
			}
			ps.close();

		} catch (SQLException e) {
			throw  e;

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {throw e;}
			}
		}
		if(count==1){
			return accountType;
		}else {
			throw new UserAccountExist("This user already have the account you are trying to create");
		}

	}
	private boolean checkUserAccountExist(String string) {
		// TODO Auto-generated method stub
		return false;
	}
	private boolean checkIfUserSame(UserInfo user1) throws UserNameExists {
		UserInfo user2 = getExternalUser(user1.getUserName());	
		if(user2!=null && checkUsers(user1,user2)){
			return true;
		}else{
			throw new UserNameExists("User name exists. Please choose another user name");
		}

	}

	private boolean checkUsers(UserInfo user1, UserInfo user2) {
		System.out.println("user1 : "+user1.toString());
		System.out.println("user2 : "+user2.toString());
		boolean flag = false;
		if(user1.getFirstName().equalsIgnoreCase(user2.getFirstName()) && user1.getUserName().equalsIgnoreCase(user2.getUserName()) && user1.getLastName().equalsIgnoreCase(user2.getLastName()) && user1.getAddress1().equalsIgnoreCase(user2.getAddress1())
				&& user1.getAddress2().equalsIgnoreCase(user2.getAddress2()) && user1.getEmaiID().equalsIgnoreCase(user2.getEmaiID()) && user1.getPhoneNumber().equals(user2.getPhoneNumber())
				&& user2.getRole().equalsIgnoreCase("ROLE_"+user1.getRole())	){
			flag = true;
		}
		System.out.println("user1 == user2 ? "+ flag);
		return flag;
	}

	private int insertToLoginTable(UserInfo userInfo,boolean flag){

		String sql = "Insert into tbl_login(user_name, pwd_hash, user_type, first_time) values(?,?,?,?)";
		try
		{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userInfo.getUserName());
			ps.setString(2, userInfo.getPassword());
			ps.setString(3, userInfo.getRole());
			ps.setBoolean(4, flag);
			System.out.println("Insert to login : "+ps.toString());
			ps.executeUpdate();
		}
		catch (SQLIntegrityConstraintViolationException e) {
			return 2;
		}
		catch (SQLException e) {
			return 3;
		} finally {
			if (conn != null) {
				try {
					conn.close();

				} catch (SQLException e) {}
			}
		}
		return 1;
	}
	private void insertToExternalUserTable(UserInfo userInfo) throws  UserNameExists {
		String sql = "insert into tbl_external_users(user_name, first_name, last_name, email_id, phone_number, add_l1, add_l2, role,sa1,sa2,sa3) values(?,?,?,?,?,?,?,?,?,?,?)";
		try
		{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userInfo.getUserName());
			ps.setString(2, userInfo.getFirstName());
			ps.setString(3, userInfo.getLastName());
			ps.setString(4, userInfo.getEmaiID());
			ps.setLong(5, userInfo.getPhoneNumber());
			ps.setString(6, userInfo.getAddress1());
			ps.setString(7, userInfo.getAddress2());
			if(userInfo.getRole().equalsIgnoreCase(EXTERNAL_USER)){
				ps.setString(8, "ROLE_U");
			}else if(userInfo.getRole().equalsIgnoreCase(MERCHANT)){
				ps.setString(8, "ROLE_M");
			}
			ps.setString(9, userInfo.getSq1());
			ps.setString(10, userInfo.getSq2());
			ps.setString(11, userInfo.getSq3());
			ps.executeUpdate();
		}
		catch (SQLIntegrityConstraintViolationException e) {
			throw new UserNameExists("User name exists. Please choose another user name");
		}
		catch (SQLException e) {
			throw new RuntimeException(e);

		}finally {
			if (conn != null) {
				try {
					conn.close();

				} catch (SQLException e) {}
			}
		}
	}

	private void insertToInternalUserTable(UserInfo userInfo) throws UserNameExists, SQLException {
		String sql = "insert into tbl_internal_users(user_name, first_name, last_name, email_id, phone_number, add_l1, add_l2, role,sa1,sa2,sa3) values(?,?,?,?,?,?,?,?,?,?,?)";
		try
		{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userInfo.getUserName());
			ps.setString(2, userInfo.getFirstName());
			ps.setString(3, userInfo.getLastName());
			ps.setString(4, userInfo.getEmaiID());
			ps.setLong(5, userInfo.getPhoneNumber());
			ps.setString(6, userInfo.getAddress1());
			ps.setString(7, userInfo.getAddress2());
			ps.setString(8, userInfo.getRole());
			ps.setString(9, userInfo.getSq1());
			ps.setString(10, userInfo.getSq2());
			ps.setString(11, userInfo.getSq3());
			System.out.println("insertToInternalUserTable : "+ps.toString());
			ps.executeUpdate();
		}
		catch (SQLIntegrityConstraintViolationException e) {
			throw new UserNameExists("User name exists. Please choose another user name");
		}
		catch (SQLException e) {
			throw  e;

		}finally {
			if (conn != null) {
				try {
					conn.close();

				} catch (SQLException e) {}
			}
		}
	}
	private Long insertToAccountsTable(UserInfo userInfo, Useraccounts account) {
		// TODO Auto-generated method stub
		Long uniqueAccountNumber = getUniqueAccountNumber();
		String sql = "insert into tbl_accounts(account_id, user_name, type, balance, account_open_date) values(?,?,?,?,?)";
		try
		{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, uniqueAccountNumber);
			ps.setString(2, userInfo.getUserName());
			ps.setString(3, account.getAccountType());
			ps.setDouble(4, account.getBalance());
			ps.setTimestamp(5, getTodaysDate());
			ps.executeUpdate();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();

				} catch (SQLException e) {}
			}
		}
		return uniqueAccountNumber;
	}
	public UserInfo getExternalUser(String username) {
		String sql = "select * from tbl_external_users where user_name= ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<UserInfo> user = jdbcTemplate.query(sql, new Object[] { username },
				new UserRowMapper());
		if(user.size()==0) return null;
		return user.get(0);
	}
	public UserInfo getInternalUser(String username) {
		String sql = "select * from tbl_internal_users where user_name= ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<UserInfo> user = jdbcTemplate.query(sql, new Object[] { username },
				new UserRowMapper());
		if(user.size()==0) return null;
		return user.get(0);
	}
	private Long getUniqueAccountNumber() {
		SimpleDateFormat simpleDateFormat =
				new SimpleDateFormat("MMddkkmmss");
		String dateAsString = simpleDateFormat.format(new java.sql.Timestamp(getTodaysDate().getTime()));
		return Long.valueOf(dateAsString).longValue();
	}

	private Timestamp getTodaysDate(){
		java.util.Date today = new java.util.Date();
		return  new java.sql.Timestamp(today.getTime());
	}
	public List<Useraccounts> getAccountsInfobyUserName(String userName) {
		List<Useraccounts> userAccounts = new ArrayList<>();

		String sql = "select * from tbl_accounts where user_name = ?";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		userAccounts = jdbcTemplate.query(sql, new Object[] { userName }, new UseraccountsRowMapper());
		return userAccounts;
	}
	@Override
	public boolean checkAccountExists(Long accountid) {

		String sql = "select count(*) from tbl_accounts where account_id = ?";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		int accStatus = jdbcTemplate.queryForObject(sql, new Object[] { accountid }, Integer.class);		
		if (accStatus == 1)
		{
			return true;
		}

		return false;
	}

	@Override
	public void insertPubKey(String userName, String pubKey) {
		try {
		String sql = "INSERT into tbl_userpubkeys (username, user_pubkey) values (?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(
				sql,
				new Object[] { userName, pubKey});
		}
		catch (Exception e) {
			String sql = "UPDATE tbl_userpubkeys SET user_pubkey = ? where username = ?";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.update(
					sql,
					new Object[] { userName, pubKey});
			
		}
	
		
	}
	
	@Override
	public String getPubKey(String userName){
		
		String sql = "select user_pubkey from tbl_userpubkeys where username = ?";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String pubKey = jdbcTemplate.queryForObject(sql, new Object[] { userName }, String.class);
		
		return pubKey;
	}


}
