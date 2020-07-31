package eu.miaplatform.crud.library

import com.google.gson.JsonElement
import eu.miaplatform.crud.library.enums.Parameters
import eu.miaplatform.crud.library.enums.State
import eu.miaplatform.crud.library.listeners.NoObjectCallback
import eu.miaplatform.crud.library.listeners.SingleObjectCallback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class DELETE (private val collection: String, private val network: Network, private val crudVersion: Int? = null) {

    private var query: String? = null
    private var state: String? = null

    constructor(collection: String, query: String, network: Network, crudVersion: Int? = null) : this(collection, network, crudVersion) {
        this.query = query
    }

    constructor(collection: String, queryBuilder: QueryBuilder, network: Network, crudVersion: Int? = null): this(collection, network, crudVersion) {
        this.query = queryBuilder.build()
    }

    // Async

    fun async(id: String, callback: NoObjectCallback){
        val queryParameters = getQueryParameters()
        val partialPath = if (crudVersion != null) "v$crudVersion/$collection/$id" else "$collection/$id"
        val relativePath = if(queryParameters.isEmpty()) partialPath else "$partialPath?$queryParameters"

        network.queryRestInterface.deleteId(relativePath).enqueue(object : Callback<ResponseBody> {

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

    fun async(callback: SingleObjectCallback<Int>){
        val queryParameters = getQueryParameters()
        val partialPath = if (crudVersion != null) "v$crudVersion/$collection/" else "$collection/"
        val relativePath = "$partialPath?$queryParameters"

        network.queryRestInterface.delete(relativePath).enqueue(object : Callback<JsonElement> {

            override fun onResponse(call: Call<JsonElement>?, response: Response<JsonElement>) {
                var error: CRUDError? = null
                var result: Int? = null
                if (response.isSuccessful && response.body() != null) {
                    try {
                        result =  response.body()!!.asInt
                    } catch (e: Exception){
                        error = CRUDError(e.message)
                    }
                } else {
                    error = CRUDError("Error code ${response.code()}")
                }

                callback.onCompleted(result, error)
            }

            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                callback.onCompleted(null, CRUDError(t?.message))
            }
        })
    }

    // Sync

    @Throws
    fun sync(id: String) {
        val queryParameters = getQueryParameters()
        val partialPath = if (crudVersion != null) "v$crudVersion/$collection/$id" else "$collection/$id"
        val relativePath = if(queryParameters.isEmpty()) partialPath else "$partialPath?$queryParameters"
        val response = network.queryRestInterface.deleteId(relativePath).execute()
        if(!response.isSuccessful){
            throw CRUDError("Error code ${response.code()}")
        }
    }

    @Throws
    fun sync(): Int? {
        val queryParameters = getQueryParameters()
        val partialPath = if (crudVersion != null) "v$crudVersion/$collection/" else "$collection/"
        val relativePath = "$partialPath?$queryParameters"
        val response = network.queryRestInterface.delete(relativePath).execute()
        if (response.isSuccessful){
            try {
                return response.body()!!.asInt
            } catch (e: Exception) {
                throw e
            }
        } else {
            throw CRUDError("Error code ${response.code()}")
        }
    }


    // Public

    fun state(state: State){
        this.state = state.value
    }

    fun state(state: ArrayList<State>){
        this.state = state.joinToString(separator = ",") { it.value }
    }

    // Private

    private fun getQueryParameters(): String {
        val queryParameters = HashMap<String, Any>()

        if (query != null){
            queryParameters[Parameters.QUERY.value] = query!!
        }

        if(state != null){
            queryParameters[Parameters.STATE.value] = state!!
        }

        return queryParameters.map { "${it.key}=${it.value}" }.joinToString(separator = "&")

    }
}