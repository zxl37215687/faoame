package com.lsr.frame.base.entityconver;

import java.util.List;

public interface EntityConver {
	
	public <T> T copyPoToVo(BasePO po,Class<T> voClass);

	public BasePO copyVoToPo(VOInterface vo);
	
	public List<VOInterface> copyPoListToVO(List<BasePO> poList, Class<VOInterface> voClass);
	
	public List<BasePO> copyVoListToPO(List<VOInterface> voList);
	
}
