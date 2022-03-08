package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_UserWithNullData_NotOk() {
        User user = new User();
        assertThrows(NullDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithNullLogin_NotOk() {
        User user = new User();
        user.setPassword("123456");
        user.setAge(19);
        assertThrows(NullDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithNullPassword_NotOk() {
        User user = new User();
        user.setLogin("Baby Shark");
        user.setAge(22);
        assertThrows(NullDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithNullAge_NotOk() {
        User user = new User();
        user.setLogin("Homer Simpson");
        user.setPassword("donut321");
        assertThrows(NullDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithLessThanSixSymbolsPassword_NotOk() {
        User user = new User();
        user.setLogin("Alice");
        user.setPassword("pass");
        user.setAge(30);
        assertThrows(PasswordLengthException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithNegativeAge_NotOk() {
        User user = new User();
        user.setLogin("A");
        user.setPassword("BBBBBB");
        user.setAge(-20);
        assertThrows(InvalidAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithZeroAge_NotOk() {
        User user = new User();
        user.setLogin("ZeroBoy");
        user.setPassword("zerozerozero");
        user.setAge(0);
        assertThrows(InvalidAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserUnderEighteenYearsOld_NotOk() {
        User user = new User();
        user.setLogin("Big Boba");
        user.setPassword("bigboba8");
        user.setAge(8);
        assertThrows(InvalidAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserEighteenYearsOld_Ok() {
        User user = new User();
        user.setLogin("Joe");
        user.setPassword("qwerty02");
        user.setAge(18);
        registrationService.register(user);
        assertNotNull(user.getId());
    }

    @Test
    void register_UserOverEighteenYearsOld_Ok() {
        User user = new User();
        user.setLogin("granny");
        user.setPassword("08031960");
        user.setAge(62);
        registrationService.register(user);
        assertNotNull(user.getId());
    }

    @Test
    void register_UserWithCorrectData_Ok() {
        User user = new User();
        user.setLogin("Bob");
        user.setPassword("rightPass3822");
        user.setAge(19);
        registrationService.register(user);
        assertNotNull(user.getId());
    }

    @Test
    void register_UsersWithSameLogin_NotOk() {
        User firstUser = new User();
        firstUser.setLogin("Casual User");
        firstUser.setPassword("password39");
        firstUser.setAge(23);
        registrationService.register(firstUser);

        User secondUser = new User();
        secondUser.setLogin("Casual User");
        secondUser.setPassword("qazwsx");
        secondUser.setAge(28);
        registrationService.register(secondUser);

        assertNotNull(firstUser.getId());
        assertNull(secondUser.getId());
    }
}
