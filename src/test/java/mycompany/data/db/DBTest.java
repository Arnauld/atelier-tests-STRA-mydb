package mycompany.data.db;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DBTest {

    @Test
    public void should_() {
        DB db = DB.getInstance();
        db.connect();
    }
}