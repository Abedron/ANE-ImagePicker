package anespace.imagepicker.code;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Vibrator;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREWrongThreadException;

import anespace.imagepicker.ImagePickerActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImagePickerCode {
	public static final String SELECT_IMAGE_EVENT = "select image ";
	public static final String CANCEL_EVENT = "cancel ";
	public static final String ERROR_EVENT = "error ";
	
	
	private static ImagePickerCode instance;
	private FREContext context;
	
	public void browseForImage(FREContext context){
		this.context = context;
		
		Intent intent = new Intent(context.getActivity(), ImagePickerActivity.class);
        
        try {
        	context.getActivity().startActivity(intent);
        } catch (Exception e) {
        	context.dispatchStatusEventAsync(ERROR_EVENT, "browseForImage()" + e.toString());
		}
	}
	
	
	public void onPickerActivityResult(int requestCode, int resultCode, Intent data) throws IllegalArgumentException, IllegalStateException, FREASErrorException, FREWrongThreadException, FREInvalidObjectException, IOException
	{
		if (resultCode == Activity.RESULT_OK)
		{
			handleResultForGallery(requestCode, resultCode, data);
		}
		else
		{
			context.dispatchStatusEventAsync(CANCEL_EVENT, "onPickerActivityResult: " + resultCode );
		}

		// FOR TEST RESULT -------------------------------------------------------------------------
        Vibrator vibs = (Vibrator) context.getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(10);
	}
	
	
	private static final int REQUEST_CODE = 1;
	public Bitmap bitmap;

    
	private void handleResultForGallery(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
		{
            // We need to recyle unused bitmaps
			if (bitmap != null) {
			    bitmap.recycle();
			}
			
			try {
				InputStream stream = context.getActivity().getContentResolver().openInputStream(data.getData());
	            bitmap = BitmapFactory.decodeStream(stream);
	            stream.close();
	            
	            context.dispatchStatusEventAsync(SELECT_IMAGE_EVENT, "handleResultForGallery " + data.getData().getEncodedPath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				context.dispatchStatusEventAsync(ERROR_EVENT, "handleResultForGallery1 " + data.getData().getEncodedPath());
			} catch (IllegalStateException e) {
				e.printStackTrace();
				context.dispatchStatusEventAsync(ERROR_EVENT, "handleResultForGallery2 " + e.toString());
			} catch (IOException e) {
				e.printStackTrace();
				context.dispatchStatusEventAsync(ERROR_EVENT, "handleResultForGallery3 " + e.toString());
			}
		}
		
	}
	
	public static ImagePickerCode getInstance(){
		if(instance == null){
			instance = new ImagePickerCode();
		}
		return instance;
	}
	
	public void dispose(){
		instance = null;
	}
}
