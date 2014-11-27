package anespace.imagepicker;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

/**
 * Created by Abedron
 */
public class ImagePickerExtension implements FREExtension {
	
    @Override
    public FREContext createContext(String s) {
        return new ImagePickerContext();
    }
    
    @Override
    public void initialize() {

    }

    @Override
    public void dispose() {

    }
}
