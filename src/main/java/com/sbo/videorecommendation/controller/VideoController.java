package com.sbo.videorecommendation.controller;
import com.sbo.videorecommendation.dto.MovieDTO;
import com.sbo.videorecommendation.dto.SerieDTO;
import com.sbo.videorecommendation.dto.VideoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final Map<String, VideoDTO> videoStore = new HashMap<>();
    private final List<String> deletedVideoIds = new ArrayList<>();

    @PostMapping("/add")
    public ResponseEntity<VideoDTO> addVideo(@RequestBody VideoDTO video) {
            try {
            videoStore.put(video.getId(), video);
            return new ResponseEntity<>(video, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoDTO> getVideoById(@PathVariable String videoId) {
        try {
            VideoDTO video = videoStore.get(videoId);
            if (video != null) {
                return new ResponseEntity<>(video, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<VideoDTO>> searchVideosByTitle(@PathVariable String keyword) {
        try {
            if (keyword.length() < 3) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            List<VideoDTO> matchingVideos = videoStore.values().stream()
                    .filter(video -> video instanceof VideoDTO)
                    .filter(video -> video.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());

            if (!matchingVideos.isEmpty()) {
                return new ResponseEntity<>(matchingVideos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{videoId}")
    public ResponseEntity<String> deleteVideo(@PathVariable String videoId) {
        try {
            VideoDTO deletedVideo = videoStore.remove(videoId);

            if (deletedVideo != null) {
                deletedVideoIds.add(videoId);
                return new ResponseEntity<>("video with ID " + videoId + " is deleted.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("video with ID " + videoId + " does not exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error when deleting the video with ID " + videoId , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/deleted-ids")
    public ResponseEntity<List<String>> getDeletedVideoIds() {
        try {
            if (deletedVideoIds.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(deletedVideoIds, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> getMovies() {
        try {
            List<MovieDTO> movies = videoStore.values().stream()
                    .filter(video -> video instanceof MovieDTO)
                    .map(video -> (MovieDTO) video)
                    .collect(Collectors.toList());

            if (movies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/series")
    public ResponseEntity<List<SerieDTO>> getSeries() {
        try {
            List<SerieDTO> series = videoStore.values().stream()
                    .filter(video -> video instanceof SerieDTO)
                    .map(video -> (SerieDTO) video)
                    .collect(Collectors.toList());

            if (series.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(series, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping("/similar-videos")
    public ResponseEntity<List<VideoDTO>> getSimilarVideos(
            @RequestParam String videoId,
            @RequestParam int minCommonLabels
    ) {
        VideoDTO referenceVideo = videoStore.get(videoId);

        if (referenceVideo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<VideoDTO> similarVideos = videoStore.values().stream()
                .filter(video -> !video.getId().equals(videoId))
                .filter(video -> countCommonLabels(referenceVideo, video) >= minCommonLabels)
                .collect(Collectors.toList());

        return new ResponseEntity<>(similarVideos, HttpStatus.OK);
    }

    private int countCommonLabels(VideoDTO video1, VideoDTO video2) {
        List<String> labels1 = video1.getLabels();
        List<String> labels2 = video2.getLabels();

        return (int) labels1.stream().filter(labels2::contains).count();
    }
}