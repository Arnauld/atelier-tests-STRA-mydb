package mycompany.data.db;

import mycompany.data.DatabaseException;
import mycompany.data.Settings;
import org.apache.commons.io.IOUtils;
import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class DB {
    private static final Logger LOG = LoggerFactory.getLogger(DB.class);

    private static DB instance = new DB(new Settings());

    public static DB getInstance() {
        return instance;
    }

    private final Settings settings;
    private DBI dbi;

    public DB(Settings settings) {
        this.settings = settings;
    }

    public void connect() {
        LOG.info("Connecting to database...");
        JdbcConnectionPool ds = JdbcConnectionPool.create(
                settings.dbUrl(),
                settings.dbUsername(),
                settings.dbPassword());
        dbi = new DBI(ds);
        checkInitialState();
    }

    private void checkInitialState() {
        Handle h = dbi.open();

        try {
            h.select("select * from meta");
        } catch (RuntimeException up) {
            if (up.getMessage().contains("Table \"META\" not found")) {
                initInitialState();
                return;
            }
            throw up;
        } finally {
            h.close();
        }
    }

    private void initInitialState() {
        InputStream in = getClass().getResourceAsStream("/init.sql");

        Handle h = null;
        try {
            String[] sql = IOUtils.toString(in).split(";");
            h = dbi.open();
            for (String s : sql) {
                h.execute(s);
            }
        } catch (IOException e) {
            throw new DatabaseException("Contact your admin.", e);
        } finally {
            IOUtils.closeQuietly(in);
            if (h != null)
                h.close();
        }

    }

    public <T> T open(Class<T> daoType) {
        return dbi.open(daoType);
    }

}
