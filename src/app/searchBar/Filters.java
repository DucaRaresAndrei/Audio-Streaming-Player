package app.searchBar;

import fileio.input.FiltersInput;
import lombok.Data;

import java.util.ArrayList;

/**
 * The type Filters.
 */
@Data
public class Filters {
    private String name;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;
    private String owner;
    private String followers;
    private String description;

    /**
     * Instantiates a new Filters.
     *
     * @param filters the filters
     */
    public Filters(final FiltersInput filters) {
        this.name = filters.getName();
        this.album = filters.getAlbum();
        this.tags = filters.getTags();
        this.lyrics = filters.getLyrics();
        this.genre = filters.getGenre();
        this.releaseYear = filters.getReleaseYear();
        this.artist = filters.getArtist();
        this.owner = filters.getOwner();
        this.followers = filters.getFollowers();
        this.description = filters.getDescription();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets album.
     *
     * @return the album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Gets tags.
     *
     * @return the tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    /**
     * Gets lyrics.
     *
     * @return the lyrics
     */
    public String getLyrics() {
        return lyrics;
    }

    /**
     * Gets genre.
     *
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Gets release year.
     *
     * @return the release year
     */
    public String getReleaseYear() {
        return releaseYear;
    }

    /**
     * Gets artist.
     *
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Gets followers.
     *
     * @return the followers
     */
    public String getFollowers() {
        return followers;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
