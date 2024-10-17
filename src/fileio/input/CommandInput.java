package fileio.input;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class CommandInput {
    private String command;
    private String username;
    private Integer timestamp;
    private String type; // song / playlist / podcast
    private FiltersInput filters; // for search
    private Integer itemNumber; // for select
    private Integer repeatMode; // for repeat
    private Integer playlistId; // for add/remove song
    private String playlistName; // for create playlist
    private Integer seed; // for shuffle
    private Integer age;
    private String city;
    private String name;
    private int releaseYear;
    private String description;
    private List<Song> songs;
    private String date;
    private int price;
    private List<Episode> episodes;
    private String nextPage;

    public CommandInput() {
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Integer timestamp) {
        this.timestamp = timestamp;
    }

    public FiltersInput getFilters() {
        return filters;
    }

    public void setFilters(final FiltersInput filters) {
        this.filters = filters;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(final Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Integer getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(final Integer playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(final Integer seed) {
        this.seed = seed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(final List<JsonNode> songNodes) {
        List<Song> newSongs = new ArrayList<>();

        for (JsonNode songNode : songNodes) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Song song = objectMapper.treeToValue(songNode, Song.class);
                newSongs.add(song);
            } catch (Exception e) {
                e.printStackTrace();
                // Treats conversion error
            }
        }

        this.songs = newSongs;
    }

    public void setEpisodes(final List<JsonNode> episodeNodes) {
        List<Episode> newEpisodes = new ArrayList<>();

        for (JsonNode episodeNode : episodeNodes) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Episode episode = objectMapper.treeToValue(episodeNode, Episode.class);
                newEpisodes.add(episode);
            } catch (Exception e) {
                e.printStackTrace();
                // Treats conversion error
            }
        }

        this.episodes = newEpisodes;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public String getDate() {
        return date;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(final String nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public String toString() {
        return "CommandInput{"
                + "command='" + command + '\''
                + ", username='" + username + '\''
                + ", timestamp=" + timestamp
                + ", type='" + type + '\''
                + ", filters=" + filters
                + ", itemNumber=" + itemNumber
                + ", repeatMode=" + repeatMode
                + ", playlistId=" + playlistId
                + ", playlistName='" + playlistName + '\''
                + ", seed=" + seed
                + '}';
    }
}
