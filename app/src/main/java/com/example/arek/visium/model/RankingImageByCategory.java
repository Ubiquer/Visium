package com.example.arek.visium.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by arek on 10/31/2017.
 */

public class RankingImagesByCategory {

    @SerializedName("PictureId")
    private int pictureId;
    @SerializedName("RankingPoints")
    private int rankingPoints;
    @SerializedName("DateOfUpload")
    private Date dateOfUpload;
    @SerializedName("Likes")
    private int likes;
    @SerializedName("Dislikes")
    private int dislikes;
    @SerializedName("ImagePath")
    private String imagePath;
    @SerializedName("Category")
    private String category;

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public int getRankingPoints() {
        return rankingPoints;
    }

    public void setRankingPoints(int rankingPoints) {
        this.rankingPoints = rankingPoints;
    }

    public Date getDateOfUpload() {
        return dateOfUpload;
    }

    public void setDateOfUpload(Date dateOfUpload) {
        this.dateOfUpload = dateOfUpload;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
