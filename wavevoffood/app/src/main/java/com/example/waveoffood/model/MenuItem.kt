package com.example.waveoffood.model

import android.media.Image
import android.net.Uri

data class MenuItem(
    val foodName:String?= null,
    val foodPrice:String?= null,
    val foodDescription:String?= null,
    val foodIngredient:String?= null,
    val foodImage:String?= null
)
