package solid.repository;

import java.util.Optional;

import solid.domain.Book;

public interface BookRepository {
    Optional<Book> findById(String id);

    void save(Book book);
}
