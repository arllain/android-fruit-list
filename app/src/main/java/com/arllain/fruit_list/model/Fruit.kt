package com.arllain.fruit_list.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fruit(
    var imageBase64: String? = null,
    var name: String? = null,
    var benefits: String? = null
) : Parcelable