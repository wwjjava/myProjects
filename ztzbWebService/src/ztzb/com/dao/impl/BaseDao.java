package ztzb.com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("BaseDao")
public class BaseDao extends HibernateDaoSupport{
	private Logger log = Logger.getLogger(BaseDao.class);
	
	@Resource(name="sessionFactory")
	public void setSuperSessionFactory(SessionFactory factory){
		super.setSessionFactory(factory);
	}
	
	public List searchPaginated(String hql){
		return this.searchPaginated(hql, null);
	}
	
	public List searchPaginated(String hql,Object params){
		return this.searchPaginated(hql, new Object[]{params});
	}
	
	
	public List searchPaginated(String hql,int offset, int pagesize){
		return this.searchPaginated(hql, null, offset, pagesize);
	}
	
	public List searchPaginated(String hql, Object params,int offset, int pagesize){
		return this.searchPaginated(hql, new Object []{params}, offset, pagesize);
	}
	
	public List searchPaginated(String hql, Object params[],int offset, int pagesize){
		
		//��ѯ�ܼ�¼��
		String countHql = this.getCountQuery(hql);
		
		Query query = this.getSession().createQuery(countHql);
		
		if(params != null && params.length > 0){
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		
		int _total = ((Long)query.uniqueResult()).intValue();
		
		
		//��ѯ��ǰҳ�����
		
		query = this.getSession().createQuery(hql);
		
		if(params != null && params.length > 0){
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		
		query.setFirstResult(offset);
		query.setMaxResults(pagesize);
		
		List datas = query.list();
		
		return datas;
	}
	
	
	/**
	 * ƴװ��ѯTotal���
	 * ���ǰ̨����select o from Organization o where o.parent is null
	 * ��ת��Ϊselect count (*)��from Organization o where o.parent is null
	 * @param hql
	 * @return
	 */
	private String getCountQuery(String hql){
		String lowerHql = hql.toLowerCase();
		int index = lowerHql.indexOf("from");
		
		if(index != -1){
			return "select count (*)"+hql.substring(index);
		}else{
			throw new RuntimeException("��Ч��sql��䣺"+hql);
		}
		
	}
	
	/**
	 * ������ɾ��ʱ����ǰ̨������id����תΪList
	 * @param s
	 * @return
	 */
	public List stringToList(String ids){
		
		//ǰ̨���ݵ�����ɾ���Id
		String[] id = ids.trim().split(",");
		
		//��orgIdתΪint���뵽һ��list��
		List _ids = new ArrayList();
		
		for (int i = 0; i < id.length; i++) {
			System.out.println(id[i]);
			_ids.add(Integer.parseInt(id[i]));
		}
		
		return _ids;
	}
	
}
