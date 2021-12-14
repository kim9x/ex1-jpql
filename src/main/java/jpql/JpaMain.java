package jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.usertype.UserType;

public class JpaMain {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin() ;
		
		try {
			Team team = new Team();
			team.setName("teamA");
			em.persist(team);
			
			Member member = new Member();
			member.setUsername("member");
			member.setAge(10);
			member.setType(MemberType.ADMIN);
			
			member.setTeam(team);
			
			em.persist(member);
			
			em.flush();
			em.clear();
			
			
//			String query = "select m.username, 'HELLO', TRUE from Member m" +
//						" where m.type = jpql.MemberType.USER";
			
			String query = "select m.username, 'HELLO', TRUE from Member m" +
					" where m.type = :userType";
			
			List<Object[]> result = em.createQuery(query)
					// 보통 아래처럼 사용하므로 패키지명 포함하는 형식으로 잘 사용하지 않게됨!
					.setParameter("userType", MemberType.ADMIN)
					.getResultList(); 
			
			System.out.println("result = " + result.size());
			
			for (Object[] objects : result) {
				System.out.println("member1 = " + objects[0]);
				System.out.println("member1 = " + objects[1]);
				System.out.println("member1 = " + objects[2]);
			}
			
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
