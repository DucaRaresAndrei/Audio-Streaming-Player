package app.user.ArtistThings;

/**
 * The type Event.
 */
public class Event {
    private String name;
    private String owner;
    private String description;
    private String date;

    /**
     * Instantiates a new Event.
     *
     * @param name        the name
     * @param owner       the owner
     * @param description the description
     * @param date        the date
     */
    public Event(final String name, final String owner, final String description,
                 final String date) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.date = date;
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
     * Gets owner.
     *
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }
}
