package org.javacord.event.server;

/**
 * A server becomes available event.
 * Unavailability means, that a Discord server is down due to a temporary outage.
 *
 * @see <a href="https://discordapp.com/developers/docs/topics/gateway#guild-unavailability">Discord docs</a>
 */
public interface ServerBecomesAvailableEvent extends ServerEvent {
}