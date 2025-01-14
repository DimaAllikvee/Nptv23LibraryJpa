package ee.ivkhkdev.nptv23libraryjpa.helpers;

import ee.ivkhkdev.nptv23libraryjpa.entity.Book;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.BookRepository;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookHelper implements AppHelper<Book> {

    @Autowired
    private Input input;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Optional<Book> create() {
        Book book = new Book();
        System.out.print("Название книги: ");
        book.setTitle(input.getString());
        System.out.print("Год издания книги: ");
        book.setPublishedYear(input.getInt());
        // По умолчанию available=true в конструкторе, так что вручную ставить не нужно
        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Book book) {
        // Здесь могла бы быть логика редактирования названия, года и т.д.
        return Optional.empty();
    }

    @Override
    public boolean printList() {
        List<Book> books = bookRepository.findAll();
        try {
            if (books.isEmpty()) {
                System.out.println("Список книг пуст");
                return false;
            }
            System.out.println("---------- Список книг --------");
            for (Book book : books) {
                // Выводим ID, Название, Год, и текущее значение поля available
                System.out.printf("ID: %d, Название: %s, Год издания: %d, Доступна: %b%n",
                        book.getId(),
                        book.getTitle(),
                        book.getPublishedYear(),
                        book.isAvailable());
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error bookHelper.printList(books): " + e.getMessage());
        }
        return false;
    }

    /**
     * Метод для изменения доступности (available) книги.
     */
    public Optional<Book> changeAvailability() {
        // Сначала показываем список, чтобы пользователь видел все книги и их ID
        this.printList();
        System.out.print("Введите ID книги, доступность которой хотите изменить: ");
        long bookId = input.getInt();

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            System.out.printf("Текущая доступность: %b%n", book.isAvailable());
            System.out.print("Введите новую доступность (true/false): ");
            boolean newAvailability = Boolean.parseBoolean(input.getString());
            book.setAvailable(newAvailability);
            return Optional.of(book);
        } else {
            System.out.println("Книга с таким ID не найдена.");
            return Optional.empty();
        }
    }
}
