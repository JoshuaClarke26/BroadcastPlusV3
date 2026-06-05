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

    public RedisMessage(String type, String message, String extra, String originServer) {
        this.type = type;
        this.message = message;
        this.extra = extra;
        this.originServer = originServer;
    }

    public String getType()         { return type; }
    public String getMessage()      { return message; }
    public String getExtra()        { return extra != null ? extra : ""; }
    public String getOriginServer() { return originServer; }

    /** Serializes to JSON */
    public String toJson() {
        return GSON.toJson(this);
    }

    /** Deserializes from JSON */
    public static RedisMessage fromJson(String json) {
        return GSON.fromJson(json, RedisMessage.class);
    }
}
