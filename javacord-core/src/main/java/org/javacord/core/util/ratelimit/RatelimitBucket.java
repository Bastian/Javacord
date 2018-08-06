package org.javacord.core.util.ratelimit;

import org.javacord.api.DiscordApi;
import org.javacord.core.DiscordApiImpl;
import org.javacord.core.util.rest.RestEndpoint;
import org.javacord.core.util.rest.RestRequest;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RatelimitBucket {

    // The key is the token, as global ratelimits are shared across the same account.
    private static final Map<String, Long> globalRatelimit = new ConcurrentHashMap<>();

    private final DiscordApiImpl api;

    private final ConcurrentLinkedQueue<RestRequest<?>> requestQueue = new ConcurrentLinkedQueue<>();

    private final RestEndpoint endpoint;
    private final String majorUrlParameter;

    private volatile long rateLimitResetTimestamp = 0;
    private volatile int rateLimitRemaining = 1;

    /**
     * Creates a RatelimitBucket for the given endpoint / parameter combination.
     *
     * @param api The api/shard to use.
     * @param endpoint The REST endpoint the ratelimit is tracked for.
     */
    public RatelimitBucket(DiscordApi api, RestEndpoint endpoint) {
        this(api, endpoint, null);
    }

    /**
     * Creates a RatelimitBucket for the given endpoint / parameter combination.
     *
     * @param api The api/shard to use.
     * @param endpoint The REST endpoint the ratelimit is tracked for.
     * @param majorUrlParameter The url parameter this bucket is specific for. May be null.
     */
    public RatelimitBucket(DiscordApi api, RestEndpoint endpoint, String majorUrlParameter) {
        this.api = (DiscordApiImpl) api;
        if (endpoint.isGlobal()) {
            endpoint = null;
        }
        this.endpoint = endpoint;
        this.majorUrlParameter = majorUrlParameter;
    }

    /**
     * Sets a global ratelimit.
     *
     * @param api A discord api instance.
     * @param resetTimestamp The reset timestamp of the global ratelimit.
     */
    public static void setGlobalRatelimit(DiscordApi api, long resetTimestamp) {
        globalRatelimit.put(api.getToken(), resetTimestamp);
    }

    /**
     * Adds the given request to the bucket's queue.
     *
     * @param request The request to add.
     */
    public void addRequestToQueue(RestRequest<?> request) {
        requestQueue.add(request);
    }

    /**
     * Gets the bucket's current queue.
     * This queue is the actual internal used reference and thus can be modified.
     *
     * @return The buckets queue.
     */
    public ConcurrentLinkedQueue<RestRequest<?>> getRequestQueue() {
        return requestQueue;
    }

    /**
     * Polls a request from the bucket's queue.
     *
     * @return The polled request.
     */
    public RestRequest<?> pollRequestFromQueue() {
        return requestQueue.poll();
    }

    /**
     * Peeks a request from the bucket's queue.
     *
     * @return The peeked request.
     */
    public RestRequest<?> peekRequestFromQueue() {
        return requestQueue.peek();
    }

    /**
     * Gets the rest endpoint of the bucket.
     *
     * @return The endpoint of the bucket. If it's a global limit, the endpoint will be not be present.
     */
    public Optional<RestEndpoint> getEndpoint() {
        return Optional.ofNullable(endpoint);
    }

    /**
     * Sets the remaining requests till ratelimit.
     *
     * @param rateLimitRemaining The remaining requests till ratelimit.
     */
    public void setRateLimitRemaining(int rateLimitRemaining) {
        this.rateLimitRemaining = rateLimitRemaining;
    }

    /**
     * Sets the ratelimit reset timestamp.
     *
     * @param rateLimitResetTimestamp The rateLimit reset timestamp.
     */
    public void setRateLimitResetTimestamp(long rateLimitResetTimestamp) {
        this.rateLimitResetTimestamp = rateLimitResetTimestamp;
    }

    /**
     * Gets the time in seconds how long you have to wait till there's space in the bucket again.
     *
     * @return The time in seconds how long you have to wait till there's space in the bucket again.
     */
    public int getTimeTillSpaceGetsAvailable() {
        if (rateLimitRemaining > 0) {
            return 0;
        }
        long globalRatelimitResetTimestamp = RatelimitBucket.globalRatelimit.getOrDefault(api.getToken(), 0L);
        long timestamp = System.currentTimeMillis() + (api.getTimeOffset() == null ? 0 : api.getTimeOffset());
        return (int) (Math.max(rateLimitResetTimestamp, globalRatelimitResetTimestamp) - timestamp);
    }

    /**
     * Checks if a bucket created with the given parameters would equal this bucket.
     *
     * @param endpoint The endpoint.
     * @param majorUrlParameter The major url parameter.
     * @return Whether a bucket created with the given parameters would equal this bucket or not.
     */
    public boolean equals(RestEndpoint endpoint, String majorUrlParameter) {
        if (endpoint.isGlobal()) {
            endpoint = null;
        }
        boolean endpointSame = this.endpoint == endpoint;
        boolean majorUrlParameterBothNull = this.majorUrlParameter == null && majorUrlParameter == null;
        boolean majorUrlParameterEqual =
                this.majorUrlParameter != null && this.majorUrlParameter.equals(majorUrlParameter);

        return endpointSame && (majorUrlParameterBothNull || majorUrlParameterEqual);
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RatelimitBucket)) {
            return false;
        }
        RatelimitBucket otherBucket = (RatelimitBucket) obj;
        return equals(otherBucket.endpoint, otherBucket.majorUrlParameter);
    }

    @Override
    public int hashCode() {
        int hash = 42;
        int urlParamHash = majorUrlParameter == null ? 0 : majorUrlParameter.hashCode();
        int endpointHash = endpoint == null ? 0 : endpoint.hashCode();

        hash = hash * 11 + urlParamHash;
        hash = hash * 17 + endpointHash;
        return hash;
    }

    @Override
    public String toString() {
        String str = "Endpoint: " + (endpoint == null ? "global" : endpoint.getEndpointUrl());
        str += ", Major url parameter:" + (majorUrlParameter == null ? "none" : majorUrlParameter);
        return str;
    }
}
