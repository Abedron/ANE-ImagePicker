/**
 * Created by ciz on 15-May-14.
 */
package anespace.imagepicker{

import flash.display.BitmapData;
import flash.events.EventDispatcher;
import flash.events.StatusEvent;
import flash.external.ExtensionContext;

import anespace.imagepicker.events.ImagePickerEvent;

public class ImagePicker extends EventDispatcher{
    private static var _instance:ImagePicker;

    private var context:ExtensionContext;
    private var resizeMode:int = 0;
    private var lengthSide:int = 0;

    public var enabledLog:Boolean = true;

    public static const SCALE_TO_FILL:int = 0;
    public static const SCALE_TO_FIT:int = 2;
    public static const NO_SCALE:int = 1;

    public function ImagePicker() {
        if (!_instance)
        {
            context = ExtensionContext.createExtensionContext("anespace.imagepicker", null);

            if (context == null)
            {
                trace("[ImagePicker] ERROR - Extension context is null. Please check if android-descriptor.xml is setup correctly.");
                return;
            }

            context.addEventListener( StatusEvent.STATUS, statusHandler );

            _instance = this;
        }
        else
        {
            throw Error("This is a singleton, use instance, do not call the constructor directly.");
        }
    }

    /**
     * Start dialog for select image browser and image
     * @param lengthSide defined size one side (width or height) image on authority scaleMode
     * @param scaleMode for define resize image with constant MINIMUM_SIDE, MAXIMUM_SIDE, ORIGINAL_SIDE
     */
    public function browseForImage(scaleMode:int = 0, lengthSide:int = 512) : void
    {
        log("[ImagePicker] - browseForImage");

        this.lengthSide = lengthSide;
        this.resizeMode = scaleMode;

        context.call("browseForImage");
    }

    private function statusHandler(e:StatusEvent):void {
        log("[ImagePicker] - statusHandler", e.code, e.level, context.call("getImage"));

        if(e.code == ImagePickerEvent.SELECT_IMAGE)
        {
            var imageBD:BitmapData = BitmapData(context.call("getImage", resizeMode, lengthSide));
            dispatchEvent( new ImagePickerEvent( ImagePickerEvent.SELECT_IMAGE, imageBD, false, false ) );
        }
        else if(e.code == ImagePickerEvent.CANCEL)
        {
            dispatchEvent( new ImagePickerEvent( ImagePickerEvent.CANCEL, null, false, false ) );
        }
        else if(e.code == ImagePickerEvent.ERROR)
        {
            dispatchEvent( new ImagePickerEvent( ImagePickerEvent.ERROR, null, false, false ) );
        }
    }

    private function log(...rest):void {
        if(enabledLog) {
            var str:String = "";
            for each(var inputLog:Object in rest) {
                str += inputLog + " ";
            }
            trace(str);
        }
    }

    /**
     * Instance for work with ImagePicker
     */
    public static function getInstance():ImagePicker {
        if ( _instance == null) {
            trace("[ImagePicker] - create instance");
            _instance = new ImagePicker();
        }

        return _instance;
    }

    /**
     * Release any associated native resources.
     */
    public function dispose():void {
        trace("[ImagePicker] - dispose");
        context.dispose();
    }
}
}