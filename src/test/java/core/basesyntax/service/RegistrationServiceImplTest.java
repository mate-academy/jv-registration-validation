package core.basesyntax.service;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {

    RegistrationService registrationService = new RegistrationServiceImpl();


    @Test
    @DisplayName("register should work with user who has correct data ")
    void register_userWithCorrectData_ok() {
        // given
        User user = new User();
        user.setAge(18);
        user.setLogin("abcedfg");
        user.setPassword("poiuytr");

        // when
        User actual = registrationService.register(user);

        //then
        assertNotNull(actual);
        assertEquals(user, actual);
    }

    @Test
    @DisplayName("register should throw Exception when user is null")
    void register_nullUser_notOk() {
        // given
        User user = null;

        // when
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));

        //then
        assertEquals("User is null", exception.getMessage());

    }

    @Test
    @DisplayName("register should throw Exception when user login is less than six characters")
    void register_userLoginLessThanSixCharacters_notOk() {
        // given
        User user = new User();
        user.setLogin("abcde");

        // when
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));

        //then
        assertEquals("User login has less than six characters", exception.getMessage());

    }

    @Test
    @DisplayName("register should throw Exception when user password is less than six characters")
    void register_userPasswordLessThanSixCharacters_notOk() {
        // given
        User user = new User();
        user.setPassword("qwert");

        // when
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));

        //then
        assertEquals("User password has less than six characters", exception.getMessage());

    }

    @Test
    @DisplayName("register should throw Exception when user age is less than 18")
    void register_userAgeIsLessThanEighteen() {
        // given
        User user = new User();
        user.setAge(17);

        // when
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));

        //then
        assertEquals("The user's age is less than eighteen", exception.getMessage());

    }
}
