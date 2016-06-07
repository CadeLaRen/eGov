package org.egov.assets.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.egov.assets.model.Asset;
import org.egov.assets.model.Asset.ModeOfAcquisition;
import org.egov.assets.service.AssetCategoryService;
import org.egov.assets.service.AssetService;
import org.egov.commons.dao.EgwStatusHibernateDAO;
import org.egov.egassets.web.adaptor.AssetJsonAdaptor;
import org.egov.eis.service.PersonalInformationService;
import org.egov.infra.admin.master.service.BoundaryService;
import org.egov.infra.admin.master.service.DepartmentService;
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
@RequestMapping("/asset")
public class AssetController {
	private final static String ASSET_NEW = "asset-new";
	private final static String ASSET_RESULT = "asset-result";
	private final static String ASSET_EDIT = "asset-edit";
	private final static String ASSET_VIEW = "asset-view";
	private final static String ASSET_SEARCH = "asset-search"; 
	@Autowired
	private AssetService assetService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AssetCategoryService assetCategoryService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private BoundaryService boundaryService;

	@Autowired
	private EgwStatusHibernateDAO egwStatusService;
	@Autowired
	private PersonalInformationService personalInformationService;

	private void prepareNewForm(Model model) {
		model.addAttribute("assetCategorys", assetCategoryService.findAll());
		model.addAttribute("departments", departmentService.getAllDepartments());
		//model.addAttribute("boundarys", boundaryService.findAll());
	 
		model.addAttribute("modeOfAcquisitions",ModeOfAcquisition.values());
		model.addAttribute("egwStatuss", egwStatusService.getStatusByModule("asset"));
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newForm(final Model model) {
		prepareNewForm(model);
		model.addAttribute("asset", new Asset());
		return ASSET_NEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute final Asset asset,
			final BindingResult errors, final Model model,
			final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return ASSET_NEW;
		}
		assetService.create(asset);
		redirectAttrs.addFlashAttribute("message",
				messageSource.getMessage("msg.assetcategory.success", null, null));
		return "redirect:/asset/result/" + asset.getId();
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") final Long id, Model model) {
		Asset asset = assetService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("asset", asset);
		return ASSET_EDIT;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute final Asset asset,
			final BindingResult errors, final Model model,
			final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return ASSET_EDIT;
		}
		assetService.update(asset);
		redirectAttrs.addFlashAttribute("message",
				messageSource.getMessage("msg.assetcategory.success", null, null));
		return "redirect:/asset/result/" + asset.getId();
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") final Long id, Model model) {
		Asset asset = assetService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("asset", asset);
		return ASSET_VIEW;
	}

	@RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
	public String result(@PathVariable("id") final Long id, Model model) {
		Asset asset = assetService.findOne(id);
		model.addAttribute("asset", asset);
		return ASSET_RESULT;
	}

	@RequestMapping(value = "/search/{mode}", method = RequestMethod.GET)
	public String search(@PathVariable("mode") final String mode, Model model) {
		Asset asset = new Asset();
		prepareNewForm(model);
		model.addAttribute("asset", asset);
		return ASSET_SEARCH;

	}

	@RequestMapping(value = "/ajaxsearch/{mode}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String ajaxsearch(
			@PathVariable("mode") final String mode, Model model,
			@ModelAttribute final Asset asset) {
		List<Asset> searchResultList = assetService.search(asset);
		String result = new StringBuilder("{ \"data\":")
				.append(toSearchResultJson(searchResultList)).append("}")
				.toString();
		return result;
	}

	public Object toSearchResultJson(final Object object) {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.registerTypeAdapter(Asset.class,
				new AssetJsonAdaptor()).create();
		final String json = gson.toJson(object);
		return json;
	}
}