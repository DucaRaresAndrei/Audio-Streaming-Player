package app.user;

import app.audio.LibraryEntry;
import lombok.Getter;

/**
 * The type User skelet.
 */
public class UserSkelet extends LibraryEntry {
    @Getter
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;

    /**
     * Instantiates a new User skelet.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public UserSkelet(final String username, final int age, final String city) {
        super(username);
        this.username = username;
        this.age = age;
        this.city = city;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return super.getName();
    }
}
