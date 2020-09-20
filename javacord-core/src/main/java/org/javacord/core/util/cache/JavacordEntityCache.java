package org.javacord.core.util.cache;

import java.util.function.UnaryOperator;

/**
 * An immutable cache with all Javacord entites.
 */
public class JavacordEntityCache {

    private static final JavacordEntityCache EMPTY_CACHE = new JavacordEntityCache(
            ChannelCache.empty(), MemberCache.empty());

    private final ChannelCache channelCache;
    private final MemberCache memberCache;
    
    /**
     * Gets an empty Javacord cache.
     *
     * @return An empty Javacord cache.
     */
    public static JavacordEntityCache empty() {
        return EMPTY_CACHE;
    }

    private JavacordEntityCache(ChannelCache channelCache, MemberCache memberCache) {
        this.channelCache = channelCache;
        this.memberCache = memberCache;
    }

    /**
     * Gets the channel cache.
     *
     * @return The channel cache.
     */
    public ChannelCache getChannelCache() {
        return channelCache;
    }

    /**
     * Updates the channel cache.
     *
     * @param mapper A function that takes the old channel cache and returns the new one.
     * @return The new Javacord entity cache.
     */
    public JavacordEntityCache updateChannelCache(UnaryOperator<ChannelCache> mapper) {
        return setChannelCache(mapper.apply(channelCache));
    }

    /**
     * Sets the channel cache.
     *
     * @param channelCache The channel cache to set.
     * @return The new Javacord entity cache.
     */
    public JavacordEntityCache setChannelCache(ChannelCache channelCache) {
        return new JavacordEntityCache(channelCache, memberCache);
    }

    /**
     * Gets the member cache.
     *
     * @return The member cache.
     */
    public MemberCache getMemberCache() {
        return memberCache;
    }

    /**
     * Updates the member cache.
     *
     * @param mapper A function that takes the old member cache and returns the new one.
     * @return The new Javacord entity cache.
     */
    public JavacordEntityCache updateMemberCache(UnaryOperator<MemberCache> mapper) {
        return setMemberCache(mapper.apply(memberCache));
    }

    /**
     * Sets the member cache.
     *
     * @param memberCache The member cache to set.
     * @return The new Javacord entity cache.
     */
    public JavacordEntityCache setMemberCache(MemberCache memberCache) {
        return new JavacordEntityCache(channelCache, memberCache);
    }
}
