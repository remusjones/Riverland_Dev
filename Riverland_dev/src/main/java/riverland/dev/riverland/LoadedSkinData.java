package riverland.dev.riverland;

import org.json.simple.JSONObject;

public class LoadedSkinData
{
    String uuid = null;
    String textureEncoded = null;
    String signature = null;
    String url = null;

    /**Checks if the skin data is a valid skin data.*/
    // checks if the contents are valid ..
    public boolean isValid() {
        boolean isValid = true;
        if (uuid == null || uuid.length() == 0) {
            isValid = false;
        }
        if (textureEncoded == null || textureEncoded.length() == 0) {
            isValid = false;
        }
        if (signature == null || signature.length() == 0) {
            isValid = false;
        }
        if (url == null || url.length() == 0) {
            isValid = false;
        }
        return isValid;
    }

}
