package club.ccit.shared.user.impi;

/**
 * FileName: IUserPreferences
 *
 * @author: mosaic
 * Date: 2023/4/4 15:51
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
