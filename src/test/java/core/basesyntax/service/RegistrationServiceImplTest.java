package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private StorageDao storageDao = new StorageDaoImpl();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_Ok() {
        User bob = new User();
        bob.setAge(20);
        bob.setLogin("bobiik");
        bob.setPassword("hhjfbchbv");
        bob.setId(52698L);
        registrationService.register(bob);

        User alice = new User();
        alice.setAge(18);
        alice.setLogin("aliceFox");
        alice.setPassword("hhjf562bv");
        alice.setId(36898L);
        registrationService.register(alice);

        assertEquals(bob, storageDao.get(bob.getLogin()),
                "No user has been added. Please check the data");
        assertEquals(alice, storageDao.get(alice.getLogin()),
                "No user has been added. Please check the data");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        }, "An exception should be thrown");
    }

    @Test
    void register_nullAge_notOk() {
        User bobAgeNull = new User();
        bobAgeNull.setAge(null);
        bobAgeNull.setLogin("bobist");
        bobAgeNull.setPassword("hhjfbchbv");
        bobAgeNull.setId(52698L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bobAgeNull);
        }, "An exception should be thrown");
    }

    @Test
    void register_nullLogin_notOk() {
        User bobLoginNull = new User();
        bobLoginNull.setAge(19);
        bobLoginNull.setLogin(null);
        bobLoginNull.setPassword("hhjfbchbv");
        bobLoginNull.setId(52698L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bobLoginNull);
        }, "An exception should be thrown");
    }

    @Test
    void register_nullPassword_notOk() {
        User bobPasswordNull = new User();
        bobPasswordNull.setAge(19);
        bobPasswordNull.setLogin("bobist");
        bobPasswordNull.setPassword(null);
        bobPasswordNull.setId(52698L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bobPasswordNull);
        }, "An exception should be thrown");
    }

    @Test
    void register_invalidAge_notOk() {
        User bob = new User();
        bob.setAge(10);
        bob.setLogin("bobist");
        bob.setPassword("hhjfbchbv");
        bob.setId(52698L);

        User alice = new User();
        alice.setAge(15);
        alice.setLogin("aliceFox");
        alice.setPassword("hhjf562bv");
        alice.setId(36898L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(alice);
        }, "An exception should be thrown");
    }

    @Test
    void register_negativeAge_notOk() {
        User bob = new User();
        bob.setAge(-10);
        bob.setLogin("bobist");
        bob.setPassword("hhjfbchbv");
        bob.setId(52698L);

        User alice = new User();
        alice.setAge(-3);
        alice.setLogin("aliceFox");
        alice.setPassword("hhjf562bv");
        alice.setId(36898L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(alice);
        }, "An exception should be thrown");
    }

    @Test
    void register_invalidLogin_notOk() {
        User john = new User();
        john.setAge(19);
        john.setLogin("bobist");
        john.setPassword("hhfjbcdyb");
        john.setId(1259L);
        registrationService.register(john);

        User bob = new User();
        bob.setAge(19);
        bob.setLogin("bobist");
        bob.setPassword("hiokfbchbv");
        bob.setId(52698L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");
    }

    @Test
    void register_lengthPassword_notOk() {
        User bob = new User();
        bob.setAge(19);
        bob.setLogin("bobistik");
        bob.setPassword("hhjf");
        bob.setId(52698L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");

        User john = new User();
        john.setAge(19);
        john.setLogin("johnidd");
        john.setPassword("jo");
        john.setId(1259L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(john);
        }, "An exception should be thrown");
    }

    @Test
    void register_lengthLogin_notOk() {
        User bob = new User();
        bob.setAge(19);
        bob.setLogin("bob");
        bob.setPassword("hhjfklk");
        bob.setId(52698L);

        User john = new User();
        john.setAge(19);
        john.setLogin("john");
        john.setPassword("jolop5963");
        john.setId(1259L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(john);
        }, "An exception should be thrown");
    }
}
