package mycompany.data;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import mycompany.data.internal.RandomEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Database {
    private static Database instance = new Database(RandomEvent.get(), DefaultStorage.get());

    public static Database getDatabase() {
        return instance;
    }

    private Map<String, Map<String, String>> valuesByType;
    private final RandomEvent randomEvent;
    private final Storage storage;
    private final Gson gson;

    @VisibleForTesting
    Database(RandomEvent randomEvent, Storage storage) {
        this.randomEvent = randomEvent;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.storage = storage;
        reload(storage.read());
    }

    public String dump() {
        return gson.toJson(valuesByType);
    }

    @SuppressWarnings("unchecked")
    public void reload(String raw) {
        if (raw == null) {
            valuesByType = new ConcurrentHashMap<String, Map<String, String>>();
        }
        else {
            Type mapType = new TypeToken<Map<String, Map<String, String>>>() {
            }.getType();
            valuesByType = gson.fromJson(raw, mapType);
        }
    }

    void flush() {
        storage.write(dump());
    }

    public <T> T get(Class<T> type, String key) {
        what();
        String s = getTableForType(type).get(key);
        return gson.fromJson(s, type);
    }

    private void what() {
        if (randomEvent != null)
            randomEvent.what();
    }

    public <T> List<T> getAll(Class<T> type) {
        what();
        ArrayList<T> ts = new ArrayList<T>();
        for (String o : getTableForType(type).values()) {
            ts.add(gson.fromJson(o, type));
        }
        return ts;
    }

    public <T> void store(Class<T> type, T value, String key) {
        what();
        Map<String, String> valuesOfType = getTableForType(type);
        valuesOfType.put(key, gson.toJson(value));
        flush();
    }

    public <T> void traverse(Class<T> type, Predicate<T> predicate) {
        for (String o : getTableForType(type).values()) {
            what();

            if (predicate.apply(gson.fromJson(o, type)))
                return;
        }
    }

    private <T> Map<String, String> getTableForType(Class<T> type) {
        Map<String, String> valuesOfType = valuesByType.get(type.getName());
        if (valuesOfType == null) {
            valuesOfType = new HashMap<String, String>();
            valuesByType.put(type.getName(), valuesOfType);
        }
        return valuesOfType;
    }

    public static Object __native_call() {
        try {
            Constructor<Database> dbCtor = (Constructor<Database>) Database.class.getDeclaredConstructors()[0];
            dbCtor.setAccessible(true);
            return dbCtor.newInstance((RandomEvent) null);
        } catch (InstantiationException e) {
            throw new DatabaseException("Native invokation failed", e);
        } catch (IllegalAccessException e) {
            throw new DatabaseException("Native invokation failed", e);
        } catch (InvocationTargetException e) {
            throw new DatabaseException("Native invokation failed", e);
        }
    }

}
