package ba.bitcamp.bitNavigator.controllers;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.here.android.mpa.ar.ARObject;
import com.here.android.mpa.cluster.ClusterLayer;
import com.here.android.mpa.common.GeoBoundingBox;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import java.io.IOException;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.lists.PlaceList;
import ba.bitcamp.bitNavigator.models.Place;
import ba.bitcamp.bitNavigator.service.MapHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.here.android.mpa.ar.ARController;
import com.here.android.mpa.ar.ARController.Error;
import com.here.android.mpa.ar.ARIconObject;
import com.here.android.mpa.ar.CompositeFragment;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.routing.RouteManager;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;

public class MapsActivity extends Activity {

    // map embedded in the composite fragment
    private Map map;

    // composite fragment embedded in this activity
    private CompositeFragment compositeFragment;

    // ARController is a facade for controlling LiveSight behavior
    private ARController arController;

    // buttons which will allow the user to start LiveSight and add objects
    private Button startButton;
    private Button stopButton;
    private ImageView hereButton;
    private ImageView clearRouteButton;

    // the image we will display in LiveSight
    private Image image;

    // ARIconObject represents the image model which LiveSight accepts for display
    private ARIconObject arIconObject;
    private boolean objectAdded;

    // Application paused
    private boolean paused;

    // Define positioning listener
    private PositioningManager.OnPositionChangedListener positionListener;

    //
    private ClusterLayer mClusterLayer;

    private GeoCoordinate mGPSPosition;

    private MapRoute mapRoute;

    private MapMarker mRouteMarker;

    private List<ARIconObject> arIconObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Search for the composite fragment to finish setup by calling init().
        compositeFragment = (CompositeFragment) getFragmentManager().findFragmentById(R.id.compositefragment);
        compositeFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(Error error) {
                if (error == Error.NONE) {
                    Log.d("dibag", "if-------------");
                    // retrieve a reference of the map from the composite fragment
                    map = compositeFragment.getMap();
                    // Set the map center coordinate to current position (no animation)
                    map.setCenter(new GeoCoordinate(43.850, 18.390, 0.0), Map.Animation.NONE);
                    getPosition();
                    // Set the map zoom level to the average between min and max (no animation)
                    map.setZoomLevel((map.getMaxZoomLevel() + map.getMinZoomLevel()) / 2);
                    // LiveSight setup should be done after fragment init is complete
                    addMarkers();
                    setupLiveSight();
                } else {
                    Log.d("dibag", "else-------------"+error);
                    System.out.println("ERROR: Cannot initialize Composite Fragment");
                }
            }
        });

        // hold references to the buttons for future use
        startButton = (Button) findViewById(R.id.startLiveSight);
        stopButton = (Button) findViewById(R.id.stopLiveSight);
        hereButton = (ImageView) findViewById(R.id.here_button);
        clearRouteButton = (ImageView) findViewById(R.id.clear_route_button);

        Button mLoginButton = (Button) findViewById(R.id.btnProfile);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(i);
                                            }
                                        }
        );

        Button mRegisterButton = (Button) findViewById(R.id.btnReservations);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
                                               public void onClick(View v) {
                                                   Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                                   startActivity(i);
                                               }
                                           }
        );

        Button mSearchButton = (Button) findViewById(R.id.btnSearch);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
                                             public void onClick(View v) {
                                                 Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                                                 startActivity(i);
                                             }
                                         }
        );

        Button mMapButton = (Button) findViewById(R.id.btnMap);
        mMapButton.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              Intent i = new Intent(getApplicationContext(), ba.bitcamp.bitNavigator.controllers.MapsActivity.class);
                                              startActivity(i);
                                          }
                                      }
        );

    }

    private void addMarkers() {
        mClusterLayer = new ClusterLayer();
        for (Place place : PlaceList.getInstance().getPlaceList()) {
            MapMarker marker = new MapMarker();
            marker.setCoordinate(new GeoCoordinate(place.getLatitude(), place.getLongitude()));
            marker.setTitle(place.getTitle());
            //marker.setDescription(place.getDescription());

            mClusterLayer.addMarker(marker);
        }

        // Create a gesture listener and add it to the MapFragment
        MapGesture.OnGestureListener listener = new MapGesture.OnGestureListener.OnGestureListenerAdapter() {
            @Override
            public boolean onMapObjectsSelected(List<ViewObject> objects) {
                for (ViewObject viewObj : objects) {
                    if (viewObj.getBaseType() == ViewObject.Type.USER_OBJECT) {
                        if (((MapObject)viewObj).getType() == MapObject.Type.MARKER) {
                            if (!((MapMarker)viewObj).isInfoBubbleVisible()) {
                                ((MapMarker)viewObj).showInfoBubble();
                            } else {
                                ((MapMarker)viewObj).hideInfoBubble();
                            }
                        }
                    }
                }
                // return false to allow the map to handle this callback also
                return false;
            }

            @Override
            public boolean onLongPressEvent(PointF pointF) {
                for (ViewObject object : map.getSelectedObjects(pointF)) {
                    if (object instanceof MapMarker) {
                        mRouteMarker = (MapMarker) object;
                        if (mGPSPosition != null) {
                            getDirections(mGPSPosition, mRouteMarker.getCoordinate());
                            map.removeClusterLayer(mClusterLayer);
                            map.addMapObject(mRouteMarker);
                        }

                    }
                }
                return false;
            }
        };

        compositeFragment.getMapGesture().addOnGestureListener(listener);

        map.addClusterLayer(mClusterLayer);
    }

    private void setupLiveSight() {
        // ARController should not be used until fragment init has completed
        arController = compositeFragment.getARController();
        // tells LiveSight to display icons while viewing the map (pitch down)
        arController.setUseDownIconsOnMap(true);

        // tells LiveSight to use a static mock location instead of the devices GPS fix
        //arController.setAlternativeCenter(new GeoCoordinate(49.279483, -123.116906, 0.0));
    }

    public void startLiveSight(View view) {

        if (mGPSPosition == null) {
            Toast.makeText(this, getString(R.string.error_gps), Toast.LENGTH_SHORT).show();
            return;
        }

        if (arController != null) {
            setupLiveSight();
            // triggers the transition from Map mode to LiveSight mode
            Error error = arController.start();

            if (error == Error.NONE) {

                getARObjects();

                startButton.setVisibility(View.GONE);
                hereButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getApplicationContext(), "Error starting LiveSight: " + error.toString(), Toast.LENGTH_LONG);
            }
        }
    }

    private void getARObjects() {
        for (Place place : PlaceList.getInstance().getPlaceList()) {
            String distance = String.format("%.2f m", mGPSPosition.distanceTo(new GeoCoordinate(place.getLatitude(), place.getLongitude())));
            View info = MapHelper.getInfoView(this, place.getTitle(), distance);
            String service = place.getService().toLowerCase();
            if (service.contains(" ")) {
                service.replace(" ", "_");
            }
            if (service.contains("&")) {
                service = "arts";
            }
            int icon = getResources().getIdentifier(service, "drawable", getPackageName());
            ARIconObject arIconObject = new ARIconObject(new GeoCoordinate(place.getLatitude(), place.getLongitude()), info, icon);
            arIconObjects.add(arIconObject);
            arController.addARObject(arIconObject);
        }
        map.removeClusterLayer(mClusterLayer);
    }

    public void stopLiveSight(View view) {
        if (arController != null) {
            // exits LiveSight mode and returns to Map mode with exit animation
            Error error = arController.stop(true);

            if (error == Error.NONE) {

                removeARObjects();

                startButton.setVisibility(View.VISIBLE);
                hereButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.GONE);
            } else {
                Toast.makeText(getApplicationContext(), "Error stopping LiveSight: " + error.toString(), Toast.LENGTH_LONG);
            }
        }
    }

    private void removeARObjects() {
        map.addClusterLayer(mClusterLayer);
        for (ARIconObject object : arIconObjects) {
            arController.removeARObject(object);
        }
    }

    // Resume positioning listener on wake up
    @Override
    public void onResume() {
        super.onResume();
        paused = false;
    }

    public void getPosition() {
        // Register positioning listener
        positionListener = new PositioningManager.OnPositionChangedListener() {

            @Override
            public void onPositionUpdated(PositioningManager.LocationMethod method, GeoPosition position, boolean isMapMatched) {
                // set the center only when the app is in the foreground
                // to reduce CPU consumption
                if (!paused) {

                    mGPSPosition = position.getCoordinate();
                    // Display position indicator
                    map.getPositionIndicator().setVisible(true);
                    if (stopButton.getVisibility() == View.VISIBLE) {
                        removeARObjects();
                        getARObjects();
                    }
                }
            }
            @Override
            public void onPositionFixChanged(PositioningManager.LocationMethod method, PositioningManager.LocationStatus status) {

            }
        };

        PositioningManager.getInstance().addListener(new WeakReference<PositioningManager.OnPositionChangedListener>(positionListener));

        PositioningManager.getInstance().start(PositioningManager.LocationMethod.GPS_NETWORK);
    }

    public void setCenter(View view) {
        if (mGPSPosition != null) {
            map.setCenter(mGPSPosition, Map.Animation.BOW);
        } else {
            Toast.makeText(this, R.string.error_gps, Toast.LENGTH_SHORT).show();
        }
    }

    private class RouteListener implements RouteManager.Listener {

        // Method defined in Listener
        public void onProgress(int percentage) {
            // Display a message indicating calculation progress
        }

        // Method defined in Listener
        public void onCalculateRouteFinished(RouteManager.Error error, List<RouteResult> routeResult) {
            // If the route was calculated successfully
            if (error == RouteManager.Error.NONE) {
                // Add clear route button
                clearRouteButton.setVisibility(View.VISIBLE);
                // Render the route on the map
                mapRoute = new MapRoute(routeResult.get(0).getRoute());
                map.addMapObject(mapRoute);
                // Get the bounding box containing the route and zoom in
                GeoBoundingBox gbb = routeResult.get(0).getRoute().getBoundingBox();
                map.zoomTo(gbb, Map.Animation.BOW, Map.MOVE_PRESERVE_ORIENTATION);

            }
            else {
                // Display a message indicating route calculation failure
            }
        }
    }

    // Functionality for hold on the marker
    public void getDirections(GeoCoordinate start, GeoCoordinate destination) {
        // 1. clear previous results
        //textViewResult.setText("");
        if (map != null && mapRoute != null) {
            map.removeMapObject(mapRoute);
            mapRoute = null;
        }

        // 2. Initialize RouteManager
        RouteManager routeManager = new RouteManager();

        // 3. Select routing options via RoutingMode
        RoutePlan routePlan = new RoutePlan();

        RouteOptions routeOptions = new RouteOptions();
        routeOptions.setTransportMode(RouteOptions.TransportMode.CAR);
        routeOptions.setRouteType(RouteOptions.Type.FASTEST);
        routePlan.setRouteOptions(routeOptions);

        // 4. Select Waypoints for your routes
        routePlan.addWaypoint(start);
        routePlan.addWaypoint(destination);

        // 5. Retrieve Routing information via RouteManagerListener
        RouteManager.Error error = routeManager.calculateRoute(routePlan, new RouteListener());
        if (error != RouteManager.Error.NONE) {
            Toast.makeText(getApplicationContext(),
                    "Route calculation failed with: " + error.toString(),
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void clearRoute(View view) {

        if (mapRoute != null) {
            map.removeMapObject(mapRoute);
            mapRoute = null;
            map.removeMapObject(mRouteMarker);
            map.addClusterLayer(mClusterLayer);
        }
        clearRouteButton.setVisibility(View.GONE);

    }
}
