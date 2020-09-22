package org.javacord.core.event.user;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.Member;
import org.javacord.api.entity.user.User2;
import org.javacord.api.event.user.TextChannelUserEvent;

import java.util.Optional;

/**
 * The implementation of {@link TextChannelUserEvent}.
 */
public abstract class ChannelUserEventImpl extends UserEventImpl implements TextChannelUserEvent {

    private final TextChannel channel;
    private final Member member;

    /**
     * Creates a new text channel user event.
     *
     * @param user The user of the event.
     * @param member The member of the event.
     * @param channel The text channel of the event.
     */
    public ChannelUserEventImpl(User2 user, Member member, TextChannel channel) {
        super(user);
        this.member = member;
        this.channel = channel;
    }

    @Override
    public TextChannel getChannel() {
        return channel;
    }

    @Override
    public Optional<Member> getMember() {
        return Optional.ofNullable(member);
    }
}
