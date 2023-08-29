package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {

    private static final int LOGIN_MIN_LENGHT = 6;
    private static final int USER_PWD_MIN_LENGHT = 6;
    private static final int USER_MIN_AGE = 18;
    private RegistrationService registrationService = new RegistrationServiceImpl();

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Alice66");
        user.setAge(20);
        user.setPassword("QWERT666");

    }

    @Test
    void loginLenghtLessSixCharacters_Is_NOt_OK() {

        user.setLogin("abcd");
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void pwdLessSixCharacters() {

        user.setPassword("qwert");
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAgeAdult() {

        user.setAge(5);
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAgeLessZeroNotOK() {
        user.setAge(-1);
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });

    }

    @Test
    void isUserLoginNotUnique() {
        User user1 = new User();
        user1.setLogin("Alice66");
        user1.setAge(26);
        user1.setPassword("QWERT6666");
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
            registrationService.register(user1);
        });
    }

    @Test
    void checkNullDataNotOK() {
        user.setPassword(null);
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAgeMoreMax_Value_Not_Ok() {
        user.setAge(Integer.MIN_VALUE + 1);
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }
}
