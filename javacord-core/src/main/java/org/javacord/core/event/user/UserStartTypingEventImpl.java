package org.javacord.core.event.user;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.Member;
import org.javacord.api.entity.user.User2;
import org.javacord.api.event.user.UserStartTypingEvent;

import java.util.Optional;

/**
 * The implementation of {@link UserStartTypingEvent}.
 */
public class UserStartTypingEventImpl extends ChannelUserEventImpl implements UserStartTypingEvent {

    /**
     * Creates a new user start typing event.
     *
     * @param user The user of the event.
     * @param member The member of the event.
     * @param channel The text channel of the event.
     */
    public UserStartTypingEventImpl(User2 user, Member member, TextChannel channel) {
        super(user, member, channel);
    }

    @Override
    public Optional<Member> getMember() {
        return Optional.empty();
    }
}
