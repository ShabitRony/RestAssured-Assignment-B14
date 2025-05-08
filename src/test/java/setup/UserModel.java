package setup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {
    private String email;
    private  String password;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    private String phoneNumber;
    private String termsAccepted;



    public UserModel(){

    }
}
