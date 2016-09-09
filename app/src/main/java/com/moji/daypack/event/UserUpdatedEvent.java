package com.moji.daypack.event;

import com.moji.daypack.data.model.User;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/2/16
 * Time: 11:53 PM
 * Desc: UserUpdatedEvent
 */
public class UserUpdatedEvent {

    public User user;

    public UserUpdatedEvent(User user) {
        this.user = user;
    }
}
