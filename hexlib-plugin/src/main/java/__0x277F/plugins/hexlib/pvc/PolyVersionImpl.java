package __0x277F.plugins.hexlib.pvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * To annotate subclasses that extends a class annotated with
 * {@link PolyVersionCompat} and represent a version-specific
 * implementation of that functionality. Class <i>must</i>
 * follow naming guidelines: [superclass]_version. Example:
 * With superclass ParticleEffect, you would have
 * ParticleEffect_v1_9_R1. Note that this is case-sensitive.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PolyVersionImpl {
}
