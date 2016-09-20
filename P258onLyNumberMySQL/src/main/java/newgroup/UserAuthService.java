package newgroup;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import newgroup.entity.MyDataEntity;
import newgroup.repository.MyDataRepository;

@Service
public class UserAuthService implements UserDetailsService{
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException{
		//UserDetailsServiceのメソッド実装。UserDetailsManagerなど既存の認証はUserDetailsServiceを実装している
		if (name==null || "".equals(name)){
			throw new UsernameNotFoundException("ユーザー名を入力してください");
		}
		MyDataEntity getUserNameForAuth = MyDataRepository.findByName(name);
		if (getUserNameForAuth == null){
			throw new UsernameNotFoundException("ユーザー名が登録されていません" + name);
		}
		return new LoginUserDetails(getUserNameForAuth);
	}
}
