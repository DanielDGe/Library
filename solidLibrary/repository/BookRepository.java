package solidLibrary.repository;

import java.util.Optional;

import solidLibrary.domain.Book;

public interface BookRepository {
    Optional<Book> findById(String id);

    void save(Book book);
}
