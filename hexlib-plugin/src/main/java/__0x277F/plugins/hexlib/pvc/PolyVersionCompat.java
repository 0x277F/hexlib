package __0x277F.plugins.hexlib.pvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * To annotate abstract classes implemented on a by-version basis
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PolyVersionCompat {
    /**
     * Different versions something may support (e.g. v1_9_R1)
     * Special value "spigot" works for any spigot server
     */
    MCVersionImpl[] value() default {@MCVersionImpl(MinecraftVersion.ANY_SPIGOT)};

    /**
     * Will not disable plugin if no valid implementation is found.
     */
    boolean lenient() default false;

    /**
     * Will automatically attempt to initialize a
     * static field inside the abstract class with
     * signature Ljava/lang/Class with the the class
     * object associated with this version. Your plugin will
     * still have to instantiate this class.
     */
    boolean autoInit() default true;
}
