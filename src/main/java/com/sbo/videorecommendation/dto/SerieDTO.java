package com.sbo.videorecommendation.dto;

public class SerieDTO extends VideoDTO {
    private int numberOfEpisodes;

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }
}