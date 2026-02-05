package solid.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import solid.domain.Book;
import solid.repository.BookRepository;

public class InMemoryBookRepository implements BookRepository {
    private final List<Book> books = new ArrayList<>();

    @Override
    public Optional<Book> findById(String id) {
        return books.stream()
            .filter(book -> book.getId().equals(id))
            .findFirst();
    }

    @Override
    public void save(Book book) {
        findById(book.getId()).ifPresent(books::remove);
        books.add(book);
    }

    public List<Book> findAll() {
        return new ArrayList<>(books);
    }
}
