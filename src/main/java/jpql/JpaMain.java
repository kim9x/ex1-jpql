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
//			// update ������ ������.(��ƼƼ ��������)
//			// ��, ���Ӽ� ���ؽ�Ʈ���� ������.!
//			findMember.setAge(20);
			
			// ��ƼƼ ��������
			// ������ "select m.team from Member m"�� �ۼ��ص� �Ǳ� �ϳ�
			// ������ join ������ ������.
			// �� �� �����(?)���� �� ���̱� ���� jpql ���������� join�� ���Խ����ش�.
//			List<Team> result2 = em.createQuery("select m.team from Member m join m.team t", Team.class)
//					.getResultList();

			
			// �Ӻ���� Ÿ�� ��������
//			List<Address> result3 = em.createQuery("select o.address from Order o", Address.class)
//					.getResultList();
			
			// ��Į�� Ÿ�� ��������(�Ϲ� sql��?) 
			// �Ʒ�ó�� distinct ���� ��� �����ϴ�.
//			List resultList = em.createQuery("select distinct m.username, m.age from Member m")
//					.getResultList();
//			
//			Object o = resultList.get(0);
//			Object[] result = (Object[]) o;
//			System.out.println("result = " + result[0]);
//			System.out.println("result = " + result[1]);
			
			// �Ʒ�ó�� List<Object[]>�� �޴� ����� �ִ�.
//			List<Object[]> resultList = em.createQuery("select distinct m.username, m.age from Member m")
//					.getResultList();
//			
//			Object[] result = resultList.get(0);
//			System.out.println("result = " + result[0]);
//			System.out.println("result = " + result[1]);
			
			// DTO�� ���� ����Ͽ� �������� �Ʒ�ó�� new operation�� ������־�� �Ѵ�.
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
