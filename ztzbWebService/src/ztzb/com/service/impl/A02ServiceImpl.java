package ztzb.com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ztzb.com.dao.IZtzbDao;
import ztzb.com.entity.A02EntityBean;
import ztzb.com.service.IA02Service;
@Service("A02ServiceImpl")
public class A02ServiceImpl implements IA02Service {
	@Resource(name = "ZtzbDaoImpl")
    private IZtzbDao ztzbDao;

	public IZtzbDao getZtzbDao() {
		return ztzbDao;
	}

	public void setZtzbDao(IZtzbDao ztzbDao) {
		this.ztzbDao = ztzbDao;
	}

	@Override
	public void save(A02EntityBean a02) throws Exception {
		// TODO Auto-generated method stub
		ztzbDao.save(a02);
	}

}
