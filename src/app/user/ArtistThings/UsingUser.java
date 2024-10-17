package app.user.ArtistThings;

import app.user.User;

/**
 * The type Using user.
 */
public class UsingUser {
    private User user;
    private boolean isUsingArtis;
    private boolean isUsingBySelect;

    /**
     * Instantiates a new Using user.
     *
     * @param user the user
     */
    public UsingUser(final User user) {
        this.user = user;
        this.isUsingArtis = false;
        this.isUsingBySelect = false;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets using artis.
     *
     * @param usingArtis the using artis
     */
    public void setUsingArtis(final boolean usingArtis) {
        isUsingArtis = usingArtis;
    }

    /**
     * Sets using by select.
     *
     * @param usingBySelect the using by select
     */
    public void setUsingBySelect(final boolean usingBySelect) {
        isUsingArtis = usingBySelect;
    }

    /**
     * Is using by select boolean.
     *
     * @return the boolean
     */
    public boolean isUsingBySelect() {
        return isUsingBySelect;
    }

    /**
     * Is using artis boolean.
     *
     * @return the boolean
     */
    public boolean isUsingArtis() {
        return isUsingArtis;
    }
}
