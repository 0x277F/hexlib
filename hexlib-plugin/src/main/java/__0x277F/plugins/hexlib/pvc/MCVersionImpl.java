package __0x277F.plugins.hexlib.pvc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MCVersionImpl {
    MinecraftVersion value();

    Completeness completeness() default Completeness.FULL;

    /**
     * Use this if the implementation:
     * <ul>
     * <li>Isn't in the same package</li>
     * <li>Doesn't have the same beginning value as described in {@link PolyVersionImpl}</li>     *
     * </ul>
     */
    String name() default "##PROVIDED##";

    public static enum Completeness {
        FULL, PARTIAL, DUMMY
    }
}
