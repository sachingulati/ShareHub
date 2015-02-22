package com.sharehub.enums

enum ResourceType {
    LINK("Link"),
    DOCUMENT("Document")
    final String displayName

    ResourceType(String displayName){
        this.displayName = displayName
    }
}
