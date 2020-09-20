package org.javacord.api.event.server;

/**
 * A server change owner event.
 */
public interface ServerChangeOwnerEvent extends ServerEvent {

    /**
     * Gets the old owner of the server.
     *
     * @return The old owner of the server.
     */
    User getOldOwner();

    /**
     * Gets the new owner of the server.
     *
     * @return The new owner of the server.
     */
    User getNewOwner();

}
