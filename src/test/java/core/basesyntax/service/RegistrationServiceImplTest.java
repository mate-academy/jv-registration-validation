package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    public static void initializeService() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void emptyStorage() {
        Storage.people.clear();
    }

    @Test
    public void loginNull_notOk() {
        User user = new User();
        user.setPassword("123456");
        user.setAge(18);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void passwordNull_notOk() {
        User user = new User();
        user.setLogin("Samantha");
        user.setAge(18);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void ageNull_notOk() {
        User user = new User();
        user.setLogin("Samantha");
        user.setPassword("123456");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void underageUser_notOk() {
        User user = new User();
        user.setLogin("Samantha");
        user.setPassword("123456");
        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void negativeAge_notOk() {
        User user = new User();
        user.setLogin("Samantha");
        user.setPassword("123456");
        user.setAge(-1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void validUser_ok() {
        User user = new User();
        user.setLogin("Samantha");
        user.setPassword("123456");
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    public void multipleValidUser_ok() {
        User firstUser = new User();
        firstUser.setLogin("Samantha");
        firstUser.setPassword("123456");
        firstUser.setAge(18);
        User secondUser = new User();
        secondUser.setLogin("Arthur");
        secondUser.setPassword("123456");
        secondUser.setAge(18);
        User thirdUser = new User();
        thirdUser.setLogin("abcdef");
        thirdUser.setPassword("1234567");
        thirdUser.setAge(19);
        User fourthUser = new User();
        fourthUser.setLogin("abcdefg");
        fourthUser.setPassword("123456");
        fourthUser.setAge(18);
        User fifthUser = new User();
        fifthUser.setLogin("abcdefgh");
        fifthUser.setPassword("12345678");
        fifthUser.setAge(999);

        User actual = registrationService.register(firstUser);
        assertEquals(firstUser, actual);
        actual = registrationService.register(secondUser);
        assertEquals(secondUser, actual);
        actual = registrationService.register(thirdUser);
        assertEquals(thirdUser, actual);
        actual = registrationService.register(fourthUser);
        assertEquals(fourthUser, actual);
        actual = registrationService.register(fifthUser);
        assertEquals(fifthUser, actual);
    }

    @Test
    public void loginDuplicate_notOk() {
        User user = new User();
        user.setLogin("Samantha");
        user.setPassword("123456");
        user.setAge(18);
        Storage.people.add(user);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void loginUnique_ok() {
        User user = new User();
        user.setLogin("Samantha");
        user.setPassword("123456");
        user.setAge(18);
        User secondUser = new User();
        secondUser.setLogin("Arthur");
        secondUser.setPassword("123456");
        secondUser.setAge(18);
        User thirdUser = new User();
        thirdUser.setLogin("Arthurr");
        thirdUser.setPassword("123456");
        thirdUser.setAge(18);

        Storage.people.add(user);
        User actual = registrationService.register(secondUser);
        assertEquals(secondUser, actual);
        actual = registrationService.register(thirdUser);
        assertEquals(thirdUser, actual);
    }

    @Test
    public void loginTooShort_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("123456");
        user.setAge(18);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Service accepted too short login: " + user.getLogin());
        user.setLogin("a");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Service accepted too short login: " + user.getLogin());
        user.setLogin("abc");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Service accepted too short login: " + user.getLogin());
        user.setLogin("abcde");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Service accepted too short login: " + user.getLogin());
    }

    @Test
    public void passwordTooShort_notOk() {
        User user = new User();
        user.setLogin("Samantha");
        user.setPassword("");
        user.setAge(18);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Service accepted too short password: " + user.getLogin());
        user.setPassword("a");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Service accepted too short password: " + user.getLogin());
        user.setPassword("abc");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Service accepted too short password: " + user.getLogin());
        user.setPassword("abcde");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Service accepted too short password: " + user.getLogin());
    }
}
