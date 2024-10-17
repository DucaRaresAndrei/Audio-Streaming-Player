package app.user.ArtistThings;

/**
 * The type Merch.
 */
public class Merch {
    private String name;
    private String owner;
    private String description;
    private int price;

    /**
     * Instantiates a new Merch.
     *
     * @param name        the name
     * @param owner       the owner
     * @param description the description
     * @param price       the price
     */
    public Merch(final String name, final String owner, final String description, final int price) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.price = price;
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
     * Gets price.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }
}
