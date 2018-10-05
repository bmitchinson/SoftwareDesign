package types;

public class File {
    String filePath, fileType;
    public File(String filePath, String fileType){
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFilePath(){
        return filePath;
    }

    @Override
    public String toString(){
        return "\nFile Path: " + filePath + "\nFile Type: " + fileType;
    }
}
