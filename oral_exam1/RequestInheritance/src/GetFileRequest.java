import types.File;

import java.util.UUID;

public class GetFileRequest extends GetRequest{

    private File file;
    public static int getFileRequestCount;

    public GetFileRequest(UUID requestUUID, File file) {
        super(requestUUID, "file:///"+file.getFilePath()+'.'+file.getFileType());
        this.file = file;
        getFileRequestCount += 1;
    }

    @Override
    public String toString(){
        return super.toString() + file.toString();
    }

    public static int count(){
        return getFileRequestCount;
    }

}
