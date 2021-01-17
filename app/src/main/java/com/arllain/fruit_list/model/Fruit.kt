package com.arllain.fruit_list.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fruit(
    var imageResource: Int,
    var name: String,
    var benefits: String
) : Parcelable