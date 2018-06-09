package ztzb.com.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ztzb.com.dao.IZtzbDao;
import ztzb.com.entity.A20EntityBean;

@Repository("ZtzbDaoImpl")
public class ZtzbDaoImpl extends BaseDao implements IZtzbDao {
    
	private Logger log = Logger.getLogger(ZtzbDaoImpl.class);
	public void save(Object object) {
    	
		try {
			this.getHibernateTemplate().saveOrUpdate(object);
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			throw new RuntimeException("insert into database Exception");
		}
	}

}
