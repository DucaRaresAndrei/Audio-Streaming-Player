package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.player.Player;
import app.player.PlayerSource;
import app.user.ArtistThings.Event;
import app.user.ArtistThings.Merch;
import app.user.ArtistThings.UsingUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Artist.
 */
public class Artist extends UserSkelet {
    @Getter
    private ArrayList<Album> albums;
    private ArrayList<Event> events;
    private ArrayList<Merch> merches;
    private ArrayList<UsingUser> usingUsers;
    private int timestamp;
    private static final int LAST_DAY = 31;
    private static final int LAST_MONTH = 12;
    private static final int FIRST_YEAR = 1900;
    private static final int CURRENT_YEAR = 2023;
    private static final int FEBRUARY = 2;
    private static final int LAST_FEBRUARY_DAY = 28;

    /**
     * Instantiates a new Artist.
     *
     * @param username  the username
     * @param age       the age
     * @param city      the city
     * @param timestamp the timestamp
     */
    public Artist(final String username, final int age, final String city, final int timestamp) {
        super(username, age, city);
        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merches = new ArrayList<>();
        this.usingUsers = new ArrayList<>();
        this.timestamp = timestamp;
    }

    /**
     * Artist clear.
     */
    public void artistClear() {
        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merches = new ArrayList<>();
        this.usingUsers = new ArrayList<>();
    }

    /**
     *
     * @return the userName
     */
    public String getUsername() {
        return super.getUsername();
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * Add album string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String addAlbum(final CommandInput commandInput) {
        if (!albums.isEmpty()) {
            for (Album album : albums) {
                if (album.getName().equals(commandInput.getName())) {
                    return commandInput.getUsername() + " has another album with the same name.";
                }
            }
        }

        List<Song> songcheck = commandInput.getSongs();

        for (int i = 0; i < songcheck.size() - 1; i++) {
            for (int j = i + 1; j < songcheck.size(); j++) {
                if (songcheck.get(i).getName().equals(songcheck.get(j).getName())) {
                    return commandInput.getUsername()
                            + " has the same song at least twice in this album.";
                }
            }
        }

        albums.add(new Album(commandInput.getName(), commandInput.getUsername(),
                commandInput.getTimestamp(), commandInput.getReleaseYear(),
                commandInput.getDescription(), commandInput.getSongs()));

        Admin admin = Admin.getInstance();
        admin.addSongs(commandInput.getSongs());
        return commandInput.getUsername() + " has added new album successfully.";
    }

    /**
     * Show albums object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public ObjectNode showAlbums(final CommandInput commandInput) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode resultNode = objectMapper.createObjectNode();

        resultNode.put("command", commandInput.getCommand());
        resultNode.put("user", commandInput.getUsername());
        resultNode.put("timestamp", commandInput.getTimestamp());

        ArrayNode albumsArray = resultNode.putArray("result");

        // Add each album to the array
        for (Album album : albums) {
            ObjectNode albumNode = JsonNodeFactory.instance.objectNode();
            albumNode.put("name", album.getName());

            // Create an array for the names of the songs in the album
            ArrayNode songsArray = albumNode.putArray("songs");
            for (Song song : album.getSongs()) {
                songsArray.add(song.getName());
            }

            albumsArray.add(albumNode);
        }

        return resultNode;
    }

    /**
     * Is valid date boolean.
     *
     * @param date the date
     * @return the boolean
     */
    public boolean isValidDate(final String date) {
        // Separate the string using the '-' character
        String[] dateComponents = date.split("-");

        // Converts components to int variables
        int day = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int year = Integer.parseInt(dateComponents[2]);

        if (day > LAST_DAY || month > LAST_MONTH || year < FIRST_YEAR || year > CURRENT_YEAR) {
            return false;
        } else if (month == FEBRUARY && day > LAST_FEBRUARY_DAY) {
            return false;
        }

        return true;
    }

    /**
     * Add event string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String addEvent(final CommandInput commandInput) {
        for (Event event : events) {
            if (event.getName().equals(commandInput.getName())) {
                return commandInput.getUsername() + " has another event with the same name.";
            }
        }

        if (!isValidDate(commandInput.getDate())) {
            return "Event for " + commandInput.getUsername() + " does not have a valid date.";
        }

        events.add(new Event(commandInput.getName(), commandInput.getUsername(),
                commandInput.getDescription(), commandInput.getDate()));

        return commandInput.getUsername() + " has added new event successfully.";
    }

    /**
     * Add merch string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String addMerch(final CommandInput commandInput) {
        for (Merch merch : merches) {
            if (merch.getName().equals(commandInput.getName())) {
                return commandInput.getUsername() + " has merchandise with the same name.";
            }
        }

        if (commandInput.getPrice() < 0) {
            return "Price for merchandise can not be negative.";
        }

        merches.add(new Merch(commandInput.getName(), commandInput.getUsername(),
                commandInput.getDescription(), commandInput.getPrice()));

        return commandInput.getUsername() + " has added new merchandise successfully.";
    }

    /**
     *
     * @return artist page
     */
    public String printArtistPage() {
        StringBuilder result = new StringBuilder("Albums:\n\t");

        // Albums
        result.append(albums.isEmpty() ? "[]" : getAlbumNames()).append("\n\n");

        // Merch
        result.append("Merch:\n\t");
        result.append(merches.isEmpty() ? "[]" : getMerchandiseInfo()).append("\n\n");

        // Events
        result.append("Events:\n\t");
        result.append(events.isEmpty() ? "[]" : getEventInfo());

        return result.toString();
    }

    private String getAlbumNames() {
        List<String> albumNames = new ArrayList<>();
        for (Album album : albums) {
            albumNames.add(album.getName());
        }
        return albumNames.toString();
    }

    private String getMerchandiseInfo() {
        List<String> merchInfo = new ArrayList<>();
        for (Merch merch : merches) {
            merchInfo.add(merch.getName() + " - " + merch.getPrice()
                    + ":\n\t" + merch.getDescription());
        }
        return merchInfo.toString();
    }

    private String getEventInfo() {
        List<String> eventInfo = new ArrayList<>();
        for (Event event : events) {
            eventInfo.add(event.getName() + " - " + event.getDate()
                    + ":\n\t" + event.getDescription());
        }
        return eventInfo.toString();
    }

    /**
     * Is using album boolean.
     *
     * @param album the album
     * @return the boolean
     */
    public boolean isUsingAlbum(final Album album) {
        Admin admin = Admin.getInstance();
        for (User user : admin.getUsers()) {
            Player player = user.getPlayer();
            PlayerSource playerSource = player.getSource();
            if (playerSource != null) {
                AudioCollection audioCollection = playerSource.getAudioCollection();

                String type = player.getType();

                if (type.equals("song")) {
                    Song song = (Song) playerSource.getAudioFile();
                    if (album.getSongs().contains(song)) {
                        return true;
                    }
                } else if (type.equals("playlist")) {
                    Playlist playlist = (Playlist) audioCollection;
                    for (Song song : playlist.getSongs()) {
                        if (album.getSongs().contains(song)) {
                            return true;
                        }
                    }
                } else if (type.equals("album")) {
                    if (audioCollection.getName().equals(album.getName())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Remove album string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String removeAlbum(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        int haveAlbum = -1;
        for (int i = 0; i < albums.size(); i++) {
            Album album = albums.get(i);
            if (album.getName().equals(commandInput.getName())) {
                haveAlbum = i;
                break;
            }
        }

        if (haveAlbum == -1) {
            return commandInput.getUsername() + " doesn't have an album with the given name.";
        }

        Album album = albums.get(haveAlbum);

        if (isUsingAlbum(album)) {
            return commandInput.getUsername() + " can't delete this album.";
        }

        admin.removeAlbum(album);
        albums.remove(album);
        album = null;

        return commandInput.getUsername() + " deleted the album successfully.";
    }

    /**
     * Add using users.
     *
     * @param user the user
     */
    public void addUsingUsers(final User user) {
        usingUsers.add(new UsingUser(user));
    }

    /**
     * Is used boolean.
     *
     * @return the boolean
     */
    public boolean isUsed() {
        for (UsingUser usingUser : usingUsers) {
            if (usingUser.isUsingArtis() || usingUser.isUsingBySelect()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Remove event string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String removeEvent(final CommandInput commandInput) {
        int position = -1;

        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            if (event.getName().equals(commandInput.getName())) {
                position = i;
                break;
            }
        }

        if (position == -1) {
            return commandInput.getUsername() + " doesn't have an event with the given name.";
        }

        events.remove(position);
        return commandInput.getUsername() + " deleted the event successfully.";
    }

    /**
     * Gets total likes.
     *
     * @return the total likes
     */
    public int getTotalLikes() {
        int totalLikes = 0;

        for (Album album : albums) {
            totalLikes += album.getTotalLikes();
        }

        return totalLikes;
    }

    /**
     * Gets using users.
     *
     * @return the using users
     */
    public ArrayList<UsingUser> getUsingUsers() {
        return usingUsers;
    }

    /**
     *
     * @return the name
     */
    @Override
    public String getName() {
        return super.getName();
    }
}
