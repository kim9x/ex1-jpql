package jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class JpaMain {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin() ;
		
		try {
			
			Member member = new Member();
			member.setUsername("member1");
			member.setAge(10);
			em.persist(member);
			
			em.flush();
			em.clear();
			
//			List<Member> result = em.createQuery("select m from Member m", Member.class)
//					.getResultList();
//			
//			Member findMember = result.get(0);
//			
//			// update 쿼리가 나간다.(엔티티 프로젝션)
//			// 즉, 영속성 컨텍스트에서 관리됨.!
//			findMember.setAge(20);
			
			// 엔티티 프로젝션
			// 쿼리를 "select m.team from Member m"로 작성해도 되긴 하나
			// 어차피 join 쿼리가 나간다.
			// 좀 더 명시적(?)으로 잘 보이기 위해 jpql 구문에서도 join을 포함시켜준다.
//			List<Team> result2 = em.createQuery("select m.team from Member m join m.team t", Team.class)
//					.getResultList();

			
			// 임베디드 타입 프로젝션
//			List<Address> result3 = em.createQuery("select o.address from Order o", Address.class)
//					.getResultList();
			
			// 스칼라 타입 프로젝션(일반 sql문?) 
			// 아래처럼 distinct 또한 사용 가능하다.
//			List resultList = em.createQuery("select distinct m.username, m.age from Member m")
//					.getResultList();
//			
//			Object o = resultList.get(0);
//			Object[] result = (Object[]) o;
//			System.out.println("result = " + result[0]);
//			System.out.println("result = " + result[1]);
			
			// 아래처럼 List<Object[]>로 받는 방법도 있다.
//			List<Object[]> resultList = em.createQuery("select distinct m.username, m.age from Member m")
//					.getResultList();
//			
//			Object[] result = resultList.get(0);
//			System.out.println("result = " + result[0]);
//			System.out.println("result = " + result[1]);
			
			// DTO를 따로 사용하여 받으려면 아래처럼 new operation을 사용해주어야 한다.
			List<MemberDTO> resultList2 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
					.getResultList();
			
			MemberDTO memberDTO = resultList2.get(0);
			
			System.out.println("memberDTO = " + memberDTO.getUsername());
			System.out.println("memberDTO = " + memberDTO.getAge());
			
			tx.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			tx.rollback();	
			e.printStackTrace();
		} finally {
			emf.close();
		}
		 
		
		
	}

}
