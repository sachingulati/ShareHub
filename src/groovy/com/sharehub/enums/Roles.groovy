package com.sharehub.enums

/**
 * Created by intelligrape on 20/5/15.
 */
enum Roles {
    ADMIN("admin"),
    USER("user")
    final String displayName
    Roles(String name){
        displayName = name
    }
    String toString(){
        "ROLE_" + this.displayName.toUpperCase()
    }
}
