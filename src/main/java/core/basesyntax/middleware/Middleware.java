package core.basesyntax.middleware;

import core.basesyntax.model.User;

public abstract class Middleware {
    private Middleware next;

    public static Middleware link(Middleware first, Middleware... chain) {
        Middleware head = first;
        for (Middleware nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract void check(User user);

    protected void checkNext(User user) {
        if (next == null) {
            return;
        }
        next.check(user);
    }
}
