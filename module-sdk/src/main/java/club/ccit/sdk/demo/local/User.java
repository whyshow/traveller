package club.ccit.sdk.demo.local;

/**
 * FileName: User
 *
 * @author: 张帅威
 * Date: 2021/12/19 2:11 下午
 * Description:
 * Version:
 */
public class User {
    private int code;
    private String message;

    @Override
    public String toString() {
        return "User{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
