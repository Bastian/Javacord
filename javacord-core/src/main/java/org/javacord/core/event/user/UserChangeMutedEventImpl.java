package org.javacord.core.event.user;

import org.javacord.api.entity.user.Member;
import org.javacord.api.event.user.UserChangeMutedEvent;

import java.util.Optional;

/**
 * The implementation of {@link UserChangeMutedEvent}.
 */
public class UserChangeMutedEventImpl extends ServerUserEventImpl implements UserChangeMutedEvent {

    private final Member newMember;
    private final Member oldMember;

    /**
     * Creates a new user change muted event.
     *
     * @param newMember The new member.
     * @param oldMember The old member.
     */
    public UserChangeMutedEventImpl(Member newMember, Member oldMember) {
        super(newMember.getUser(), newMember.getServer());
        this.newMember = newMember;
        this.oldMember = oldMember;
    }

    @Override
    public Optional<Member> getOldMember() {
        return Optional.ofNullable(oldMember);
    }

    @Override
    public Member getMember() {
        return newMember;
    }

    @Override
    public boolean isNewMuted() {
        // TODO This is wrong.
        return newMember.isSelfMuted();
    }

    @Override
    public boolean isOldMuted() {
        return oldMember.isSelfMuted();
    }
}
