//controllerやmainより下の階層、または同じ階層におくこと。
//loginページが表示されず、ダイアログが出てくる

package newgroup.security;

import javax.sql.DataSource;

//ここで認証認可の設定をする

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//USER Role
@Configuration
@EnableWebSecurity
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/nosecurity").permitAll() //URLがマッチしたら誰でも見れる
                .antMatchers("/admin/**").hasRole("ADMIN")
               // .antMatchers("/adminspage/**").hasRole("ADMIN")
                //.hasRole("USER") // USER 権限のみアクセス可 認可
                //.permitAll()は全ユーザーアクセス可
                //なお、html側ではROLE_を付ける必要がある。このページでROLE_ADMINと指定すると、ROLE_が自動でつけられるので邪魔というエラーが出る
                .anyRequest().authenticated() //リクエストあるとここにリダイレクト
                .and()
            .formLogin()
                .loginPage("/login") //認証ページ
                .permitAll() //URLがどこにもマッチしなかったとき、loginマッチしたとき誰でも見れる
                .defaultSuccessUrl("/", true) // 認証成功時の遷移先
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .jdbcAuthentication()
            .dataSource(dataSource)
            .authoritiesByUsernameQuery("select name as username, role as authority from mydata where name = ?")
            .usersByUsernameQuery("select name as username, password as password, true as enabled from mydata where name = ?")
            ;
        /*application.propertiesで指定されたdataSourceのデータベースにアクセスした後、mydataテーブルのnameを参照し、
          パスワードを比較している。asで置き換えてるのはspringSecurityが求める形に合わせるため*/
/*        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");*/
		auth
			.inMemoryAuthentication()
				.withUser("admin").password("password").roles("ADMIN");
				//.withUser("admin").password("password").roles("ADMIN", "CREATEUSER); //複数できそう？
        //scope globalでmemory保存
    }
}
