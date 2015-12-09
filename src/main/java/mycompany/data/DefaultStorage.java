package mycompany.data;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class DefaultStorage implements Storage {
    private static final Charset UTF8 = Charset.forName("utf8");

    public static Storage get() {
        return new DefaultStorage();
    }

    private final String path;

    public DefaultStorage() {
        this(".storage");
    }

    public DefaultStorage(String path) {
        this.path = path;
    }


    public synchronized void write(String data) {
        File file = new File(getPath());
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(writer);
        }
    }

    private String getPath() {
        return path;
    }

    public synchronized String read() {
        InputStream in = null;
        try {
            File data = new File(getPath());
            if (data.exists()) {
                in = new FileInputStream(data);
                return toString(in);
            } else {
                in = getClass().getResourceAsStream("/raw");
                return toString(in);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeQuietly(in);
        }
    }

    private String toString(InputStream in) throws IOException {
        Reader reader = new InputStreamReader(in, UTF8);
        StringBuilder b = new StringBuilder();
        char[] buffer = new char[2048];
        int read;
        while ((read = reader.read(buffer)) > 0) {
            b.append(buffer, 0, read);
        }
        return b.toString();
    }

    private void closeQuietly(InputStream reader) {
        try {
            if (reader != null)
                reader.close();
        } catch (IOException e) {
            // ignore
        }
    }

    private void closeQuietly(Writer writer) {
        try {
            if (writer != null)
                writer.close();
        } catch (IOException e) {
            // ignore
        }
    }
}
