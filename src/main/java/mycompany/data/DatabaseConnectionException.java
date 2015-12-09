package mycompany.data;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class DatabaseConnectionException extends DatabaseException {
    public DatabaseConnectionException(String message) {
        super(message, new DatabaseException("Something got wrong", new java.io.IOException(message)));
    }
}
