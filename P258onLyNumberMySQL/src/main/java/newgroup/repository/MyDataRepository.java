//dataの処理は、controller空呼ばれたこのripositoryが処理をする

package newgroup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import newgroup.entity.MyDataEntity;

@Repository
public interface MyDataRepository  extends JpaRepository<MyDataEntity, Long> {

	public static MyDataEntity findByName(String name) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	public MyDataEntity findById(long id);
	public MyDataEntity findByPassword(String password);
	public List<MyDataEntity> findByNameLike(String name);
	public List<MyDataEntity> findByIdIsNotNullOrderByIdDesc();
	public List<MyDataEntity> findByAgeGreaterThan(Integer age);
	public List<MyDataEntity> findByAgeBetween(Integer age1, Integer age2);

}
