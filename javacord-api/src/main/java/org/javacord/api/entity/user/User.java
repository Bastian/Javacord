package org.javacord.api.entity.user;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.DiscordClient;
import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.Nameable;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.ServerUpdater;
import org.javacord.api.listener.user.UserAttachableListenerManager;

import java.awt.Color;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
     * Gets all mutual servers with this user.
     *
     * <p>This method does only work with the user cache enabled!
     *
     * @return All mutual servers with this user.
     */
    default Collection<Server> getMutualServers() {
        return getApi().getServers().stream()
                .filter(server -> server.isMember(this))
                .collect(Collectors.toList());
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
     * Gets the discriminated name of the user, e. g. {@code Bastian#0001}.
     *
     * @return The discriminated name of the user.
     */
    default String getDiscriminatedName() {
        return getName() + "#" + getDiscriminator();
    }

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
     * Gets the connection status of the user as it is displayed in the user list.
     *
     * <p>This will return {@link UserStatus#OFFLINE} for invisible users.
     *
     * <p>To see if a non-offline user is connected via a mobile client, a desktop client, a web client or any
     * combination of the three use the {@link #getStatusOnClient(DiscordClient)}} method.
     *
     * @return The status of the user.
     */
    UserStatus getStatus();

    /**
     * Gets the status of the user on the given client.
     *
     * <p>This will return {@link UserStatus#OFFLINE} for invisible users.
     *
     * @param client The client.
     * @return The status of the user
     * @see #getStatus()
     */
    UserStatus getStatusOnClient(DiscordClient client);

    /**
     * Gets the status of the user on the {@link DiscordClient#DESKTOP desktop} client.
     *
     * <p>This will return {@link UserStatus#OFFLINE} for invisible users.
     *
     * @return The status of the the user.
     * @see #getStatusOnClient(DiscordClient)
     */
    default UserStatus getDesktopStatus() {
        return getStatusOnClient(DiscordClient.DESKTOP);
    }

    /**
     * Gets the status of the user on the {@link DiscordClient#MOBILE mobile} client.
     *
     * <p>This will return {@link UserStatus#OFFLINE} for invisible users.
     *
     * @return The status of the the user.
     * @see #getStatusOnClient(DiscordClient)
     */
    default UserStatus getMobileStatus() {
        return getStatusOnClient(DiscordClient.MOBILE);
    }

    /**
     * Gets the status of the user on the {@link DiscordClient#WEB web} (browser) client.
     *
     * <p>This will return {@link UserStatus#OFFLINE} for invisible users.
     *
     * @return The status of the the user.
     * @see #getStatusOnClient(DiscordClient)
     */
    default UserStatus getWebStatus() {
        return getStatusOnClient(DiscordClient.WEB);
    }

    /**
     * Gets all clients of the user that are not {@link UserStatus#OFFLINE offline}.
     *
     * @return A set with the clients.
     * @see #getStatusOnClient(DiscordClient)
     */
    default Set<DiscordClient> getCurrentClients() {
        Set<DiscordClient> connectedClients = Arrays
                .stream(DiscordClient.values())
                .filter(client -> getStatusOnClient(client) != UserStatus.OFFLINE)
                .collect(Collectors.toSet());
        return Collections.unmodifiableSet(connectedClients);
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

    /**
     * Gets the server voice channels the user is connected to.
     *
     * @return The server voice channels the user is connected to.
     */
    default Collection<ServerVoiceChannel> getConnectedVoiceChannels() {
        return Collections.unmodifiableCollection(getApi().getServerVoiceChannels().stream()
                .filter(this::isConnected)
                .collect(Collectors.toList()));
    }

    /**
     * Checks whether this user is connected to the given channel.
     *
     * @param channel The channel to check.
     * @return Whether this user is connected to the given channel or not.
     */
    default boolean isConnected(ServerVoiceChannel channel) {
        return channel.isConnected(getId());
    }

    /**
     * Gets the voice channel this user is connected to on the given server if any.
     *
     * @param server The server to check.
     * @return The server voice channel the user is connected to.
     */
    default Optional<ServerVoiceChannel> getConnectedVoiceChannel(Server server) {
        return server.getConnectedVoiceChannel(getId());
    }

    /**
     * Changes the nickname of the user in the given server.
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerUpdater} from {@link Server#createUpdater()} which provides a better performance!
     *
     * @param server The server.
     * @param nickname The new nickname of the user.
     * @return A future to check if the update was successful.
     */
    default CompletableFuture<Void> updateNickname(Server server, String nickname) {
        return server.updateNickname(this, nickname);
    }

    /**
     * Changes the nickname of the user in the given server.
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerUpdater} from {@link Server#createUpdater()} which provides a better performance!
     *
     * @param server The server.
     * @param nickname The new nickname of the user.
     * @param reason The audit log reason for this update.
     * @return A future to check if the update was successful.
     */
    default CompletableFuture<Void> updateNickname(Server server, String nickname, String reason) {
        return server.updateNickname(this, nickname, reason);
    }

    /**
     * Removes the nickname of the user in the given server.
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerUpdater} from {@link Server#createUpdater()} which provides a better performance!
     *
     * @param server The server.
     * @return A future to check if the update was successful.
     */
    default CompletableFuture<Void> resetNickname(Server server) {
        return server.resetNickname(this);
    }

    /**
     * Removes the nickname of the user in the given server.
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerUpdater} from {@link Server#createUpdater()} which provides a better performance!
     *
     * @param server The server.
     * @param reason The audit log reason for this update.
     * @return A future to check if the update was successful.
     */
    default CompletableFuture<Void> resetNickname(Server server, String reason) {
        return server.resetNickname(this, reason);
    }

    /**
     * Moves this user to the given channel.
     *
     * @param channel The channel to move the user to.
     * @return A future to check if the move was successful.
     */
    default CompletableFuture<Void> move(ServerVoiceChannel channel) {
        return channel.getServer().moveUser(this, channel);
    }

    /**
     * Gets the self-muted state of the user in the given server.
     *
     * @param server The server to check.
     * @return Whether the user is self-muted in the given server.
     */
    default boolean isSelfMuted(Server server) {
        return server.isSelfMuted(getId());
    }

    /**
     * Gets the self-deafened state of the user in the given server.
     *
     * @param server The server to check.
     * @return Whether the user is self-deafened in the given server.
     */
    default boolean isSelfDeafened(Server server) {
        return server.isSelfDeafened(getId());
    }

    /**
     * Mutes this user on the given server.
     *
     * @param server The server to umute this user on.
     * @return A future to check if the mute was successful.
     */
    default CompletableFuture<Void> mute(Server server) {
        return server.muteUser(this);
    }

    /**
     * Mutes this user on the given server.
     *
     * @param server The server to umute this user on.
     * @param reason The audit log reason for this action.
     * @return A future to check if the mute was successful.
     */
    default CompletableFuture<Void> mute(Server server, String reason) {
        return server.muteUser(this, reason);
    }

    /**
     * Unmutes this user on the given server.
     *
     * @param server The server to unumute this user on.
     * @return A future to check if the unmute was successful.
     */
    default CompletableFuture<Void> unmute(Server server) {
        return server.unmuteUser(this);
    }

    /**
     * Unmutes this user on the given server.
     *
     * @param server The server to unumute this user on.
     * @param reason The audit log reason for this action.
     * @return A future to check if the unmute was successful.
     */
    default CompletableFuture<Void> unmute(Server server, String reason) {
        return server.unmuteUser(this, reason);
    }

    /**
     * Gets the muted state of the user in the given server.
     *
     * @param server The server to check.
     * @return Whether the user is muted in the given server.
     */
    default boolean isMuted(Server server) {
        return server.isMuted(getId());
    }

    /**
     * Deafens this user on the given server.
     *
     * @param server The server to deafen this user on.
     * @return A future to check if the deafen was successful.
     */
    default CompletableFuture<Void> deafen(Server server) {
        return server.deafenUser(this);
    }

    /**
     * Deafens this user on the given server.
     *
     * @param server The server to deafen this user on.
     * @param reason The audit log reason for this action.
     * @return A future to check if the deafen was successful.
     */
    default CompletableFuture<Void> deafen(Server server, String reason) {
        return server.deafenUser(this, reason);
    }

    /**
     * Undeafens this user on the given server.
     *
     * @param server The server to undeafen this user on.
     * @return A future to check if the undeafen was successful.
     */
    default CompletableFuture<Void> undeafen(Server server) {
        return server.undeafenUser(this);
    }

    /**
     * Undeafens this user on the given server.
     *
     * @param server The server to undeafen this user on.
     * @param reason The audit log reason for this action.
     * @return A future to check if the undeafen was successful.
     */
    default CompletableFuture<Void> undeafen(Server server, String reason) {
        return server.undeafenUser(this, reason);
    }

    /**
     * Gets the deafened state of the user in the given server.
     *
     * @param server The server to check.
     * @return Whether the user is deafened in the given server.
     */
    default boolean isDeafened(Server server) {
        return server.isDeafened(getId());
    }

    /**
     * Gets the timestamp of when the user joined the given server.
     *
     * @param server The server to check.
     * @return The timestamp of when the user joined the server.
     * @deprecated Use {@link Server#getMemberById(long)} and {@link Member#getJoinedAtTimestamp()}.
     */
    @Deprecated
    default Optional<Instant> getJoinedAtTimestamp(Server server) {
        return server.getJoinedAtTimestamp(this);
    }

    /**
     * Gets a sorted list (by position) with all roles of the user in the given server.
     *
     * @param server The server.
     * @return A sorted list (by position) with all roles of the user in the given server.
     * @see Server#getRoles(User)
     * @deprecated Use {@link Server#getMemberById(long)} and {@link Member#getRoles()}.
     */
    @Deprecated
    default List<Role> getRoles(Server server) {
        return server.getRoles(this);
    }

    /**
     * Gets the displayed color of the user based on his roles in the given server.
     *
     * @param server The server.
     * @return The color.
     * @see Server#getRoleColor(User)
     * @deprecated Use {@link Server#getMemberById(long)} and {@link Member#getRoleColor()}.
     */
    @Deprecated
    default Optional<Color> getRoleColor(Server server) {
        return server.getRoleColor(this);
    }

    /**
     * Adds the given role to the user.
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerUpdater} from {@link Server#createUpdater()} which provides a better performance!
     *
     * @param role The role which should be added to the user.
     * @return A future to check if the update was successful.
     * @see Server#addRoleToUser(User, Role)
     */
    default CompletableFuture<Void> addRole(Role role) {
        return addRole(role, null);
    }

    /**
     * Adds the given role to the user.
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerUpdater} from {@link Server#createUpdater()} which provides a better performance!
     *
     * @param role The role which should be added to the user.
     * @param reason The audit log reason for this update.
     * @return A future to check if the update was successful.
     * @see Server#addRoleToUser(User, Role, String)
     */
    default CompletableFuture<Void> addRole(Role role, String reason) {
        return role.getServer().addRoleToUser(this, role, reason);
    }

    /**
     * Removes the given role from the user.
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerUpdater} from {@link Server#createUpdater()} which provides a better performance!
     *
     * @param role The role which should be removed from the user.
     * @return A future to check if the update was successful.
     * @see Server#removeRoleFromUser(User, Role)
     */
    default CompletableFuture<Void> removeRole(Role role) {
        return removeRole(role, null);
    }

    /**
     * Removes the given role from the user.
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerUpdater} from {@link Server#createUpdater()} which provides a better performance!
     *
     * @param role The role which should be removed from the user.
     * @param reason The audit log reason for this update.
     * @return A future to check if the update was successful.
     * @see Server#removeRoleFromUser(User, Role, String)
     */
    default CompletableFuture<Void> removeRole(Role role, String reason) {
        return role.getServer().removeRoleFromUser(this, role, reason);
    }

    /**
     * Checks if the user can manage the target role.
     *
     * @param role The role that is to be managed.
     * @return Whether the user can manage the role.
     */
    default boolean canManageRole(Role role) {
        return role.getServer().canManageRole(this, role);
    }

}
