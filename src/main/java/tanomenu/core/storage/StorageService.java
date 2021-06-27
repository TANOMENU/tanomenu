package tanomenu.core.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tanomenu.core.RepositoryException;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

@Service
public class StorageService {

    private final File file;

    public StorageService() {
        this.file = ensure_dir();
    }

    private File ensure_dir() {
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
        var file = Paths.get(this.file.getAbsolutePath(), uuid.toString());
        return new File(file.toString());
    }

    public UUID save(MultipartFile file) {
        Objects.requireNonNull(file);
        var uuid =  UUID.randomUUID();
        try {
            file.transferTo(fileOf(uuid));
        } catch (IOException e) {
            throw new StorageException(e);
        }
        return uuid;
    }

    public Optional<byte[]> find(UUID uuid) {
        Objects.requireNonNull(uuid);
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

    public UUID update(UUID uuid, MultipartFile multipartFile) {
        Objects.requireNonNull(uuid);
        Objects.requireNonNull(multipartFile);
        if(!delete(uuid)) {
            throw new RepositoryException();
        }
        return save(multipartFile);
    }

    public boolean delete(UUID uuid) {
        Objects.requireNonNull(uuid);
        var file = fileOf(uuid);
        try {
            return file.exists() && file.delete();
        } catch (SecurityException e) {
            throw new StorageException(e);
        }
    }
}
