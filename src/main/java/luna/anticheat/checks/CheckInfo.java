package luna.anticheat.checks;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CheckInfo {
    String name();
    boolean enabled() default true;
    boolean dev() default false;
    int punishVL() default 100;
}
