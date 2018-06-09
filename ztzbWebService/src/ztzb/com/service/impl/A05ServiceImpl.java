package ztzb.com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ztzb.com.dao.IZtzbDao;
import ztzb.com.entity.A05EntityBean;
import ztzb.com.service.IA05Service;
@Service("A05ServiceImpl")
public class A05ServiceImpl implements IA05Service {
	@Resource(name = "ZtzbDaoImpl")
    private IZtzbDao ztzbDao;

	public IZtzbDao getZtzbDao() {
		return ztzbDao;
	}

	public void setZtzbDao(IZtzbDao ztzbDao) {
		this.ztzbDao = ztzbDao;
	}

	@Override
	public void save(A05EntityBean a05) throws Exception {
		ztzbDao.save(a05);
	}

}
