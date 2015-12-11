package mycompany.data.db;

import com.google.common.annotations.VisibleForTesting;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
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
            throw cleanupStack(new DatabaseException(messages[i - 90]));
        }

    }

    private DatabaseException cleanupStack(DatabaseException e) {
        List<StackTraceElement> kept = new ArrayList<StackTraceElement>();
        for (StackTraceElement elem : e.getStackTrace()) {
            if (elem.getClassName().contains("RandomEvent")
                    || elem.getClassName().contains("DB$1")) {
                kept.add(new StackTraceElement("enterprise.solution.Connector$1", "execute", "Connector.java", 28340));
                kept.add(new StackTraceElement("enterprise.solution.Connection$1", "process", null, -1));
                kept.add(new StackTraceElement("enterprise.solution.driver.TLTPro", "evaluate", "TLTPro.java", 318));
                kept.add(new StackTraceElement("enterprise.solution.driver.TLTProAgain", "execute", null, -1));
                kept.add(new StackTraceElement("enterprise.framework.ProcessorManagerServiceImpl", "execute", null, -1));
                kept.add(new StackTraceElement("enterprise.solution.A", "sorry", null, -1));
                continue;
            }
            kept.add(elem);
        }
        e.setStackTrace(kept.toArray(new StackTraceElement[kept.size()]));
        return e;
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
