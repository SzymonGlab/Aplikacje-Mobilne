package com.example.gallery

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.text.DecimalFormat



class MainActivity : AppCompatActivity() {

    private lateinit var img_0: ImageView
    private lateinit var img_1: ImageView
    private lateinit var img_2: ImageView
    private lateinit var img_3: ImageView
    private lateinit var img_4: ImageView
    private lateinit var img_5: ImageView
    private lateinit var img_6: ImageView
    private lateinit var img_7: ImageView
    private lateinit var img_8: ImageView
    private lateinit var img_9: ImageView
    private lateinit var img_10: ImageView
    private lateinit var img_11: ImageView

    private var description = "I was born in an empty sea, My tears created oceans. " +
            "Producing tsunami waves with emotions. " +
            "Patrolling the open seas of an unknown galaxy. " +
            "I was floating in front of who I am physically. " +
            "Spiritually paralyzing mind body and soul. " +
            "Follow me baby. "

    private lateinit var emptyImage: ImageView
    private lateinit var linearLayout: LinearLayout


    private val IMAGE_INFORMATION_CODE = 997
    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    private val IMAGE_ADD_CODE = 1002

    var layoutCounter = 5

    var image_uri: Uri? = null

    private var pictureCounter = 0

    private var imageList = ArrayList<ImageView>()

    private var imageObjects = ArrayList<ImageObject>()

    private var decFormat = DecimalFormat("#.##")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabHandler()
        loadImageViews()
        createImageObjects()
        addOnClickListeners()
        sortByRating()
        loadDataFromAsset()
        pictureCounter = imageObjects.size - 1          // poniewąż tablica inicjowana od 0
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            val savedImageObjects =
                savedInstanceState.getParcelableArrayList<ImageObject>("imageObjects") as java.util.ArrayList<ImageObject>
            pictureCounter = savedImageObjects.size
            imageObjects.clear()
            imageObjects.addAll(savedImageObjects)

            var newImages = pictureCounter - 12
            while (newImages > 0) {
                addNewImageView(pictureCounter - newImages)
                newImages--
            }

            pictureCounter = savedImageObjects.size - 1
            sortByRating()
            loadDataFromAsset()
            addOnClickListeners()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList("imageObjects", imageObjects)
        outState?.putInt("pictureCounter", pictureCounter)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this, "Permission denided", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun sortByRating() {
        imageObjects.sortByDescending { it.avgOpinion }
    }

    private fun createImageObjects() {
        for (i in 0 until imageList.size) {
            var randomOpinion = (0..5).random().toFloat()
            val image = ImageObject("pic$i.jpg", 1, randomOpinion, randomOpinion, description, null)
            imageObjects.add(image)
        }
    }

    private fun fabHandler() {
        val actionButton = cameraButton
        actionButton.setOnClickListener {
            checkPermissions()
        }
    }

    private fun checkPermissions() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
            || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
        ) {
            val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            requestPermissions(permission, PERMISSION_CODE)
        } else {
            openCamera()
        }
    }

    private fun openCamera() {

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "pic$pictureCounter.jpg")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Picture number $pictureCounter from camera.")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    private fun loadImageViews() {
        img_0 = findViewById(R.id.image_0)
        img_1 = findViewById(R.id.image_1)
        img_2 = findViewById(R.id.image_2)
        img_3 = findViewById(R.id.image_3)
        img_4 = findViewById(R.id.image_4)
        img_5 = findViewById(R.id.image_5)
        img_6 = findViewById(R.id.image_6)
        img_7 = findViewById(R.id.image_7)
        img_8 = findViewById(R.id.image_8)
        img_9 = findViewById(R.id.image_9)
        img_10 = findViewById(R.id.image_10)
        img_11 = findViewById(R.id.image_11)

        imageList.add(img_0)
        imageList.add(img_1)
        imageList.add(img_2)
        imageList.add(img_3)
        imageList.add(img_4)
        imageList.add(img_5)
        imageList.add(img_6)
        imageList.add(img_7)
        imageList.add(img_8)
        imageList.add(img_9)
        imageList.add(img_10)
        imageList.add(img_11)
    }

    private fun loadDataFromAsset() {
        try {
            for (i in 0 until imageList.size) {
                val fileName = imageObjects[i].name
                if (fileName == "") {

                    imageList[i].setImageURI(imageObjects[i].photoUri)
                } else {
                    val ims = assets.open(fileName)
                    val d = Drawable.createFromStream(ims, null)
                    imageList[i].setImageDrawable(d)
                }
                imageList[i].layoutParams.height = 500
                imageList[i].layoutParams.width = 500
            }
        } catch (ex: IOException) {
            return
        }
    }

    private fun addOnClickListeners() {
        for (i in 0 until imageObjects.size) {
            imageList[i].setOnClickListener {
                val intent = Intent(this, PhotoDetails::class.java)
                intent.putExtra("imageObjects", imageObjects)
                intent.putExtra("index", i)
                startActivityForResult(intent, IMAGE_INFORMATION_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_INFORMATION_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                imageObjects = data!!.getParcelableArrayListExtra("imageObjects")
                val index = data.getIntExtra("index", 0)
                Toast.makeText(
                    this,
                    "Now avarage opinion is " + decFormat.format(imageObjects[index].avgOpinion),
                    Toast.LENGTH_SHORT
                ).show()
                sortByRating()
                loadDataFromAsset()
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        } else if (requestCode == IMAGE_CAPTURE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                openAddPhoto(image_uri)
            }
        } else if (requestCode == IMAGE_ADD_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val description = data!!.getStringExtra("description")
                val photoUri = data!!.getParcelableExtra<Uri>("imgUri")
                val imageObject = ImageObject("", 0, 0.toFloat(), 0.toFloat(), description, photoUri)
                pictureCounter++
                imageObjects.add(imageObject)
                addNewImageView(pictureCounter)

                addOnClickListeners()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun openAddPhoto(uri: Uri?) {
        val intent = Intent(this, AddPhoto::class.java)
        intent.putExtra("imgUri", uri)
        startActivityForResult(intent, IMAGE_ADD_CODE)
    }

    private fun addNewImageView(photoNumber: Int) {
        if (photoNumber % 2 == 0) {
            val params = img_0.layoutParams as LinearLayout.LayoutParams
            val newImage = ImageView(this)

            linearLayout = LinearLayout(this)
            emptyImage = ImageView(this)

            newImage.layoutParams = params
            newImage.scaleType = ImageView.ScaleType.CENTER_CROP
            newImage.setImageURI(imageObjects[photoNumber].photoUri)
            emptyImage.layoutParams = params
            emptyImage.scaleType = ImageView.ScaleType.CENTER_CROP
            emptyImage.setImageURI(imageObjects[photoNumber].photoUri)

            linearLayout.gravity = Gravity.CENTER
            linearLayout.addView(newImage)

            scrollViewLayout.addView(linearLayout)
            imageList.add(newImage)
            layoutCounter++
        } else {
            emptyImage.setImageURI(imageObjects[photoNumber].photoUri)
            linearLayout.addView(emptyImage)
            imageList.add(emptyImage)
        }
    }
}
