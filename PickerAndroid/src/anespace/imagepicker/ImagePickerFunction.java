package anespace.imagepicker;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import anespace.imagepicker.code.ImagePickerCode;

/**
 * Created by Abedron
 */
public class ImagePickerFunction implements FREFunction {
	
    @Override
    public FREObject call(FREContext context, FREObject[] args) {
    	
        ImagePickerCode.getInstance().browseForImage(context);
        
        return null;
    }
    
}
