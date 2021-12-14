package jpql;

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
			
//			TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
//			TypedQuery<Member> query2 = em.createQuery("select m.username from Member m", Member.class);
//			Query query3 = em.createQuery("select m.username, m.age from Member m");
			
			// 파라미터 바인딩
			TypedQuery<Member> query4 = em.createQuery("select m from Member m where m.username = :username", Member.class);
			query4.setParameter("username", "member1");
			Member singleResult = query4.getSingleResult();
			System.out.println("singleResult = " + singleResult);
			
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
