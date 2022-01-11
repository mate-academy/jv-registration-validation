package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User tooShortPasswordUser;
    private User tooYoungUser;
    private User nullPassUser;
    private User validUserDmytro;
    private User validUserKotyhoroshko;
    private User tooYoungUserIvan;

    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        tooShortPasswordUser = new User("Petro", "Pet", 34);
        tooYoungUser = new User("Semen", "SeMen2005", 16);
        nullPassUser = new User("Vasyl", null, 41);
        validUserDmytro = new User("Dmytro2000", "nullPassword", 22);
        validUserKotyhoroshko = new User("Kotyhoroshko", "bestPassword", 28);
        tooYoungUserIvan = new User("Ivan Mykhailovych", "Vano133", 17);
    }

    @Test
    void register_nullPassword_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(nullPassUser));
    }

    @Test
    void register_GetValidUser() {
        Storage.people.add(validUserDmytro);
        Storage.people.add(tooYoungUser);
        Assertions.assertEquals(validUserDmytro, Storage.people.get(0));
        Assertions.assertEquals(tooYoungUser, Storage.people.get(1));
    }

    @Test
    void register_CheckIfAgeIsValid_NotOk() {
        Assertions
                .assertThrows(RuntimeException.class,
                    () -> registrationService.register(tooYoungUserIvan));
    }

    @Test
    void register_checkIfAgeIsOk() {
        registrationService.register(validUserKotyhoroshko);
        boolean actual = Storage.people.contains(validUserKotyhoroshko);
        Assertions.assertTrue(actual);
    }

    @Test
    void register_IfPasswordLengthIsWrong() {
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(tooShortPasswordUser),
                "You should throw an exception for invalid pass user");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
