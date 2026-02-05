package solidLibrary.notification;

import solidLibrary.domain.Book;
import solidLibrary.domain.Member;

public interface NotificationService {
    void notifyLoanCreated(Member member, Book book);

    void notifyLoanReturned(Member member, Book book);
}
