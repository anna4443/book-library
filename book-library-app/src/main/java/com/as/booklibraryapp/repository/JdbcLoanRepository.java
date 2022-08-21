package com.as.booklibraryapp.repository;

import com.as.booklibraryapp.domain.Loan;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class JdbcLoanRepository implements  LoanRepository{
    private final JdbcTemplate jdbc;

    private static final String TABLE_NAME = "loan";
    private static final String GENERATED_KEY_COLUMN = "id";

    private final SimpleJdbcInsert loanInserter;

    private static final String SELECT_ALL = "SELECT id, name, surname, username, title FROM loan";

    public JdbcLoanRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.loanInserter = new SimpleJdbcInsert(jdbc)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(GENERATED_KEY_COLUMN);
    }

    private Loan mapRowToLoan(ResultSet rs, int rowNum) throws SQLException {
        return new Loan(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("username"),
                rs.getString("title")
        );
    }

    @Override
    public Set<Loan> findAllLoans() {
        return Set.copyOf(jdbc.query(SELECT_ALL, this::mapRowToLoan));
    }

    @Override
    public Optional<Loan> saveLoans(Loan loan) {
        try {
            loan.setId(saveInfoLoans(loan));
            return Optional.of(loan);
        } catch (DuplicateKeyException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Loan> findByUsername(String username) {

        //try{
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + " WHERE username = ?", this::mapRowToLoan, username)
            );
        //} catch (EmptyResultDataAccessException e){
          //  return Optional.empty();
        //}
    }

    private Long saveInfoLoans(Loan loan) {
        Map<String, Object> values = new HashMap<>();

        values.put("name", loan.getName());
        values.put("surname", loan.getSurname());
        values.put("username", loan.getUsername());
        values.put("title", loan.getTitle());

       return loanInserter.executeAndReturnKey(values).longValue();
    }
}
