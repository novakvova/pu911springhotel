package hotel.storage;

public interface StorageService {
    void init();
    String save(String base64);
}
