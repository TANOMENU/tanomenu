package tanomenu.repository;

import tanomenu.models.Model;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Repository<T extends Model<T>> {

    private final String name;
    protected final List<T> data;

    public Repository() {
        this.name = String.format("%s.%s", this.getClass().getSimpleName(), "repository-data");
        this.data = recover_data();
    }

    private List<T> recover_data() {
        try(
                var fis = new FileInputStream(this.name);
                var ois = new ObjectInputStream(fis)
        ) {
            return Collections.synchronizedList((List<T>) ois.readObject());
        }
        catch (FileNotFoundException e)
        {
            return Collections.synchronizedList(new ArrayList<>());
        }
        catch (IOException e)
        {
            throw new RepositoryCannotLoadDataException("Error deserializing repository data", e);
        }
        catch (ClassCastException e)
        {
            throw new RepositoryCannotLoadDataException("Error casting data", e);
        }
        catch (ClassNotFoundException e)
        {
            throw new RepositoryCannotLoadDataException("Class not found", e);
        }
    }

    @PreDestroy
    public void persist_data() {
        try(
                var fos = new FileOutputStream(name);
                var oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(this.data);
        }
        catch (IOException e)
        {
            throw new RepositoryCannotLoadDataException("Error serializing repository data", e);
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
                .map(Model::clone)
                .collect(Collectors.toList());
    }

}
