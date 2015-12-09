package mycompany.data;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public interface Storage {
    void write(String data);
    String read();
}
