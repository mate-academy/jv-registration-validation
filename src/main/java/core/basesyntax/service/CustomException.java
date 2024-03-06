package core.basesyntax.service;

import java.rmi.UnexpectedException;

public class CustomException extends UnexpectedException {
    public CustomException(String s) {
        super(s);
    }
}
