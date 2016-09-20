package newgroup;

import org.springframework.security.core.authority.AuthorityUtils;

import lombok.Data;
import newgroup.entity.MyDataEntity;

@Data
public class LoginUserDetails extends org.springframework.security.core.userdetails.User {
    private final MyDataEntity myDataEntity;

    public LoginUserDetails(MyDataEntity myDataEntity) {
        super(myDataEntity.getName(), myDataEntity.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
        this.myDataEntity = myDataEntity;
    }
}