package tanomenu.core;

import java.io.Serializable;
import java.util.UUID;

public interface Entity<T> extends Cloneable, Serializable {

    UUID getUuid();

    void setUuid(UUID uuid);

    T clone();

    void update(T t);
}
