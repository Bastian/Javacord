package org.javacord.core.entity.user;

import com.fasterxml.jackson.databind.JsonNode;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.Member;
import org.javacord.api.entity.user.User2;
import org.javacord.core.DiscordApiImpl;
import org.javacord.core.entity.server.ServerImpl;

import java.awt.Color;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Maps a member object.
 *
 * @see <a href="https://discord.com/developers/docs/resources/guild#guild-member-object">Discord Docs</a>
 */
public final class MemberImpl implements Member {

    private final DiscordApiImpl api;
    private final ServerImpl server;
    private final User2Impl user;
    private final String nickname;
    private final List<Long> roleIds;
    private final String joinedAt;
    private final String serverBoostingSince;
    private final boolean selfDeafened;
    private final boolean selfMuted;

    /**
     * Creates a new immutable member instance.
     *
     * @param api The api instance.
     * @param server The server of the member.
     * @param data The json data of the member.
     * @param user A user object in case the json does not contain user data (e.g., for message create events).
     *             If the json contains a non-null user field, this parameter is ignored.
     */
    public MemberImpl(DiscordApiImpl api, ServerImpl server, JsonNode data, User2Impl user) {
        this.api = api;
        this.server = server;

        if (data.hasNonNull("user")) {
            this.user = new User2Impl(api, data.get("user"));
        } else {
            this.user = user;
        }

        if (data.hasNonNull("nick")) {
            nickname = data.get("nick").asText();
        } else {
            nickname = null;
        }

        roleIds = new ArrayList<>();
        for (JsonNode roleIdJson : data.get("roles")) {
            roleIds.add(roleIdJson.asLong());
        }

        joinedAt = data.get("joined_at").asText();
        if (data.hasNonNull("premium_since")) {
            serverBoostingSince = data.get("premium_since").asText();
        } else {
            serverBoostingSince = null;
        }

        selfDeafened = data.get("deaf").asBoolean();
        selfMuted = data.get("mute").asBoolean();
    }

    @Override
    public DiscordApi getApi() {
        return api;
    }

    @Override
    public long getId() {
        return user.getId();
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public User2 getUser() {
        return user;
    }

    @Override
    public Optional<String> getNickname() {
        return Optional.ofNullable(nickname);
    }

    @Override
    public List<Role> getRoles() {
        return roleIds.stream()
                .map(server::getRoleById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Color> getRoleColor() {
        return getRoles().stream()
                .filter(role -> role.getColor().isPresent())
                .max(Comparator.comparingInt(Role::getRawPosition))
                .flatMap(Role::getColor);
    }

    @Override
    public Instant getJoinedAtTimestamp() {
        return OffsetDateTime.parse(joinedAt).toInstant();
    }

    @Override
    public Optional<Instant> getServerBoostingSinceTimestamp() {
        return Optional.ofNullable(serverBoostingSince)
                .map(OffsetDateTime::parse)
                .map(OffsetDateTime::toInstant);
    }

    @Override
    public boolean isSelfMuted() {
        return selfMuted;
    }

    @Override
    public boolean isSelfDeafened() {
        return selfDeafened;
    }
}
