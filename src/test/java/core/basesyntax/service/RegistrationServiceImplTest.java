package core.basesyntax.service;

import core.basesyntax.exeptions.*;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private List<User> usersList;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        this.registrationService = new RegistrationServiceImpl();
        usersList = new ArrayList<>();
        User max = new User(1L, "max123", "1234567",20);
        User alex = new User(2L, "alex@gmail.com", "1234567",17);
        User bella = new User(3L, "bella", "1234567",18);
        User carloss = new User(4L, "carloss@gmail.com", "1234567",24);
        User kelly = new User(5L, "kelly@gmail.com", "1234",24);
        User lando = new User(6L, "lando@gmail.com", "123456",18);
        User elizabet = new User(7L, "elizabet@gmail.com", "1234567",68);
        User oskar = new User(8L, "oskar@gmail.com", "1234567",25);
        User charles = new User(9L, "charles@gmail.com", "1234567",18);
        User lewis = new User(10L, "lewis", "123",15);
        User george = new User(11L, null, "1234567",20);
        User kimmy = new User(12L, "kimmy@gmail.com", null,20);
        usersList.add(max); //0
        usersList.add(alex); //1
        usersList.add(bella); //2
        usersList.add(carloss); //3
        usersList.add(kelly); //4
        usersList.add(lando); //5
        usersList.add(elizabet); //6
        usersList.add(oskar); //7
        usersList.add(charles); //8
        usersList.add(lewis); // 9
        usersList.add(george); //10
        usersList.add(kimmy); //11

    }

    @Test
    void AllCriteriesAreFailed_NotOk() {
        try {
            registrationService.register(usersList.get(9));
        } catch (LoginShorterThanSixException e) {
            return;
        }
        fail("Login should be longer than 6");
    }

    @Test
    void UserEqualsNull_NotOk() {
        try {
            registrationService.register(null); //user is null
        } catch (NullPointerException e) {
            return;
        }
        fail("User can't be null");
    }

    @Test
    void UserAlreadyExists_NotOk() {
        try {
            registrationService.register(usersList.get(0));
            registrationService.register(usersList.get(0)); // user already exists
        } catch (UserAlreadyExistsException e) {
            return;
        }
        fail("User already exists");
    }

    @Test
    void LoginShorterThanSix_NotOk() {
        try {
            registrationService.register(usersList.get(2)); // bella; login is shorter than 6
        } catch (LoginShorterThanSixException e) {
            return;
        }
        fail("Login must be longer or equals to 6");
    }

    @Test
    void LoginIsSix_Ok() {
        User current = registrationService.register(usersList.get(0));
        assertEquals(people.get(2), current); //check if the user was registered
    }

    @Test
    void LoginLongerThanSix_Ok() {
        User current = registrationService.register(usersList.get(3)); // carloss; login.length equals 17
        assertEquals(people.get(0), current);
    }

    @Test
    void PasswordShorterThanSix_NotOk() {
        try {
            registrationService.register(usersList.get(4));// kelly; password is 4
        } catch (PasswordShorterThanSixException e) {
            return;
        }
       fail("Password must be longer or equals to 6");
    }

    @Test
    void PasswordIsSix_Ok() {
        User current = registrationService.register(usersList.get(5));
        assertEquals(people.get(3), current);
    }

    @Test
    void PasswordLongerThanSix_Ok() {
        User current = registrationService.register(usersList.get(6));
        assertEquals(people.get(4), current);
    }

    @Test
    void UserYoungerThanEighteen_NotOk() {
        try {
            registrationService.register(usersList.get(1));
        } catch (UserYoungerThanEighteenException e) {
            return;
        }
        fail("User must be 18 or older");
    }

    @Test
    void UserIsEighteen_Ok() {
        User current = registrationService.register(usersList.get(8)); //charles is 18
        assertEquals(people.get(5), current);
    }

    @Test
    void UserOlderThanEighteen_Ok() {
        User current = registrationService.register(usersList.get(7));
        assertEquals(people.get(1), current);
    }

    @Test
    void LoginIsNull_NotOk() {
        try {
            registrationService.register(usersList.get(10));
        } catch(LoginIsNullException e) {
            return;
        }
        fail("Login can't be null");
    }

    @Test
    void PasswordsNull_NotOk() {
        try {
            registrationService.register(usersList.get(11));
        } catch(PasswordIsNullException e) {
            return;
        }
        fail("Password can't be null");
    }
}