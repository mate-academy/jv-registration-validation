package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.interfaces.AgeValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AgeValidatorImlTest {
    private static final int ZERO_AGE = 0;
    private static final int MIN_AGE = 18;
    private static final int NEGATIVE_AGE = -18;
    private static final String TEST_FAILED_EXPECTED_FALSE =
            "The test failed, expected false but actual ";
    private static final String TEST_FAILED_BY_AGE_EXPECTED_FALSE =
            "The test failed, expected false but by age ";
    private static final String TEST_FAILED_EXPECTED_TRUE =
            "The test failed, expected true but by age ";

    private static AgeValidator ageValidator;

    @BeforeAll
    static void beforeAll() {
        ageValidator = new AgeValidatorImpl();
    }

    @Test
    void isValid_nullAge_notOK() {
        boolean actual = ageValidator.isValid(null);
        assertFalse(actual, TEST_FAILED_EXPECTED_FALSE + actual);
    }

    @Test
    void isValid_fromZeroToLimitAge_notOk() {
        for (int age = ZERO_AGE; age < MIN_AGE; age++) {
            boolean actual = ageValidator.isValid(age);
            assertFalse(actual, TEST_FAILED_BY_AGE_EXPECTED_FALSE + age + " actual " + actual);
        }
    }

    @Test
    void isValid_negativeAge_notOk() {
        boolean actual = ageValidator.isValid(NEGATIVE_AGE);
        assertFalse(actual, TEST_FAILED_BY_AGE_EXPECTED_FALSE + NEGATIVE_AGE + " actual " + actual);
    }

    @Test
    void isValid_limitAge_ok() {
        boolean actual = ageValidator.isValid(MIN_AGE);
        assertTrue(actual, TEST_FAILED_EXPECTED_TRUE + MIN_AGE + " actual " + actual);
    }
}
