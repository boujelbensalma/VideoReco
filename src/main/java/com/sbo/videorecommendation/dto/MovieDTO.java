package com.sbo.videorecommendation.dto;

import java.time.LocalDate;

public class MovieDTO extends VideoDTO {
    private String director;
    private LocalDate releaseDate;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}