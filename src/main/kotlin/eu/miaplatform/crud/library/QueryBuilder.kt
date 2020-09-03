package eu.miaplatform.crud.library

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import eu.miaplatform.crud.library.enums.MongoOperator
import java.io.Serializable


class QueryBuilder {

    var query = JsonObject()

    fun build() : String {
        return query.toString()
    }

    fun and(vararg queryBuilders: QueryBuilder) : QueryBuilder {
        return logicalQuery(queryBuilders, MongoOperator.AND.value)
    }

    fun or(vararg queryBuilders: QueryBuilder) : QueryBuilder {
        return logicalQuery(queryBuilders, MongoOperator.OR.value)
    }

    fun nor(vararg queryBuilders: QueryBuilder) : QueryBuilder {
        return logicalQuery(queryBuilders, MongoOperator.NOR.value)
    }

    fun <T: Serializable> not(key: String, comparisonOperator: MongoOperator, value: T) : QueryBuilder {
        val obj = JsonObject()
        obj.add(comparisonOperator.value, GsonFactory.newGsonInstance().toJsonTree(value))
        val notQuery = JsonObject()
        notQuery.add(MongoOperator.NOT.value, obj)
        query.add(key, notQuery)
        return this
    }

    fun <T: Serializable> equals(key: String, value: T): QueryBuilder {

        query.add(key, GsonFactory.newGsonInstance().toJsonTree(value))

        return this
    }

    fun <T: Serializable> notEquals(key: String, value: T): QueryBuilder {
        return comparisonQuery(key, value, MongoOperator.NOT_EQUALS.value)
    }

    fun <T: Serializable> greater(key: String, value: T): QueryBuilder {
        return comparisonQuery(key, value, MongoOperator.GREATER_THAN.value)
    }

    fun <T: Serializable> greaterOrEquals(key: String, value: T): QueryBuilder {
        return comparisonQuery(key, value, MongoOperator.GREATER_THAN_EQUALS.value)
    }

    fun <T: Serializable> less(key: String, value: T): QueryBuilder {
        return comparisonQuery(key, value, MongoOperator.LESS_THAN.value)
    }

    fun <T: Serializable> lessOrEquals(key: String, value: T): QueryBuilder {
        return comparisonQuery(key, value, MongoOperator.LESS_THAN_EQUALS.value)
    }

    fun <T: Serializable> inArray(key: String, vararg values: T): QueryBuilder {
        val valuesArray = JsonArray()

        for (value in values) {
            valuesArray.add(GsonFactory.newGsonInstance().toJsonTree(value))
        }

        val inJsonObject = JsonObject()
        inJsonObject.add(MongoOperator.IN.value, valuesArray)

        query.add(key, inJsonObject)

        return this
    }

    fun <T: Serializable> notInArray(key: String, vararg values: T): QueryBuilder {
        val valuesArray = JsonArray()

        for (value in values) {
            valuesArray.add(GsonFactory.newGsonInstance().toJsonTree(value))
        }

        val inJsonObject = JsonObject()
        inJsonObject.add(MongoOperator.NOT_IN.value, valuesArray)

        query.add(key, inJsonObject)

        return this
    }

    private fun <T> comparisonQuery(key: String, value: T, mongoOperator: String): QueryBuilder {
        val obj = JsonObject()
        obj.add(mongoOperator, GsonFactory.newGsonInstance().toJsonTree(value))
        query.add(key, obj)

        return this
    }

    private fun logicalQuery(queryBuilders: Array<out QueryBuilder>, mongoOperator: String): QueryBuilder {
        val array = JsonArray()

        for(builder in queryBuilders){
            array.add(builder.query)
        }

        query.add(mongoOperator, array)

        return this
    }

}