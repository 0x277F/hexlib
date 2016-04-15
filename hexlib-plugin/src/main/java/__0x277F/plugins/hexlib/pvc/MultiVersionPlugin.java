package __0x277F.plugins.hexlib.pvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiVersionPlugin {
    /**
     * Fully-qualified class names of all version-specific superclasses
     * (annotated with {@link PolyVersionCompat})
     *
     * @return
     */
    String[] value();
}
