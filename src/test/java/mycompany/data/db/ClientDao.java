package mycompany.data.db;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterColumnMapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
@RegisterMapper(ClientMapper.class)
public interface ClientDao {
    @SqlUpdate("create table clients (id int primary key, name varchar(100))")
    void createTable();

    @SqlUpdate("insert into clients (id, name) values (:id, :name)")
    void insert(@Bind("id") int id, @Bind("name") String name);

    @SqlQuery("select * from clients where id = :id")
    Client findNameById(@Bind("id") int id);

    /**
     * close with no args is used to close the connection
     */
    void close();
}
