package com.example.gallery
import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ImageObject(
    var name: String,
    var opinionCounter: Int,
    var opinionSum: Float,
    var avgOpinion: Float,
    var description: String,
    var photoUri: Uri?
) : Parcelable {

//    constructor(uri : Uri, opinionCounter: Int, opinionSum: Float, avgOpinion: Float, description: String):
//            this(
//                name = "",
//                opinionCounter = opinionCounter,
//                opinionSum = opinionSum,
//                avgOpinion = avgOpinion,
//                description = description,
//                photoUri = null
//            ){
//        photoUri = uri
//    }

}


