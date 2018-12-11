package eu.miaplatform.crud.library

class CRUD(basePath: String) {

    private val crudVersion = 2
    val network: Network = Network(basePath)

    /**
     * Builds the GET object with a query builder.
     *
     * @param collection name of the BaaS collection
     * @param queryBuilder QueryBuilder object that represents a Mongo query
     * @return a GET object to perform the call
     */
    public fun get(collection: String, queryBuilder: QueryBuilder?): GET {
        return GET(collection, queryBuilder, network, crudVersion)
    }

    /**
     * Builds the GET object with a Mongo query.
     *
     * @param collection name of the BaaS collection
     * @param query Mongo query
     * @return a GET object to perform the call
     */
    fun get(collection: String, query: String): GET {
        return GET(collection, query, network, crudVersion)
    }

    /**
     * Builds the POST object.
     *
     * @param collection name of the BaaS collection
     * @return a POST object to perform the call
     */
    fun post(collection: String): POST {
        return POST(collection, network, crudVersion)
    }

    /**
     * Builds the DELETE object.
     *
     * @param collection name of the BaaS collection
     * @return a DELETE object to perform the call
     */
    fun delete(collection: String): DELETE {
        return DELETE(collection, network, crudVersion)
    }

    /**
     * Builds the DELETE object with a Mongo query.
     *
     * @param collection name of the BaaS collection
     * @param query Mongo query
     * @return a DELETE object to perform the call
     */
    fun delete(collection: String, query: String): DELETE {
        return DELETE(collection, query, network, crudVersion)
    }

    /**
     * Builds the DELETE object.
     *
     * @param collection name of the BaaS collection
     * @param queryBuilder QueryBuilder object that represents a Mongo query
     * @return a DELETE object to perform the call
     */
    fun delete(collection: String, queryBuilder: QueryBuilder): DELETE {
        return DELETE(collection, queryBuilder, network, crudVersion)
    }

    /**
     * Builds the PATCH object.
     *
     * @param collection name of the BaaS collection
     * @return a PATCH object to perform the call
     */
    fun patch(collection: String): PATCH {
        return PATCH(collection, network, crudVersion)
    }
}