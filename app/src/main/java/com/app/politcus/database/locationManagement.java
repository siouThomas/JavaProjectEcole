package com.app.politcus.database;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.app.politcus.App;
import android.Manifest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static android.support.v4.app.ActivityCompat.requestPermissions;


/**
 * Created by Antoine on 19/10/2016.
 */

public class LocationManagement implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{
  private GoogleApiClient mGoogleApiClient;
  private FusedLocationProviderApi fusedLocationProviderApi;

public LocationManagement(){


  if (mGoogleApiClient == null) {
    mGoogleApiClient = new GoogleApiClient.Builder(App.getContext())
      .addConnectionCallbacks(this)
      .addOnConnectionFailedListener(this)
      .addApi(LocationServices.API)
      .build();
  }
  mGoogleApiClient.connect();
}

  @Override
  public void onConnected(Bundle connectionHint) {


    int permissionCheck = ContextCompat.checkSelfPermission(App.getContext(),
      "android.permission.ACCESS_COARSE_LOCATION");
    int permissionCheck2 = ContextCompat.checkSelfPermission(App.getContext(),
      "android.permission.ACCESS_FINE_LOCATION");


    LocationRequest mLocationRequest = LocationRequest.create();
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    mLocationRequest.setInterval(1000); // Update location every second

    if (ContextCompat.checkSelfPermission(App.getContext(),
      android.Manifest.permission.ACCESS_FINE_LOCATION)
      != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(
        (Activity)App.getContext(), // Activity
        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
        111);
    }


    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
      mGoogleApiClient);


    double latitude =  mLastLocation.getLatitude();

    if (mLastLocation != null) {
      System.out.println(String.valueOf(mLastLocation.getLatitude()));
      System.out.println(String.valueOf(mLastLocation.getLongitude()));
    }
  }

  // Get permission result
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[], int[] grantResults) {
    switch (requestCode) {
      case 111: {
        if (grantResults.length > 0
          && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // permission was granted

        } else {
          // permission was denied
        }
        return;
      }
    }
  }

  @Override
  public void onConnectionSuspended(int i) {

  }


  protected void onStart() {
    mGoogleApiClient.connect();
  }

  protected void onStop() {
    mGoogleApiClient.disconnect();
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  @Override
  public void onLocationChanged(Location location) {

    System.out.println(String.valueOf(location.getLatitude()));
    System.out.println(String.valueOf(location.getLongitude()));

    LocationServices.FusedLocationApi.removeLocationUpdates(
      mGoogleApiClient, this);

    mGoogleApiClient.disconnect();
  }

}

