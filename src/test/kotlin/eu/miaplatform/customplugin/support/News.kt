package eu.miaplatform.crud.support

import com.google.gson.annotations.SerializedName
import eu.miaplatform.crud.model.BaseObject

class News (
        @SerializedName("title")
        var title: String,
        @SerializedName("body")
        var body: String,
        @SerializedName("author")
        var author: String,
        @SerializedName("publishedAt")
        var publishedAt: String,
        @SerializedName("maxCharacters")
        var maxCharacters: Int
) : BaseObject()