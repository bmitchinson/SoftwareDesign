import java.util.UUID;

/**
 * GetRequest is an extension of the Request class that adds a String to represent
 * a url, as well as it's own instance counter.
 *
 * @see Request
 */
public class GetRequest extends Request {

    private String url;
    static private int getRequestCount;

    /**
     * the constructor works as a setter for requestUUID and the url by forwarding
     * the UUID to the parent constructor, and storing the url.
     *
     * @param requestUUID unique id to represent the request
     * @param url url to represent the location of information
     */
    public GetRequest(UUID requestUUID, String url){
        super(requestUUID);
        this.url = url;
        getRequestCount += 1;
    }

    @Override
    public String toString(){
        return super.toString() + "\nUniversal Resource Locator (URL): " + url;
    }

    /**
     * getter for the number of GetRequest objects currently instantiated.
     *
     * @return number of GetRequest objects currently instantiated.
     */
    public static int count(){
        return getRequestCount;
    }
}
