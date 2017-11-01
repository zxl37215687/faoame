package com.lsr.frame.base.service;

import com.lsr.frame.base.entityconver.VOInterface;


/**
 * Service接口
 * @author kuaihuolin
 *
 */
public interface Service {

	void insert(VOInterface vo);

	void update(VOInterface vo);

	void delBySelect(VOInterface newNullBaseVO, String[] ids);

	VOInterface findByPk(VOInterface newNullBaseVO, String fid);
}
