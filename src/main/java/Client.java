import java.util.UUID;

public class Client extends User{

    public final String firstName;
    public final String lastName;
    public final String cpr;

    public Client(String firstName, String lastName, String cpr, UUID uuid, String bankAccountID){
        super(uuid, bankAccountID);
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpr = cpr;
        
        
    }
}
