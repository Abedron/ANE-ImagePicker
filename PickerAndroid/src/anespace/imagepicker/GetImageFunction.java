package anespace.imagepicker;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREBitmapData;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

import anespace.imagepicker.code.ImagePickerCode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class GetImageFunction implements FREFunction {

	private FREContext context;

    final int SCALE_TO_FILL = 0;
    final int SCALE_TO_FIT = 2;

	@Override
	public FREBitmapData call(FREContext context, FREObject[] arg1) {	
		this.context = context;
		
		FREBitmapData bitmapData = null;
		
		try {
			bitmapData = getImage(arg1[0].getAsInt(), arg1[1].getAsInt());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}
		return bitmapData;
	}
	
	private FREBitmapData getImage(int resizeMode, int lengthSide){
		Bitmap bitmapSource = ImagePickerCode.getInstance().bitmap;
		Bitmap bitmap;
		int width;
		int height;
		double ratio = 1;

		if(resizeMode == SCALE_TO_FIT){
            if(bitmapSource.getWidth() > bitmapSource.getHeight()){
                ratio = (double)bitmapSource.getHeight() / (double)bitmapSource.getWidth();
                width = lengthSide;
                height = (int) (lengthSide * ratio);
            }else{
                ratio = (double)bitmapSource.getWidth() / (double)bitmapSource.getHeight();
                height = lengthSide;
                width = (int) (lengthSide * ratio);
            }
            bitmap = Bitmap.createScaledBitmap(bitmapSource, width, height, true);
		}else if (resizeMode == SCALE_TO_FILL){
			if(bitmapSource.getWidth() > bitmapSource.getHeight()){
				ratio = (double)bitmapSource.getWidth() / (double)bitmapSource.getHeight();
				height = lengthSide;
				width = (int) (lengthSide * ratio);
			}else{
				ratio = (double)bitmapSource.getHeight() / (double)bitmapSource.getWidth();
				width = lengthSide;
				height = (int) (lengthSide * ratio);
			}
			bitmap = Bitmap.createScaledBitmap(bitmapSource, width, height, true);
		}else {
            width = bitmapSource.getWidth();
            height = bitmapSource.getHeight();
            bitmap = bitmapSource;
        }
		
		context.dispatchStatusEventAsync("[TEST]", "ratio: " + ratio + " width: " + bitmapSource.getWidth() + "height: " + bitmapSource.getHeight());
		
		
		Byte[] fillcolor = {0,0,0,0};
		FREBitmapData bitmapData = null;
			
		Bitmap outBitmap  = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(outBitmap);
	    Paint paint = new Paint();
	    ColorMatrix colorMatrix = new ColorMatrix();
	    colorMatrix.set(new float[] 
	    	    { 0,  0,  1f, 0,  0, 
	              0,  1f, 0,  0,  0,
	              1f, 0,  0,  0,  0, 
	              0,  0,  0,  1f, 0 });
	    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
	    paint.setColorFilter(filter);
	    canvas.drawBitmap(bitmap, 0, 0, paint);
	    
		try{
			bitmapData = FREBitmapData.newBitmapData(width, height, true, fillcolor);
			bitmapData.acquire();
			outBitmap.copyPixelsToBuffer(bitmapData.getBits());
			bitmapData.release();
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
		}
		
		return bitmapData;
	}

}
