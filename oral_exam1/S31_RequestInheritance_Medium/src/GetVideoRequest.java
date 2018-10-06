import types.Video;

import java.util.UUID;

/**
 * GetVideoRequest is an extension of the GetRequest class that adds a Video
 * object, as well as it's own instance counter.
 *
 * @see GetRequest
 */
public class GetVideoRequest extends GetRequest{

    private Video video;
    private static int getVideoRequestCount;

    /**
     * The constructor acts as a setter for requestUUID and the Video, by
     * forwarding the UUID to the parent constructor, and storing the Video object.
     *
     * @param requestUUID unique id to represent the request
     * @param video file to represent video information
     */
    public GetVideoRequest(UUID requestUUID, Video video) {
        super(requestUUID, video.getVidUrl());
        this.video = video;
        getVideoRequestCount += 1;
    }

    @Override
    public String toString(){
        return super.toString() + video.toString();
    }

    /**
     * getter for the number of GetVideoRequest objects currently instantiated
     *
     * @return number of GetVideoRequest objects currently instantiated
     */
    public static int count(){
        return getVideoRequestCount;
    }

}
