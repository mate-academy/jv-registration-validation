package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User firstUser;
    private static User secondUser;
    private static User threeUser;
    private static User fourthUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        firstUser = new User();
        secondUser = new User();
        threeUser = new User();
        fourthUser = new User();
    }

    @Test
    void register_twoUser_Ok() {
        firstUser.setAge(20);
        firstUser.setLogin("Bob");
        firstUser.setPassword("123456");
        secondUser.setAge(24);
        secondUser.setLogin("Henry");
        secondUser.setPassword("654321");
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        boolean actual = Storage.people.size() == 2;
        assertTrue(actual);
    }

    @Test
    void register_passwordShortLength_notOk() {
        threeUser.setAge(20);
        threeUser.setLogin("Alice");
        threeUser.setPassword("1234");
        assertThrows(RuntimeException.class, () -> registrationService.register(threeUser));
    }

    @Test
    void register_existedUser_notOk() {
        fourthUser.setAge(20);
        fourthUser.setLogin("Bob");
        fourthUser.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(fourthUser));
    }
}
