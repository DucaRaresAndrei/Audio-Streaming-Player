package app.audio.Collections;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public final class PlaylistOutputSerializer extends JsonSerializer<PlaylistOutput> {
    @Override
    public void serialize(final PlaylistOutput playlistOutput, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", playlistOutput.getName());
        jsonGenerator.writeObjectField("songs", playlistOutput.getSongs());
        jsonGenerator.writeStringField("visibility", playlistOutput.getVisibility());
        jsonGenerator.writeNumberField("followers", playlistOutput.getFollowers());
        jsonGenerator.writeEndObject();
    }
}
