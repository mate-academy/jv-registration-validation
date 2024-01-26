package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;
import java.util.Objects;

class UserTest {

    @Test
    void equals_ReflectionTest_Ok() {
        User user1 = new User("altCunningham", "strongPassword", 27);
        assertEquals(user1, user1);
    }

    @Test
    void equals_EqualityOfDiffClassesTest_Ok() {
        User user1 = new User("altCunningham", "strongPassword", 27);
        RegistrationService registrationService = new RegistrationServiceImpl();
        assertNotEquals(user1, registrationService);
    }

    @Test
    void equals_NullEquality_Ok() {
        User user1 = new User("altCunningham", "strongPassword", 27);
        assertFalse(user1.equals(null));
    }

    @Test
    void equals_Equals_Ok() {
        User user1 = new User("altCunningham", "strongPassword", 27);
        User user2 = new User("altCunningham", "strongPassword", 27);
        assertEquals(user1, user2);
    }

    @Test
    void testHashCode_Ok() {
        User user = new User("altCunningham", "strongPassword", 27);
        int hash1 = user.hashCode();
        int hash2 = Objects.hash(user.getLogin(), user.getPassword(), user.getAge());
        assertEquals(hash1, hash2);
    }
}
