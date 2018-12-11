package eu.miaplatform.crud.library

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import eu.miaplatform.crud.library.enums.Parameters
import eu.miaplatform.crud.library.enums.State
import eu.miaplatform.crud.library.listeners.MultipleObjectsCallback
import eu.miaplatform.crud.library.listeners.SingleObjectCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.util.*

class GET (private var collection: String, private val network: Network, private val crudVersion: Int) {

    private var properties: String? = null
    private var query: String? = null
    private var state: String? = null
    private var limit: Int? = null
    private var skip: Int? = null
    private var sort: String? = null

    constructor(collection: String, query: String?, network: Network, crudVersion: Int) : this(collection, network, crudVersion) {
        if(query != null) this.query = query
    }

    constructor(collection: String, queryBuilder: QueryBuilder?, network: Network, crudVersion: Int): this(collection, network, crudVersion) {
        if(queryBuilder != null) this.query = queryBuilder.build()
    }


    // Public

    fun limitProperties(items: ArrayList<String>){
        this.properties = items.joinToString(separator = ",")
    }

    fun limitProperty(item: String){
        this.properties = item
    }

    fun state(states: ArrayList<State>){
        this.state = states.joinToString(separator = ",") { it.value }
    }

    fun state(state: State){
        this.state = state.value
    }

    fun limit(limit: Int){
        this.limit = limit
    }

    fun skip(skip: Int){
        this.skip = skip
    }

    fun sortBy(property: String, descending: Boolean){
        this.sort = if(descending) "-$property" else property
    }

    // Async

    fun async(callback: SingleObjectCallback<JsonElement>){
        val queryParameters = getQueryParameters()
        val relativePath = "/v$crudVersion/$collection/"
        async(relativePath, queryParameters, callback)
    }

    fun async(id: String, callback: SingleObjectCallback<JsonElement>){
        val queryParameters = getQueryParameters()
        val relativePath = "/v$crudVersion/$collection/$id"
        async(relativePath, queryParameters, callback)
    }

    fun <T: Serializable> async(clazz: Class<T>, callback: MultipleObjectsCallback<T>){
        async (object: SingleObjectCallback<JsonElement> {
            override fun onCompleted(result: JsonElement?, error: CRUDError?) {
                var callError: CRUDError? = null
                var objs: ArrayList<T>? = null
                if(result != null && error == null){
                    try {
                        objs = ArrayList()
                        val jsonArray = result.asJsonArray
                        for (element in jsonArray){
                            objs.add(Gson().fromJson(element, clazz))
                        }
                    } catch (e: Exception){
                        callback.onCompleted(null, CRUDError(e.message))
                    }
                } else {
                    callError = error
                }
                callback.onCompleted(objs, callError)
            }
        })
    }

    fun <T: Serializable> async(id: String, clazz: Class<T>, callback: SingleObjectCallback<T>){
        async (id, object: SingleObjectCallback<JsonElement> {
            override fun onCompleted(result: JsonElement?, error: CRUDError?) {
                var callError: CRUDError? = null
                var obj: T? = null
                if(result != null && error == null){
                    try {
                        obj = Gson().fromJson(result, clazz)
                    } catch (e: Exception){
                        callback.onCompleted(null, CRUDError(e.message))
                    }

                } else {
                    callError = error
                }
                callback.onCompleted(obj, callError)
            }
        })
    }

    // Sync

    @Throws
    fun sync(): JsonElement? {
        val queryParameters = getQueryParameters()
        val relativePath = "/v$crudVersion/$collection/"
        return sync(relativePath, queryParameters)
    }

    @Throws
    fun sync(id: String): JsonElement? {
        val queryParameters = getQueryParameters()
        val relativePath = "/v$crudVersion/$collection/$id"
        return sync(relativePath, queryParameters)
    }

    @Throws
    fun <T: Serializable> sync(clazz: Class<T>): ArrayList<T>? {
        val queryParameters = getQueryParameters()
        val relativePath = "/v$crudVersion/$collection/"
        val jsonElement = sync(relativePath, queryParameters)
        var objs: ArrayList<T>? = null

        if(jsonElement != null) {
            objs = ArrayList()
            try {
                val jsonArray = (jsonElement as JsonArray)
                for (element in jsonArray) {
                    objs.add(Gson().fromJson(element, clazz))
                }
            } catch (e: Exception) {
                throw e
            }
        }
        return objs
    }

    @Throws
    fun <T: Serializable> sync(id: String, clazz: Class<T>): T? {
        val queryParameters = getQueryParameters()
        val relativePath = "/v$crudVersion/$collection/$id"
        val jsonElement = sync(relativePath, queryParameters)
        try {
            return Gson().fromJson(jsonElement, clazz)
        } catch (e: Exception) {
            throw e
        }
    }

    // Count

    fun asyncCount(callback: SingleObjectCallback<Int>?) {
        val queryParameters = getQueryParameters()
        val relativePath = "/v$crudVersion/$collection/count"

        network.queryRestInterface.get("$relativePath?$queryParameters").enqueue(object : Callback<JsonElement> {

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                var error: CRUDError? = null
                var result: Int? = null
                if (response.isSuccessful && response.body() != null) {
                    try {
                        result = response.body()!!.asInt
                    } catch (e: Exception) {
                        error = CRUDError(e.message)
                    }
                } else {
                    error = CRUDError("Error code ${response.code()}")
                }

                callback?.onCompleted(result, error)
            }

            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                callback?.onCompleted(null, CRUDError(t?.message))
            }
        })
    }

    @Throws
    fun syncCount(): Int? {
        val queryParameters = getQueryParameters()
        val relativePath = "/v$crudVersion/$collection/count"
        val response = network.queryRestInterface.get("$relativePath?$queryParameters").execute()
        if(response.isSuccessful) {
            try {
                return response.body()!!.asInt
            } catch (e: Exception){
                throw e
            }
        } else {
            throw CRUDError("Error code ${response.code()}")
        }
    }

    // Private

    private fun async(relativePath: String, queryParameters: String, callback: SingleObjectCallback<JsonElement>?) {
        network.queryRestInterface.get("$relativePath?$queryParameters").enqueue(object : Callback<JsonElement> {

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                var error: CRUDError? = null
                var result: JsonElement? = null
                if (response.isSuccessful && response.body() != null) {
                    result = response.body()
                } else {
                    error = CRUDError("Error code ${response.code()}")
                }

                callback?.onCompleted(result, error)
            }

            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                callback?.onCompleted(null, CRUDError(t?.message))
            }
        })
    }

    private fun sync(relativePath: String, queryParameters: String): JsonElement? {
        val response = network.queryRestInterface.get("$relativePath?$queryParameters").execute()
        if(response.isSuccessful) {
            return response.body()
        } else {
            throw CRUDError("Error code ${response.code()}")
        }
    }

    private fun getQueryParameters(): String {
        val queryParameters = HashMap<String, Any>()

        if (query != null){
            queryParameters[Parameters.QUERY.value] = query!!
        }

        if (properties != null){
            queryParameters[Parameters.PROPERTIES.value] = properties!!
        }

        if(state != null){
            queryParameters[Parameters.STATE.value] = state!!
        }

        if(limit != null){
            queryParameters[Parameters.LIMIT.value] = limit!!
        }

        if(skip != null){
            queryParameters[Parameters.SKIP.value] = skip!!
        }

        if(sort != null){
            queryParameters[Parameters.SORT.value] = sort!!
        }

        return queryParameters.map { "${it.key}=${it.value}" }.joinToString(separator = "&")

    }

}