package app.user;

import app.Admin;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.player.Player;
import app.player.PlayerSource;
import app.user.ArtistThings.UsingUser;
import app.user.HostThings.Announcement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Host.
 */
public class Host extends UserSkelet {
    private ArrayList<Podcast> podcasts;
    private ArrayList<Announcement> announcements;
    private ArrayList<UsingUser> usingUsers;

    /**
     * Instantiates a new Host.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public Host(final String username, final int age, final String city) {
        super(username, age, city);
        this.podcasts = new ArrayList<>();
        this.announcements = new ArrayList<>();
        this.usingUsers = new ArrayList<>();
    }

    /**
     * Host clear.
     */
    public void hostClear() {
        this.podcasts = new ArrayList<>();
        this.announcements = new ArrayList<>();
        this.usingUsers = new ArrayList<>();
    }

    /**
     * Add podcast string.
     *
     * @param commandInput the command input
     * @return string
     */
    public String addPodcast(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();

        if (!podcasts.isEmpty()) {
            for (Podcast podcast : podcasts) {
                if (podcast.getName().equals(commandInput.getName())) {
                    return commandInput.getUsername() + " has another podcast with the same name.";
                }
            }
        }

        List<Episode> episodeCheck = commandInput.getEpisodes();

        for (int i = 0; i < episodeCheck.size() - 1; i++) {
            for (int j = i + 1; j < episodeCheck.size(); j++) {
                if (episodeCheck.get(i).getName().equals(episodeCheck.get(j).getName())) {
                    return commandInput.getUsername() + " has the same episode in this podcast.";
                }
            }
        }

        Podcast podcast = new Podcast(commandInput.getName(), commandInput.getUsername(),
                commandInput.getEpisodes());
        podcasts.add(podcast);

        admin.addPodcasts(podcast);
        return commandInput.getUsername() + " has added new podcast successfully.";
    }

    /**
     * Add announcement string.
     *
     * @param commandInput the command input
     * @return string
     */
    public String addAnnouncement(final CommandInput commandInput) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(commandInput.getName())) {
                return commandInput.getUsername()
                        + " has already added an announcement with this name.";
            }
        }


        announcements.add(new Announcement(commandInput.getName(), commandInput.getUsername(),
                commandInput.getDescription()));

        return commandInput.getUsername() + " has successfully added new announcement.";
    }

    /**
     * Remove announcement string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String removeAnnouncement(final CommandInput commandInput) {
        int position = -1;

        for (int i = 0; i < announcements.size(); i++) {
            Announcement announcement = announcements.get(i);
            if (announcement.getName().equals(commandInput.getName())) {
                position = i;
                break;
            }
        }

        if (position == -1) {
            return commandInput.getUsername() + " has no announcement with the given name.";
        }

        announcements.remove(position);
        return commandInput.getUsername() + " has successfully deleted the announcement.";
    }

    /**
     *
     * @return host page
     */
    public String printHostPage() {
        StringBuilder result = new StringBuilder("Podcasts:\n\t[");

        // Show the list of podcasts
        for (int i = 0; i < podcasts.size(); i++) {
            Podcast podcast = podcasts.get(i);

            if (i != 0) {
                result.append(" ");
            }

            result.append(podcast.getName()).append(":\n\t[");

            // Add each episode of the podcast
            for (int j = 0; j < podcast.getEpisodes().size(); j++) {
                Episode episode = podcast.getEpisodes().get(j);
                result.append(episode.getName()).append(" - ").append(episode.getDescription());

                if (j != podcast.getEpisodes().size() - 1) {
                    result.append(", ");
                }
            }

            if (i != podcasts.size() - 1) {
                result.append("]\n,");
            } else {
                result.append("]\n]\n");
            }
        }

        // Show the list of announcements
        result.append("\nAnnouncements:\n");
        result.append("\t[");

        for (int i = 0; i < announcements.size(); i++) {
            Announcement announcement = announcements.get(i);

            result.append(announcement.getName()).append(":\n\t");
            result.append(announcement.getDescription()).append("\n");

            if (i != announcements.size() - 1) {
                result.append(", ");
            }
        }

        result.append("]");
        return result.toString();
    }

    /**
     * Show podcasts object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public ObjectNode showPodcasts(final  CommandInput commandInput) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode resultNode = objectMapper.createObjectNode();

        resultNode.put("command", commandInput.getCommand());
        resultNode.put("user", commandInput.getUsername());
        resultNode.put("timestamp", commandInput.getTimestamp());

        ArrayNode podcastsArray = resultNode.putArray("result");

        // Add each podcast to the array
        for (Podcast podcast : podcasts) {
            ObjectNode podcastNode = JsonNodeFactory.instance.objectNode();
            podcastNode.put("name", podcast.getName());

            // Create an array for the podcast episode names
            ArrayNode episodesArray = podcastNode.putArray("episodes");
            for (Episode episode : podcast.getEpisodes()) {
                episodesArray.add(episode.getName());
            }

            podcastsArray.add(podcastNode);
        }

        return resultNode;
    }

    /**
     * Is using podcast boolean.
     *
     * @param podcast the podcast
     * @return the boolean
     */
    public boolean isUsingPodcast(final Podcast podcast) {
        Admin admin = Admin.getInstance();

        for (User user : admin.getUsers()) {
            Player player = user.getPlayer();
            PlayerSource playerSource = player.getSource();
            if (playerSource != null) {
                AudioCollection audioCollection = playerSource.getAudioCollection();

                String type = player.getType();

                if (type.equals("podcast")) {
                    if (audioCollection.getName().equals(podcast.getName())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Remove podcast string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String removePodcast(final CommandInput commandInput) {
        Admin admin = Admin.getInstance();
        int havePodcast = -1;
        for (int i = 0; i < podcasts.size(); i++) {
            Podcast podcast = podcasts.get(i);
            if (podcast.getName().equals(commandInput.getName())) {
                havePodcast = i;
                break;
            }
        }

        if (havePodcast == -1) {
            return commandInput.getUsername() + " doesn't have a podcast with the given name.";
        }

        Podcast podcast = podcasts.get(havePodcast);

        if (isUsingPodcast(podcast)) {
            return commandInput.getUsername() + " can't delete this podcast.";
        }

        admin.removePodcast(podcast);
        podcasts.remove(podcast);
        podcast = null;

        return commandInput.getUsername() + " deleted the podcast successfully.";
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
     *
     * @return
     */
    public String getUsername() {
        return super.getUsername();
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    /**
     * Gets using users.
     *
     * @return the using users
     */
    public ArrayList<UsingUser> getUsingUsers() {
        return usingUsers;
    }
}
