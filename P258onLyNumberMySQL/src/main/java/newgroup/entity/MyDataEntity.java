//entitypackageはcontrollerより下の階層に配置すること。上の階層に置くとJuniterror当たりが出てきて動かない。
//データベースのエンティティを格納

package newgroup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import phoneVaridation.Phone;

//JPAの管理化にエンティティクラスとしておかれる
//よくわからんけどentityパッケージ配下に移動するとJUnitでBeansの名前が創れずコケる。
@Data
@Entity
@Table(name = "mydata")
public class MyDataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //値は自動生成
	@Column
	@NotNull
	//この辺でバリデーション。引っかかったらtemplatesの下のValidationMessages.propertiesが呼ばれる
	private long id;

	@Column(length = 50, nullable = false)
	@NotEmpty
	private String name;

	@Column(nullable = false)
	@Size(min=5, max=16) //文字数5文字以上最大16文字
	private String password;

	@Column(length = 200, nullable = true)
	@Email
	private String mail;

	@Column(nullable = true)
	@Min(value=0)
	@Max(value=200)
	private Integer age;

	@Column(nullable = true)
	@Phone(onlyNumber=true)
	private String memo;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}