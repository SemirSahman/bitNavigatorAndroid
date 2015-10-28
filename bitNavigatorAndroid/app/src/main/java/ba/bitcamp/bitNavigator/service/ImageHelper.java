package ba.bitcamp.bitNavigator.service;

import android.content.Context;
import android.media.Image;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;

/**
 * Created by ognje on 28-Oct-15.
 */
public class ImageHelper {

    public static String getImage(Context context, String publicId, int width, int height) {
        Cloudinary cloudinary = new Cloudinary("cloudinary://756156952378232:X8lT5_JXOX_H3P4mUdEI93XNA9E@dxuplnpya");
        return cloudinary.url().format("jpg")
                .transformation(new Transformation().width(width).height(height).crop("fill"))
                .generate(publicId);
    }

}
