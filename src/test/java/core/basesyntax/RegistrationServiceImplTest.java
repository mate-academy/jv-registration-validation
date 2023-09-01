package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
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
    void get_allData_Ok() {
        user.setLogin("Alice66");
        user.setAge(20);
        user.setPassword("QWERT666");
        assertEquals("Alice66", user.getLogin());
        assertEquals(20, user.getAge());
        assertEquals("QWERT666", user.getPassword());
    }

    @Test
    void get_userNull_notOk() {
        user = null;
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void get_loginLessSixCharacters_notOk() {

        user.setLogin("abcde");
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void get_pwdLessSixCharacters_notOk() {

        user.setPassword("qwert");
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void get_userAgeNotAdult_notOk() {

        user.setAge(5);
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void get_userAgeLessZero_notOk() {
        user.setAge(-1);
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });

    }

    @Test
    void set_nullPwd_notOk() {
        user.setPassword(null);
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void get_userAgeMoreMaxValue_notOk() {
        user.setAge(Integer.MIN_VALUE + 1);
        assertThrows(DataNotVaidExeption.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void get_userLoginNotUnique_notOk() {
        User user1 = new User();
        user1.setLogin("Alice66");
        user1.setAge(20);
        user1.setPassword("QWERT666");
        assertThrows(DataNotVaidExeption.class, () -> {
            Storage.people.add(user1);
            registrationService.register(user);
        });
    }
  }
