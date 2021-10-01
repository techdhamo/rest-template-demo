package in.otomate.adminloginservice.util;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.LongType;

import in.otomate.adminloginservice.logger.Log;
import in.otomate.adminloginservice.model.AdminModel; 
 

public class AdminIDGenerator implements IdentifierGenerator {

	private Long max;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
		if (obj instanceof AdminModel) {
			String query = String.format("SELECT %s FROM admin_details ORDER BY %s DESC LIMIT 1",
					session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName(),
					 session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName());
			Query qry = session.createSQLQuery(query).addScalar("id", LongType.INSTANCE);
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