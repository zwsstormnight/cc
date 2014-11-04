package com.copex.example.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.copex.core.template.directive.BaseDirective;
import com.copex.example.entity.Company;
import com.copex.example.service.CompanyService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("companyListDriective")
public class CompanyListDriective extends BaseDirective {

	private static final String VARIABLE_NAME = "companies";

	@Resource(name = "companyServiceImpl")
	private CompanyService companyService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		List<Company> companies = companyService.findList(getSort(params));
		setLocalVariable(VARIABLE_NAME, companies, env, body);

	}

}
