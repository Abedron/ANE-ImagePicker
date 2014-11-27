package {

import flash.display.Bitmap;
import flash.display.BitmapData;

import flash.display.Sprite;
import flash.display.StageAlign;
import flash.display.StageScaleMode;
import flash.events.Event;
import flash.events.MouseEvent;
import flash.system.Capabilities;
import flash.text.TextFormat;

import com.bit101.components.Label;
import com.bit101.components.PushButton;

import anespace.imagepicker.ImagePicker;
import anespace.imagepicker.events.ImagePickerEvent;


public class Main extends Sprite {
    private var imagePicker:ImagePicker;
    private var psh:PushButton;

    public function Main() {
        addEventListener(Event.ADDED_TO_STAGE, init);
    }

    private function init(e:Event):void {
        removeEventListener(Event.ADDED_TO_STAGE, init);

        stage.align = StageAlign.TOP_LEFT;
        stage.scaleMode = StageScaleMode.NO_SCALE;

        psh = new PushButton(this, 10, 10, "LOAD", pushHandler);
        psh.height = psh.width;

        graphics.beginFill(0xcccccc);
        graphics.drawRect(0, psh.height + 10, 512, 512);
        graphics.endFill();

        var label:Label = new Label(this, stage.stageWidth - 150, 10, "Debug: " + Capabilities.isDebugger.toString());
        label.textField.setTextFormat(new TextFormat(null, 18));

        imagePicker = ImagePicker.getInstance();
        imagePicker.addEventListener(ImagePickerEvent.SELECT_IMAGE, selectHandler);
        imagePicker.addEventListener(ImagePickerEvent.CANCEL, cancelHandler);
        imagePicker.addEventListener(ImagePickerEvent.ERROR, errorHandler);
    }

    private function pushHandler(e:MouseEvent):void {
        imagePicker.browseForImage(ImagePicker.SCALE_TO_FILL, 512);

        log("TEST", this);
    }

    private function selectHandler(e:ImagePickerEvent):void {
        trace("[Main] SELECT IMAGE", e);

        var sourceBD:BitmapData = e.imageBD;
        var bmp:Bitmap = new Bitmap(sourceBD);
        bmp.y = psh.y + psh.height + 10;
        addChild(bmp);
    }

    private function cancelHandler(e:ImagePickerEvent):void {
        trace("[Main] CANCEL", e);
    }

    private function errorHandler(e:ImagePickerEvent):void {
        trace("[Main] ERROR", e);
    }

    private function log(...rest):void {
        if(true) {
            var str:String = "";
            for each(var inputLog:Object in rest) {
                str += inputLog + " ";
            }
            trace(str);
        }
    }
}
}

