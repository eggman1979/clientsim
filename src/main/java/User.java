import java.io.Serializable;
import java.util.UUID;

public class User{
    public final UUID uuid;
    public final String bankAccountID;


    public User(UUID uuid, String bankAccountID) {
        this.uuid = uuid;
        this.bankAccountID = bankAccountID;
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        if (  o == null || getClass() != o.getClass()) return false;
        User other = (User) o;
        return uuid.equals(other.uuid);// Objects.equals(uuid, other.uuid);
    }

    @Override
    public int hashCode() {

        return uuid.hashCode();
    }
}



