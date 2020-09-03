package eu.miaplatform.crud.library

import eu.miaplatform.crud.library.restInterfaces.DefaultRestInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network(private val APIEndpoint: String) {

    private var queryAdapter: Retrofit
    var queryRestInterface: DefaultRestInterface

    private var retrofitLogLevel: HttpLoggingInterceptor.Level? = null
    private var loggingInterceptor: HttpLoggingInterceptor

    private var httpClient: OkHttpClient

    /**
     * The entry-point method of the SDK
     *
     * @param context     context
     * @param APISecret   the BaaS api secret
     * @param APIEndpoint the BaaS url
     */
    init {
        this.loggingInterceptor = HttpLoggingInterceptor()
        this.httpClient = OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build()
        this.queryAdapter = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonFactory.newGsonInstance())).baseUrl(this.APIEndpoint).client(httpClient).build()
        this.queryRestInterface = queryAdapter.create<DefaultRestInterface>(DefaultRestInterface::class.java)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
    }

    /**
     * The http request log level can be changed here, default value is [okhttp3.logging.HttpLoggingInterceptor.Level.BASIC]
     *
     * @param loglevel the level to set
     */
    public fun setHttpLogLevel(loglevel: HttpLoggingInterceptor.Level) {
        loggingInterceptor.level = loglevel
        this.retrofitLogLevel = loglevel
    }

    public fun addInterceptor(interceptor: Interceptor) {
        val builder = httpClient.newBuilder()

        try {
            builder.interceptors().remove(loggingInterceptor)
        } catch (ignored: Exception) {
        }

        builder.addInterceptor(interceptor)

        try {
            builder.addInterceptor(loggingInterceptor)
        } catch (ignored: Exception) {
        }

        httpClient = builder.addInterceptor(interceptor).build()
        this.queryAdapter = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonFactory.newGsonInstance())).baseUrl(this.APIEndpoint).client(httpClient).build()
        this.queryRestInterface = queryAdapter.create<DefaultRestInterface>(DefaultRestInterface::class.java)
    }
}
