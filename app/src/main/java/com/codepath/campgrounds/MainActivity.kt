package com.codepath.campgrounds

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.json.Json
import okhttp3.Headers

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val campgrounds = mutableListOf<Campground>()
    private lateinit var campgroundAdapter: CampgroundAdapter

    // Using the correct NPS API Key
    private val PARKS_API_KEY = "5XPjaPSE6Lr00qdbVjMa7n57UDPxR4KIDTEGcX7Q"
    private val CAMPGROUND_URL =
        "https://developer.nps.gov/api/v1/campgrounds?api_key=$PARKS_API_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.campgroundsRecyclerView)
        campgroundAdapter = CampgroundAdapter(this, campgrounds)
        recyclerView.adapter = campgroundAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchCampgrounds()
    }

    private fun fetchCampgrounds() {
        val client = AsyncHttpClient()
        Log.d(TAG, "Fetching from: $CAMPGROUND_URL")

        client.get(CAMPGROUND_URL, object : JsonHttpResponseHandler() {

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch campgrounds: $statusCode")
                Log.e(TAG, "Response: $response")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                try {
                    Log.d(TAG, "onSuccess: ${json.jsonObject}")
                    
                    val parsedJson = createJson().decodeFromString(
                        CampgroundResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    parsedJson.data?.let { list ->
                        Log.d(TAG, "Parsed ${list.size} campgrounds")
                        campgrounds.clear()
                        campgrounds.addAll(list)
                        campgroundAdapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Parsing error: $e")
                }
            }
        })
    }

    private fun createJson(): Json {
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }
}
