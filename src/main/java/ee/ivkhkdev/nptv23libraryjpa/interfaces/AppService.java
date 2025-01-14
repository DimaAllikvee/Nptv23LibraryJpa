package ee.ivkhkdev.nptv23libraryjpa.interfaces;

public interface AppService<T> {
    boolean add();
    boolean update(T t);
    boolean delete(T t);
    boolean print();
}
