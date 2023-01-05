package com.nextfeed.service.core.session.ports.incoming.usermanagement;

import com.nextfeed.library.core.valueobject.user.OptionalUserValue;
import com.nextfeed.library.core.valueobject.user.UserValue;

public interface IUserManager {

    UserValue createUser(String mailAddress, String name, String pw);
    OptionalUserValue getUserById(Integer id);
    OptionalUserValue getUserByMailAddress(String mailAddress);
    boolean validatePasswordByMailAddress(String mailAddress, String pw);
}
