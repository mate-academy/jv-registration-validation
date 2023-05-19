package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "Ferruccio";
    private static final String DEFAULT_PASSWORD = "df89(exZ45";
    private static final int DEFAULT_AGE = 18;
    private User user;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userWithShortLoginContainsFiveCharacters_notOk() {
        user.setLogin("mario");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown when "
                        + "login is shorter than 6 characters");
    }

    @Test
    void register_userWithShortLoginContainsThreeCharacters_notOk() {
        user.setLogin("Kim");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown "
                        + "when login is shorter than 6 characters");
    }

    @Test
    void register_userWithShortLoginContainsOneCharacter_notOk() {
        user.setLogin("P");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown "
                        + "when login is shorter than 6 characters");
    }

    @Test
    void register_userWithShortPasswordContainsFiveCharacters_notOk() {
        user.setPassword("proXX");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown "
                        + "when password is shorter than 6 characters");
    }

    @Test
    void register_userWithShortPasswordContainsFourCharacters_notOk() {
        user.setPassword("four");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown "
                        + "when password is shorter than 6 characters");
    }

    @Test
    void register_userWithShortPasswordContainsTwoCharacters_notOk() {
        user.setPassword("sI");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown "
                        + "when password is shorter than 6 characters");
    }

    @Test
    void register_userWithImproperAge16_notOk() {
        user.setAge(16);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown "
                        + "when age is less than 18 years old");
    }

    @Test
    void register_userWithImproperAge12_notOk() {
        user.setAge(12);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown "
                        + "when age is less than 18 years old");
    }

    @Test
    void register_userWithImproperAge1_notOk() {
        user.setAge(1);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown "
                        + "when age is less than 18 years old");
    }

    @Test
    void register_userWithImproperAge5_notOk() {
        user.setAge(5);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown "
                        + "when age is less than 18 years old");
    }

    @Test
    void register_userWithNoughtAge_notOk() {
        user.setAge(0);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown when age is negative");
    }

    @Test
    void register_userWithNegativeAge_notOk() {
        user.setAge(-34);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown when age is negative");
    }

    @Test
    void register_userWithNullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown when login is \"null\"");
    }

    @Test
    void register_userWithNullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown when password is \"null\"");
    }

    @Test
    void register_userAlreadyInStorage_notOk() {
        registrationService.register(user);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown when the user is already in storage");
    }

    @Test
    void register_userWithEmptyLogin_notOk() {
        user.setLogin("");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown when login is empty");
    }

    @Test
    void register_userWithEmptyPassword_notOk() {
        user.setPassword("");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown when password is empty");
    }

    @Test
    void register_singleUser_Ok() {
        User actual = registrationService.register(user);
        User expected = user;

        assertEquals(expected, actual);
    }

    @Test
    void register_differentPluralUsers_ok() {
        User user1 = new User();
        user1.setLogin("vendetta");
        user1.setAge(50);
        user1.setPassword("formEE9090");
        User user2 = new User();
        user2.setLogin("Leopard");
        user2.setAge(48);
        user2.setPassword("Sf87*lls");

        registrationService.register(user);
        registrationService.register(user1);
        registrationService.register(user2);

        int expectedSize = 3;
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void register_completelyInvalidUser_notOk() {
        user.setLogin("BBBq");
        user.setPassword("kio9");
        user.setAge(9);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown when user is not valid");
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "\"InvalidInputException\" must be thrown when user is null");
    }
}
