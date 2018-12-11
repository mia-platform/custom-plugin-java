package eu.miaplatform.crud.library

import com.google.gson.JsonObject
import eu.miaplatform.crud.library.enums.Parameters
import eu.miaplatform.crud.library.enums.PatchCodingKey
import eu.miaplatform.crud.library.enums.State
import eu.miaplatform.crud.library.listeners.SingleObjectCallback
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.io.UnsupportedEncodingException
import java.util.*

class PATCH (private val collection: String, private val network: Network, private val crudVersion: Int) {

    private var state: String? = null

    private var set: HashMap<String, Any>? = null
    private var setOnInsert: HashMap<String, Any>? = null
    private var unset: HashMap<String, Boolean>? = null
    private var inc: HashMap<String, Double>? = null
    private var mul: HashMap<String, Double>? = null
    private var currentDate: HashMap<String, Boolean>? = null

    fun async(id: String, callback: SingleObjectCallback<String>){
        val queryParameters = getQueryParameters()
        val relativePath = if(queryParameters.isEmpty()) "v$crudVersion/$collection/$id" else "v$crudVersion/$collection/$id?$queryParameters"

        val body = buildBody()

        network.queryRestInterface.patch(relativePath, body).enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>) {
                var error: CRUDError? = null
                var result: String? = null
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val obj = response.body()!!.asJsonObject
                        result = obj.get("_id").asString
                    } catch (e: Exception){
                        error = CRUDError(e.message)
                    }

                } else {
                    error = CRUDError("Error code ${response.code()}")
                }

                callback.onCompleted(result, error)
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                callback.onCompleted(null, CRUDError(t?.message))
            }
        })
    }

    @Throws
    fun sync(id: String) : String? {
        val queryParameters = getQueryParameters()
        val relativePath = if(queryParameters.isEmpty()) "v$crudVersion/$collection/$id" else "v$crudVersion/$collection/$id?$queryParameters"

        val body = buildBody()
        val response = network.queryRestInterface.patch(relativePath, body).execute()

        if(response.isSuccessful){
            try {
                val obj = response.body() as JsonObject
                return obj.get("_id").asString
            } catch (e: Exception) {
                throw e
            }
        } else {
            throw CRUDError("Error code ${response.code()}")
        }

    }

    // Public

    fun <T: Serializable> update(newObject: T, property: String){
        if(CRUDObject.CollectionDefaultProperties.contains(property)){
            return
        }

        val temp: HashMap<String, Any> = if (set == null) HashMap() else set!!

        if(newObject is Date){
            val formattedDate = CRUDObject.toISO8601(newObject)
            temp[property] = formattedDate
        } else {
            temp[property] = newObject
        }

        set = temp
    }

    fun <T: Serializable> updateOnInsert(newObject: T, property: String){
        if(CRUDObject.CollectionDefaultProperties.contains(property)){
            return
        }

        val temp: HashMap<String, Any> = if (setOnInsert == null) HashMap() else setOnInsert!!

        if(newObject is Date){
            val formattedDate = CRUDObject.toISO8601(newObject)
            temp[property] = formattedDate
        } else {
            temp[property] = newObject
        }

        setOnInsert = temp
    }

    fun removeObject(property: String){
        if(CRUDObject.CollectionDefaultProperties.contains(property)){
            return
        }

        val temp: HashMap<String, Boolean> = if (unset == null) HashMap() else unset!!

        temp[property] = true
        unset = temp
    }

    fun increment(value: Double, property: String){
        if(CRUDObject.CollectionDefaultProperties.contains(property)){
            return
        }

        val temp: HashMap<String, Double> = if (inc == null) HashMap() else inc!!

        temp[property] = value
        inc = temp
    }

    fun multiply(value: Double, property: String){
        if(CRUDObject.CollectionDefaultProperties.contains(property)){
            return
        }

        val temp: HashMap<String, Double> = if (mul == null) HashMap() else mul!!

        temp[property] = value
        mul = temp
    }

    fun setCurrentDate(property: String){
        if(CRUDObject.CollectionDefaultProperties.contains(property)){
            return
        }

        val temp: HashMap<String, Boolean> = if (currentDate == null) HashMap() else currentDate!!

        temp[property] = true
        currentDate = temp

    }

    fun state(state: State){
        this.state = state.value
    }

    fun state(state: ArrayList<State>){
        this.state = state.joinToString(separator = ",") { it.value }
    }

    // Private

    private fun buildBody(): RequestBody {

        val jsonObject = JsonObject()

        if(set != null){
            jsonObject.add (PatchCodingKey.Set.value, GsonFactory.newGsonInstance().toJsonTree(set))
        }
        if(setOnInsert != null){
            jsonObject.add (PatchCodingKey.SetOnInsert.value, GsonFactory.newGsonInstance().toJsonTree(setOnInsert))
        }
        if(unset != null){
            jsonObject.add (PatchCodingKey.Unset.value, GsonFactory.newGsonInstance().toJsonTree(unset))
        }
        if(inc != null){
            jsonObject.add (PatchCodingKey.Inc.value, GsonFactory.newGsonInstance().toJsonTree(inc))
        }
        if(mul != null){
            jsonObject.add (PatchCodingKey.Mul.value, GsonFactory.newGsonInstance().toJsonTree(mul))
        }
        if(currentDate != null){
            jsonObject.add (PatchCodingKey.CurrentDate.value, GsonFactory.newGsonInstance().toJsonTree(currentDate))
        }

        val jsonString = GsonFactory.newGsonInstance().toJson(jsonObject)
        return createRawBodyFromJson(jsonString)!!
    }

    private fun createRawBodyFromJson(json: String): RequestBody? {
        try {
            return RequestBody.create(MediaType.parse("application/json"), json.toByteArray(charset("UTF-8")))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return null
    }

    private fun getQueryParameters(): String {
        val queryParameters = HashMap<String, Any>()

        if(state != null){
            queryParameters[Parameters.STATE.value] = state!!
        }

        return queryParameters.map { "${it.key}=${it.value}" }.joinToString(separator = "&")

    }
}