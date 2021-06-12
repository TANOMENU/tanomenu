package tanomenu.core.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tanomenu.core.RepositoryException;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

@Service
public class StorageService {

    private final File dir;

    public StorageService() {
        this.dir = make_dir();
    }

    private File make_dir() {
        var path = String.format("%s/upload/", System.getProperty("user.dir"));
        var dir = new File(path);
        try {
            if(!dir.mkdirs() && !dir.exists()) {
                throw new StorageException("Cannot make repository directory");
            }
        } catch (SecurityException e) {
            throw new StorageException(e);
        }
        return dir;
    }

    private File fileOf(UUID uuid) {
        var file = Paths.get(this.dir.getAbsolutePath(), uuid.toString());
        return new File(file.toString());
    }

    public UUID save(MultipartFile file) {
        var uuid =  UUID.randomUUID();
        try {
            file.transferTo(fileOf(uuid));
        } catch (IOException e) {
            throw new StorageException(e);
        }
        return uuid;
    }

    public Optional<byte[]> find(UUID uuid) {
        try(var fis = new FileInputStream(fileOf(uuid))) {
            return Optional.of(fis.readAllBytes());
        }
        catch (FileNotFoundException e) {
            return Optional.empty();
        }
        catch (IOException | ClassCastException e) {
            throw new RepositoryException(e);
        }
    }

    public boolean delete(UUID uuid) {
        var file = fileOf(uuid);
        try {
            return file.exists() && file.delete();
        } catch (SecurityException e) {
            throw new StorageException(e);
        }
    }

}
