package tanomenu.models;

import java.io.Serializable;
import java.util.UUID;

public interface Model<T> extends Cloneable, Serializable {

    public UUID getUuid();

    public void setUuid(UUID uuid);

    public T clone();

    public void update(T t);
}
