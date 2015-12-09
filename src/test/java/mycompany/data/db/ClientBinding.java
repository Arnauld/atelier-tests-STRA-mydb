package mycompany.data.db;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
@BindingAnnotation(ClientBinding.Factory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface ClientBinding {
    public static class Factory implements BinderFactory {
        public Binder build(Annotation annotation) {
            return new Binder<ClientBinding, Client>() {
                public void bind(SQLStatement q, ClientBinding bind, Client user) {
                    q.bind("id", user.getId());
                    q.bind("name", user.getName());
                }
            };
        }
    }
}
