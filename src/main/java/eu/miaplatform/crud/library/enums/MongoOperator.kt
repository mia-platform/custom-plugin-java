package eu.miaplatform.crud.library.enums

enum class MongoOperator(var value: String) {
    AND("\$and"),
    OR("\$or"),
    GREATER_THAN("\$gt"),
    GREATER_THAN_EQUALS("\$gte"),
    LESS_THAN("\$lt"),
    LESS_THAN_EQUALS("\$lte"),
    IN("\$in"),
    NOT_IN("\$nin"),
    NOT_EQUALS("\$ne"),
    NOT("\$not"),
    NOR("\$nor")
}