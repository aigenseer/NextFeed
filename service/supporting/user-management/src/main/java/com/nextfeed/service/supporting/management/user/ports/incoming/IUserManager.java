package com.nextfeed.service.supporting.management.user.ports.incoming;

import com.nextfeed.library.core.valueobject.user.OptionalUserValue;
import com.nextfeed.library.core.valueobject.user.UserValue;

public interface IUserManager {

    UserValue createUser(String mailAddress, String name, String pw);
    OptionalUserValue getUserById(Integer id);
    OptionalUserValue getUserByMailAddress(String mailAddress);
    boolean validatePasswordByMailAddress(String mailAddress, String pw);
}
