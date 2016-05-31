package org.egov.assets.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.egov.assets.model.AssetCategory;
import org.egov.assets.service.AssetCategoryService;
import org.egov.assets.util.AssetConstants;
import org.egov.assets.web.adaptor.AssetCategoryJsonAdaptor;
import org.egov.commons.dao.ChartOfAccountsHibernateDAO;
import org.egov.commons.service.UOMService;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/assetcategory")
public class AssetCategoryController {
	private final static String ASSETCATEGORY_NEW = "assetcategory-new";
	private final static String ASSETCATEGORY_RESULT = "assetcategory-result";
	private final static String ASSETCATEGORY_EDIT = "assetcategory-edit";
	private final static String ASSETCATEGORY_VIEW = "assetcategory-view";
	private final static String ASSETCATEGORY_SEARCH = "assetcategory-search";
	@Autowired
	private AssetCategoryService assetCategoryService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AppConfigValueService appConfigValueService;	 
	 
	@Autowired
	private ChartOfAccountsHibernateDAO chartOfAccountsService;
	@Autowired
    private UOMService uomService;
	
	
	/*@Autowiredc
	private UOMDao uOMService;*/

	private void prepareNewForm(Model model) {
		model.addAttribute("assetTypes",Arrays.asList(AssetCategory.AssetType.values()));
		model.addAttribute("assetCategorys", assetCategoryService.findAll());
		model.addAttribute("depreciationMethods",Arrays.asList(AssetCategory.DepreciationMethod.values()));
		AppConfigValues accountCodePurposeId = appConfigValueService.getConfigValuesByModuleAndKey(AssetConstants.MODULE_NAME, 
				AssetConstants.ASSETACCCODEPURPOSEID).get(0);  
		model.addAttribute("accountCodes",
				chartOfAccountsService.getAccountCodeByPurpose(Integer.valueOf(accountCodePurposeId.getValue())));
		
		AppConfigValues accountDepPurposeId = appConfigValueService.getConfigValuesByModuleAndKey(AssetConstants.MODULE_NAME, 
				AssetConstants.ACCDEPPURPOSEID).get(0);
		model.addAttribute("accountDeps",
				chartOfAccountsService.getAccountCodeByPurpose(Integer.valueOf(accountDepPurposeId.getValue())));
		
		AppConfigValues accountDepExpPurposeId = appConfigValueService.getConfigValuesByModuleAndKey(AssetConstants.MODULE_NAME, 
				AssetConstants.DEPEXPACCPURPOSEID).get(0);
		model.addAttribute("accountDepExps",
				chartOfAccountsService.getAccountCodeByPurpose(Integer.valueOf(accountDepExpPurposeId.getValue())));
		
		AppConfigValues accountRevResPurposeId = appConfigValueService.getConfigValuesByModuleAndKey(AssetConstants.MODULE_NAME, 
				AssetConstants.REVRESACCPURPOSEID).get(0);
		model.addAttribute("accountRevRess",
				chartOfAccountsService.getAccountCodeByPurpose(Integer.valueOf(accountRevResPurposeId.getValue())));
		model.addAttribute("uOMs",uomService.findAllOrderByCategory());
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newForm(final Model model) {
		prepareNewForm(model);
		model.addAttribute("assetCategory", new AssetCategory());
		return ASSETCATEGORY_NEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute final AssetCategory assetCategory,
			final BindingResult errors, final Model model,
			final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return ASSETCATEGORY_NEW;
		}
		assetCategoryService.create(assetCategory);
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage(
				"msg.assetCategory.success", null, null));
		return "redirect:/assetcategory/result/" + assetCategory.getId();
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") final Long id, Model model) {
		AssetCategory assetCategory = assetCategoryService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("assetCategory", assetCategory);
		return ASSETCATEGORY_EDIT;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			@Valid @ModelAttribute final AssetCategory assetCategory,
			final BindingResult errors, final Model model,
			final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return ASSETCATEGORY_EDIT;
		}
		assetCategoryService.update(assetCategory);
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage(
				"msg.assetCategory.success", null, null));
		return "redirect:/assetcategory/result/" + assetCategory.getId();
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") final Long id, Model model) {
		AssetCategory assetCategory = assetCategoryService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("assetCategory", assetCategory);
		return ASSETCATEGORY_VIEW;
	}

	@RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
	public String result(@PathVariable("id") final Long id, Model model) {
		AssetCategory assetCategory = assetCategoryService.findOne(id);
		model.addAttribute("assetCategory", assetCategory);
		return ASSETCATEGORY_RESULT;
	}

	@RequestMapping(value = "/search/{mode}", method = RequestMethod.GET)
	public String search(@PathVariable("mode") final String mode, Model model) {
		AssetCategory assetCategory = new AssetCategory();
		prepareNewForm(model);
		model.addAttribute("assetCategory", assetCategory);
		return ASSETCATEGORY_SEARCH;

	}

	@RequestMapping(value = "/ajaxsearch/{mode}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String ajaxsearch(
			@PathVariable("mode") final String mode, Model model,
			@ModelAttribute final AssetCategory assetCategory) {
		List<AssetCategory> searchResultList = assetCategoryService
				.search(assetCategory);
		String result = new StringBuilder("{ \"data\":")
				.append(toSearchResultJson(searchResultList)).append("}")
				.toString();
		return result;
	}

	public Object toSearchResultJson(final Object object) {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.registerTypeAdapter(AssetCategory.class,
				new AssetCategoryJsonAdaptor()).create();
		final String json = gson.toJson(object);
		return json;
	}
}