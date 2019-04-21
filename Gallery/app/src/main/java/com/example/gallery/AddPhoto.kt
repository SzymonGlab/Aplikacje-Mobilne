package com.example.gallery

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.add_photo.*
import kotlinx.android.synthetic.main.photo_details.*

class AddPhoto : AppCompatActivity() {

    lateinit var photoUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_photo)
        val bundle: Bundle? = intent.extras
        photoUri = bundle!!.getParcelable<Uri>("imgUri")
        photo.setImageURI(photoUri)

        actionButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val description = newDescription.text
            intent.apply {
                putExtra("imgUri", photoUri)
                putExtra("description", description.toString())
            }
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

}