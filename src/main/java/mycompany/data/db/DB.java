package mycompany.data.db;

import org.apache.commons.io.IOUtils;
import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class DB {
    private static final Logger LOG = LoggerFactory.getLogger(DB.class);

    private static DB instance = new DB(new Settings(), RandomEvent.get());
    private JdbcConnectionPool ds;

    public static DB getInstance() {
        return instance;
    }

    private final Settings settings;
    private final RandomEvent randomEvent;
    private DBI dbi;
    private volatile boolean connected = false;

    DB(Settings settings, RandomEvent randomEvent) {
        this.settings = settings;
        this.randomEvent = randomEvent;
    }

    public synchronized DB connect() {
        if(connected)
            return this;

        connected = true;
        LOG.info("Connecting to database...");
        ds = JdbcConnectionPool.create(
                settings.dbUrl(),
                settings.dbUsername(),
                settings.dbPassword());
        dbi = new DBI(ds);
        checkInitialState();
        return this;
    }

    public synchronized void disconnect() {
        LOG.info("Disconnecting from database...");
        ds.dispose();
        connected = false;
    }

    private synchronized void checkInitialState() {
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
        InputStream in = getClass().getResourceAsStream("/init_aefb4a.sql");

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

    @SuppressWarnings("unchecked")
    public <T> T open(Class<T> daoType) {
        if(!connected)
            throw new DatabaseException("Connect database first", null);

        if (!daoType.isInterface())
            throw new DatabaseException("Invalid dao type", null);

        final T impl = dbi.open(daoType);
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{daoType}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (randomEvent != null)
                    randomEvent.what();
                return method.invoke(impl, args);
            }
        });
    }
}
