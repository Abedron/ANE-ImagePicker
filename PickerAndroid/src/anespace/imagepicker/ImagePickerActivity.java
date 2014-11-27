package anespace.imagepicker;

import java.io.IOException;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREWrongThreadException;

import anespace.imagepicker.code.ImagePickerCode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ImagePickerActivity extends Activity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);		
		
		Intent intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intentGallery, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		try {
			ImagePickerCode.getInstance().onPickerActivityResult(requestCode, resultCode, data);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FREASErrorException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			finish();
		}
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
}
