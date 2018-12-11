package eu.miaplatform.crud.library.enums

enum class State(var value: String) {
    Pub("PUBLIC"),
    Draft("DRAFT"),
    Trash("TRASH"),
    Deleted("DELETED"),
    ToPush("TO_PUSH"),
    ToPub("TO_PUBLIC"),
    ToDraft("TO_DRAFT"),
    ToTrash("TO_TRASH"),
    ToDeleted("TO_DELETED")
}