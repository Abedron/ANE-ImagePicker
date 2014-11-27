/**
 * Created by ciz on 15-May-14.
 */
package anespace.imagepicker{

import flash.events.EventDispatcher;

public class ImagePicker extends EventDispatcher{
    private static var _instance:ImagePicker;

    public static const SCALE_TO_FILL:int = 0;
    public static const SCALE_TO_FIT:int = 2;
    public static const NO_SCALE:int = 1;

    public function ImagePicker() {
        super();

        if (_instance)
        {
            throw Error("This is a singleton, use instance, do not call the constructor directly.");
        }
    }

    /**
     * Start dialog for select image browser and image
     * @param lengthSide defined size one side (width or height) image on authority scaleMode
     * @param scaleMode for define resize image with constant MINIMUM_SIDE, MAXIMUM_SIDE, ORIGINAL_SIDE
     */
    public function browseForImage(scaleMode:int = 0, lengthSide:int = 512):void {

    }

    /**
     * Instance for work with ImagePicker
     */
    public static function getInstance():ImagePicker {
        if ( !_instance ) {
            _instance = new ImagePicker();
        }

        return _instance;
    }

    /**
     * Release any associated native resources.
     */
    public function dispose():void {

    }
}
}