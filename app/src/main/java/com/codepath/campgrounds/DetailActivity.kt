package com.codepath.campgrounds

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val nameTV = findViewById<TextView>(R.id.campgroundName)
        val descTV = findViewById<TextView>(R.id.campgroundDescription)
        val latLongTV = findViewById<TextView>(R.id.campgroundLocation)
        val imageIV = findViewById<ImageView>(R.id.campgroundImage)

        val campground = intent.getSerializableExtra(CAMPGROUND_EXTRA) as Campground

        nameTV.text = campground.name ?: ""
        descTV.text = campground.description ?: ""
        latLongTV.text = campground.latLong ?: ""

        Glide.with(this)
            .load(campground.imageUrl)
            .centerCrop()
            .into(imageIV)
    }
}