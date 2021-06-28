package tanomenu.core;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Repository<T extends Entity<T>> {

    private final File file;

    private final Map<UUID, T> data;

    public Repository() {
        this.file = makeDirs();
        this.data = recoverData();
    }

    private File makeDirs() {
        var path = String.format("%s/repo/%s.ser", System.getProperty("user.dir"), this.getClass().getSimpleName());

        var file = new File(path);
        try {
            if(!file.getParentFile().mkdirs() && !file.getParentFile().exists()) {
                throw new RepositoryException("Cannot make repository directory");
            }
        } catch (SecurityException e) {
            throw new RepositoryException(e);
        }

        return file;
    }

    private Map<UUID, T> recoverData() {
        try(var fis = new FileInputStream(this.file);
            var ois = new ObjectInputStream(fis)) {
            return Collections.synchronizedMap((Map<UUID, T>) ois.readObject());
        }
        catch (FileNotFoundException e) {
            return Collections.synchronizedMap(new TreeMap<>());
        }
        catch (IOException | ClassCastException | ClassNotFoundException  e) {
            throw new RepositoryException(e);
        }
    }

    @PreDestroy
    private void persistData() {
        try(var fos = new FileOutputStream(this.file);
            var oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.data);
        }
        catch (IOException e)
        {
            throw new RepositoryException(e);
        }
    }

    protected Stream<T> getStream() {
        return data.entrySet()
                .parallelStream()
                .map(entry -> {
                    var t = entry.getValue().clone();
                    t.setUuid(entry.getKey());
                    return t;
                });
    }

    public Optional<T> find(UUID uuid) {
        Objects.requireNonNull(uuid);
        var t = data.get(uuid);
        if (t != null) {
            t = t.clone();
            t.setUuid(uuid);
        }
        return Optional.ofNullable(t);
    }

    public UUID save(T t) {
        Objects.requireNonNull(t);
        var uuid = UUID.randomUUID();
        data.put(uuid, t.clone());
        return uuid;
    }

    public void update(UUID uuid, T t) {
        Objects.requireNonNull(uuid);
        Objects.requireNonNull(t);
        if(data.put(uuid, t) == null) {
            throw new NoSuchElementException();
        }
    }

    public boolean delete(UUID uuid) {
        Objects.requireNonNull(uuid);
        return data.remove(uuid) != null;
    }

    public List<T> findAll() {
        return getStream()
                .collect(Collectors.toList());
    }

}
