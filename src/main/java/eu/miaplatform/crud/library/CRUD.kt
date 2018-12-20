package eu.miaplatform.crud.library

class CRUD(basePath: String = "https://crud-service") {

    val network: Network = Network(basePath)
    private var version: Int? = null

    /**
     * Builds the GET object with a query builder.
     *
     * @param collection name of the BaaS collection
     * @param queryBuilder QueryBuilder object that represents a Mongo query
     * @return a GET object to perform the call
     */
    public fun get(collection: String, queryBuilder: QueryBuilder?): GET {
        version?.let { return GET(collection, queryBuilder, network, version!!) }
        return GET(collection, queryBuilder, network)
    }

    /**
     * Builds the GET object with a Mongo query.
     *
     * @param collection name of the BaaS collection
     * @param query Mongo query
     * @return a GET object to perform the call
     */
    fun get(collection: String, query: String): GET {
        version?.let { return GET(collection, query, network, version!!) }
        return GET(collection, query, network)
    }

    /**
     * Builds the POST object.
     *
     * @param collection name of the BaaS collection
     * @return a POST object to perform the call
     */
    fun post(collection: String): POST {
        version?.let {
            return POST(collection, network, version!!)
        }
        return POST(collection, network)
    }

    /**
     * Builds the DELETE object.
     *
     * @param collection name of the BaaS collection
     * @return a DELETE object to perform the call
     */
    fun delete(collection: String): DELETE {
        version?.let {
            return DELETE(collection, network, version!!)
        }
        return DELETE(collection, network)
    }

    /**
     * Builds the DELETE object with a Mongo query.
     *
     * @param collection name of the BaaS collection
     * @param query Mongo query
     * @return a DELETE object to perform the call
     */
    fun delete(collection: String, query: String): DELETE {
        version?.let {
            return DELETE(collection, query, network, version!!)
        }
        return DELETE(collection, query, network)
    }

    /**
     * Builds the DELETE object.
     *
     * @param collection name of the BaaS collection
     * @param queryBuilder QueryBuilder object that represents a Mongo query
     * @return a DELETE object to perform the call
     */
    fun delete(collection: String, queryBuilder: QueryBuilder): DELETE {
        version?.let {
            return DELETE(collection, queryBuilder, network, version!!)
        }
        return DELETE(collection, queryBuilder, network)
    }

    /**
     * Builds the PATCH object.
     *
     * @param collection name of the BaaS collection
     * @return a PATCH object to perform the call
     */
    fun patch(collection: String): PATCH {
        version?.let {
            return PATCH(collection, network, version!!)
        }
        return PATCH(collection, network)
    }

    fun setVersion(version: Int) {
        this.version = version
    }

    fun getVersion() : Int? {
        return this.version
    }
}