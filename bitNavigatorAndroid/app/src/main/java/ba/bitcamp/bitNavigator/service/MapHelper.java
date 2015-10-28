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

        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundColor(context.getResources().getColor(android.support.design.R.color.primary_material_light));
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView titletext = new TextView(context);
        titletext.setText(title);
        titletext.setTextColor(context.getResources().getColor(android.support.design.R.color.primary_material_dark));
        layout.addView(titletext);
        Log.d("dibag", distance);
        TextView distanceText = new TextView(context);
        distanceText.setText(distance);
        distanceText.setTextColor(context.getResources().getColor(android.support.design.R.color.primary_material_dark));
        layout.addView(distanceText);

        return layout;
    }

}
