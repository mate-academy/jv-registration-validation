package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.DuplicateAnExistingLoginException;
import core.basesyntax.exceptions.NotCorrectAgeException;
import core.basesyntax.exceptions.NotCorrectPassword;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static final User anna = new User();
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void beforeAll() {
        anna.setAge(18);
        anna.setId(15893234L);
        anna.setLogin("Anna@gmaol.com");
        anna.setPassword("123456789");
    }

    @BeforeEach
    void beforeEach() {
        Storage.people.clear();
    }

    @Test
    void correctUserCheck_Ok() {
        User bob = new User();
        bob.setAge(18);
        bob.setId(15893234L);
        bob.setLogin("Bob@gmaol.com");
        bob.setPassword("123456789");
        User actualBob = registrationService.register(bob);
        assertEquals(bob,actualBob);
        User actualAnna = registrationService.register(anna);
        assertEquals(anna,actualAnna);
    }

    @Test
    void nullLoginOrPassword_NotOk() {
        User nullUser = new User();
        nullUser.setPassword(null);
        nullUser.setLogin(null);
        nullUser.setId(0L);
        nullUser.setAge(0);
        User actualAnna = registrationService.register(anna);
        assertEquals(anna,actualAnna);
        assertThrows(NullPointerException.class,() -> registrationService.register(nullUser));
    }

    @Test
    void notCorrectAge_NotOk() {
        User ungAndgrey = new User();
        ungAndgrey.setAge(15);
        ungAndgrey.setId(15893234L);
        ungAndgrey.setLogin("Andrey@gmial.com");
        ungAndgrey.setPassword("123456789");
        User actualAnna = registrationService.register(anna);
        assertEquals(anna,actualAnna);
        assertThrows(NotCorrectAgeException.class,() -> registrationService.register(ungAndgrey));
    }

    @Test
    void notCorrectPassword_NotOk() {
        User vadim = new User();
        vadim.setPassword("12345");
        vadim.setAge(22);
        vadim.setId(225415165L);
        vadim.setLogin("Vadim@gmail.com");
        User actualAnna = registrationService.register(anna);
        assertEquals(anna,actualAnna);
        assertThrows(NotCorrectPassword.class,() -> registrationService.register(vadim));
    }

    @Test
    void duplicateAnExistingLogin_NotOk() {
        User anotherAnna = new User();
        anotherAnna.setAge(18);
        anotherAnna.setId(15893234L);
        anotherAnna.setLogin("Anna@gmaol.com");
        anotherAnna.setPassword("123456789");
        registrationService.register(anna);
        assertThrows(DuplicateAnExistingLoginException.class,()
                -> registrationService.register(anotherAnna));
    }
}
