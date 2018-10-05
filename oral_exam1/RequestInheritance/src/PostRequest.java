import java.util.UUID;

public class PostRequest extends Request {

    private String ip;
    static private int postRequestCount;

    public PostRequest(UUID requestUUID, String ip){
        super(requestUUID);
        this.ip = ip;
        postRequestCount += 1;
    }

    @Override
    public String toString(){
        return super.toString() +
                "\nPost request to server with IP address: " + ip;
    }

    public static int count(){
        return postRequestCount;
    }
}
