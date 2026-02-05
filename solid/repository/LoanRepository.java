package solid.repository;

import java.util.List;
import java.util.Optional;

import solid.domain.Loan;

public interface LoanRepository {
    Optional<Loan> findById(String id);

    List<Loan> findByMemberId(String memberId);

    List<Loan> findAll();

    void save(Loan loan);
}
