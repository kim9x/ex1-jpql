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
			teamA.setName("��A");
			em.persist(teamA);
			
			Team teamB = new Team();
			teamB.setName("��B");
			em.persist(teamB);
			
			Member member1 = new Member();
			member1.setUsername("ȸ��1");
			member1.setAge(10);
			member1.setType(MemberType.ADMIN);
			member1.setTeam(teamA);
			em.persist(member1);
			
			Member member2 = new Member();
			member2.setUsername("ȸ��2");
			member2.setAge(10);
			member2.setType(MemberType.ADMIN);
			member2.setTeam(teamA);
			em.persist(member2);
			
			Member member3 = new Member();
			member3.setUsername("ȸ��3");
			member3.setAge(10);
			member3.setType(MemberType.ADMIN);
			member3.setTeam(teamB);
			em.persist(member3);
			
			
			em.flush();
			em.clear();
			
			
			// Member ��ƼƼ�� ���� parameter�� �־��ָ�
			// PK�� ������ ���� ����Ѵ�.
//			String query = "select m from Member m where m = :member";
//			
//			List<Member> result = em.createQuery(query, Member.class)
//					.setParameter("member", member1)
//					.getResultList();
			
			// ���� ���� ������ ������ .
//			String query = "select m from Member m where m.id = :memberId";
//			
//			List<Member> result = em.createQuery(query, Member.class)
//					.setParameter("memberId", member1.getId())
//					.getResultList();
			
			// �ܷ� ���� parameter�� �־��ָ�
			// �ܷ� Ű ���� joinclumn���� ������ ���� ����Ѵ�.
			String query2 = "select m from Member m where m.team = :team";
			
			List<Member> result2 = em.createQuery(query2, Member.class)
					.setParameter("team", teamA)
					.getResultList();
			
//			String query2 = "select m from Member m where m.teamId = :teamId";
//			
//			List<Member> result2 = em.createQuery(query2, Member.class)
//					.setParameter("teamId", teamA.getId())
//					.getResultList();
			
			
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

