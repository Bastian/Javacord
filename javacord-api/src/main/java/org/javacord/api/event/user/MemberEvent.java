package org.javacord.api.event.user;

import org.javacord.api.entity.user.Member;
import org.javacord.api.event.Event;

/**
 * A member event.
 */
public interface MemberEvent extends Event {

    /**
     * Gets the member of the event.
     *
     * @return The member of the event.
     */
    Member getMember();

}
