package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_appropriateUser_Ok() {
        assertTrue(registrationService.register(
                new User("George_Washington", "AmericanFather", 57)),
                "A user with the relevant criteria must be registered.");
    }

    @Test
    void register_incorrectAge_NotOk() {
        expectException(new User("Abraham_Lincoln", "CivilWar", 14));
    }

    @Test
    void register_negativeAge_NotOk() {
        expectException(new User("George_Bush", "TexasIsTheBestState", -1));
    }

    @Test
    void register_loginHasBeenAlreadyCreated_NotOk() {
        registrationService.register(new User("Donald_Trump", "TheFallingWig", 76));
        expectException(new User("Donald_Trump", "IamTheRealDonaldTrump", 67));
    }

    @Test
    void register_passwordIsLessThan6Characters_NotOk() {
        expectException(new User("Joe_Biden", "joe", 79));
    }

    @Test
    void register_userIsNull_NotOk() {
        expectException(null);
    }

    @Test
    void register_loginIsNull_NotOk() {
        expectException(new User(null, "States", 246));
    }

    @Test
    void register_passwordIsNull_NotOk() {
        expectException(new User("Barack Obama", null, 61));
    }

    @Test
    void register_ageIsNull_NotOk() {
        expectException(new User("John Kennedy", "omericanDemokratia", null));
    }

    private void expectException(User user) {
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }
}
