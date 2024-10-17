package app.user.HostThings;

/**
 * The type Announcement.
 */
public class Announcement {
    private String name;
    private String owner;
    private String description;

    /**
     * Instantiates a new Announcement.
     *
     * @param name        the name
     * @param owner       the owner
     * @param description the description
     */
    public Announcement(final String name, final String owner, final String description) {
        this.name = name;
        this.owner = owner;
        this.description = description;
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
}
