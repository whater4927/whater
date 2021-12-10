package com.whater.common.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whater.common.domain.AjaxResult;
import com.whater.common.service.DictService;
import com.whater.model.dto.DictDTO;
import com.whater.model.dto.DictDetailDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("dict")
@Slf4j
public class DictController {
	@Autowired	
	private DictService dictService ;
	
	
	@PostMapping("addDict")
	public AjaxResult addDict(@RequestBody DictDTO dictDTO) {
		return dictService.addDict(dictDTO);
	}
	
	@PostMapping("addDetail")
	public AjaxResult addDetail(@RequestBody DictDetailDTO dictDetailDTO) {
		return dictService.addDictDetail(dictDetailDTO);
	}
	
	@GetMapping("list")
	public AjaxResult list(@RequestParam(name = "dictName") String dictName) {
		return AjaxResult.success(dictService.listDetail(dictName));
	}
	
}
