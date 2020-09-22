package org.javacord.api.event.user;

import org.javacord.api.entity.user.Member;
import org.javacord.api.event.Event;

import java.util.Optional;

/**
 * A member update event.
 */
public interface MemberUpdateEvent extends Event, MemberEvent {

    /**
     * Gets the old member object.
     *
     * @return The old member object.
     */
    Optional<Member> getOldMember();

}
