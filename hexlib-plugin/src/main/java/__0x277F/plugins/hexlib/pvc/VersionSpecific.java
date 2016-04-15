package __0x277F.plugins.hexlib.pvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate a field inside a PolyVersionCompat class that
 * will be initialized with the class representing the version-specific
 * implementation. If none are found, a field named 'clazz' will be searched
 * for.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VersionSpecific {
}
