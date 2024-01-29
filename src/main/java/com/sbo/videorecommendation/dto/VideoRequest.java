package com.sbo.videorecommendation.dto;

import java.time.LocalDate;
import java.util.List;

public class VideoRequest {
    private String id;

    private String title;
    private List<String> labels;
    private String director;
    private LocalDate releaseDate;

    private int numberOfEpisodes;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}