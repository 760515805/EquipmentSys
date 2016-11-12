package com.chen.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chen.model.EquipmentType;
import com.chen.service.IEquipmentService;
import com.chen.service.IEquipmentTypeService;
import com.chen.util.PageUtil;
import com.chen.util.ResponseUtil;
import com.chen.util.StringUtil;


/**
 * 
 * @author chenhongjie
 *@Tool
 * 2016年10月28日
 */
@Controller
@RequestMapping("/equipmentType")
public class EquipmentTypeController {

	@Autowired
	private IEquipmentTypeService equipmentTypeService;
	
	@Autowired
	private IEquipmentService equipmentService;
	
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value="page",required=false)String page,EquipmentType s_equipmentType,HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
			session.setAttribute("s_equipmentType", s_equipmentType);
		}else{
			s_equipmentType=(EquipmentType) session.getAttribute("s_equipmentType");
		}
		//PageBean pageBean=new PageBean(Integer.parseInt(page),3);
		List<EquipmentType> equipmentTypeList=equipmentTypeService.findAll();
		int total=equipmentTypeService.count();
		
		return mav;
	}
	
	@RequestMapping("/preSave")
	public ModelAndView preSave(@RequestParam(value="id",required=false)String id){
		ModelAndView mav=new ModelAndView();
		mav.addObject("mainPage", "equipmentType/save.jsp");
		mav.addObject("modeName", "设备类型管理");
		mav.setViewName("main");
		if(StringUtil.isNotEmpty(id)){
			mav.addObject("actionName", "设备类型修改");
			EquipmentType equipmentType=equipmentTypeService.loadById(Integer.parseInt(id));
			mav.addObject("equipmentType", equipmentType);
		}else{
			mav.addObject("actionName", "设备类型添加");			
		}
		return mav;
	}
	
	@RequestMapping("/save")
	public String save(EquipmentType equipmentType){
		if(equipmentType.getId()==null){
			equipmentTypeService.add(equipmentType);			
		}else{
			equipmentTypeService.update(equipmentType);
		}
		return "redirect:/equipmentType/list.do";
	}
	
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		JSONObject result=new JSONObject();
//		if(equipmentService.existEquipmentByTypeId(Integer.parseInt(id))){
//			result.put("errorInfo", "该设备类型下存在设备，不能删除！");
//		}else{
//			equipmentTypeService.delete(Integer.parseInt(id));
//			result.put("success", true);						
//		}
		ResponseUtil.write(result, response);
	}
}
