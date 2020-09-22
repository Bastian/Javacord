package org.javacord.core.event.user;

import org.javacord.api.entity.user.User2;
import org.javacord.api.event.user.UserEvent;
import org.javacord.core.event.EventImpl;

/**
 * The implementation of {@link UserEvent}.
 */
public abstract class UserEventImpl extends EventImpl implements UserEvent {

    /**
     * The user of the event.
     */
    private final User2 user;

    /**
     * Creates a new user event.
     *
     * @param user The user of the event.
     */
    public UserEventImpl(User2 user) {
        super(user.getApi());
        this.user = user;
    }

    @Override
    public User2 getUser() {
        return user;
    }

}
