import java.util.UUID;

/**
 * Parent level class in the inheritance structure.
 * Holds a title representing it's name in relation to the structure and memory,
 * and a UUID.
 *
 * @author Ben Mitchinson
 * @see UUID
 * @since October 4th, 2018
 */
public class Request {

    private String memoryTitle = "RequestInheritance." + super.toString();
    private UUID requestUUID;
    static private int requestCount;

    /**
     * Constructor to store a provided UUID
     *
     * @param requestUUID a random UUID object received from the data generator. Represents
     *                    a unique identifier.
     */
    public Request(UUID requestUUID) {
        requestCount += 1;
        this.requestUUID = requestUUID;
    }

    public static int count(){
        return requestCount;
    }

    @Override
    public String toString(){
        return memoryTitle + "\nUUID: "
                + requestUUID.toString();
    }


}