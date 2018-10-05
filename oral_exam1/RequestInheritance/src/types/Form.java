package types;

import java.util.HashMap;

public class Form {

    HashMap<String, String> fields;

    public Form(HashMap<String, String> fields){
        this.fields = fields;
    }

    @Override
    public String toString() {
        String output = "Form Data";
        Object[] keys = fields.keySet().toArray();
        for (int i=0; i<keys.length; i++){
            output += "\n" + "Label: " + keys[i] +
                    "\nValue: " + fields.get(keys[i]);
        }
        return output;
    }

}