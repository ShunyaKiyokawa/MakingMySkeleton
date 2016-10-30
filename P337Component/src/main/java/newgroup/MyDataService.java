package newgroup;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import newgroup.entity.MyDataEntity;

//サービス登録アノテーション
@Service
public class MyDataService {

	//EntityManagerのBeanを自動的に割り当てるpersistenceContextアノテーション
	@PersistenceContext
	private EntityManager entityManager;

	public List<MyDataEntity> getAll() {
		return (List<MyDataEntity>) entityManager.createQuery("from MyDataEntity").getResultList();
	}

	//idでエンティティを取得
	public MyDataEntity getId(int num) {
		return (MyDataEntity)entityManager
				.createQuery("from MyDataEntity where id = " + num)
				.getSingleResult();
	}

	//usernameフィールドのテキスト検索でエンティティ取得
	public List<MyDataEntity> findUsername(String fstr) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MyDataEntity> query = builder.createQuery(MyDataEntity.class);
		Root<MyDataEntity> root = query.from(MyDataEntity.class);
		query.select(root).where(builder.equal(root.get("username"), fstr));
		List<MyDataEntity> list = null;
		list = (List<MyDataEntity>) entityManager.createQuery(query).getResultList();
		return list;
	}

}
