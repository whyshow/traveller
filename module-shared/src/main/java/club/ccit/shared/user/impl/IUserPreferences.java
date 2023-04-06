package club.ccit.shared.user.impl;

/**
 * FileName: IUserPreferences
 *
 * @author: mosaic
 * Date: 2023/4/6 08:24
 * Description:
 * Version:
 */
public interface IUserPreferences {
    String getUserPhone();

    void putUserPhone(String value);

    String getUserPassword();

    void putUserPassword(String value);

    String UserPhone = "userPhone";
    String UserPassword = "userPassword";

}
