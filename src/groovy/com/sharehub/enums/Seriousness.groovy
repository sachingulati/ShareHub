package com.sharehub.enums

/**
 * Created by intelligrape on 19/2/15.
 */
enum Seriousness {
    SERIOUS("Serious"),
    VERY_SERIOUS("Very serious"),
    CASUAL("Casual")
    final String displayName
    Seriousness(String name){
        displayName = name;
    }

}
