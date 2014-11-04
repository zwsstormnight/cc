package com.copex.example.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.copex.common.FileInfo.FileType;
import com.copex.common.service.FileService;
import com.copex.core.Message;
import com.copex.core.controller.BaseController;
import com.copex.example.entity.Computer;
import com.copex.example.service.CompanyService;
import com.copex.example.service.ComputerService;

@Controller("computerController")
@RequestMapping("/computer")
public class ComputerController extends BaseController {
	/** Error View */
	protected static final String ERROR_VIEW = "/common/error";

	/** Error Message */
	protected static final Message ERROR_MESSAGE = Message
			.error("example.message.error");

	/** Success Message */
	protected static final Message SUCCESS_MESSAGE = Message
			.success("example.message.success");

	private static final int PAGE_SIZE = 10;

	@Resource(name = "computerServiceImpl")
	private ComputerService computerService;

	@Resource(name = "companyServiceImpl")
	private CompanyService companyService;
	
	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	/**
	 * List
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "f", required = false) String nameFilter,
			@PageableDefault(page = 0, size = PAGE_SIZE) Pageable pageable,
			ModelMap model) {
		Page<Computer> page = computerService.findByNameLike(
				nameFilter == null ? "%%" : "%" + nameFilter + "%", pageable);
		model.addAttribute("page", page);
		model.addAttribute("pageable", pageable);
		model.addAttribute("filter", nameFilter);

		return "/example/computer/list";
	}

	/**
	 * add
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(ModelMap model) {
		Computer computer = new Computer();
		model.addAttribute("computer", computer);
		return "/example/computer/new";
	}

	/**
	 * save
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String save(@Valid Computer computer, BindingResult bindingResult,
			Long companyId, ModelMap model,
			@RequestParam("file") MultipartFile file) {
		if (bindingResult.hasErrors()) {
			return "/example/computer/new";
		}

		if(companyId != null) {
			computer.setCompany(companyService.find(companyId));
		}
		
		if(!file.isEmpty()) {
			String url = fileService.upload(FileType.image, file);
			computer.setImage(url);
		}

		computerService.save(computer);
		
		return "redirect:/computer";
	}

	/**
	 * eidt
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Long id, ModelMap model) {
		Computer computer = computerService.find(id);

		if (computer == null) {
			return "/common/resource_not_found";
		}
		model.addAttribute("computer", computer);
		return "/example/computer/edit";
	}

	/**
	 * update
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String update(@Valid Computer computer, BindingResult bindingResult,
			@PathVariable Long id, Long companyId, ModelMap model,
			@RequestParam("file") MultipartFile file) {
		if (bindingResult.hasErrors()) {
			return "/example/computer/edit";
		}
		computer.setId(id);
		if (companyId != null) {
			computer.setCompany(companyService.find(companyId));
		}
		
		if(!file.isEmpty()) {
			String url = fileService.upload(FileType.image, file);
			computer.setImage(url);
		}

		computerService.update(computer);

		return "redirect:/computer";
	}

	/**
	 * delete
	 */
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	String delete(@PathVariable Long id) {
		computerService.delete(id);
		return "redirect:/computer";
	}
}
