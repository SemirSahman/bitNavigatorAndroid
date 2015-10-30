package ba.bitcamp.bitNavigator.service;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ba.bitcamp.bitNavigator.bitnavigator.R;

/**
 * Created by ognje on 28-Oct-15.
 */
public class MapHelper {

    public static View getInfoView(Context context, String title, String distance) {

        LinearLayout info = new LinearLayout(context);
        info.setPadding(5, 5, 5, 5);

        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(5, 5, 5, 5);

        TextView titleText = new TextView(context);
        titleText.setText(title);
        titleText.setTextColor(context.getResources().getColor(R.color.textColorPrimary));



        TextView distanceText = new TextView(context);
        distanceText.setText(distance);
        distanceText.setTextColor(context.getResources().getColor(R.color.textColorPrimary));


        layout.addView(titleText);
        layout.addView(distanceText);

        info.addView(layout);

        return info;
    }

}
