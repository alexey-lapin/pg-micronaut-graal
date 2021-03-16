package pg.micronaut.graal.config;

import io.micronaut.core.annotation.Introspected;

import java.util.UUID;

//@TypeHint(
//        value = {UUID.class},
//        accessType = {
//                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
//                TypeHint.AccessType.ALL_DECLARED_FIELDS,
//                TypeHint.AccessType.ALL_PUBLIC_METHODS
//        }
//)
@Introspected(classes = UUID.class)
public class GraalConfig {
}
