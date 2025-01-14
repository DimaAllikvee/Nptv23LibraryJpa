package ee.ivkhkdev.nptv23libraryjpa.services;

import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import ee.ivkhkdev.nptv23libraryjpa.helpers.AuthorHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppService;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService implements AppService<Author> {

    @Autowired
    private AppHelper<Author> authorAppHelper;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorHelper authorHelper;

    @Override
    public boolean add() {
        Optional<Author> optionalAuthor = authorAppHelper.create();
        if (optionalAuthor.isPresent()) {
            authorRepository.save(optionalAuthor.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Author author) {
        // Можно расширить логику при необходимости
        return false;
    }

    @Override
    public boolean delete(Author author) {
        // Можно расширить логику при необходимости
        return false;
    }

    @Override
    public boolean print() {
        return authorAppHelper.printList();
    }

    /**
     * Изменить доступность (available) автора.
     */
    public boolean changeAvailability() {
        // Получаем от пользователя нужные данные в AuthorHelper
        Optional<Author> optionalAuthor = authorHelper.changeAvailability();
        if (optionalAuthor.isPresent()) {
            // Сохраняем изменения в базе
            authorRepository.save(optionalAuthor.get());
            System.out.println("Доступность автора успешно изменена.");
            return true;
        }
        System.out.println("Автор не найден или возникла ошибка при изменении доступности.");
        return false;
    }
}
