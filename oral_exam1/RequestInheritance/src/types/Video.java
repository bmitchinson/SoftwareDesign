package types;

public class Video {
    String vidUrl, vidTitle, vidAuthor;

    public Video(String vidUrl, String vidTitle, String vidAuthor){
        this.vidUrl = vidUrl;
        this.vidTitle = vidTitle;
        this.vidAuthor = vidAuthor;
    }

    public String getVidUrl(){
        return vidUrl;
    }

    public String getVidTitle(){
        return vidTitle;
    }

    public String getVidAuthor(){
        return vidAuthor;
    }
    
    @Override
    public String toString(){
        return "\nVideo: " + vidTitle + "\nBy: " + vidAuthor;
    }
}
