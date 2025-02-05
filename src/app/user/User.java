package app.user;

import app.Admin;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.user.ArtistThings.UsingUser;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type User.
 */
public class User extends UserSkelet {
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    private final Player player;
    private final SearchBar searchBar;
    private boolean lastSearched;
    private boolean connectionStatus;
    private String currentPage;
    private LibraryEntry ownerPage;
    private static final int LIMIT = 5;

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public User(final String username, final int age, final String city) {
        super(username, age, city);
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player(username);
        searchBar = new SearchBar(super.getUsername());
        lastSearched = false;
        connectionStatus = true;
        currentPage = new String("homePage");
    }

    /**
     * Clear.
     */
    public void clear() {
        for (Song song : likedSongs) {
            song.dislike();
        }

        for (Playlist playlist : followedPlaylists) {
            playlist.decreaseFollowers();
        }

        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        lastSearched = false;
        connectionStatus = true;
        currentPage = new String("homePage");
    }

    /**
     * Search array list.
     *
     * @param filters the filters
     * @param type    the type
     * @return the array list
     */
    public ArrayList<String> search(final Filters filters, final String type) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        ArrayList<String> results = new ArrayList<>();
        List<LibraryEntry> libraryEntries = searchBar.search(filters, type);

        for (LibraryEntry libraryEntry : libraryEntries) {
            results.add(libraryEntry.getName());
        }

        return results;
    }

    /**
     * Start using artist by select.
     *
     * @param artistName the artist name
     */
    public void startUsingArtistBySelect(final String artistName) {
        Admin admin = Admin.getInstance();
        for (Artist artist : admin.getArtists()) {
            if (artist.getUsername().equals(artistName)) {
                for (UsingUser usingUser : artist.getUsingUsers()) {
                    if (usingUser.getUser().getName().equals(getUsername())) {
                        usingUser.setUsingBySelect(true);
                        break;
                    }
                }
                break;
            }
        }
    }

    /**
     * Start using host by select.
     *
     * @param hostName the host name
     */
    public void startUsingHostBySelect(final String hostName) {
        Admin admin = Admin.getInstance();
        for (Host host : admin.getHosts()) {
            if (host.getUsername().equals(hostName)) {
                for (UsingUser usingUser : host.getUsingUsers()) {
                    if (usingUser.getUser().getName().equals(getUsername())) {
                        usingUser.setUsingBySelect(true);
                        break;
                    }
                }
                break;
            }
        }
    }

    /**
     * Select string.
     *
     * @param itemNumber the item number
     * @return the string
     */
    public String select(final int itemNumber) {
        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        LibraryEntry selected = searchBar.select(itemNumber);

        if (selected == null) {
            return "The selected ID is too high.";
        }

        if (searchBar.getLastSearchType().equals("artist")) {
            currentPage = new String("artistPage");
            ownerPage = selected;
            startUsingArtistBySelect(selected.getName());
            return "Successfully selected %s's page.".formatted(selected.getName());
        }

        if (searchBar.getLastSearchType().equals("host")) {
            currentPage = new String("hostPage");
            ownerPage = selected;
            startUsingHostBySelect(selected.getName());
            return "Successfully selected %s's page.".formatted(selected.getName());
        }


        return "Successfully selected %s.".formatted(selected.getName());
    }

    /**
     * Load string.
     *
     * @return the string
     */
    public String load() {
        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }

        if (!searchBar.getLastSearchType().equals("song")
            && ((AudioCollection) searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }

    /**
     * Play pause string.
     *
     * @return the string
     */
    public String playPause() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }

        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }

    /**
     * Repeat string.
     *
     * @return the string
     */
    public String repeat() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch (repeatMode) {
            case NO_REPEAT -> {
                repeatStatus = "no repeat";
            }
            case REPEAT_ONCE -> {
                repeatStatus = "repeat once";
            }
            case REPEAT_ALL -> {
                repeatStatus = "repeat all";
            }
            case REPEAT_INFINITE -> {
                repeatStatus = "repeat infinite";
            }
            case REPEAT_CURRENT_SONG -> {
                repeatStatus = "repeat current song";
            }
            default -> {
                repeatStatus = "";
            }
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    /**
     * Shuffle string.
     *
     * @param seed the seed
     * @return the string
     */
    public String shuffle(final Integer seed) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!player.getType().equals("playlist") && !player.getType().equals("album")) {
            return "The loaded source is not a playlist or an album.";
        }


        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }

    /**
     * Forward string.
     *
     * @return the string
     */
    public String forward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }

    /**
     * Backward string.
     *
     * @return the string
     */
    public String backward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }

    /**
     * Like string.
     *
     * @return the string
     */
    public String like() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType().equals("playlist")
                && !player.getType().equals("album")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }

    /**
     * Next string.
     *
     * @return the string
     */
    public String next() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Prev string.
     *
     * @return the string
     */
    public String prev() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Create playlist string.
     *
     * @param name      the name
     * @param timestamp the timestamp
     * @return the string
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }

        playlists.add(new Playlist(name, super.getUsername(), timestamp));

        return "Playlist created successfully.";
    }

    /**
     * Add remove in playlist string.
     *
     * @param id the id
     * @return the string
     */
    public String addRemoveInPlaylist(final int id) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (id > playlists.size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = playlists.get(id - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    /**
     * Switch playlist visibility string.
     *
     * @param playlistId the playlist id
     * @return the string
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    /**
     * Show playlists array list.
     *
     * @return the array list
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * Follow string.
     *
     * @return the string
     */
    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(super.getUsername())) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    /**
     * Gets player stats.
     *
     * @return the player stats
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    /**
     * Show preferred songs array list.
     *
     * @return the array list
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     * Gets preferred genre.
     *
     * @return the preferred genre
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    /**
     * Returns the first 5 songs according to the number of likes.
     *
     * @return List of song names
     */
    public List<String> getTopLikedSongsNames() {
        return likedSongs.stream()
                .sorted(Comparator.comparingInt(Song::getLikes).reversed())
                .limit(LIMIT)
                .map(Song::getName)
                .collect(Collectors.toList());
    }

    /**
     * Returns the first 5 playlists according to the total number of likes.
     *
     * @return List of playlists names
     */
    public List<String> getTopFollowedPlaylistsNames() {
        return followedPlaylists.stream()
                .sorted(Comparator.comparingInt(Playlist::getTotalLikes).reversed())
                .limit(LIMIT)
                .map(Playlist::getName)
                .collect(Collectors.toList());
    }

    /**
     * Print home string.
     *
     * @param songs     the songs
     * @param playlists the playlists
     * @return the string
     */
    public static String printHome(final List<String> songs, final List<String> playlists) {
        StringBuilder result = new StringBuilder("Liked songs:\n\t");
        result.append(songs.isEmpty() ? "[]" : songs).append("\n\nFollowed playlists:\n\t");
        result.append(playlists.isEmpty() ? "[]" : playlists);
        return result.toString();
    }

    /**
     * Print liked content page string.
     *
     * @return the string
     */
    public String printLikedContentPage() {
        StringBuilder result = new StringBuilder("Liked songs:\n\t[");

        // Add every liked song
        for (int i = 0; i < likedSongs.size(); i++) {
            Song song = likedSongs.get(i);
            result.append(song.getName()).append(" - ").append(song.getArtist());

            if (i != likedSongs.size() - 1) {
                result.append(", ");
            }
        }

        result.append("]\n\nFollowed playlists:\n\t[");

        // Add every followed playlist
        for (int i = 0; i < followedPlaylists.size(); i++) {
            Playlist playlist = followedPlaylists.get(i);
            result.append(playlist.getName()).append(" - ").append(playlist.getOwner());

            if (i != followedPlaylists.size() - 1) {
                result.append(", ");
            }
        }

        result.append("]");

        return result.toString();
    }

    /**
     * Print current page string.
     *
     * @return the string
     */
    public String printCurrentPage() {

        if (currentPage.equals("likedContentPage")) {
            return printLikedContentPage();
        } else if (currentPage.equals("artistPage")) {
            if (ownerPage != null) {
                return ownerPage.printArtistPage();
            }
        } else if (currentPage.equals("hostPage")) {
            if (ownerPage != null) {
                return ownerPage.printHostPage();
            }
        }

        return printHome(this.getTopLikedSongsNames(), this.getTopFollowedPlaylistsNames());
    }

    /**
     * Change page string.
     *
     * @param nextPage the next page
     * @return the string
     */
    public String changePage(final String nextPage) {

        if (currentPage.equals("artistPage")) {
            Artist artist = (Artist) ownerPage;

            for (UsingUser usingUser : artist.getUsingUsers()) {
                if (usingUser.getUser().getName().equals(getUsername())) {
                    usingUser.setUsingBySelect(false);
                    break;
                }
            }
        } else if (currentPage.equals("hostPage")) {
            Host host = (Host) ownerPage;

            for (UsingUser usingUser : host.getUsingUsers()) {
                if (usingUser.getUser().getName().equals(getUsername())) {
                    usingUser.setUsingBySelect(false);
                    break;
                }
            }
        }

        if (nextPage.equals("Home")) {
            currentPage = new String("homePage");
            return getUsername() + " accessed " + nextPage + " successfully.";
        } else if (nextPage.equals("LikedContent")) {
            currentPage = new String("likedContentPage");
            return getUsername() + " accessed " + nextPage + " successfully.";
        } else {
            return getUsername() + " is trying to access a non-existent page.";
        }
    }

    /**
     * Simulate time.
     *
     * @param time the time
     */
    public void simulateTime(final int time) {
        player.simulatePlayer(time);
    }

    /**
     * Switch connection status.
     */
    public void switchConnectionStatus() {
        connectionStatus = !connectionStatus;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return super.getUsername();
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * Gets liked songs.
     *
     * @return the liked songs
     */
    public ArrayList<Song> getLikedSongs() {
        return likedSongs;
    }

    /**
     * Gets followed playlists.
     *
     * @return the followed playlists
     */
    public ArrayList<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Is connection status boolean.
     *
     * @return the boolean
     */
    public boolean isConnectionStatus() {
        return connectionStatus;
    }
}
