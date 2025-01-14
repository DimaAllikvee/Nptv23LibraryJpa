package ee.ivkhkdev.nptv23libraryjpa.interfaces;

import ee.ivkhkdev.nptv23libraryjpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
