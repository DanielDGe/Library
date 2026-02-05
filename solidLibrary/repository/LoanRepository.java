package solidLibrary.repository;

import java.util.List;
import java.util.Optional;

import solidLibrary.domain.Loan;

public interface LoanRepository {
    Optional<Loan> findById(String id);

    List<Loan> findByMemberId(String memberId);

    List<Loan> findAll();

    void save(Loan loan);
}
