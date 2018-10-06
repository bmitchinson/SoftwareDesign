import types.Form;

import java.util.UUID;

/**
 * PostFormRequest extends the PostRequest class and adds it's own instance count
 * as well as a local Form object.
 *
 * @see PostRequest
 */
public class PostFormRequest extends PostRequest {
    private Form form;
    static private int postFormRequestCount;

    /**
     * A constructor that acts as an initial setter of uuid, ip, and form. uuid
     * and ip are forwarded to the parent constructor and the form is stored
     * locally
     *
     * @param uuid id to be passed to the PostRequest constructor
     * @param ip   ip address to be passed to the PostRequest constructor
     * @param form internalize a form object to represent survey results
     */
    public PostFormRequest(UUID uuid, String ip, Form form) {
        super(uuid, ip);
        this.form = form;
        postFormRequestCount += 1;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + form.toString();
    }

    /**
     * getter for the static count of PostFormRequest objects currently instantiated
     *
     * @return PostFormRequest count of PostFormRequest objects currently instantiated
     */
    public static int count() {
        return postFormRequestCount;
    }


}