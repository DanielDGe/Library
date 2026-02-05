package solidLibrary.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import solidLibrary.domain.Member;
import solidLibrary.repository.MemberRepository;

public class InMemoryMemberRepository implements MemberRepository {
    private final List<Member> members = new ArrayList<>();

    @Override
    public Optional<Member> findById(String id) {
        return members.stream()
            .filter(member -> member.getId().equals(id))
            .findFirst();
    }

    @Override
    public void save(Member member) {
        findById(member.getId()).ifPresent(members::remove);
        members.add(member);
    }

    public List<Member> findAll() {
        return new ArrayList<>(members);
    }
}
