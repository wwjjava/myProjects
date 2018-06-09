package ztzb.com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ztzb.com.dao.IZtzbDao;
import ztzb.com.entity.A20EntityBean;
import ztzb.com.service.IA20Service;


@Service("A20ServiceImpl")
public class A20ServiceImpl implements IA20Service {
	@Resource(name = "ZtzbDaoImpl")
    private IZtzbDao ztzbDao;

	public IZtzbDao getZtzbDao() {
		return ztzbDao;
	}

	public void setZtzbDao(IZtzbDao ztzbDao) {
		this.ztzbDao = ztzbDao;
	}

	public void save(A20EntityBean a20) throws Exception {
		// TODO Auto-generated method stub
		ztzbDao.save(a20);
//		if(0==0)
//		throw new Exception("Yes! Pls rollback!");
//		a20.setA200002Attr("a200002");
//		a20Dao.save(a20);
	}

}
