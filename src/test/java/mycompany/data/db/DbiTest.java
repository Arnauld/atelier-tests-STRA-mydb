package mycompany.data.db;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class DbiTest {

    @Test
    public void should_() {
        JdbcConnectionPool ds = JdbcConnectionPool.create("jdbc:h2:mem:test2", "username", "password");
        DBI dbi = new DBI(ds);

        ClientDao dao = dbi.open(ClientDao.class);
        dao.createTable();
        dao.insert(12, "john");
        Client client = dao.findNameById(12);
        dao.close();

        assertThat(client.getId()).isEqualTo(12);
        assertThat(client.getName()).isEqualTo("john");

    }
}
