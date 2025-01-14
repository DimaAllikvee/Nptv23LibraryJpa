package ee.ivkhkdev.nptv23libraryjpa.helpers;

import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.Input;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorHelper implements AppHelper<Author> {

    @Autowired
    private Input input;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Optional<Author> create() {
        try {
            Author author = new Author();
            System.out.print("Имя автора: ");
            author.setFirst_name(input.getString());
            System.out.print("Фамилия автора: ");
            author.setLast_name(input.getString());
            // Поле available у нас по умолчанию = true в конструкторе/сеттере
            return Optional.of(author);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> edit(Author author) {
        // Здесь могла бы быть ваша логика редактирования (имени, фамилии, и т.д.)
        return Optional.empty();
    }

    @Override
    public boolean printList() {
        List<Author> authors = authorRepository.findAll();
        try {
            if (authors.isEmpty()) {
                System.out.println("Список авторов пуст");
                return false;
            }
            System.out.println("---------- Список авторов --------");
            for (Author author : authors) {
                // Выводим ID, Имя и Фамилию (при желании можно расширить)
                System.out.printf("%d. %s %s (Доступен: %b)%n",
                        author.getId(),
                        author.getFirst_name(),
                        author.getLast_name(),
                        author.isAvailable());
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error authorAppHelper.printList(authors): " + e.getMessage());
            return false;
        }
    }

    /**
     * Логика изменения поля available (доступности автора).
     */
    public Optional<Author> changeAvailability() {
        try {
            // Выводим весь список, чтобы пользователь мог выбрать ID
            this.printList();
            System.out.print("Введите ID автора, чью доступность хотите изменить: ");
            long authorId = input.getInt();

            Optional<Author> optionalAuthor = authorRepository.findById(authorId);
            if (optionalAuthor.isPresent()) {
                Author author = optionalAuthor.get();
                System.out.printf("Текущая доступность: %b%n", author.isAvailable());
                System.out.print("Введите новую доступность (true/false): ");
                boolean newAvailability = Boolean.parseBoolean(input.getString());
                author.setAvailable(newAvailability);

                // Возвращаем автора с обновлённым полем available
                return Optional.of(author);
            } else {
                System.out.println("Автор с таким ID не найден.");
                return Optional.empty();
            }
        } catch (Exception e) {
            System.out.println("Ошибка при изменении доступности автора: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Метод, который возвращает список авторов для книги.
     */
    public List<Author> listAuthorsForBook() {
        this.printList();
        System.out.print("Сколько авторов у книги: ");
        int countAuthorsForBook = input.getInt();
        List<Author> authorsForBook = new ArrayList<>();
        for (int i = 0; i < countAuthorsForBook; i++) {
            System.out.printf("Выберите %d-го автора из списка (ID): ", i + 1);
            Optional<Author> optionalAuthor = authorRepository.findById((long) input.getInt());
            optionalAuthor.ifPresent(authorsForBook::add);
        }
        return authorsForBook;
    }
}
