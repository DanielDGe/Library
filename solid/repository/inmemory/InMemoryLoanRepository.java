package solid.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import solid.domain.Loan;
import solid.repository.LoanRepository;

public class InMemoryLoanRepository implements LoanRepository {
    private final List<Loan> loans = new ArrayList<>();

    @Override
    public Optional<Loan> findById(String id) {
        return loans.stream()
            .filter(loan -> loan.getId().equals(id))
            .findFirst();
    }

    @Override
    public List<Loan> findByMemberId(String memberId) {
        return loans.stream()
            .filter(loan -> loan.getMemberId().equals(memberId))
            .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(loans);
    }

    @Override
    public void save(Loan loan) {
        findById(loan.getId()).ifPresent(loans::remove);
        loans.add(loan);
    }
}
