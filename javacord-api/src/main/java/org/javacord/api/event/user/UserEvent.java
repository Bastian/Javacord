package org.javacord.api.event.user;

import org.javacord.api.entity.user.User2;
import org.javacord.api.event.Event;

/**
 * A user event.
 */
public interface UserEvent extends Event {

    /**
     * Gets the user of the event.
     *
     * @return The user of the event.
     */
    User2 getUser();

}
