
package com.example.alex.youtubelearningbuddy.model.comments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentDetails {

    @SerializedName("authorDisplayName")
    @Expose
    private String authorDisplayName;
    @SerializedName("authorProfileImageUrl")
    @Expose
    private String authorProfileImageUrl;
    @SerializedName("authorChannelUrl")
    @Expose
    private String authorChannelUrl;
    @SerializedName("authorChannelId")
    @Expose
    private AuthorChannelId authorChannelId;
    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("textDisplay")
    @Expose
    private String textDisplay;
    @SerializedName("textOriginal")
    @Expose
    private String textOriginal;
    @SerializedName("canRate")
    @Expose
    private Boolean canRate;
    @SerializedName("viewerRating")
    @Expose
    private String viewerRating;
    @SerializedName("likeCount")
    @Expose
    private Integer likeCount;
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    public void setAuthorDisplayName(String authorDisplayName) {
        this.authorDisplayName = authorDisplayName;
    }

    public String getAuthorProfileImageUrl() {
        return authorProfileImageUrl;
    }

    public void setAuthorProfileImageUrl(String authorProfileImageUrl) {
        this.authorProfileImageUrl = authorProfileImageUrl;
    }

    public String getAuthorChannelUrl() {
        return authorChannelUrl;
    }

    public void setAuthorChannelUrl(String authorChannelUrl) {
        this.authorChannelUrl = authorChannelUrl;
    }

    public AuthorChannelId getAuthorChannelId() {
        return authorChannelId;
    }

    public void setAuthorChannelId(AuthorChannelId authorChannelId) {
        this.authorChannelId = authorChannelId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTextDisplay() {
        return textDisplay;
    }

    public void setTextDisplay(String textDisplay) {
        this.textDisplay = textDisplay;
    }

    public String getTextOriginal() {
        return textOriginal;
    }

    public void setTextOriginal(String textOriginal) {
        this.textOriginal = textOriginal;
    }

    public Boolean getCanRate() {
        return canRate;
    }

    public void setCanRate(Boolean canRate) {
        this.canRate = canRate;
    }

    public String getViewerRating() {
        return viewerRating;
    }

    public void setViewerRating(String viewerRating) {
        this.viewerRating = viewerRating;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFormattedPublishedAt() {
        int index = publishedAt.indexOf("T");
        if (index != -1){
            return publishedAt.substring(0, index) + " at " +
                    publishedAt.substring(index + 1, index + 6);
        }
        return "";
    }
}
