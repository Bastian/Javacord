package org.javacord.api.event.user;

import org.javacord.api.event.server.ServerEvent;

/**
 * A user change self-deafened event.
 */
public interface UserChangeSelfDeafenedEvent extends UserEvent, ServerEvent, MemberUpdateEvent {

    /**
     * Gets the new self-deafened state of the user.
     *
     * @return Whether the user is self-deafened now.
     */
    boolean isNewSelfDeafened();

    /**
     * Gets the old self-deafened state of the user.
     *
     * @return Whether the user was self-deafened previously.
     */
    boolean isOldSelfDeafened();

}
