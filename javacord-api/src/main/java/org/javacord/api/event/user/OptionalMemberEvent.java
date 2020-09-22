package org.javacord.api.event.user;

import org.javacord.api.entity.user.Member;
import org.javacord.api.event.Event;

import java.util.Optional;

/**
 * An optional member event.
 */
public interface OptionalMemberEvent extends Event {

    /**
     * Gets the member of the event.
     *
     * @return The member of the event.
     */
    Optional<Member> getMember();

}
