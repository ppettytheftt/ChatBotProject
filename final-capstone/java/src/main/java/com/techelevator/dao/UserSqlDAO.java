package com.techelevator.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.model.RegisterUserDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techelevator.model.User;

@Service
public class UserSqlDAO implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    public UserSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        return jdbcTemplate.queryForObject("select user_id from users where username = ?", int.class, username);
    }

	@Override
	public User getUserById(Long userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if(results.next()) {
			return mapRowToUser(results);
		} else {
			throw new RuntimeException("userId "+userId+" was not found.");
		}
	}

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        for (User user : this.findAll()) {
            if( user.getUsername().toLowerCase().equals(username.toLowerCase())) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    //public boolean create(String username, String password, String role) {
    public boolean create(RegisterUserDTO user) {
        //public boolean create(User newUser) {
        //String sqlUsers = INSERT INTO users () VALUES () ...newUSer.get(un)String username, String password, String role
        //String sqlUserDetails = INSERT INTO userdetails  newUser.firstname, newUSer.getLastName,....
        boolean userCreated = false;

        // create user
        String username = user.getUsername();
        String password = user.getPassword();
        String role = user.getRole();


        String insertUser = "insert into users (username,password_hash,role) values(?,?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = "ROLE_" + role.toUpperCase();

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String id_column = "user_id";
        userCreated = jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(insertUser, new String[]{id_column});
                    ps.setString(1, username);
                    ps.setString(2, password_hash);
                    ps.setString(3, ssRole);
                    return ps;
                }
                , keyHolder) == 1;
        int newUserId = (int) keyHolder.getKeys().get(id_column);

        //Get the user_id from the users table


        //Insert into userdetails table
        String sqlInsertIntoUserDetails = "INSERT INTO userdetails (user_id,firstname,lastname,emailid,contactnumber,isstudent, zip) VALUES (?,?,?,?,?,?, ?);";
        jdbcTemplate.update(sqlInsertIntoUserDetails,newUserId,user.getFirstName(),user.getLastName(),user.getEmailId(),user.getContactNumber(),user.getIsStudent(), user.getZip());

        return userCreated;
    }

    @Override
    public RegisterUserDTO findUserByUsername(String username) {
        RegisterUserDTO registeredUser = new RegisterUserDTO();
        int id = findIdByUsername(username);
        String sql = "SELECT * FROM userdetails WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()){
            String firstName = results.getString("firstname");
            String lastName = results.getString("lastname");
            String email = results.getString("emailid");
            long phoneNumber = results.getLong("contactnumber");
            boolean isStudent = results.getBoolean("isstudent");
            int zip = results.getInt("zip");
            registeredUser = new RegisterUserDTO(firstName, lastName, email, phoneNumber, isStudent, zip);
        }
        return registeredUser;
    }

    @Override
    public void updateUserDetails(String username, RegisterUserDTO registeredUser) {
        String userName = username.toLowerCase();
        String firstName = registeredUser.getFirstName();
        String lastName = registeredUser.getLastName();
        String emailId = registeredUser.getEmailId();
        long contactNumber = registeredUser.getContactNumber();
        boolean isStudent = registeredUser.getIsStudent();
        int id = findIdByUsername(userName);
        int zip = registeredUser.getZip();
        String sql = "UPDATE userdetails SET firstname = ?,lastname = ?, emailid = ?, contactnumber = ?, isstudent = ?, zip = ? WHERE user_id = ?; ";
        jdbcTemplate.update(sql,firstName, lastName, emailId, contactNumber, isStudent, zip, id);
    }


    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(rs.getString("role"));
        user.setActivated(true);
        return user;
    }
}
