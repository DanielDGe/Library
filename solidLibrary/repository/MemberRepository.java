package solidLibrary.repository;

import java.util.Optional;

import solidLibrary.domain.Member;

public interface MemberRepository {
    Optional<Member> findById(String id);

    void save(Member member);
}
