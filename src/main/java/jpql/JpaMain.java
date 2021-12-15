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
			Team teamA = new Team();
			teamA.setName("ÆÀA");
			em.persist(teamA);
			
			Team teamB = new Team();
			teamB.setName("ÆÀB");
			em.persist(teamB);
			
			Member member1 = new Member();
			member1.setUsername("È¸¿ø1");
			member1.setAge(10);
			member1.setType(MemberType.ADMIN);
			member1.setTeam(teamA);
			em.persist(member1);
			
			Member member2 = new Member();
			member2.setUsername("È¸¿ø2");
			member2.setAge(10);
			member2.setType(MemberType.ADMIN);
			member2.setTeam(teamA);
			em.persist(member2);
			
			Member member3 = new Member();
			member3.setUsername("È¸¿ø3");
			member3.setAge(10);
			member3.setType(MemberType.ADMIN);
			member3.setTeam(teamB);
			em.persist(member3);
			
			
			em.flush();
			em.clear();
						
//			String query = "select m from Member m join fetch m.team";
			String query = "select distinct t from Team t join fetch t.members";

//			List<Member> result = em.createQuery(query, Member.class)
//					.getResultList();
			
			List<Team> result = em.createQuery(query, Team.class)
					.getResultList();
			
//			for (Member member : result) {
//				System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
//				// È¸¿ø1, ÆÀA(SQL)
//				// È¸¿ø2, ÆÀA(1Â÷Ä³½Ã)
//				// È¸¿ø3, ÆÀB(SQL)
//				
//				// È¸¿ø 100¸í -> N + 1
//			}
			
			for (Team team : result) {
				System.out.println("team = " + team.getName() + ", " + team.getMembers().size());
				for ( Member member : team.getMembers() ) {
					System.out.println("-> member = " + member);
				}
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

