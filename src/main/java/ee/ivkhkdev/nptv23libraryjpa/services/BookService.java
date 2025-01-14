package ee.ivkhkdev.nptv23libraryjpa.services;

import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import ee.ivkhkdev.nptv23libraryjpa.entity.Book;
import ee.ivkhkdev.nptv23libraryjpa.helpers.AuthorHelper;
import ee.ivkhkdev.nptv23libraryjpa.helpers.BookHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppService;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AuthorRepository;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements AppService<Book> {

    @Autowired
    private AppHelper<Book> bookHelper;
    @Autowired
    private AuthorHelper authorHelper;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    @Override
    public boolean add() {
        Optional<Book> optionalBook = bookHelper.create();
        if (optionalBook.isEmpty()) {
            return false;
        }
        Book book = optionalBook.get();
        List<Author> bookAuthors = authorHelper.listAuthorsForBook();
        book.setAuthors(bookAuthors);
        Book savedBook = bookRepository.save(book);
        for (Author author : bookAuthors) {
            author.getBooks().add(savedBook);
        }
        authorRepository.saveAll(bookAuthors);
        return true;
    }

    @Override
    public boolean update(Book book) {
        // Можете использовать для редактирования книги
        return false;
    }

    @Override
    public boolean delete(Book book) {
        // Удаление книги, если потребуется
        return false;
    }

    @Override
    public boolean print() {
        return bookHelper.printList();
    }

    /**
     * Метод для изменения доступности (available) выбранной книги.
     */
    public boolean changeAvailability() {
        // Предполагаем, что bookHelper у нас типа BookHelper,
        // либо мы вручную приводим к BookHelper, если нужно
        if (bookHelper instanceof BookHelper helper) {
            Optional<Book> optionalBook = helper.changeAvailability();
            if (optionalBook.isPresent()) {
                Book updatedBook = optionalBook.get();
                // Сохраняем обновлённое поле available
                bookRepository.save(updatedBook);
                System.out.println("Доступность книги успешно изменена.");
                return true;
            }
        }
        System.out.println("Не удалось изменить доступность книги.");
        return false;
    }
}
