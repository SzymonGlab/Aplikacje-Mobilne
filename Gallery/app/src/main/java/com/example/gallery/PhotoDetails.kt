package com.example.gallery

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.add_photo.*
import kotlinx.android.synthetic.main.photo_details.*
import java.io.IOException

class PhotoDetails : AppCompatActivity() {


    private var imageObjects = ArrayList<ImageObject>()
    private var index =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_details)
        val bundle: Bundle? = intent.extras

        imageObjects = bundle!!.getParcelableArrayList("imageObjects")
        index = bundle.getInt("index",0)
        loadPhoto()
        ratingBar.rating = imageObjects[index].avgOpinion
        description.text = imageObjects[index].description
    }

    private fun loadPhoto() {
        try {
            val photo = findViewById<ImageView>(R.id.new_photo)
            if(imageObjects[index].name == ""){
                photo.setImageURI(imageObjects[index].photoUri)
                return
            }
            val ims = assets.open(imageObjects[index].name)
            val d = Drawable.createFromStream(ims, null)
            photo.setImageDrawable(d)
        } catch (ex: IOException) {
            return
        }
    }

    fun addRate(view: View){
        val rating = ratingBar.rating

        imageObjects[index].opinionCounter ++
        imageObjects[index].opinionSum +=rating
        imageObjects[index].avgOpinion = imageObjects[index].opinionSum/imageObjects[index].opinionCounter

        val intent = Intent(this, MainActivity::class.java)
        intent.apply {
                        putExtra("imageObjects", imageObjects)
                        putExtra("index", index)
                        }
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

}
