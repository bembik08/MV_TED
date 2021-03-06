package com.movieapp.mv_ted.utils.broadcastrecievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.movieapp.mv_ted.R
import com.movieapp.mv_ted.utils.notifications.NotificationImpl

class GeofenceRequestReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val geofenceEvent = GeofencingEvent.fromIntent(intent)
        if (geofenceEvent.hasError()){
            val errorMsg =  GeofenceStatusCodes.getStatusCodeString(geofenceEvent.errorCode)
            Log.e(TAG, errorMsg )
            return
        }
        val geofenceTransition = geofenceEvent.geofenceTransition
        if (geofenceTransition  == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT ){
            val triggeringGeofence = geofenceEvent.triggeringGeofences
            val geofenceTransitionDetail =   getGeofenceDetails( triggeringGeofence)
            sendNotification(geofenceTransitionDetail,context)
            Log.i(TAG, geofenceTransitionDetail)
        } else{
            Log.e(TAG, geofenceTransition.toString() )
        }
    }

    private fun getGeofenceDetails(triggeringGeofence: List<Geofence>): String {
        return "${triggeringGeofence.get(0).requestId} is near"
    }

    private fun sendNotification(geofenceTransitionDetail: String, context: Context) {
        NotificationImpl.createNotification(
            context, CHANNEL_ID, R.drawable.ic_baseline_tag_faces_like_on,
            NOTIFICATION_TITLE, geofenceTransitionDetail, NOTIFICATION_ID
        )
   }

    companion object {
        private const val CHANNEL_ID = "Geofence_channel_id"
        private const val NOTIFICATION_TITLE = "At Area"
        private const val TAG = "Geofence_Receiver"
        private const val NOTIFICATION_ID = 2
    }
}