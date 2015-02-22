package com.sharehub.enums

/**
 * Created by intelligrape on 19/2/15.
 */
enum InviteStatus{
    SUBSCRIBED("Subscribed"),
    PENDING("Pending"),
    UNSUBSCRIBED("Unsubscribed");
    final String displayName;
    InviteStatus(String name){
        displayName = name;
    }
}