package mycompany.data.db;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class ClientMapper implements ResultSetMapper<Client> {
    public Client map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Client(
                resultSet.getLong("id"),
                resultSet.getString("name"));
    }
}
