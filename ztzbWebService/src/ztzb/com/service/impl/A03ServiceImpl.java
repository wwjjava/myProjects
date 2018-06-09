package ztzb.com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ztzb.com.dao.IZtzbDao;
import ztzb.com.entity.A03EntityBean;
import ztzb.com.service.IA03Service;
@Service("A03ServiceImpl")
public class A03ServiceImpl implements IA03Service {
	@Resource(name = "ZtzbDaoImpl")
    private IZtzbDao ztzbDao;

	public IZtzbDao getZtzbDao() {
		return ztzbDao;
	}

	public void setZtzbDao(IZtzbDao ztzbDao) {
		this.ztzbDao = ztzbDao;
	}

	@Override
	public void save(A03EntityBean a03) throws Exception {
		ztzbDao.save(a03);
	}

}
