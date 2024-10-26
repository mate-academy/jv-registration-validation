package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_addingNewUser_Ok() {
        User userOk = new User();
        userOk.setLogin("NewUser");
        userOk.setPassword("123456789");
        userOk.setAge(18);
        User actual = registrationService.register(userOk);
        assertEquals(userOk, actual);
    }

    @Test
    void register_addingUserWhichAlreadyExist_NotOk() {
        User userDouble = new User();
        userDouble.setLogin("userDouble");
        userDouble.setPassword("123456789");
        userDouble.setAge(18);
        registrationService.register(userDouble);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(userDouble));
    }

    @Test
    void register_addingUsersWithSameLogin_NotOk() {
        User baseUser = new User();
        baseUser.setLogin("twinUser");
        baseUser.setPassword("123456789");
        baseUser.setAge(18);
        User twinUser = new User();
        twinUser.setLogin("twinUser");
        twinUser.setPassword("987654321");
        twinUser.setAge(38);
        registrationService.register(baseUser);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(twinUser));
    }

    @Test
    void register_loginWithLessThanSixCharacters_NotOk() {
        User shortLoginUser = new User();
        shortLoginUser.setLogin("123");
        shortLoginUser.setPassword("123456789");
        shortLoginUser.setAge(18);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(shortLoginUser));
    }

    @Test
    void register_passwordWithLessThanSixCharacters_NotOk() {
        User shortPasswordUser = new User();
        shortPasswordUser.setLogin("123456789");
        shortPasswordUser.setPassword("123");
        shortPasswordUser.setAge(18);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(shortPasswordUser));
    }

    @Test
    void register_ageBelow18_NotOk() {
        User toYoungUser = new User();
        toYoungUser.setLogin("123456789");
        toYoungUser.setPassword("123456789");
        toYoungUser.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(toYoungUser));
    }

    @Test
    void register_emptyUserData_NotOk() {
        User emptyUser = new User();
        assertThrows(InvalidDataException.class, () -> registrationService.register(emptyUser));
    }
}
