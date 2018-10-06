import types.File;

import java.util.UUID;

/**
 * GetFileRequest extends the GetRequest class and adds a local File variable, as
 * well as it's out instance counter.
 *
 * @see GetRequest
 */
public class GetFileRequest extends GetRequest{

    private File file;
    private static int getFileRequestCount;

    /**
     * The constructor acts as a setter for requestUUID and file, and passes the
     * UUID along to the GetRequest constructor.
     *
     * @param requestUUID unique identifier set in GetRequest
     * @param file File object to represent a files path and type
     */
    public GetFileRequest(UUID requestUUID, File file) {
        super(requestUUID, "file:///"+file.getFilePath()+'.'+file.getFileType());
        this.file = file;
        getFileRequestCount += 1;
    }

    @Override
    public String toString(){
        return super.toString() + file.toString();
    }

    /**
     * getter for the number of GetFileRequest objects currently instantiated.
     *
     * @return number of GetFileRequest objects currently instantiated.
     */
    public static int count(){
        return getFileRequestCount;
    }

}
