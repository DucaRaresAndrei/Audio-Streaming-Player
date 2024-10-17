package app.audio.Files;

import app.audio.LibraryEntry;
import lombok.Getter;

/**
 * The type Audio file.
 */
@Getter
public abstract class AudioFile extends LibraryEntry {
    private final Integer duration;

    /**
     * Instantiates a new Audio file.
     *
     * @param name     the name
     * @param duration the duration
     */
    public AudioFile(final String name, final Integer duration) {
        super(name);
        this.duration = duration;
    }

    /**
     * @return the name
     */
    public String getName() {
        return super.getName();
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }
}
