package types;

/**
 * File class to represent a filePath and fileType as Strings held in the object.
 */
public class File {
    String filePath, fileType;

    /**
     * Constructor acts as an initial setter for filePath and fileType
     *
     * @param filePath String that represents the path to the file
     * @param fileType String that represents the type of the file
     */
    public File(String filePath, String fileType){
        this.filePath = filePath;
        this.fileType = fileType;
    }

    /**
     * fileType getter
     *
     * @return fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * filePath getter
     *
     * @return filePath
     */
    public String getFilePath(){
        return filePath;
    }


    @Override
    public String toString(){
        return "\nFile Path: " + filePath + "\nFile Type: " + fileType;
    }
}
