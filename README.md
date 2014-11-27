# ImagePicker #

This extension allows make the same things like AS3 CameraRoll. But CameraRoll have some bugs. For example, cannot load image from picasa album or other cloud services in devices.
ImagePicker not have any similar problems.

Primary ANE file for you using is here /build/ImagePicker.ane

## Great benefit ##

* Without bugs
* Very fast native image resize

## Configuration project ##

Before you start writing in AS3 must add few line to android manifest xml.

### XML ###

You must update *extension* node:

```
#!xml
<extensions>
    <extensionID>anespace.imagepicker</extensionID>
</extensions>
```

And *android* node:

```
#!xml
<android>
    <manifestAdditions>
        <![CDATA[
        <manifest>
            <uses-permission android:name="android.permission.VIBRATE"/>

            <application>
                <activity android:name="anespace.imagepicker.ImagePickerActivity" android:theme="@android:style/Theme.Dialog"/>
            </application>
        </manifest>
        ]]>
    </manifestAdditions>
</android>
```

### ActionScript 3 ###

Using in AS3 is very simple. Just write next line.

```
#!as3
private var imagePicker:ImagePicker;
private var bitmapDataFromImagePicker:BitmapData;

private function InitialisationImagePicker()
{
	imagePicker = ImagePicker.getInstance();
	imagePicker.addEventListener(ImagePickerEvent.SELECT_IMAGE, selectImageHandler);
	imagePicker.addEventListener(ImagePickerEvent.CANCEL, outImageHandler);
	imagePicker.addEventListener(ImagePickerEvent.ERROR, outImageHandler);
}

private function clickHandler(e:MouseEvent):void
{
	var minimumLengthSide:int = 512;
	imagePicker.browseForImage(ImagePicker.SCALE_TO_FILL, minimumLengthSide);
}

private function selectImageHandler(e:ImagePickerEvent):void
{
	bitmapDataFromImagePicker = e.imageBD;
}

private function outImageHandler(e:ImagePickerEvent):void
{
	trace("Some thing is wrong");
}
```