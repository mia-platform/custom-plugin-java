package eu.miaplatform.crud.library

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import eu.miaplatform.crud.library.enums.State
import eu.miaplatform.crud.library.listeners.NoObjectCallback
import eu.miaplatform.crud.library.listeners.MultipleObjectsCallback
import eu.miaplatform.crud.library.listeners.SingleObjectCallback
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.io.UnsupportedEncodingException
import java.util.ArrayList

class POST(var collection: String, private val network: Network, private val crudVersion: Int? = null) {

    //Async

    fun <T: Serializable> async(obj: T, state: State?, callback: SingleObjectCallback<String>){
        try {
            val jsonObject = GsonFactory.newGsonInstance().toJsonTree(obj).asJsonObject
            async(jsonObject, state, callback)
        } catch (e: Exception){
            callback.onCompleted(null, CRUDError(e.message))
        }
    }

    fun <T: Serializable> async(objs: ArrayList<T>, state: State?, callback: MultipleObjectsCallback<String>){

        try {
            val array = JsonArray()

            for(obj in objs) {
                val element = GsonFactory.newGsonInstance().toJsonTree(obj).asJsonObject
                array.add(element)
            }

            async(array, state, callback)

        } catch (e: Exception){
            callback.onCompleted(null, CRUDError(e.message))
        }
    }

    fun async(jsonObject: JsonObject, state: State?, callback: SingleObjectCallback<String>){
        val relativePath = if (crudVersion != null) "v$crudVersion/$collection" else collection

        val gson = GsonFactory.newGsonInstance()
        if(state != null) {
            jsonObject.addProperty(CRUDObject.STATE_KEY, state.value)
        } else {
            jsonObject.addProperty(CRUDObject.STATE_KEY, State.Pub.value)
        }
        val jsonString = gson.toJson(jsonObject)

        network.queryRestInterface.post(relativePath, createRawBodyFromJson(jsonString)!!).enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>) {
                var error: CRUDError? = null
                var result: String? = null
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val obj = response.body()!!.asJsonObject
                        result = obj.get("_id").asString
                    } catch (e:Exception){
                        callback.onCompleted(null, CRUDError(e.message))
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

    fun async(jsonArray: JsonArray, state: State?, callback: MultipleObjectsCallback<String>){
        val relativePath = if (crudVersion != null) "v$crudVersion/$collection" else collection

        val gson = GsonFactory.newGsonInstance()
        try {
            if (state != null) {
                for (element in jsonArray) {
                    (element.asJsonObject).addProperty(CRUDObject.STATE_KEY, state.value)
                }
            } else {
                for (element in jsonArray) {
                    (element.asJsonObject).addProperty(CRUDObject.STATE_KEY, State.Pub.value)
                }
            }
        } catch (e: Exception){
            callback.onCompleted(null, CRUDError(e.message))
        }
        val jsonString = gson.toJson(jsonArray)

        network.queryRestInterface.bulkPost(relativePath, createRawBodyFromJson(jsonString)!!).enqueue(object : Callback<JsonArray> {

            override fun onResponse(call: Call<JsonArray>?, response: Response<JsonArray>) {
                var error: CRUDError? = null
                var result: ArrayList<String>? = null
                if (response.isSuccessful && response.body() != null) {
                    try {
                        result = ArrayList()
                        val objs = response.body()!!.asJsonArray
                        for (obj in objs){
                            val jsonObject = obj.asJsonObject
                            result.add(jsonObject.get("_id").asString)
                        }
                    } catch (e: Exception){
                        callback.onCompleted(null, CRUDError(e.message))
                    }

                } else {
                    error = CRUDError("Error code ${response.code()}")
                }

                callback.onCompleted(result, error)
            }

            override fun onFailure(call: Call<JsonArray>?, t: Throwable?) {
                callback.onCompleted(null, CRUDError(t?.message))
            }
        })
    }


    //Sync

    @Throws
    fun <T: Serializable> sync(obj: T, state: State?): String?{
        try {
            val jsonObject = GsonFactory.newGsonInstance().toJsonTree(obj).asJsonObject
            return sync(jsonObject, state)
        } catch (e: Exception) {
            throw e
        }

    }

    @Throws
    fun sync(jsonObject: JsonObject, state: State?): String? {
        val relativePath = if (crudVersion != null) "v$crudVersion/$collection" else collection

        try {
            val gson = GsonFactory.newGsonInstance()
            if(state != null) {
                jsonObject.addProperty(CRUDObject.STATE_KEY, state.value)
            } else {
                jsonObject.addProperty(CRUDObject.STATE_KEY, State.Pub.value)
            }
            val jsonString = gson.toJson(jsonObject)

            val response= network.queryRestInterface.post(relativePath, createRawBodyFromJson(jsonString)!!).execute()

            if (response.isSuccessful){
                val obj = response.body()
                return obj?.get("_id")?.asString
            } else {
                throw CRUDError("Error code ${response.code()}")
            }

        } catch (e: Exception) {
            throw e
        }

    }

    @Throws
    fun <T: Serializable> sync(objs: ArrayList<T>, state: State?): ArrayList<String>?{
        val array = JsonArray()

        try {
            for(obj in objs) {
                val element = GsonFactory.newGsonInstance().toJsonTree(obj).asJsonObject
                array.add(element)
            }
            return sync(array, state)
        } catch (e: Exception){
            throw e
        }

    }

    @Throws
    fun sync(jsonArray: JsonArray, state: State?): ArrayList<String>? {
        val relativePath = if (crudVersion != null) "v$crudVersion/$collection" else collection

        try {
            val gson = GsonFactory.newGsonInstance()
            if(state != null) {
                for (element in jsonArray) {
                    (element.asJsonObject).addProperty(CRUDObject.STATE_KEY, state.value)
                }
            }else {
                for (element in jsonArray) {
                    (element.asJsonObject).addProperty(CRUDObject.STATE_KEY, State.Pub.value)
                }
            }
            val jsonString = gson.toJson(jsonArray)

            val response = network.queryRestInterface.bulkPost(relativePath, createRawBodyFromJson(jsonString)!!).execute()

            if(response.isSuccessful){
                val objs = response.body()
                var ids: ArrayList<String>? = null

                if(objs != null) {
                    ids = ArrayList()
                    for (obj in objs) {
                        val id = obj.asJsonObject.get("_id")?.asString
                        ids.add(id!!)
                    }
                }

                return ids
            } else {
                throw CRUDError("Error code ${response.code()}")
            }

        } catch (e: Exception) {
            throw e
        }
    }

    // Change state

    fun asyncChangeState(id: String, state: State, callback: NoObjectCallback){
        val relativePath = if (crudVersion != null) "v$crudVersion/$collection/$id/state" else "$collection/$id/state"

        val gson = GsonFactory.newGsonInstance()
        val jsonObject = JsonObject()
        jsonObject.addProperty("stateTo", state.value)
        val jsonString = gson.toJson(jsonObject)

        network.queryRestInterface.statePost(relativePath, createRawBodyFromJson(jsonString)!!).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                var error: CRUDError? = null
                if (!response.isSuccessful) {
                    error = CRUDError("Error code ${response.code()}")
                }
                callback.onCompleted(error)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                callback.onCompleted(CRUDError(t?.message))
            }
        })
    }

    fun syncChangeState(id: String, state: State): CRUDError?{
        val relativePath = if (crudVersion != null) "v$crudVersion/$collection/$id/state" else "$collection/$id/state"

        val gson = GsonFactory.newGsonInstance()
        val jsonObject = JsonObject()
        jsonObject.addProperty("stateTo", state.value)
        val jsonString = gson.toJson(jsonObject)

        val response = network.queryRestInterface.statePost(relativePath, createRawBodyFromJson(jsonString)!!).execute()

        return if(response.isSuccessful) null else CRUDError("Error code ${response.code()}")
    }


    // Private

    private fun createRawBodyFromJson(json: String): RequestBody? {
        try {
            return RequestBody.create(MediaType.parse("application/json"), json.toByteArray(charset("UTF-8")))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return null
    }
}