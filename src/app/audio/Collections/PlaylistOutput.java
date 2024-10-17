package app.audio.Collections;

import app.utils.Enums;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.util.ArrayList;

/**
 * The type Playlist output.
 */
@Getter
@JsonSerialize(using = PlaylistOutputSerializer.class)
public class PlaylistOutput {
    private final String name;
    private final ArrayList<String> songs;
    private final String visibility;
    private final int followers;

    /**
     * Instantiates a new Playlist output.
     *
     * @param name       the name
     * @param songs      the songs
     * @param visibility the visibility
     * @param followers  the followers
     */
    @JsonCreator
    public PlaylistOutput(@JsonProperty("name") final String name,
                          @JsonProperty("songs") final ArrayList<String> songs,
                          @JsonProperty("visibility") final String visibility,
                          @JsonProperty("followers") final int followers) {
        this.name = name;
        this.songs = songs;
        this.visibility = visibility;
        this.followers = followers;
    }

    /**
     * Instantiates a new Playlist output.
     *
     * @param playlist the playlist
     */
    public PlaylistOutput(final Playlist playlist) {
        this.name = playlist.getName();
        this.songs = new ArrayList<>();
        for (int i = 0; i < playlist.getSongs().size(); i++) {
            songs.add(playlist.getSongs().get(i).getName());
        }
        this.visibility = playlist.getVisibility() == Enums.Visibility.PRIVATE
                ? "private" : "public";
        this.followers = playlist.getFollowers();
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
     * Gets songs.
     *
     * @return the songs
     */
    public ArrayList<String> getSongs() {
        return songs;
    }

    /**
     * Gets visibility.
     *
     * @return the visibility
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * Gets followers.
     *
     * @return the followers
     */
    public int getFollowers() {
        return followers;
    }
}
