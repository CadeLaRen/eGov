package org.egov.wtms.web.controller.masters;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.validation.Valid;

import org.egov.wtms.masters.entity.DonationDetails;
import org.egov.wtms.masters.service.ConnectionCategoryService;
import org.egov.wtms.masters.service.DonationDetailsService;
import org.egov.wtms.masters.service.DonationHeaderService;
import org.egov.wtms.masters.service.PipeSizeService;
import org.egov.wtms.masters.service.PropertyTypeService;
import org.egov.wtms.masters.service.UsageTypeService;
import org.egov.wtms.utils.constants.WaterTaxConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/masters")
public class DonationMasterController {

    @Autowired
    private DonationDetailsService donationDetailsService;

    @Autowired
    private final PropertyTypeService propertyTypeService;

    @Autowired
    private final DonationHeaderService donationHeaderService;

    @Autowired
    private final ConnectionCategoryService connectionCategoryService;

    @Autowired
    private final UsageTypeService usageTypeService;

    @Autowired
    private final PipeSizeService pipeSizeService;

    public static final String CONTENTTYPE_JSON = "application/json";

    @Autowired
    public DonationMasterController(final PropertyTypeService propertyTypeService,
            final ConnectionCategoryService connectionCategoryService, final UsageTypeService usageTypeService,
            final PipeSizeService pipeSizeService, final DonationHeaderService donationHeaderService) {
        this.propertyTypeService = propertyTypeService;
        this.connectionCategoryService = connectionCategoryService;
        this.usageTypeService = usageTypeService;
        this.pipeSizeService = pipeSizeService;
        this.donationHeaderService = donationHeaderService;
    }

    @RequestMapping(value = "/donationMaster", method = GET)
    public String viewForm(final Model model) {
        final DonationDetails donationDetails = new DonationDetails();
        model.addAttribute("donationDetails", donationDetails);
        model.addAttribute("typeOfConnection", WaterTaxConstants.DONATIONMASTER);
        model.addAttribute("categoryType", connectionCategoryService.getAllActiveConnectionCategory());
        model.addAttribute("propertyType", propertyTypeService.getAllActivePropertyTypes());
        model.addAttribute("usageType", usageTypeService.getActiveUsageTypes());
        model.addAttribute("maxPipeSize", pipeSizeService.getAllActivePipeSize());
        model.addAttribute("minPipeSize", pipeSizeService.getAllActivePipeSize());
        return "donation-master";
    }

    @RequestMapping(value = "/donationMaster", method = RequestMethod.POST)
    public String addDonationMasterDetails(@Valid @ModelAttribute final DonationDetails donationDetails,
            final RedirectAttributes redirectAttrs, final Model model, final BindingResult resultBinder) {
        if (resultBinder.hasErrors())
            return "donation-master";
        donationDetails.getDonationHeader().setActive(true);
        donationHeaderService.createDonationHeader(donationDetails.getDonationHeader());
        donationDetailsService.createDonationDetails(donationDetails);
        redirectAttrs.addFlashAttribute("donationDetails", donationDetails);
        model.addAttribute("message", "Donation Master Data created successfully");
        return "donation-master-success";
    }
}