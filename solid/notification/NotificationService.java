package solid.notification;

import solid.domain.Book;
import solid.domain.Member;

public interface NotificationService {
    void notifyLoanCreated(Member member, Book book);

    void notifyLoanReturned(Member member, Book book);
}
