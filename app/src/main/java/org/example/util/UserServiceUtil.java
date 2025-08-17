package org.example.util;

import org.mindrot.jbcrypt.BCrypt;


public class UserServiceUtil {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty() && (password == null || password.isEmpty())) {
            return false;
        }
        return BCrypt.checkpw(password, hashedPassword);
    }


}
