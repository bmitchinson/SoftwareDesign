import types.Form;

import java.util.UUID;

public class PostFormRequest extends PostRequest {
    private Form form;
    static private int postFormRequestCount;

    public PostFormRequest(UUID uuid, String ip, Form form) {
        super(uuid, ip);
        this.form = form;
        postFormRequestCount += 1;
    }

    @Override
    public String toString(){
        return super.toString() + "\n" + form.toString();
    }

    public static int count(){
        return postFormRequestCount;
    }


}