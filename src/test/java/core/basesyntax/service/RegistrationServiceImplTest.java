package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void registerNewUser_Ok() {
        User firstUser = new User(2321312L, "First", "firstTSRIF_1",18);
        int expectedSize = 1;
        Storage.people.clear();
        registrationService.register(firstUser);
        int actualSize = Storage.people.size();
        assertEquals(expectedSize,actualSize, " doesn't add user");
    }

    @Test
    void userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null),
                "User is null");
    }

    @Test
    void anyFieldIsEmpty_NotOk() {
        User userWithIdNull = new User(null,"Flint200",
                "qw12ER34ty",33);
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithIdNull),
                "Id is null");

        User userWithLoginNull = new User(12433452L,null, "qw12ER34ty",33);
        assertThrows(RuntimeException.class, () -> registrationService
                        .register(userWithLoginNull),
                "Login is null");

        User userWitPasswordNull = new User(12433452L,"Flint200", null,33);
        assertThrows(RuntimeException.class, () -> registrationService
                        .register(userWitPasswordNull),
                "Password is null");

        User userWithAgeNull = new User(12433452L,"Flint200", "qw12ER34ty",null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithAgeNull),
                "Age is null");
    }

    @Test
    void shortPassword_NotOk() {
        User userWithToShortPassword = new User(12433452L,"Flint200","qw12E",33);
        assertThrows(RuntimeException.class, () -> registrationService
                        .register(userWithToShortPassword),
                "Too short password");
    }

    @Test
    void toYoungUser_NotOk() {
        User youngUser = new User(12433452L,"Flint200","qw12E",17);
        assertThrows(RuntimeException.class, () -> registrationService.register(youngUser),
                "Age is less than age requirement");
    }

    @Test
    void addUserWithSameLogin_NotOk() {
        Storage.people.add(new User(12433452L,"Flint200", "qw12ER34ty",33));
        assertThrows(RuntimeException.class,() -> registrationService.register(
                new User(1243552L,"Flint200", "ty34qw12ER",22)),
                "Two users with same Login");
    }
}
