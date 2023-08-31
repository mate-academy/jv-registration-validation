package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
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
    void get_UserNull_notOk() {
        user = null;
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void getLogin_LessSixCharacters_notOk() {

        user.setLogin("abcd");
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void get_PwdLessSixCharacters_notOk() {

        user.setPassword("qwert");
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void get_UserAgeNotAdult_notOk() {

        user.setAge(5);
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void get_UserAgeLessZero_notOk() {
        user.setAge(-1);
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });

    }

    @Test
    void get_UserLoginNotUnique_notOk() {
        User user1 = new User();
        user1.setLogin("Alice66");
        user1.setAge(20);
        user1.setPassword("QWERT666");
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
            registrationService.register(user1);
        });
    }

    @Test
    void set_NullPwd_notOk() {
        user.setPassword(null);
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void get_UserAgeMoreMaxValue_notOk() {
        user.setAge(Integer.MIN_VALUE + 1);
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void get_AllData_Ok() {
        user.setLogin("Alice66");
        user.setAge(20);
        user.setPassword("QWERT666");
        assertEquals("Alice66", user.getLogin());
        assertEquals(20, user.getAge());
        assertEquals("QWERT666", user.getPassword());
    }
}
