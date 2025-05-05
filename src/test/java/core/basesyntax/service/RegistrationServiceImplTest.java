package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationServiceImplException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void initDB() {
        Storage.people.add(new User("ValidUser01", "valid_password01", 18));
        Storage.people.add(new User("ValidUser02", "valid_password02", 25));
        Storage.people.add(new User("ValidUser03", "valid_password03", 40));
        registrationService = new RegistrationServiceImpl();
    }

    @AfterAll
    static void disconnectFromDB() {
        registrationService = null;
    }

    @Test
    void register_userNullValue_notOk() {
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_userExists_notOk() {
        assertThrows(RegistrationServiceImplException.class, () -> registrationService.register(
                new User("ValidUser01", "valid_password01", 18)));
    }

    @Test
    void register_loginNullValue_notOk() {
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User(null, "123456", 18)));
    }

    @Test
    void register_loginShort_notOk() {
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("", "123456", 18)));
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Ada", "123456", 18)));
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adam", "123456", 18)));
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adame", "123456", 18)));
    }

    @Test
    void register_loginExactlyMinLen_edgeCase_Ok() {
        User expected = new User("Adamel", "123456", 18);
        assertEquals(expected, registrationService.register(new User("Adamel", "123456", 18)));
    }

    @Test
    void register_loginMoreThanMinLen_Ok() {
        User expected1 = new User("Adamelo3", "123456", 18);
        assertEquals(expected1, registrationService.register(new User("Adamelo3", "123456", 18)));
        User expected2 = new User("Adamelo4", "123456", 18);
        assertEquals(expected2, registrationService.register(new User("Adamelo4", "123456", 18)));
        User expected3 = new User("Adamelo5", "123456", 18);
        assertEquals(expected3, registrationService.register(new User("Adamelo5", "123456", 18)));
    }

    @Test
    void register_passwordNullValue_notOk() {
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", null, 18)));
    }

    @Test
    void register_passwordShort_notOk() {
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "", 18)));
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "1", 18)));
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "123", 18)));
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "1234", 18)));
    }

    @Test
    void register_passwordShort_edgeCase_notOk() {
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "12345", 18)));
    }

    @Test
    void register_passwordExactlyMinLen_edgeCase_Ok() {
        User expected = new User("Adamelo6", "123456", 18);
        assertEquals(expected, registrationService.register(new User("Adamelo6", "123456", 18)));
    }

    @Test
    void register_passwordMoreThanMinLen_Ok() {
        User expected1 = new User("Adamelo7", "1234567", 18);
        assertEquals(expected1, registrationService.register(new User("Adamelo7", "1234567", 18)));
        User expected2 = new User("Adamelo8", "12345678", 18);
        assertEquals(expected2, registrationService.register(new User("Adamelo8", "12345678", 18)));
        User expected3 = new User("Adamelo9", "123456789", 18);
        assertEquals(expected3,
                registrationService.register(new User("Adamelo9", "123456789", 18)));
    }

    @Test
    void register_ageNullValue_notOk() {
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "123456", null)));
    }

    @Test
    void register_ageNegative_notOk() {
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "123456", -1)));
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "123456", -7)));
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "123456", -15)));
    }

    @Test
    void register_ageOfUserUnder18_notOk() {
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "123456", 1)));
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "123456", 7)));
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "123456", 15)));
    }

    @Test
    void register_ageOfUserUnder18_edgeCase_notOk() {
        assertThrows(RegistrationServiceImplException.class,
                () -> registrationService.register(new User("Adamelo", "123456", 17)));
    }

    @Test
    void register_ageOfUserExactly18_edgeCase_Ok() {
        User expected = new User("Adamelo10", "123456", 18);
        assertEquals(expected, registrationService.register(new User("Adamelo10", "123456", 18)));
    }

    @Test
    void register_ageOfUserOver18_Ok() {
        User expected1 = new User("Adamelo11", "123456", 81);
        assertEquals(expected1, registrationService.register(new User("Adamelo11", "123456", 81)));
        User expected2 = new User("Adamelo12", "123456", 95);
        assertEquals(expected2, registrationService.register(new User("Adamelo12", "123456", 95)));
        User expected3 = new User("Adamelo13", "123456", 107);
        assertEquals(expected3, registrationService.register(new User("Adamelo13", "123456", 107)));
    }
}
