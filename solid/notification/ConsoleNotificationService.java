package solid.notification;

import solid.domain.Book;
import solid.domain.Member;

public class ConsoleNotificationService implements NotificationService {
    @Override
    public void notifyLoanCreated(Member member, Book book) {
        System.out.println("Enviando email a " + member.getEmail()
            + ": Has prestado el libro " + book.getTitle());
    }

    @Override
    public void notifyLoanReturned(Member member, Book book) {
        System.out.println("Enviando email a " + member.getEmail()
            + ": Has devuelto el libro " + book.getTitle());
    }
}
