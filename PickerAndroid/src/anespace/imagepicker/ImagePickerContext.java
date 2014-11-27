package anespace.imagepicker;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abedron
 */
public class ImagePickerContext extends FREContext {


    @Override
    public Map<String, FREFunction> getFunctions() {
        Map<String, FREFunction> map = new HashMap<String, FREFunction>();
        map.put("browseForImage", new ImagePickerFunction());
        map.put("getImage", new GetImageFunction());
        return map;
    }


    @Override
    public void dispose() {

    }
}
