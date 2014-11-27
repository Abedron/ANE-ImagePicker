/**
 * Created by ciz on 16-May-14.
 */
package anespace.imagepicker.events {
import flash.display.BitmapData;
import flash.events.Event;

public class ImagePickerEvent extends Event {

    public static const SELECT_IMAGE:String = "select image ";
    public static const CANCEL:String = "cancel ";
    public static const ERROR:String = "error ";

    public var imageBD:BitmapData;

    public function ImagePickerEvent(type:String, imageBD:BitmapData = null, bubbles:Boolean = false, cancelable:Boolean = false) {
        super(type, bubbles, cancelable);

        this.imageBD = imageBD;
    }

    public override function clone():Event {
        return new ImagePickerEvent(type, imageBD, bubbles, cancelable);
    }

    public override function toString():String {
        return formatToString("ImagePickerEvent", "type", "imageBD", "bubbles", "cancelable", "eventPhase");
    }

}
}