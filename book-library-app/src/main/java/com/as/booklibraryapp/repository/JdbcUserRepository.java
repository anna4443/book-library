package com.as.booklibraryapp.repository;

import com.as.booklibraryapp.domain.Users;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class JdbcUserRepository implements UserRepository{

    // quick way of defining jdbc query
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert userInserter;
    private final SimpleJdbcInsert authorityInserter;

    private static final String TABLE_NAME = "users";
    private static final String GENERATED_KEY_COLUMN = "id";

    private static final String SELECT_ALL = "SELECT id, name, surname, username, email, password, enabled FROM users";

    private static final String TABLE_AUTHORITIES = "authorities";

    public JdbcUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.userInserter = new SimpleJdbcInsert(jdbc)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(GENERATED_KEY_COLUMN);
        this.authorityInserter = new SimpleJdbcInsert(jdbc)
                .withTableName(TABLE_AUTHORITIES);
    }

    private Users mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        return new Users(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getBoolean("enabled")
        );
    }

    @Override
    public Set<Users> findAll() {
        return Set.copyOf(jdbc.query(SELECT_ALL, this::mapRowToUser));
    }

    @Override
    public Optional<Users> findById(Long id) {
        try{
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + " WHERE id = ?", this::mapRowToUser, id)
            );
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        try{
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + " WHERE username = ?", this::mapRowToUser, username)
            );
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Users> save(Users user) {

        System.out.println(user);
        try {
            user.setId(saveUserInformation(user));
            saveUserAuthority(user);
            return Optional.of(user);
        } catch (DuplicateKeyException e){
            return Optional.empty();
        }
    }

    public void saveUserAuthority(Users user) {
        Map<String, Object> values = new HashMap<>();

        values.put("username", user.getUsername());
        values.put("authority", "ROLE_USER");

        authorityInserter.execute(values);
    }

    private long saveUserInformation(Users user) {
        Map<String, Object> values = new HashMap<>();

        String password = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        values.put("name", user.getName());
        values.put("surname", user.getSurname());
        values.put("username", user.getUsername());
        values.put("email", user.getEmail());
        values.put("password", hashedPassword);
        values.put("enabled", true);

        return userInserter.executeAndReturnKey(values).longValue();
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM users WHERE id = ?", id);
    }
}
