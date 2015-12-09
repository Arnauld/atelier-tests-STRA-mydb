package mycompany.data.db;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Client {
    private final long id;
    private final String name;

    public Client(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
