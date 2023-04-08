package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private List<User> userList;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void registerNullUser_NotOk() {
        User user = null;
        assertThrows(NotNullParameterUncheckedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "testuser", 25);
        assertThrows(NotNullParameterUncheckedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("testuser", null, 25);
        assertThrows(NotNullParameterUncheckedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User("testuser", "testuser", null);
        assertThrows(NotNullParameterUncheckedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserWithExistingLogin_notOk() {
        userList = new ArrayList<>();
        userList.add(new User("hellofirst", "user01", 21));
        userList.add(new User("hellosecond", "user02", 22));
        userList.add(new User("hellothird", "user03", 23));
        Storage.people.addAll(userList);
        User user1 = new User("hellofirst", "user01", 21);
        User user2 = new User("hellosecond", "user02", 22);
        User user3 = new User("hellothird", "user03", 23);
        User user4 = new User("hellofirst", "user04", 31);
        User user5 = new User("hellosecond", "user05", 32);
        assertAll("user already exists",
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user1);
                }),
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user2);
                }),
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user3);
                }),
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user4);
                }),
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user5);
                })
        );
    }

    @Test
    void register_UserWithInvalidAge_notOk() {
        User user1 = new User("invalidage1", "invalidage1", 0);
        User user2 = new User("invalidage2", "invalidage2", 10);
        User user3 = new User("invalidage3", "invalidage3", 17);
        assertAll("user has invalid age",
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user1);
                }),
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user2);
                }),
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user3);
                })
        );
    }

    @Test
    void passwordLengthLessThanSixSymbols_notOk() {
        User user0 = new User("zero", "", 21);
        User user1 = new User("first", "1", 21);
        User user2 = new User("second", "12", 21);
        User user3 = new User("third", "123", 21);
        User user4 = new User("fourth", "1234", 21);
        User user5 = new User("fifth", "12345", 21);
        assertAll("password length is less than 6 symbols",
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user0);
                }),
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user1);
                }),
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user2);
                }),
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user3);
                }),
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user4);
                }),
                () -> assertThrows(RuntimeException.class, () -> {
                    registrationService.register(user5);
                })
        );
    }

    @Test
    void passwordLengthNotLessThanSixSymbols_Ok() {
        User user0 = new User("zero", "123456", 21);
        User user1 = new User("first", "1234567", 21);
        User user2 = new User("second", "12345678", 21);
        User user3 = new User("third", "123456789", 21);
        User user4 = new User("fourth", "123456789011111", 21);
        assertAll("password length is not less than 6 symbols",
                () -> assertDoesNotThrow(() -> {
                    registrationService.register(user0);
                }),
                () -> assertDoesNotThrow(() -> {
                    registrationService.register(user1);
                }),
                () -> assertDoesNotThrow(() -> {
                    registrationService.register(user2);
                }),
                () -> assertDoesNotThrow(() -> {
                    registrationService.register(user3);
                }),
                () -> assertDoesNotThrow(() -> {
                    registrationService.register(user4);
                })
        );
    }

    @Test
    void register_notNullLoginPasswordAge_Ok() {
        User user = new User("testuser", "testuser", 25);
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerAllValidUsersGetNewSizeList() {
        Storage.people.clear();
        userList = new ArrayList<>();
        userList.add(new User("hellofirst", "user01", 21));
        userList.add(new User("hellosecond", "user02", 22));
        userList.add(new User("hellothird", "user03", 23));
        Storage.people.addAll(userList);
        User user4 = new User("004", "123456", 21);
        User user5 = new User("005", "1234567", 21);
        User user6 = new User("006", "12345678", 21);
        registrationService.register(user4);
        registrationService.register(user5);
        registrationService.register(user6);
        assertEquals(6, Storage.people.size());
    }
}
