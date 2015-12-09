package mycompany.data.internal;

import com.google.common.annotations.VisibleForTesting;
import mycompany.data.DatabaseConnectionException;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class RandomEvent {
    public static RandomEvent get() {
        return new RandomEvent();
    }

    private Random random = new SecureRandom();

    @VisibleForTesting
    RandomEvent() {
    }

    public final void what() {
        int i = nextRandom(100);

        if (i > 50) {
            waitALittle(nextRandom(10));
        }

        if (i >= 90) {
            String[] messages = new String[]{
                    "timeout",
                    "database lock acquisition failure",
                    "maintenance en cours",
                    "prise débranchée",
                    "base de donnée trop sollicitée",
                    "not enough ram",
                    "err09545",
                    "what?",
                    "data corrupted",
                    "data corrupted somewhere",
                    "erf..."
            };
            throw new DatabaseConnectionException(messages[i - 90]);
        }

    }

    protected void waitALittle(int nbSeconds) {
        try {
            Thread.sleep(1000 * nbSeconds);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    protected int nextRandom(int bound) {
        return random.nextInt(bound);
    }

}
