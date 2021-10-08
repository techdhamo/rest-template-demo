package in.otomate.adminloginservice.util;
 

import java.io.Serializable;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import in.otomate.adminloginservice.logger.Log; 
 

public class BasicIDGenerator implements IdentifierGenerator {

	private Long max;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
		if (obj instanceof Object) {
			String query = String.format("SELECT s.id FROM %s s ORDER BY s.id DESC",obj.getClass().getSimpleName());
			Query qry = session.createQuery(query).setFirstResult(0).setMaxResults(1);
			List<Long> id = qry.getResultList();

			try {
				max = id.get(0);
			} catch (Exception e) {
				max = 0L;
			}
		}
		Log.info(this, Long.toString(max + 1));

		return (max + 1);
	}

}
