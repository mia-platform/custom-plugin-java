package eu.miaplatform.crud.library.enums

enum class PatchCodingKey(var value: String) {
    Set("\$set"),
    SetOnInsert("\$setOnInsert"),
    Unset("\$unset"),
    Inc("\$inc"),
    Mul("\$mul"),
    CurrentDate("\$currentDate")
}