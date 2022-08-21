package com.as.booklibraryapp.repository;

import com.as.booklibraryapp.domain.Loan;

import java.util.Optional;
import java.util.Set;

public interface LoanRepository {
    Set<Loan> findAllLoans();
    Optional<Loan> saveLoans(Loan loan);
    Optional<Loan> findByUsername(String username);
}
