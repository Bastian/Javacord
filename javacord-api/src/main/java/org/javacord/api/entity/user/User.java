package org.javacord.api.entity.user;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.Nameable;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.server.Server;
import org.javacord.api.listener.user.UserAttachableListenerManager;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface User extends DiscordEntity, Messageable, Nameable, Mentionable, UserAttachableListenerManager {

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
     * Gets the nickname of the user in the given server.
     *
     * @param server The server.
     * @return The nickname of the user.
     * @deprecated Use {@link Server#getMemberById(long)} and {@link Member#getNickname()} instead.
     */
    @Deprecated
    default Optional<String> getNickname(Server server) {
        return server.getNickname(this);
    }

    /**
     * Gets the display name of the user.
     * If the user has a nickname, it will return the nickname, otherwise it will return the "normal" name.
     *
     * @param server The server.
     * @return The display name of the user.
     * @deprecated Use {@link Server#getMemberById(long)} and {@link Member#getDisplayName()} instead.
     */
    @Deprecated
    default String getDisplayName(Server server) {
        return server.getDisplayName(this);
    }

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
        return getApi().getYourself().getId() == getId();
    }

    /**
     * Gets the private channel with the user.
     *
     * <p>This will only be present, if there was an conversation with the user in the past or you manually opened a
     * private channel with the given user, using {@link #openPrivateChannel()}.
     *
     * @return The private channel with the user.
     */
    Optional<PrivateChannel> getPrivateChannel();

    /**
     * Opens a new private channel with the given user.
     *
     * <p>If there's already a private channel with the user, it will just return the one which already exists.
     *
     * @return The new (or old) private channel with the user.
     */
    CompletableFuture<PrivateChannel> openPrivateChannel();

}
