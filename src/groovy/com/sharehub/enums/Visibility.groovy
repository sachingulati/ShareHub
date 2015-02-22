package com.sharehub.enums

enum Visibility {
    PRIVATE("Private"),
    PUBLIC("Public")

    final String displayName

    Visibility(String displayName){
       this.displayName = displayName
    }
}
