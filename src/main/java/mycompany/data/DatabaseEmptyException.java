package mycompany.data;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class DatabaseEmptyException extends DatabaseException {
    public DatabaseEmptyException(String message) {
        super(message, null);
    }
}
