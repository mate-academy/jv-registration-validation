package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User petro;
    private User semen;
    private User vasyl;
    private User dmytro;
    private User kotyhoroshko;
    private User ivanMykhailovych;
    private List<User> userList;

    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        petro = new User("Petro", "Pet", 34);
        semen = new User("Semen", "SeMen2005", 16);
        vasyl = new User("Vasyl", null, 41);
        dmytro = new User("Dmytro2000", "nullPassword", 22);
        kotyhoroshko = new User("Kotyhoroshko", "bestPassword", 28);
        ivanMykhailovych = new User("Ivan Mykhailovych", "Vano133", 17);
    }

    @Test
    void register_nullPassword_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(vasyl));
    }

    @Test
    void register_GetValidUser() {
        Storage.people.add(dmytro);
        Storage.people.add(semen);
        Assertions.assertEquals(dmytro, Storage.people.get(0));
        Assertions.assertEquals(semen, Storage.people.get(1));
    }

    @Test
    void register_CheckIfAgeIsValid_NotOk() {
        Assertions
                .assertThrows(RuntimeException.class,
                    () -> registrationService.register(ivanMykhailovych));
    }

    @Test
    void register_checkIfAgeIsOk() {
        int expected = 28;
        int actual = kotyhoroshko.getAge();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_IfPasswordLengthIsWrong() {
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(petro),
                "Your password should be longer, then 6 symbols");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
