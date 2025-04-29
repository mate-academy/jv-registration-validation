package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    private User createValidUser() {
        User user = new User();
        user.setPassword("P$assword21");
        user.setAge(18);
        user.setId(1L);
        return user;
    }

    @Test
    void sizeOfLoginIsLongerThan6_Ok() {
        User user = createValidUser();
        user.setLogin("login24");

        User actual = registrationService.register(user);

        assertNotNull(actual);
    }

    @Test
    void sizeOfLoginIsShorterThan6_NotOk() {
        User user = createValidUser();
        user.setLogin("log");

        assertThrows(IncorrectDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginIsNull_NotOk() {
        User user = createValidUser();
        user.setLogin(null);

        assertThrows(IncorrectDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void sizeOfPasswordIsLongerThan6_Ok() {
        User user = createValidUser();
        user.setLogin("login54");

        User actual = registrationService.register(user);

        assertNotNull(actual);
    }

    @Test
    void sizeOfPasswordIsShorterThan6_NotOk() {
        User user = createValidUser();
        user.setLogin("login64");
        user.setPassword("pass");

        assertThrows(IncorrectDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordHasAtLeastOneCapitalLetter_Ok() {
        User user = createValidUser();
        user.setLogin("login74");

        User actual = registrationService.register(user);

        assertNotNull(actual);
    }

    @Test
    void passwordDoesNotHaveAtLeastOneCapitalLetter_NotOk() {
        User user = createValidUser();
        user.setLogin("login84");
        user.setPassword("p$assword21");

        assertThrows(IncorrectDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordHasAtLeastOneLowerCaseLetter_Ok() {
        User user = createValidUser();
        user.setLogin("login95");

        User actual = registrationService.register(user);
        assertNotNull(actual);
    }

    @Test
    void passwordDoesNotHaveAtLeastOneLowerCaseLetter_NotOk() {
        User user = createValidUser();
        user.setLogin("login105");
        user.setPassword("P$ASSWORD21");

        assertThrows(IncorrectDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordHasAtLeastOneNumber_Ok() {
        User user = createValidUser();
        user.setLogin("login115");

        User actual = registrationService.register(user);

        assertNotNull(actual);
    }

    @Test
    void passwordDoesNotHaveAtLeastOneNumber_NotOk() {
        User user = createValidUser();
        user.setLogin("login125");
        user.setPassword("P$assword");

        assertThrows(IncorrectDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordHasAtLeastOneSpecialSymbol_Ok() {
        User user = createValidUser();
        user.setLogin("login136");

        User actual = registrationService.register(user);
        assertNotNull(actual);
    }

    @Test
    void passwordDoesNotHaveAtLeastOneSpecialSymbol_NotOk() {
        User user = createValidUser();
        user.setLogin("login145");
        user.setPassword("Password21");

        assertThrows(IncorrectDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordIsNull_NotOk() {
        User user = createValidUser();
        user.setLogin("login156");
        user.setPassword(null);

        assertThrows(IncorrectDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageIsGreaterThan18_Ok() {
        User user = createValidUser();
        user.setLogin("login167");

        User actual = registrationService.register(user);

        assertNotNull(actual);
    }

    @Test
    void ageIsLessThan18_NotOk() {
        User user = createValidUser();
        user.setLogin("login177");
        user.setAge(15);

        assertThrows(IncorrectDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageIsNull_NotOk() {
        User user = createValidUser();
        user.setLogin("login188");
        user.setAge(null);

        assertThrows(IncorrectDataException.class, () -> {
            registrationService.register(user);
        });
    }
}
