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
			member.setUsername("������");
			member.setAge(10);
			member.setType(MemberType.ADMIN);
			
			member.setTeam(team);
			
			em.persist(member);
			
			em.flush();
			em.clear();
			
//			String query = "select " +
//								"case when m.age <= 10 then '�л����' " +
//								"	  when m.age >= 60 then '��ο��' " +
//								"	  else '�Ϲݿ��' " +
//								"end " +
//						   "from Member m";
//			
//			List<String> result = em.createQuery(query, String.class)
//					.getResultList();
//			
//			for (String s : result) {
//				System.out.println("s = " + s);
//			}
			
//			String query = "select coalesce(m.username, '�̸� ���� ȸ��') from Member m";
			
			String query = "select nullif(m.username, '������') from Member m";

			List<String> result = em.createQuery(query, String.class)
					.getResultList();
			
			for (String s : result) {
				System.out.println("s = " + s);
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

