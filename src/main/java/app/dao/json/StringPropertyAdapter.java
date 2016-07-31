package app.dao.json;

import com.google.gson.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.lang.reflect.Type;

/**
 * Created by Antic on 31.07.2016.
 */
final class StringPropertyAdapter implements JsonSerializer<StringProperty>, JsonDeserializer<StringProperty>
{
  @Override
  public StringProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    return new SimpleStringProperty(json.getAsJsonPrimitive().getAsString());
  }

  @Override
  public JsonElement serialize(StringProperty src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.getValue());
  }
}
