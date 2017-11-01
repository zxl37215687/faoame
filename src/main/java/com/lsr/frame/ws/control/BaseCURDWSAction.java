package com.lsr.frame.ws.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Transaction;

import com.lsr.frame.base.condition.Condition;
import com.lsr.frame.base.entityconver.VOInterface;
import com.lsr.frame.base.exception.DefaultException;
import com.lsr.frame.base.service.Service;
import com.lsr.frame.base.utils.StringUtils;
import com.lsr.frame.ws.common.WriteLog;
import com.lsr.frame.ws.conver.Parameters;
import com.lsr.frame.ws.utils.ClassUtils;
import com.lsr.frame.ws.utils.CxfUtil;
import com.lsr.frame.ws.utils.KeyGenerator;

public abstract class BaseCURDWSAction extends AbstractWSAction{
	private final static KeyGenerator.StringIDGenerator IDGENERATOR = new KeyGenerator.StringIDGenerator();


	/**
	 * 获得VO的Class
	 * 
	 * @return
	 */
	protected abstract Class getBaseVOType();

	/**
	 * 获得BeanId
	 * 
	 * @return
	 */
	protected abstract String getBaseBeanId();

	/**
	 * 
	 * @return
	 */
	protected Service getBaseService() {
		return getService(getBaseBeanId());
	}

	/**
	 * 创建一个属性都为空的VO
	 * 
	 * @return
	 */
	protected VOInterface newNullBaseVO() {
		return (VOInterface) ClassUtils.newInstance(getBaseVOType());
	}

	protected void actionInsert(VOInterface vo, Service service) throws Exception {
		beforeInsert(vo, service);
		service.insert(vo);
	}

	protected void actionUpdate(VOInterface vo, Service service) throws Exception {
		beforeUpdate(vo, service);
		service.update(vo);
	}

	protected void actionDelete(String[] ids, Service service) throws Exception {
		beforeDelete(ids, service);
		service.delBySelect(newNullBaseVO(), ids);
	}

	/**
	 * 保存操作(新增,更新,删除)
	 * 
	 * @param insertVOStr
	 *            新增的VO字符串数组
	 * @param updateVOStr
	 *            更新的VO字符串数组
	 * @param id
	 *            删除的环节ID
	 * @return 0: 全部成功
	 */
	public Map save(Parameters parameter) throws Exception {

		List insertVOColl = (List) parameter.getCollection("insertVOStr", getBaseVOType(), ArrayList.class);
		List updateVOColl = (List) parameter.getCollection("updateVOStr", getBaseVOType(), ArrayList.class);
		String[] deleteFids = parameter.getIdArray();

		Service service = getBaseService();
		Transaction tx = null;
		//TransHandler trans = TransHandlerFactory.getInstance().createTransHandler(service);
		//trans.addTransaction(service);
		try {
			//tx = trans.begin();
			// 删除
			if(deleteFids != null && deleteFids.length > 0){
				actionDelete(deleteFids, service);
			}
			// 更新
			for (int i = 0; i < updateVOColl.size(); i++) {
				actionUpdate((VOInterface) updateVOColl.get(i), service);
			}
			// 新增
			for (int i = 0; i < insertVOColl.size(); i++) {
				actionInsert((VOInterface) insertVOColl.get(i), service);
			}
			//trans.commit(tx);
		} catch (Exception ex) {
			//trans.rollback(tx);
			throw ex;
		} finally {
			//trans.end();
		}
		return getResult().addOperateSucced();
	}

	/**
	 * 新增
	 * 
	 * @param insertVOStr
	 * @return
	 * @throws DefaultException
	 */
	public Map insert(Parameters parameter) throws Exception {
		return save(parameter);
	}

	/**
	 * 更新
	 * 
	 * @param updateVOStr
	 * @return
	 * @throws DefaultException
	 */
	public Map update(Parameters parameter) throws Exception {
		return save(parameter);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws DefaultException
	 */
	public Map delete(Parameters parameter) throws Exception {
		return save(parameter);
	}

	/**
	 * 
	 * @param parameter
	 * @return
	 * @throws DefaultException
	 */
	public Map list(Parameters parameter) throws Exception {
		List listData = list(parameter, null);
		return getResult().addListData(listData, parameter.getShowFields());
	}
	
	/**
	 * 
	 * @param parameter
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	protected List list(Parameters parameter, Condition condition) throws Exception {
		if(condition == null){
			condition = decorateCondition(condition, parameter);
		}
		Service service = getBaseService();
//		if(! (service instanceof ExtService)){
//			throw new NoSuchMethodException("请在" + BaseService.class.getName() + "加上getByCondition()方法");
//		}
		//TODO 是否使用对分页
		List listData =null;// ((ExtService) service).getByCondition(newNullBaseVO(), condition);
		return listData;
	}

	/**
	 * 
	 * @param id
	 * @param dbField
	 * @return
	 * @throws DefaultException
	 */
	public Map get(Parameters parameter) throws Exception {
		String fid = parameter.getId();
		if(StringUtils.isBlank(fid)){
			throw new IllegalArgumentException("id is null");
		}
		String showFields = parameter.getShowFields();
		VOInterface vo = this.getBaseService().findByPk(newNullBaseVO(), fid);
		setDetailProperty(vo);
		return getResult().addViewData(vo, showFields);
	}

	/**
	 * 新增前的VO处理,供子类覆盖
	 * 
	 * @param vo
	 * @param service
	 * @throws WebServiceOperateException
	 */
	protected void beforeInsert(VOInterface vo, Service service) throws Exception {

	}

	/**
	 * 更新前的VO处理,供子类覆盖
	 * 
	 * @param vo
	 * @param service
	 * @throws WebServiceOperateException
	 */
	protected void beforeUpdate(VOInterface vo, Service service) throws Exception {

	}

	/**
	 * 删除前的VO处理,供子类覆盖
	 * 
	 * @param ids
	 * @param service
	 * @throws WebServiceOperateException
	 */
	protected void beforeDelete(String[] ids, Service service) throws Exception {

	}

	/**
	 * 子类可以覆盖该方法设定从表数据
	 * 
	 * @param vo
	 */
	protected void setDetailProperty(VOInterface vo) throws Exception {

	}

	/**
	 * 组装Condition
	 * 
	 * @param condition
	 * @param parameter
	 */
	protected Condition decorateCondition(Condition condition, Parameters parameter){
		if(condition == null){
			condition = new Condition();
		}
		//先组装从客户端传过来的默认搜索条件
		String defaultCondition = parameter.getString("defaultCondition");
		if(StringUtils.isNotBlank(defaultCondition)){
			condition.constructCond(defaultCondition, true);
		}
		return condition;
	}
	
	/**
	 * 创建一个临时的VO Id
	 * @return
	 */
	protected String createVOTemplateID(){
		return "temp_" + IDGENERATOR.generateID().substring(5);
	}
	
	/**
	 * 写日志
	 * @param optAct
	 * @param optNode
	 */
	protected void log(String optAct, String optNode){
		WriteLog.writeRecord(getCurrentUser(), CxfUtil.getHttpServletRequest().getRemoteAddr(), optAct, optNode, getCurrentUser().getChrPosotion());
	}
}
