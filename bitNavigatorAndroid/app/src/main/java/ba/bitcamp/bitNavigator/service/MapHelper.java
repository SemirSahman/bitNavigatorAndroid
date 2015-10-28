package ba.bitcamp.bitNavigator.service;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ognje on 28-Oct-15.
 */
public class MapHelper {

    public static View getInfoView(Context context, String title, String distance) {

        LinearLayout info = new LinearLayout(context);
        info.setPadding(5, 5, 5, 5);

        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundColor(context.getResources().getColor(android.support.design.R.color.primary_material_light));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(5, 5, 5, 5);

        TextView titleText = new TextView(context);
        titleText.setText(title);
        titleText.setTextColor(context.getResources().getColor(android.support.design.R.color.primary_material_dark));



        TextView distanceText = new TextView(context);
        distanceText.setText(distance);
        distanceText.setTextColor(context.getResources().getColor(android.support.design.R.color.primary_material_dark));


        layout.addView(titleText);
        layout.addView(distanceText);

        info.addView(layout);

        return info;
    }

}
