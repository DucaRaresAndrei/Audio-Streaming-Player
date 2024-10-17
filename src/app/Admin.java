package app;

import app.audio.Collections.Album;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.player.Player;
import app.player.PlayerSource;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The type Admin.
 */
public final class Admin {
    private static Admin instance = null;
    private List<User> users;
    private List<Artist> artists;
    private List<Host> hosts;
    private List<Song> songs;
    private List<Podcast> podcasts;
    private int timestamp;
    private static final int LIMIT = 5;

    private Admin() {
        users = new ArrayList<>();
        artists = new ArrayList<>();
        hosts = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * Add songs.
     *
     * @param songInputList the song input list
     */
    public void addSongs(final List<Song> songInputList) {
        for (Song songInput : songInputList) {
            if (!songs.contains(songInput)) {
                songs.add(songInput);
            }
        }
    }


    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                                         episodeInput.getDuration(),
                                         episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }


    /**
     * Gets artists.
     *
     * @return the artists
     */
    public List<Artist> getArtists() {
        return new ArrayList<>(artists);
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();
        for (Artist artist : artists) {
            albums.addAll(artist.getAlbums());
        }
        return albums;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets artist.
     *
     * @param username the username
     * @return the artist
     */
    public Artist getArtist(final String username) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }

    /**
     * Gets host.
     *
     * @param username the username
     * @return the host
     */
    public Host getHost(final String username) {
        for (Host host : hosts) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }
        return null;
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            if (user.isConnectionStatus()) {
                user.simulateTime(elapsed);
            }
        }
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Gets top 5 albums.
     *
     * @return the top 5 albums
     */
    public List<String> getTop5Albums() {
        List<Album> sortedAlbums = new ArrayList<>(getAlbums());

        sortedAlbums.sort(Comparator.comparingInt(Album::getTotalLikes)
                        .reversed()
                        .thenComparing(Album::getName));

        List<String> topAlbums = new ArrayList<>();
        int count = 0;
        for (Album album : sortedAlbums) {
            if (count >= LIMIT) {
                break;
            }
            topAlbums.add(album.getName());
            count++;
        }
        return topAlbums;
    }

    /**
     * Gets top 5 artists.
     *
     * @return the top 5 artists
     */
    public List<String> getTop5Artists() {
        List<Artist> sortedArtists = new ArrayList<>(getArtists());
        sortedArtists.sort(Comparator.comparingInt(Artist::getTotalLikes)
                .reversed()
                .thenComparing(Artist::getTimestamp, Comparator.naturalOrder()));
        List<String> topArtists = new ArrayList<>();
        int count = 0;
        for (Artist artist : sortedArtists) {
            if (count >= LIMIT) {
                break;
            }
            topArtists.add(artist.getUsername());
            count++;
        }
        return topArtists;
    }

    /**
     * Reset.
     */
    public void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        artists = new ArrayList<>();
        hosts = new ArrayList<>();
        timestamp = 0;
    }

    /**
     * Gets online users.
     *
     * @return the online users
     */
    public ArrayList<String> getOnlineUsers() {
        ArrayList<String> results = new ArrayList<>();
        for (User user : users) {
            if (user.isConnectionStatus()) {
                results.add(user.getUsername());
            }
        }

        return  results;
    }

    /**
     * Add user string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String addUser(final CommandInput commandInput) {
        String type = commandInput.getType();
        boolean isTaken = false;

        for (Artist artist : artists) {
            if (artist.getUsername().equals(commandInput.getUsername())) {
                isTaken = true;
                break;
            }
        }

        for (Host host : hosts) {
            if (isTaken) {
                break;
            }

            if (host.getUsername().equals(commandInput.getUsername())) {
                isTaken = true;
                break;
            }
        }

        for (User user : users) {
            if (isTaken) {
                break;
            }

            if (user.getUsername().equals(commandInput.getUsername())) {
                isTaken = true;
                break;
            }
        }

        if (isTaken) {
            return "The username " + commandInput.getUsername() + " is already taken.";
        }

        if (type.equals("artist")) {
            Artist artist = new Artist(commandInput.getUsername(), commandInput.getAge(),
                    commandInput.getCity(), commandInput.getTimestamp());
            artists.add(artist);
            for (User user : users) {
                artist.addUsingUsers(user);
            }
        } else if (type.equals("host")) {
            Host host = new Host(commandInput.getUsername(), commandInput.getAge(),
                    commandInput.getCity());
            hosts.add(host);
            for (User user : users) {
                host.addUsingUsers(user);
            }
        } else if (type.equals("user")) {
            User user = new User(commandInput.getUsername(), commandInput.getAge(),
                    commandInput.getCity());
            users.add(user);
            for (Artist artist : artists) {
                artist.addUsingUsers(user);
            }
            for (Host host : hosts) {
                host.addUsingUsers(user);
            }
        }

        return "The username " + commandInput.getUsername() + " has been added successfully.";
    }

    /**
     * Gets all usernames.
     *
     * @return the all usernames
     */
    public List<String> getAllUsernames() {
        List<String> usenames = new ArrayList<>();

        for (User user : users) {
            usenames.add(user.getUsername());
        }

        for (Artist artist : artists) {
            usenames.add(artist.getUsername());
        }

        for (Host host : hosts) {
            usenames.add(host.getUsername());
        }

        return usenames;
    }

    /**
     * Gets all users.
     *
     * @param commandInput the command input
     * @return the all users
     */
    public ObjectNode getAllUsers(final CommandInput commandInput) {
        ObjectNode resultNode = JsonNodeFactory.instance.objectNode();
        resultNode.put("command", commandInput.getCommand());
        resultNode.put("timestamp", commandInput.getTimestamp());

        // Retrieve all usernames
        List<String> allUsernames = getAllUsernames();

        // Create an array for the result
        ArrayNode resultArray = resultNode.putArray("result");

        // Add each username to the array
        for (String username : allUsernames) {
            resultArray.add(username);
        }

        return resultNode;
    }

    /**
     * Is using user boolean.
     *
     * @param userName the userName
     * @return the boolean
     */
    public boolean isUsingUser(final User userName) {
        for (User user : instance.getUsers()) {
            Player player = user.getPlayer();
            PlayerSource playerSource = player.getSource();
            if (playerSource != null) {
                AudioCollection audioCollection = playerSource.getAudioCollection();

                String type = player.getType();

                if (type.equals("playlist")) {
                    Playlist playlist = (Playlist) audioCollection;
                    if (userName.getPlaylists().contains(playlist)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Delete user string.
     *
     * @param username the username
     * @param type     the type
     * @return the string
     */
    public String deleteUser(final String username, final String type) {
        if (type.equals("artist")) {
            Artist artist = getArtist(username);

            if (artist.isUsed()) {
                return username + " can't be deleted.";
            }

            for (Album album : artist.getAlbums()) {
                removeAlbum(album);
            }

            artist.artistClear();
            artists.remove(artist);
            artist = null;
            return username + " was successfully deleted.";
        } else if (type.equals("host")) {
            Host host = getHost(username);

            if (host.isUsed()) {
                return username + " can't be deleted.";
            }

            for (Podcast podcast : host.getPodcasts()) {
                removePodcast(podcast);
            }

            host.hostClear();
            hosts.remove(host);
            host = null;
            return username + " was successfully deleted.";
        } else if (type.equals("user")) {
            User user = getUser(username);

            if (isUsingUser(user)) {
                return username + " can't be deleted.";
            }

            for (Playlist playlist : user.getPlaylists()) {
                removePlaylists(playlist);
            }

            user.clear();
            users.remove(user);
            user = null;
            return username + " was successfully deleted.";
        }

        return "";
    }

    /**
     * Remove playlists.
     *
     * @param playlist the playlist
     */
    public void removePlaylists(final Playlist playlist) {
        for (User user : users) {
            if (user.getFollowedPlaylists().contains(playlist)) {
                user.getFollowedPlaylists().remove(playlist);
            }
        }
    }

    /**
     * Remove album.
     *
     * @param album the album
     */
    public void removeAlbum(final Album album) {
        for (Song song : album.getSongs()) {
            if (songs.contains(song)) {
                songs.remove(song);
            }

            for (User user : users) {
                if (user.getLikedSongs().contains(song)) {
                    user.getLikedSongs().remove(song);
                }

                for (Playlist playlist : user.getPlaylists()) {
                    if (playlist.getSongs().contains(song)) {
                        playlist.getSongs().remove(song);
                    }
                }
            }
        }
    }

    /**
     * Remove podcast.
     *
     * @param podcast the podcast
     */
    public void removePodcast(final Podcast podcast) {
        podcasts.remove(podcast);
    }

    /**
     * Add podcasts.
     *
     * @param podcast the podcast
     */
    public void addPodcasts(final Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Gets hosts.
     *
     * @return the hosts
     */
    public List<Host> getHosts() {
        return hosts;
    }
}
