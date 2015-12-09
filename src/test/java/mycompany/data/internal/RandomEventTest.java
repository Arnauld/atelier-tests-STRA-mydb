package mycompany.data.internal;

import mycompany.data.internal.RandomEvent;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomEventTest {
    @Test
    public void weird_thing_should_happen() {
        final AtomicInteger nbSecondsToWait = new AtomicInteger(0);
        RandomEvent event = new RandomEvent() {
            @Override
            protected void waitALittle(int nbSeconds) {
                nbSecondsToWait.addAndGet(nbSeconds);
            }
        };

        int nbEx = 0;
        int nbOk = 0;
        for (int i = 0; i < 100; i++) {
            try {
                event.what();
                nbOk++;
            } catch (Exception e) {
                e.printStackTrace();
                nbEx++;
            }
        }

        assertThat(nbSecondsToWait.get()).isGreaterThan(10);
        assertThat(nbOk).isGreaterThan(nbEx);
        assertThat(nbEx).isGreaterThan(1);
    }
}