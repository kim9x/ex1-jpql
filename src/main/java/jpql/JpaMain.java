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
			
			// �Ʒ��� ��ũ ���� ������ ����ص� ���Ӽ� ���ؽ�Ʈ�� �ִ�
			// ��� ���� ���������� ���Ѵ�.
			// JPA�� �� ������ �ȶ����� �ʴ�.. �Ф�
			int resultCount = em.createQuery("update Member m set m.age = 20")
					.executeUpdate();
			em.clear();
			
			// ���� ���� �������Ƿ� 20���� ����� �����͸� �����´�.
			Member findMember = em.find(Member.class, member1.getId());
			System.out.println("findMember = " + findMember);
			
			System.out.println("resultCount = " + resultCount);
			
			// �Ʒ� ������ age�� 10�� �����Ǿ� �ִ�.
			// ������ 58~60 ���� ����.!
			System.out.println("member1.getAge() = " + member1.getAge());
			System.out.println("member2.getAge() = " + member2.getAge());
			System.out.println("member3.getAge() = " + member3.getAge());
			
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

