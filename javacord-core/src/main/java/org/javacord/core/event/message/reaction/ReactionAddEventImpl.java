package org.javacord.core.event.message.reaction;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.user.Member;
import org.javacord.api.event.message.reaction.ReactionAddEvent;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The implementation of {@link ReactionAddEvent}.
 */
public class ReactionAddEventImpl extends SingleReactionEventImpl implements ReactionAddEvent {

    private final Member member;

    /**
     * Creates a new reaction add event.
     *
     * @param api The discord api instance.
     * @param messageId The id of the message.
     * @param channel The text channel in which the message was sent.
     * @param emoji The emoji.
     * @param userId The if of the user who added the reaction.
     * @param member The member if it happened in a server.
     */
    public ReactionAddEventImpl(
            DiscordApi api, long messageId, TextChannel channel, Emoji emoji, long userId, Member member) {
        super(api, messageId, channel, emoji, userId);
        this.member = member;
    }

    @Override
    public Optional<Member> getMember() {
        return Optional.ofNullable(member);
    }

    @Override
    public CompletableFuture<Void> removeReaction() {
        return Reaction.removeUser(getApi(), getChannel().getId(), getMessageId(), getEmoji(), getUserId());
    }

}
