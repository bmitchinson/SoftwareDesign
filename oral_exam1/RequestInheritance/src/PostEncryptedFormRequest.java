import types.Form;

import java.util.UUID;

public class PostEncryptedFormRequest extends PostFormRequest {
    String encryption;
    static private int postEncryptedFormRequestCount;

    public PostEncryptedFormRequest(UUID uuid, String ip, Form form, String encryption) {
        super(uuid, ip, form);
        this.encryption = encryption;
        postEncryptedFormRequestCount += 1;
    }

    @Override
    public String toString() {
        return super.toString() + "\nThis form was encrypted using: " + encryption;
    }

    public static int count() {
        return postEncryptedFormRequestCount;
    }

}
