package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        User bob = new User("bob12345@gmail.com", "6h6%m3dS2jAy", 19);
        User alice = new User("alice23145@gmail.com", "6g!RO008ry^8", 20);
        User mike = new User("mike2352@gmail.com", "h760BVS1wh!n", 18);
        registrationService.register(bob);
        registrationService.register(alice);
        registrationService.register(mike);

    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_invalidAge_notOk() {
        User invalidAgeUser = new User("invalidAge@gmail.com", "h760BVdS2jAy", 10);
        assertThrows(RuntimeException.class, () -> registrationService.register(invalidAgeUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User negativeAgeUser = new User("negativeAge@gmail.com", ",32hc';,653ap", -1);
        assertThrows(RuntimeException.class, () -> registrationService.register(negativeAgeUser));
    }

    @Test
    void register_invalidPassword_notOk() {
        User invalidPassword = new User("invalidPassword@gmail.com", ",2l64", 26);
        assertThrows(RuntimeException.class, () -> registrationService.register(invalidPassword));
    }

    @Test
    void register_nullPassword_notOk() {
        User nullPasswordUser = new User("nullPassword@gmail.com", null, 46);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullPasswordUser));
    }

    @Test
    void register_nullLogin_notOk() {
        User nullLoginUser = new User(null, "0wU2IqT#W2f%", 44);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullLoginUser));
    }

    @Test
    void register_existedPassword_notOk() {
        User anotherBob = new User("bob12345@gmail.com", "01V@8Y6Jj3Vc", 20);
        assertThrows(RuntimeException.class, () -> registrationService.register(anotherBob));
    }

    @Test
    void register_checkValidUsers_Ok() {
        int expectedSize = 3;
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize, "Something went wrong, "
                + "the size of list should be " + expectedSize
                + " but was " + actualSize);
    }
}
