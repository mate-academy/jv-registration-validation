package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static List<User> usersForCheck;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void classImplementation() {
        usersForCheck = new ArrayList<>();
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void clearingListOfUsers() {
        usersForCheck.clear();
        Storage.people.clear();
    }

    @BeforeEach
    void fillingListWith_5_ValidUsers() {
        User user1 = new User();
        user1.setLogin("login_to_go145");
        user1.setAge(53);
        user1.setId(4324L);
        user1.setPassword("fdfsJsn1");
        User user2 = new User();
        user2.setLogin("myLogin");
        user2.setAge(23);
        user2.setId(9302L);
        user2.setPassword("80301dd");
        User user3 = new User();
        user3.setLogin("BestttLOg");
        user3.setAge(18);
        user3.setId(342667L);
        user3.setPassword("%#@^*#@");
        User user4 = new User();
        user4.setLogin("coll)((!#$");
        user4.setAge(99);
        user4.setId(12L);
        user4.setPassword("FMDOFWFMFWWFWDFDGSGDWEFGRGW");
        User user5 = new User();
        user5.setLogin("ruioewrwIWJRNW902938-4--28=1");
        user5.setAge(29);
        user5.setId(432143L);
        user5.setPassword("JFIEWW");
        usersForCheck.add(user1);
        usersForCheck.add(user2);
        usersForCheck.add(user3);
        usersForCheck.add(user4);
        usersForCheck.add(user5);
    }

    @Test
    void allValidUsers_Check() {
        assertEquals(registrationService.register(usersForCheck.get(0)), usersForCheck.get(0));
        assertEquals(registrationService.register(usersForCheck.get(1)), usersForCheck.get(1));
        assertEquals(registrationService.register(usersForCheck.get(2)), usersForCheck.get(2));
        assertEquals(registrationService.register(usersForCheck.get(3)), usersForCheck.get(3));
        assertEquals(registrationService.register(usersForCheck.get(4)), usersForCheck.get(4));
    }

    @Test
    void loginIsNotNullAndNotEmpty_Check() {
        usersForCheck.get(0).setLogin(null);
        usersForCheck.get(1).setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(0));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(1));
        });
    }

    @Test
    void passwordIsNullOrLess_6_characters() {
        usersForCheck.get(0).setPassword("");
        usersForCheck.get(1).setPassword("null");
        usersForCheck.get(2).setPassword(null);
        usersForCheck.get(3).setPassword("12345");
        usersForCheck.get(4).setPassword("m");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(0));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(1));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(2));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(3));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(4));
        });
    }

    @Test
    void unvalidAgeOrLessThan_18() {
        usersForCheck.get(0).setAge(1);
        usersForCheck.get(1).setAge(-43);
        usersForCheck.get(2).setAge(0);
        usersForCheck.get(3).setAge(12345);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(0));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(1));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(2));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(3));
        });
    }

    @Test
    void sameLogin() {
        usersForCheck.get(0).setLogin("login");
        usersForCheck.get(1).setLogin("login");
        usersForCheck.get(2).setLogin("5235L");
        usersForCheck.get(3).setLogin("5235L");
        assertEquals(registrationService.register(usersForCheck.get(0)), usersForCheck.get(0));
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(1));
        });
        assertEquals(registrationService.register(usersForCheck.get(2)), usersForCheck.get(2));
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(usersForCheck.get(3));
        });
    }
}
