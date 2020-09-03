package eu.miaplatform.crud.library.enums

enum class Parameters(var value: String) {
    QUERY("_q"),
    PROPERTIES("_p"),
    STATE("_st"),
    SORT("_s"),
    LIMIT("_l"),
    SKIP("_sk")
}