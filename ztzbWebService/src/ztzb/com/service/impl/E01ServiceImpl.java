package ztzb.com.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


import ztzb.com.dao.IZtzbDao;
import ztzb.com.entity.E01EntityBean;
import ztzb.com.service.IE01Service;


@Service("E01ServiceImpl")
public class E01ServiceImpl implements IE01Service {
	private Logger log = Logger.getLogger(E01ServiceImpl.class);
	@Resource(name = "ZtzbDaoImpl")
    private IZtzbDao ztzbDao;

	public IZtzbDao getZtzbDao() {
		return ztzbDao;
	}

	public void setZtzbDao(IZtzbDao ztzbDao) {
		this.ztzbDao = ztzbDao;
	}


	public void save(E01EntityBean a20) throws Exception {
		// TODO Auto-generated method stub
		ztzbDao.save(a20);
		log.info(a20.getE010005Attr());
//		if(0==0)
//		throw new Exception("Yes! Pls rollback!");
//		a20.setA200002Attr("a200002");
//		a20Dao.save(a20);
	}

}
