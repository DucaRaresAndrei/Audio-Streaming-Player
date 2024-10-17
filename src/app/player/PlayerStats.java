package app.player;

import app.utils.Enums;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * The type Player stats.
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerStats {
    private final String name;
    private final int remainedTime;
    private String repeat;
    private final boolean shuffle;
    private final boolean paused;

    /**
     * Instantiates a new Player stats.
     *
     * @param name         the name
     * @param remainedTime the remained time
     * @param repeatMode   the repeat mode
     * @param shuffle      the shuffle
     * @param paused       the paused
     */
    public PlayerStats(final String name,
                       final int remainedTime,
                       final Enums.RepeatMode repeatMode,
                       final boolean shuffle,
                       final boolean paused) {
        this.name = name;
        this.remainedTime = remainedTime;
        this.paused = paused;
        switch (repeatMode) {
            case REPEAT_ALL -> {
                this.repeat = "Repeat All";
            }
            case REPEAT_ONCE -> {
                this.repeat = "Repeat Once";
            }
            case REPEAT_INFINITE -> {
                this.repeat = "Repeat Infinite";
            }
            case REPEAT_CURRENT_SONG -> {
                this.repeat = "Repeat Current Song";
            }
            case NO_REPEAT -> {
                this.repeat = "No Repeat";
            }
            default -> {
                this.repeat = "";
            }
        }
        this.shuffle = shuffle;
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
     * Gets remained time.
     *
     * @return the remained time
     */
    public int getRemainedTime() {
        return remainedTime;
    }

    /**
     * Gets repeat.
     *
     * @return the repeat
     */
    public String getRepeat() {
        return repeat;
    }

    /**
     * Is shuffle boolean.
     *
     * @return the boolean
     */
    public boolean isShuffle() {
        return shuffle;
    }

    /**
     * Is paused boolean.
     *
     * @return the boolean
     */
    public boolean isPaused() {
        return paused;
    }
}
