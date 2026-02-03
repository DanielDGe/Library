package solid.repository;

import java.util.Optional;
import solid.domain.Member;

public interface MemberRepository {
    Optional<Member> findById(String id);

    void save(Member member);
}
