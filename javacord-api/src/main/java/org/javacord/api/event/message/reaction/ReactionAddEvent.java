package org.javacord.api.event.message.reaction;

import org.javacord.api.event.user.OptionalMemberEvent;

import java.util.concurrent.CompletableFuture;

/**
 * A reaction add event.
 */
public interface ReactionAddEvent extends SingleReactionEvent, OptionalMemberEvent {

    /**
     * Removes the added reaction.
     *
     * @return A future to tell us if the action was successful.
     */
    CompletableFuture<Void> removeReaction();

}
