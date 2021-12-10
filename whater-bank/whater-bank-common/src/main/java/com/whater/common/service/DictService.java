package com.whater.common.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whater.common.domain.AjaxResult;
import com.whater.dao.DictDetailRepository;
import com.whater.dao.DictRepository;
import com.whater.entity.Dict;
import com.whater.entity.DictDetail;
import com.whater.model.dto.DictDTO;
import com.whater.model.dto.DictDetailDTO;
@Service
public class DictService {
	@Autowired
	private DictRepository dictRepository;
	@Autowired
	private DictDetailRepository detailRepository ;
	@Transactional
	public AjaxResult addDict(DictDTO dictDTO) {
		Dict dict = dictRepository.findByName(dictDTO.getName());
		if(dict != null) {
			return AjaxResult.error(String.format("%s is exist!", dictDTO.getName()));
		}
		dict = new Dict();
		dict.setName(dictDTO.getName());
		dict.setRemark(dictDTO.getRemark());
		dictRepository.save(dict);
		return AjaxResult.success();
	}
	@Transactional
	public AjaxResult addDictDetail(DictDetailDTO dictDetailDTO) {
		Dict dict = dictRepository.findByName(dictDetailDTO.getName());
		if(dict == null) {
			return AjaxResult.error(String.format("%s is not exist!", dictDetailDTO.getName()));
		}
		DictDetail detail  = new DictDetail() ;
		detail.setKey(dictDetailDTO.getKey());
		detail.setValue(dictDetailDTO.getValue());
		detail.setPid(dict.getId());
		detail.setName(dictDetailDTO.getName());
		detailRepository.save(detail);
		return AjaxResult.success();
	}
	
	public List<DictDetail> listDetail(String dictName){
		return detailRepository.findByName(dictName);
	}
	
}
