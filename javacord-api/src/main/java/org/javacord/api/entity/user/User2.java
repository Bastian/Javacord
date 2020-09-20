package org.javacord.api.entity.user;

import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.Nameable;

public interface User2 extends DiscordEntity, Nameable, Mentionable {

    @Override
    default String getMentionTag() {
        return "<@" + getIdAsString() + ">";
    }

    /**
     * Gets the name of the user.
     *
     * <p>Note that the name is not unique to a user and can change.
     * To uniquely identify a user, us their id from {@link #getId()}.
     *
     * <p>Another way to uniquely identify a user is their name in combination with their discriminator which
     * can be obtained by calling {@link #getDiscriminator()}. However, unlike the id, the name and discriminator
     * of a user can change.
     *
     * @return The name.
     */
    @Override
    String getName();

    /**
     * Gets the discriminator of the user.
     *
     * <p>Besides their name, every user also has a 4-digit discriminator which is usually appended to their name,
     * separated by a hashtag (e.g, {@code Username#1234}). This combination of username and discriminator is
     * unique for every user. However, both the username and the discriminator can change and then be claimed
     * by another user. Because of this, it is recommended to use the id to uniquely identify a user (e.g., when
     * storing information in a database).
     *
     * @return The discriminator.
     */
    String getDiscriminator();

    /**
     * Gets the avatar of the user.
     *
     * <p>If the user has not upload an avatar, the default avatar will be returned.
     *
     * @return The avatar.
     */
    Icon getAvatar();

    /**
     * Gets if the user has a default Discord avatar.
     *
     * <p>This avatar is used if the user did not upload an avatar theirselves.
     *
     * @return Whether this user has a default avatar or not.
     */
    boolean hasDefaultAvatar();

    /**
     * Checks if the user is a bot account.
     *
     * @return Whether or not the user is a bot account.
     */
    boolean isBot();

    /**
     * Gets if this user is the user of the connected account.
     *
     * @return Whether this user is the user of the connected account or not.
     * @see DiscordApi#getYourself()
     */
    default boolean isYourself() {
        // TODO
        return false;
    }

}
