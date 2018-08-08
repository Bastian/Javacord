package org.javacord.api.entity.channel;

import org.javacord.api.audio.AudioConnection;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.channel.VoiceChannelAttachableListenerManager;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents a voice channel.
 */
public interface VoiceChannel extends Channel, VoiceChannelAttachableListenerManager {

    /**
     * Connects to this voice channel.
     * <p>
     * If the bot is already connected to this voice channel, it will return the current connection.
     * <p>
     * If the bot is connected to another voice channel in the same server, it will close the other audio
     * channel and connect to this channel with a new audio connection.
     * 
     * @return The connected channel.
     */
    default CompletableFuture<AudioConnection> connect() {
        throw new UnsupportedOperationException("This method is missing an implementation!");
    }

    /**
     * Checks if the given user can connect to the voice channel.
     * In private chats (private channel or group channel) this always returns <code>true</code> if the user is
     * part of the chat.
     *
     * @param user The user to check.
     * @return Whether the given user can connect or not.
     */
    default boolean canConnect(User user) {
        if (!canSee(user)) {
            return false;
        }
        Optional<ServerVoiceChannel> severVoiceChannel = asServerVoiceChannel();
        return !severVoiceChannel.isPresent()
               || severVoiceChannel.get().hasAnyPermission(user,
                                                          PermissionType.ADMINISTRATOR,
                                                          PermissionType.CONNECT);
    }

    /**
     * Checks if the user of the connected account can connect to the voice channel.
     * In private chats (private channel or group channel) this always returns {@code true} if the user is
     * part of the chat.
     *
     * @return Whether the user of the connected account can connect or not.
     */
    default boolean canYouConnect() {
        return canConnect(getApi().getYourself());
    }

    /**
     * Checks if the given user can mute other users in this voice channel.
     * In private chats (private channel or group channel) this always returns <code>false</code>.
     *
     * @param user The user to check.
     * @return Whether the given user can mute other users or not.
     */
    default boolean canMuteUsers(User user) {
        if (!canConnect(user) || getType() == ChannelType.PRIVATE_CHANNEL || getType() == ChannelType.GROUP_CHANNEL) {
            return false;
        }
        Optional<ServerVoiceChannel> serverVoiceChannel = asServerVoiceChannel();
        return !serverVoiceChannel.isPresent()
               || serverVoiceChannel.get().hasAnyPermission(user,
                                                            PermissionType.ADMINISTRATOR,
                                                            PermissionType.MUTE_MEMBERS);
    }

    /**
     * Checks if the user of the connected account can mute other users in this voice channel.
     * In private chats (private channel or group channel) this always returns {@code false}.
     *
     * @return Whether the user of the connected account can mute other users or not.
     */
    default boolean canYouMuteUsers() {
        return canMuteUsers(getApi().getYourself());
    }

    @Override
    default Optional<? extends VoiceChannel> getCurrentCachedInstance() {
        return getApi().getVoiceChannelById(getId());
    }

    @Override
    default CompletableFuture<? extends VoiceChannel> getLatestInstance() {
        Optional<? extends VoiceChannel> currentCachedInstance = getCurrentCachedInstance();
        if (currentCachedInstance.isPresent()) {
            return CompletableFuture.completedFuture(currentCachedInstance.get());
        } else {
            CompletableFuture<? extends VoiceChannel> result = new CompletableFuture<>();
            result.completeExceptionally(new NoSuchElementException());
            return result;
        }
    }

}
