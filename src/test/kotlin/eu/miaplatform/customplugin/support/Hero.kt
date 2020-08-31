package eu.miaplatform.crud.support

import com.google.gson.annotations.SerializedName
import eu.miaplatform.crud.library.annotations.CollectionAnnotation
import eu.miaplatform.crud.model.BaseObject

@CollectionAnnotation("heroes")
class Hero(
        @SerializedName("name")
        var name: String,
        @SerializedName("power")
        var power: Int
): BaseObject()
