package mycompany.data.db;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
