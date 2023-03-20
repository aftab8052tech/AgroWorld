package com.project.agroworld.articles.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HowToExpandResponse implements Serializable {

    @SerializedName("irrigation")
    private String irrigation;

    @SerializedName("image_link")
    private String imageLink;

    @SerializedName("seed")
    private String seed;

    @SerializedName("harvesting")
    private String harvesting;

    @SerializedName("required_temperature")
    private String requiredTemperature;

    @SerializedName("crop_info")
    private String cropInfo;

    @SerializedName("crop_name")
    private String cropName;

    @SerializedName("season")
    private String season;

    @SerializedName("fertilizer")
    private String fertilizer;

    public void setIrrigation(String irrigation) {
        this.irrigation = irrigation;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getSeed() {
        return seed;
    }

    public void setHarvesting(String harvesting) {
        this.harvesting = harvesting;
    }

    public String getHarvesting() {
        return harvesting;
    }

    public void setRequiredTemperature(String requiredTemperature) {
        this.requiredTemperature = requiredTemperature;
    }

    public String getRequiredTemperature() {
        return requiredTemperature;
    }

    public void setCropInfo(String cropInfo) {
        this.cropInfo = cropInfo;
    }

    public String getCropInfo() {
        return cropInfo;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropName() {
        return cropName;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSeason() {
        return season;
    }

    public void setFertilizer(String fertilizer) {
        this.fertilizer = fertilizer;
    }

    public String getFertilizer() {
        return fertilizer;
    }

    @Override
    public String toString() {
        return
                "HowToExpandResponse{" +
                        "irrigation = '" + irrigation + '\'' +
                        ",image_link = '" + imageLink + '\'' +
                        ",seed = '" + seed + '\'' +
                        ",harvesting = '" + harvesting + '\'' +
                        ",required_temperature = '" + requiredTemperature + '\'' +
                        ",crop_info = '" + cropInfo + '\'' +
                        ",crop_name = '" + cropName + '\'' +
                        ",season = '" + season + '\'' +
                        ",fertilizer = '" + fertilizer + '\'' +
                        "}";
    }
}