package mycompany.data;

import com.google.common.base.Predicate;
import mycompany.data.internal.RandomEvent;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class DatabaseTest {

    @Test
    public void store_and_retrieve_but_weird_thing_should_happen_too() {
        RandomEvent re = Mockito.mock(RandomEvent.class);
        Database db = new Database(re, new DefaultStorage("target/storage.json"));

        db.store(String.class, "yuk", "1");
        db.store(String.class, "yak", "2");
        db.store(String.class, "yok", "4");

        final List<String> values = new ArrayList<String>();
        db.traverse(String.class, new Predicate<String>() {
            public boolean apply(String s) {
                values.add(s);
                return false;
            }
        });

        assertThat(values).containsExactly("yuk", "yak", "yok");
        verify(re, Mockito.atLeast(6)).what();
    }
}