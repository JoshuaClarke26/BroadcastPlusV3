package Dev.ScalerGames.BroadcastPlus.Redis;

import com.google.gson.Gson;

/**
 * Represents a message sent/received through the Redis channel.
 * Serialized as JSON to travel through pub/sub.
 */
public class RedisMessage {

    private static final Gson GSON = new Gson();

    /** Broadcast type: chat, title, bar, boss, advancement */
    private final String type;

    /** Main content (message text, preset, etc.) */
    private final String message;

    /** Extra: timing for bar, world for broadcastworld, etc. */
    private final String extra;

    /** Origin server name (to avoid re-broadcasting on the same server) */
    private final String originServer;

    /**
     * Target server name. If null or empty, the broadcast is sent to ALL servers.
     * If set, only the server with this name will process the message.
     */
    private final String targetServer;

    public RedisMessage(String type, String message, String extra, String originServer) {
        this(type, message, extra, originServer, null);
    }

    public RedisMessage(String type, String message, String extra, String originServer, String targetServer) {
        this.type = type;
        this.message = message;
        this.extra = extra;
        this.originServer = originServer;
        this.targetServer = (targetServer != null && !targetServer.isEmpty()) ? targetServer : null;
    }

    public String getType()         { return type; }
    public String getMessage()      { return message; }
    public String getExtra()        { return extra != null ? extra : ""; }
    public String getOriginServer() { return originServer; }

    /**
     * Returns the target server name, or null if the broadcast is global.
     */
    public String getTargetServer() { return targetServer; }

    /** Serializes to JSON */
    public String toJson() {
        return GSON.toJson(this);
    }

    /** Deserializes from JSON */
    public static RedisMessage fromJson(String json) {
        return GSON.fromJson(json, RedisMessage.class);
    }
}
