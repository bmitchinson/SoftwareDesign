import java.util.UUID;

public class GetRequest extends Request {


    private String url;
    static private int getRequestCount;

    public GetRequest(UUID requestUUID, String url){
        super(requestUUID);
        this.url = url;
        getRequestCount += 1;
    }

    @Override
    public String toString(){
        return super.toString();
    }

    public static int count(){
        return getRequestCount;
    }
}
