import types.Video;

import java.util.UUID;

public class GetVideoRequest extends GetRequest{

    private Video video;
    private static int getVideoRequestCount;

    public GetVideoRequest(UUID requestUUID, Video video) {
        super(requestUUID, video.getVidUrl());
        this.video = video;
        getVideoRequestCount += 1;
    }

    @Override
    public String toString(){
        return super.toString() + video.toString();
    }

    public static int count(){
        return getVideoRequestCount;
    }

}
