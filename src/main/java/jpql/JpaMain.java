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
			Team team = new Team();
			team.setName("teamA");
			em.persist(team);
			
			Member member = new Member();
			member.setUsername("member");
			member.setAge(10);
			
			member.setTeam(team);
			
			em.persist(member);
			
			em.flush();
			em.clear();
			
			// inner join (inner 생략 가능)
//			String query = "select m from Member m inner join m.team t";
			
			// outer join (outer 생략 가능)
//			String query = "select m from Member m left outer join m.team t";
			
			// 세타 조인(=croos join?, 막조인?)
			String query = "select m from Member m, Team t where m.username = t.name";
			List<Member> result = em.createQuery(query, Member.class) 
					.getResultList(); 
			
			System.out.println("result = " + result.size());
			for (Member member1 : result) {
				System.out.println("member1 = " + member1);
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
