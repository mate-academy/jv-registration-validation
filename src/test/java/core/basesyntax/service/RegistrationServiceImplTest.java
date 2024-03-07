package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.IncorrectInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User normisUser;
    private User wrongAgeUser;
    private User wrongLoginUser;
    private User wrongPassUser;

    @BeforeEach
    void setUp() {
        normisUser = new User();
        normisUser.setAge(25);
        normisUser.setLogin("Albert_01");
        normisUser.setPassword("qwerty123");

        wrongAgeUser = new User();
        wrongAgeUser.setAge(16);
        wrongAgeUser.setLogin("Oksana_22@");
        wrongAgeUser.setPassword("ytrewq431");

        wrongLoginUser = new User();
        wrongLoginUser.setAge(98);
        wrongLoginUser.setLogin("1212");
        wrongLoginUser.setPassword("15765424");

        wrongPassUser = new User();
        wrongPassUser.setAge(44);
        wrongPassUser.setLogin("fghjsdd123");
        wrongPassUser.setPassword("321");
    }

    @Test
    void register_returnType() throws IncorrectInputDataException {
        Assertions.assertEquals(normisUser, registrationService.register(normisUser));
    }

    @Test
    void register_addUser_Ok() throws IncorrectInputDataException {
        registrationService.register(normisUser);
        Assertions.assertEquals(1, Storage.people.size());
    }

    @Test
    void register_addWrongAge_Ok() {
        Assertions.assertThrows(IncorrectInputDataException.class,
                () -> registrationService.register(wrongAgeUser));
    }

    @Test
    void register_addBadLogin_Ok() throws IncorrectInputDataException {
        registrationService.register(wrongLoginUser);
        Assertions.assertEquals(1, Storage.people.size());
    }

    @Test
    void register_addWrongPass_Ok() throws IncorrectInputDataException {
        registrationService.register(wrongPassUser);
        Assertions.assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullLogin_NotOk() {
        wrongLoginUser.setLogin(null);
        Assertions.assertThrows(IncorrectInputDataException.class,
                () -> registrationService.register(wrongLoginUser));
    }

    @Test
    void register_nullPass_NotOk() {
        wrongPassUser.setPassword(null);
        Assertions.assertThrows(IncorrectInputDataException.class,
                () -> registrationService.register(wrongPassUser));
    }
}
