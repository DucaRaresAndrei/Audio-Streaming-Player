package app;

import app.audio.Collections.PlaylistOutput;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Command runner.
 */
public final class CommandRunner {
    /**
     * The Object mapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    private CommandRunner() {
    }

    /**
     * Search object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode search(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        ArrayList<String> results;

        if (!user.isConnectionStatus()) {
            results = new ArrayList<>();
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            objectNode.put("results", objectMapper.valueToTree(results));
            return  objectNode;
        }


        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();

        results = user.search(filters, type);
        String message = "Search returned " + results.size() + " results";

        objectNode.put("message", message);
        objectNode.put("results", objectMapper.valueToTree(results));

        return objectNode;
    }


    /**
     * Select object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode select(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.select(commandInput.getItemNumber());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Load object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode load(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.load();
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Play pause object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.playPause();
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Repeat object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.repeat();
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Shuffle object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.shuffle(seed);
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Forward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.forward();
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Backward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.backward();
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Like object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode like(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.like();
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Next object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode next(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.next();
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Prev object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.prev();
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Create playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.createPlaylist(commandInput.getPlaylistName(),
                                             commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Add remove in playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Switch visibility object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Show playlists object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Follow object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        String message = user.follow();
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Status object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode status(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    /**
     * Show liked songs object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets preferred genre.
     *
     * @param commandInput the command input
     * @return the preferred genre
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    /**
     * Gets top 5 songs.
     *
     * @param commandInput the command input
     * @return the top 5 songs
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        List<String> songs = admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets top 5 playlists.
     *
     * @param commandInput the command input
     * @return the top 5 playlists
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        List<String> playlists = admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        List<String> albums = admin.getTop5Albums();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        List<String> albums = admin.getTop5Artists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));

        return objectNode;
    }

    /**
     *
     */
    private static ObjectNode createErrorNode(final CommandInput commandInput) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("command", commandInput.getCommand());
        errorNode.put("user", commandInput.getUsername());
        errorNode.put("timestamp", commandInput.getTimestamp());
        errorNode.put("message", "The username "
                + commandInput.getUsername() + " doesn't exist.");
        return errorNode;
    }

    /**
     *
     */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());
        Artist artist = admin.getArtist(commandInput.getUsername());
        Host host = admin.getHost(commandInput.getUsername());

        if (user == null) {
            if (artist != null || host != null) {
                ObjectNode errorNode = objectMapper.createObjectNode();
                errorNode.put("command", commandInput.getCommand());
                errorNode.put("user", commandInput.getUsername());
                errorNode.put("timestamp", commandInput.getTimestamp());
                errorNode.put("message", commandInput.getUsername()
                        + " is not a normal user.");
                return errorNode;
            } else {
                ObjectNode errorNode = createErrorNode(commandInput);
                return errorNode;
            }
        }

        user.switchConnectionStatus();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", commandInput.getUsername()
                + " has changed status successfully.");

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        ArrayList<String> results = admin.getOnlineUsers();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(results));

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode addUser(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        String message = admin.addUser(commandInput);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        Artist artist = admin.getArtist(commandInput.getUsername());
        User user = admin.getUser(commandInput.getUsername());
        Host host = admin.getHost(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (artist == null) {
            if (user != null || host != null) {
                objectNode.put("message", commandInput.getUsername()
                        + " is not an artist.");
                return objectNode;
            } else {
                objectNode.put("message", "The username " + commandInput.getUsername()
                        + " doesn't exist.");
                return objectNode;
            }
        }

        String message = artist.addAlbum(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        Artist artist = admin.getArtist(commandInput.getUsername());
        User user = admin.getUser(commandInput.getUsername());
        Host host = admin.getHost(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (host == null) {
            if (user != null || artist != null) {
                objectNode.put("message", commandInput.getUsername()
                        + " is not a host.");
                return objectNode;
            } else {
                objectNode.put("message", "The username " + commandInput.getUsername()
                        + " doesn't exist.");
                return objectNode;
            }
        }

        String message = host.addPodcast(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        Artist artist = admin.getArtist(commandInput.getUsername());

        return artist.showAlbums(commandInput);
    }

    /**
     *
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        objectNode.put("message", user.printCurrentPage());

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode changePage(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        User user = admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode errorNode = createErrorNode(commandInput);
            return errorNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.isConnectionStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return  objectNode;
        }

        objectNode.put("message", user.changePage(commandInput.getNextPage()));

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        Artist artist = admin.getArtist(commandInput.getUsername());
        User user = admin.getUser(commandInput.getUsername());
        Host host = admin.getHost(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (artist == null) {
            if (user != null || host != null) {
                objectNode.put("message", commandInput.getUsername()
                        + " is not an artist.");
                return objectNode;
            } else {
                objectNode.put("message", "The username " + commandInput.getUsername()
                        + " doesn't exist.");
                return objectNode;
            }
        }

        String message = artist.addEvent(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        Artist artist = admin.getArtist(commandInput.getUsername());
        User user = admin.getUser(commandInput.getUsername());
        Host host = admin.getHost(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (artist == null) {
            if (user != null || host != null) {
                objectNode.put("message", commandInput.getUsername()
                        + " is not an artist.");
                return objectNode;
            } else {
                objectNode.put("message", "The username " + commandInput.getUsername()
                        + " doesn't exist.");
                return objectNode;
            }
        }

        String message = artist.addMerch(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        return admin.getAllUsers(commandInput);
    }

    /**
     *
     */
    public static ObjectNode deleteUser(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        Artist artist = admin.getArtist(commandInput.getUsername());
        User user = admin.getUser(commandInput.getUsername());
        Host host = admin.getHost(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (artist == null && user == null && host == null) {
            objectNode.put("message", "The username " + commandInput.getUsername()
                    + " doesn't exist.");
            return objectNode;
        }

        String type = "";

        if (artist != null) {
            type = "artist";
        } else if (user != null) {
            type = "user";
        } else if (host != null) {
            type = "host";
        }

        String message = admin.deleteUser(commandInput.getUsername(), type);
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        Artist artist = admin.getArtist(commandInput.getUsername());
        User user = admin.getUser(commandInput.getUsername());
        Host host = admin.getHost(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (host == null) {
            if (user != null || artist != null) {
                objectNode.put("message", commandInput.getUsername()
                        + " is not a host.");
                return objectNode;
            } else {
                objectNode.put("message", "The username " + commandInput.getUsername()
                        + " doesn't exist.");
                return objectNode;
            }
        }

        String message = host.addAnnouncement(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        Artist artist = admin.getArtist(commandInput.getUsername());
        User user = admin.getUser(commandInput.getUsername());
        Host host = admin.getHost(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (host == null) {
            if (user != null || artist != null) {
                objectNode.put("message", commandInput.getUsername()
                        + " is not a host.");
                return objectNode;
            } else {
                objectNode.put("message", "The username " + commandInput.getUsername()
                        + " doesn't exist.");
                return objectNode;
            }
        }

        String message = host.removeAnnouncement(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        Host host = admin.getHost(commandInput.getUsername());

        return host.showPodcasts(commandInput);
    }

    /**
     *
     */
    public static ObjectNode removeAlbum(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        Artist artist = admin.getArtist(commandInput.getUsername());
        User user = admin.getUser(commandInput.getUsername());
        Host host = admin.getHost(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (artist == null) {
            if (user != null || host != null) {
                objectNode.put("message", commandInput.getUsername()
                        + " is not an artist.");
                return objectNode;
            } else {
                objectNode.put("message", "The username " + commandInput.getUsername()
                        + " doesn't exist.");
                return objectNode;
            }
        }

        String message = artist.removeAlbum(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        Artist artist = admin.getArtist(commandInput.getUsername());
        User user = admin.getUser(commandInput.getUsername());
        Host host = admin.getHost(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (host == null) {
            if (user != null || artist != null) {
                objectNode.put("message", commandInput.getUsername()
                        + " is not a host.");
                return objectNode;
            } else {
                objectNode.put("message", "The username " + commandInput.getUsername()
                        + " doesn't exist.");
                return objectNode;
            }
        }

        String message = host.removePodcast(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     *
     */
    public static ObjectNode removeEvent(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        Artist artist = admin.getArtist(commandInput.getUsername());
        User user = admin.getUser(commandInput.getUsername());
        Host host = admin.getHost(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (artist == null) {
            if (user != null || host != null) {
                objectNode.put("message", commandInput.getUsername()
                        + " is not an artist.");
                return objectNode;
            } else {
                objectNode.put("message", "The username " + commandInput.getUsername()
                        + " doesn't exist.");
                return objectNode;
            }
        }

        String message = artist.removeEvent(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }
}
