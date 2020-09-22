package org.javacord.api.event.server.member;

import org.javacord.api.entity.server.Ban;
import org.javacord.api.event.server.ServerEvent;
import org.javacord.api.event.user.UserEvent;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * A server member ban event.
 */
public interface ServerMemberBanEvent extends ServerEvent, UserEvent {

    /**
     * Requests the Ban object of the event.
     *
     * @return The ban object.
     */
    CompletableFuture<Optional<Ban>> requestBan();

    /**
     * Requests the reason of the ban.
     *
     * @return The reason of the ban.
     */
    default CompletableFuture<Optional<String>> requestReason() {
        return requestBan().thenApply(ban -> ban.flatMap(Ban::getReason));
    }

}
