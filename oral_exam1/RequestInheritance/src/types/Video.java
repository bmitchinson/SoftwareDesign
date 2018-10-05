package types;

/**
 * A class to represent Video as Strings for the url, title, and author of the video.
 */
public class Video {
    String vidUrl, vidTitle, vidAuthor;

    /**
     * The constructor acts as an initial setter for vidUrl, vidTitle, and vidAuthor
     *
     * @param vidUrl a String that holds the url of the video
     * @param vidTitle a String that holds the title of the video
     * @param vidAuthor a String that holds the author of the video
     */
    public Video(String vidUrl, String vidTitle, String vidAuthor){
        this.vidUrl = vidUrl;
        this.vidTitle = vidTitle;
        this.vidAuthor = vidAuthor;
    }

    /**
     * a getter for vidUrl
     * @return the String vidUrl
     */
    public String getVidUrl(){
        return vidUrl;
    }

    /**
     * a getter for vidTitle
     * @return the String vidTitle
     */
    public String getVidTitle(){
        return vidTitle;
    }

    /**
     * a getter for vidAuthor
     * @return the String vidAuthor
     */
    public String getVidAuthor(){
        return vidAuthor;
    }
    
    @Override
    public String toString(){
        return "\nVideo: " + vidTitle + "\nBy: " + vidAuthor;
    }
}
