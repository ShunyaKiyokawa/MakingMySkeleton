//controllerやmainより下の階層、または同じ階層におくこと。
//loginページが表示されず、ダイアログが出てくる

package newgroup;

//ここで認証認可の設定をする

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//USER Role
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/nosecurity").permitAll() //URLがマッチしたら誰でも見れる
                .antMatchers("/admin/**").hasRole("ROLE-ADMIN")
                //.hasRole("USER") // USER 権限のみアクセス可 認可
                //.permitAll()は全ユーザーアクセス可
                .anyRequest().authenticated() //リクエストあるとここにリダイレクト
                .and()
            .formLogin()
                .loginPage("/login") //認証ページ
                .permitAll() //URLがどこにもマッチしなかったとき、loginマッチしたとき誰でも見れる
                .defaultSuccessUrl("/", true) // 認証成功時の遷移先
                //.failureUrl("/")
                .usernameParameter("name")
                .passwordParameter("password")
                .and()
            .logout()
           // .logoutSuccessUrl("/")
            //.deleteCookies("JSESSIONID")
            //.invalidateHttpSession(true)
                .permitAll();
    }
//@ModelAttribute MyDataEntity mydata
    @Autowired
   // private UserAuthService userAuthService;
    //private DataSource datasource; //コンテナが管理してる接続先情報を取得しdatasourceに入れる。

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
/*        auth
        //.userDetailsService(userAuthService);
        .jdbcAuthentication()
        .dataSource(datasource)
        .authoritiesByUsernameQuery("select name, 'ROLE-USER' from mydata where name = ?")
        .usersByUsernameQuery("select name, password, enabled from mydata where name = ?");*/
 /*		auth
             .inMemoryAuthentication()
             .withUser("user").password("password").roles("ROLE-USER");*/
		auth
			.inMemoryAuthentication()
				.withUser("admin").password("password").roles("ROLE-ADMIN");
				//.withUser("admin").password("password").roles("ADMIN", "CREATEUSER); //複数できそう？
        //scope globalでmemory保存
    }



}
