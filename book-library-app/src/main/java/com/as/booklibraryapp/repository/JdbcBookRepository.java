package com.as.booklibraryapp.repository;

import com.as.booklibraryapp.domain.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

@Repository
public class JdbcBookRepository implements BookRepository{

    private final JdbcTemplate jdbc;

    private static final String TABLE_NAME = "book";
    private static final String GENERATED_KEY_COLUMN = "id";

    private static final String SELECT_ALL = "SELECT id, title, author, year_release, img FROM book";

    public JdbcBookRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private Book mapRowToBook(ResultSet rs, int rowNum) throws SQLException {
        return new Book(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("year_release"),
                rs.getString("img")
        );
    }

    @Override
    public Set<Book> findAllBooks() {
        return Set.copyOf(jdbc.query(SELECT_ALL, this::mapRowToBook));
    }

    @Override
    public Optional<Book> findBook(Long id) {
        try{
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + " WHERE id = ?", this::mapRowToBook, id)
            );
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
}
