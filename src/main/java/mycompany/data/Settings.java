package mycompany.data;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Settings {
    private static final Charset UTF8 = Charset.forName("utf8");

    public static Properties load() {
        InputStream in = Settings.class.getResourceAsStream("/env.conf");
        Properties properties = new Properties();
        initDefaults(properties);
        if (in != null) {
            InputStreamReader reader = new InputStreamReader(in, UTF8);
            try {
                properties.load(reader);
            } catch (IOException e) {
                IOUtils.closeQuietly(in);
            }
        }
        return properties;
    }

    private static void initDefaults(Properties properties) {
        File dbFile = new File(".data");
        properties.put("db.url", "jdbc:h2:" + dbFile.getAbsolutePath().replace("\\\\", "/").replace("\\", "/"));
        properties.put("db.username", "sa");
        properties.put("db.password", "");
    }

    private final Properties properties;

    public Settings() {
        this(load());
    }

    public Settings(Properties properties) {
        this.properties = properties;
    }

    public String dbUrl() {
        return properties.getProperty("db.url");
    }

    public String dbUsername() {
        return properties.getProperty("db.username");
    }

    public String dbPassword() {
        return properties.getProperty("db.password");
    }
}
