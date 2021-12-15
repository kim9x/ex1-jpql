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
			teamA.setName("팀A");
			em.persist(teamA);
			
			Team teamB = new Team();
			teamB.setName("팀B");
			em.persist(teamB);
			
			Member member1 = new Member();
			member1.setUsername("회원1");
			member1.setAge(10);
			member1.setType(MemberType.ADMIN);
			member1.setTeam(teamA);
			em.persist(member1);
			
			Member member2 = new Member();
			member2.setUsername("회원2");
			member2.setAge(10);
			member2.setType(MemberType.ADMIN);
			member2.setTeam(teamA);
			em.persist(member2);
			
			Member member3 = new Member();
			member3.setUsername("회원3");
			member3.setAge(10);
			member3.setType(MemberType.ADMIN);
			member3.setTeam(teamB);
			em.persist(member3);
			
			
			em.flush();
			em.clear();
			
			// 아래의 벌크 연산 쿼리를 사용해도 영속성 컨텍스트에 있는
			// 모든 값을 변경해주지 못한다.
			// JPA가 그 정도로 똑똑하지 않다.. ㅠㅠ
			int resultCount = em.createQuery("update Member m set m.age = 20")
					.executeUpdate();
			em.clear();
			
			// 값을 새로 가져오므로 20으로 변경된 데이터를 가져온다.
			Member findMember = em.find(Member.class, member1.getId());
			System.out.println("findMember = " + findMember);
			
			System.out.println("resultCount = " + resultCount);
			
			// 아래 값들은 age가 10로 설정되어 있다.
			// 설명은 58~60 라인 참고.!
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

