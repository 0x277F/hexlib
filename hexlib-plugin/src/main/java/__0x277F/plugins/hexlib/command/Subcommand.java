package __0x277F.plugins.hexlib.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subcommand {
    String name();

    String[] aliases() default {};

    /**
     * Defaults to the declaring class annotated with CommandHolder.
     *
     * @return
     */
    String root() default "$INFER$";
}
