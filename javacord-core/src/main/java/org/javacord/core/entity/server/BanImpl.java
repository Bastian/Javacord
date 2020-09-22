package org.javacord.core.entity.server;

import com.fasterxml.jackson.databind.JsonNode;
import org.javacord.api.entity.server.Ban;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User2;
import org.javacord.core.DiscordApiImpl;
import org.javacord.core.entity.user.User2Impl;

import java.util.Optional;

/**
 * The implementation of {@link Ban}.
 */
public class BanImpl implements Ban {

    /**
     * The server of the ban.
     */
    private final Server server;

    /**
     * The banned user.
     */
    private final User2 user;

    /**
     * The reason for the ban.
     */
    private final String reason;

    /**
     * Creates a new ban.
     *
     * @param server The server of the ban.
     * @param data The json data of the ban.
     */
    public BanImpl(Server server, JsonNode data) {
        this.server = server;
        user = new User2Impl((DiscordApiImpl) server.getApi(), data.get("user"));
        reason = data.has("reason") ? data.get("reason").asText() : null;
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
    public Optional<String> getReason() {
        return Optional.ofNullable(reason);
    }
}
