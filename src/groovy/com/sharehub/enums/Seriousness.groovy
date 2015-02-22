package com.sharehub.enums

/**
 * Created by intelligrape on 19/2/15.
 */
enum Seriousness {
    SERIOUS("serious"),
    VERY_SERIOUS("very serious"),
    CASUAL("casual")
    final String displayName;
    Seriousness(String name){
        displayName = name;
    }

}
