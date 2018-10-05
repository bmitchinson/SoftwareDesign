import java.util.UUID;

/**
 * PostRequest extends the Request class and adds a String to represent the an
 * IP address.
 *
 * @see Request
 */
public class PostRequest extends Request {

    private String ip;
    static private int postRequestCount;

    /**
     * A constructor to act as a setter for requestUUID and ip.
     *
     * @param requestUUID a UUID to be passed along to the constructor of Request
     * @param ip a String representing the ip address of the post.
     */
    public PostRequest(UUID requestUUID, String ip) {
        super(requestUUID);
        this.ip = ip;
        postRequestCount += 1;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nPost request to server with IP address: " + ip;
    }

    /**
     * getter for the static count of PostRequest objects currently instantiated
     *
     * @return count of PostRequest objects currently instantiated.
     */
    public static int count() {
        return postRequestCount;
    }
}
