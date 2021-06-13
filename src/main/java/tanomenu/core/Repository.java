package tanomenu.core;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Repository<T extends Entity<T>> {

    private final File file;

    protected final List<T> data;

    public Repository() {
        this.file = make_dirs();
        this.data = recover_data();
    }

    private File make_dirs() {
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

    private List<T> recover_data() {
        try(var fis = new FileInputStream(this.file);
            var ois = new ObjectInputStream(fis)) {
            return Collections.synchronizedList((List<T>) ois.readObject());
        }
        catch (FileNotFoundException e) {
            return Collections.synchronizedList(new ArrayList<>());
        }
        catch (IOException | ClassCastException | ClassNotFoundException  e) {
            throw new RepositoryException(e);
        }
    }

    @PreDestroy
    public void persist_data() {
        try(
                var fos = new FileOutputStream(this.file);
                var oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(this.data);
        }
        catch (IOException e)
        {
            throw new RepositoryException(e);
        }
    }

    public Optional<T> find(UUID uuid) {
        return data.stream()
                .filter(u -> u.getUuid().equals(uuid))
                .findFirst();
    }

    public T save(T t) {
        t  = t.clone();
        t.setUuid(UUID.randomUUID());
        data.add(t);
        return t.clone();
    }

    public T update(UUID uuid, T t) {
        var result = data.stream()
                .filter(u -> u.getUuid().equals(uuid))
                .findFirst();

        return result.map(u -> {
            u.update(t);
            return u.clone();
        }).orElseThrow();
    }

    public boolean delete(UUID uuid) {
        return data.removeIf(u -> u.getUuid().equals(uuid));
    }

    public List<T> findAll() {
        return data.stream()
                .map(Entity::clone)
                .collect(Collectors.toList());
    }

}