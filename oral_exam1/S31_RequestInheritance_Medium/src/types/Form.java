package types;

import java.util.HashMap;

/**
 * Class to represent form data as a private String:String HashMap.
 */
public class Form {

    HashMap<String, String> fields;

    /**
     * Constructor acts as an initial setter for the fields HashMaps
     *
     * @param fields String:String hashmap to map form questions to their
     *               responses.
     */
    public Form(HashMap<String, String> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        String output = "Form Data";
        Object[] keys = fields.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            output += "\n" + "Label: " + keys[i] +
                    "\nValue: " + fields.get(keys[i]);
        }
        return output;
    }

}