package eu.miaplatform.crud.library.restInterfaces

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DefaultRestInterface {

    @GET
    fun get(@Url path: String): Call<JsonElement>

    @POST("/{path}/")
    fun post(@Path(value = "path", encoded = true) path: String, @Body body: RequestBody): Call<JsonObject>

    @POST("/{path}/bulk")
    fun bulkPost(@Path(value = "path", encoded = true) path: String, @Body body: RequestBody): Call<JsonArray>

    @DELETE
    fun deleteId(@Url path: String): Call<ResponseBody>

    @DELETE
    fun delete(@Url path: String): Call<JsonElement>

    @PATCH
    fun patch(@Url path: String, @Body body: RequestBody): Call<JsonObject>

    @POST("/{path}")
    fun statePost(@Path(value = "path", encoded = true) path: String, @Body body: RequestBody): Call<ResponseBody>
}