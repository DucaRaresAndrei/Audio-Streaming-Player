package app.audio.Files;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public final class Episode extends AudioFile {
    private final String description;

    @JsonCreator
    public Episode(@JsonProperty("name") final String name,
                   @JsonProperty("duration") final Integer duration,
                   @JsonProperty("description") final String description) {
        super(name, duration);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Integer getDuration() {
        return super.getDuration();
    }
}
