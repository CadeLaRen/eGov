/* eGov suite of products aim to improve the internal efficiency,transparency,
   accountability and the service delivery of the government  organizations.

    Copyright (C) <2015>  eGovernments Foundation

    The updated version of eGov suite of products as by eGovernments Foundation
    is available at http://www.egovernments.org

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or
    http://www.gnu.org/licenses/gpl.html .

    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:

        1) All versions of this program, verbatim or modified must carry this
           Legal Notice.

        2) Any misrepresentation of the origin of the material is prohibited. It
           is required that all modified versions of this material be marked in
           reasonable ways as different from the original version.

        3) This license does not grant any rights to any user of the program
           with regards to rights under trademark law for use of the trade names
           or trademarks of eGovernments Foundation.

  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.adtax.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.adtax.entity.AdvertisementPermitDetail;
import org.egov.adtax.exception.HoardingValidationError;
import org.egov.adtax.repository.AdvertisementPermitDetailRepository;
import org.egov.adtax.search.contract.HoardingSearch;
import org.egov.adtax.utils.AdTaxNumberGenerator;
import org.egov.adtax.utils.constants.AdvertisementTaxConstants;
import org.egov.adtax.workflow.ApplicationWorkflowCustomDefaultImpl;
import org.egov.collection.integration.services.CollectionIntegrationService;
import org.egov.commons.EgwStatus;
import org.egov.commons.dao.EgwStatusHibernateDAO;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.utils.EgovThreadLocals;
import org.egov.infstr.utils.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdvertisementPermitDetailService {
    @Autowired
    private AdvertisementPermitDetailRepository advertisementPermitDetailRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Autowired
    protected CollectionIntegrationService collectionIntegrationService;

    @Autowired
    private AdvertisementDemandService advertisementDemandService;

    @Autowired
    private ApplicationWorkflowCustomDefaultImpl applicationWorkflowCustomDefaultImpl;

    @Autowired
    private EgwStatusHibernateDAO egwStatusHibernateDAO;

    @Autowired
    private AdTaxNumberGenerator adTaxNumberGenerator;

    @Autowired
    private CityService cityService;

    @Autowired
    private AssignmentService assignmentService;

    @Transactional
    public AdvertisementPermitDetail createAdvertisementPermitDetail(final AdvertisementPermitDetail advertisementPermitDetail,
            final Long approvalPosition, final String approvalComent, final String additionalRule,
            final String workFlowAction) {
        if (advertisementPermitDetail != null && advertisementPermitDetail.getId() == null)
            advertisementPermitDetail.getAdvertisement()
                    .setDemandId(advertisementDemandService.createDemand(advertisementPermitDetail));
        roundOfAllTaxAmount(advertisementPermitDetail);
        if (advertisementPermitDetail.getApplicationNumber() == null)
            advertisementPermitDetail.setApplicationNumber(adTaxNumberGenerator.generateApplicationNumber());
        if (advertisementPermitDetail.getAdvertisement().getAdvertisementNumber() == null)
            advertisementPermitDetail.getAdvertisement()
                    .setAdvertisementNumber(adTaxNumberGenerator.generateAdvertisementNumber());
        if (advertisementPermitDetail.getAdvertisement().getLegacy() && advertisementPermitDetail.getPermissionNumber() == null)
            advertisementPermitDetail.setPermissionNumber(adTaxNumberGenerator.generatePermitNumber());
        advertisementPermitDetailRepository.save(advertisementPermitDetail);

        if (approvalPosition != null && approvalPosition > 0 && additionalRule != null
                && StringUtils.isNotEmpty(workFlowAction))
            applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(advertisementPermitDetail,
                    approvalPosition, approvalComent, additionalRule, workFlowAction);
        return advertisementPermitDetail;
    }

    @Transactional
    public AdvertisementPermitDetail updateAdvertisementPermitDetail(final AdvertisementPermitDetail advertisementPermitDetail,
            final Long approvalPosition, final String approvalComent, final String additionalRule,
            final String workFlowAction) throws HoardingValidationError {
        final boolean anyDemandPendingForCollection = advertisementDemandService
                .anyDemandPendingForCollection(advertisementPermitDetail);

        /*
         * if (!actualHoarding.getAgency().equals(advertisementPermitDetail.getAgency()) && anyDemandPendingForCollection) throw
         * new HoardingValidationError("agency", "ADTAX.001");
         */
        // If demand already collected for the current year, fee updated from
        // UI, do not update demand details. Update only fee details of hoarding.
        // We should not allow user to update demand if any collection happened in
        // the current year.

        /*
         * if (advertisementDemandService.collectionDoneForThisYear(actualHoarding) && anyDemandPendingForCollection &&
         * (!actualHoarding.getCurrentTaxAmount().equals(hoarding.getCurrentTaxAmount()) || checkEncroachmentFeeChanged(hoarding,
         * actualHoarding) || checkPendingTaxChanged(hoarding, actualHoarding))) throw new HoardingValidationError("taxAmount",
         * "ADTAX.002");
         */
        /*
         * if (!actualHoarding.getStatus().equals(advertisementPermitDetail.getStatus()) &&
         * advertisementPermitDetail.getStatus().equals(AdvertisementStatus.CANCELLED) && anyDemandPendingForCollection) throw new
         * HoardingValidationError("status", "ADTAX.003");
         */

        // If demand pending for collection, then only update demand details.
        // If demand fully paid and user changed tax details, then no need to
        // update demand details.
        if (anyDemandPendingForCollection)
            advertisementDemandService.updateDemand(advertisementPermitDetail,
                    advertisementPermitDetail.getAdvertisement().getDemandId());
        roundOfAllTaxAmount(advertisementPermitDetail);
        advertisementPermitDetailRepository.save(advertisementPermitDetail);
        if (approvalPosition != null && additionalRule != null && StringUtils.isNotEmpty(workFlowAction))
            applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(advertisementPermitDetail,
                    approvalPosition, approvalComent, additionalRule, workFlowAction);
        return advertisementPermitDetail;
    }

    private void roundOfAllTaxAmount(final AdvertisementPermitDetail advertisementPermitDetail) {
        if (advertisementPermitDetail.getEncroachmentFee() != null)
            advertisementPermitDetail
                    .setEncroachmentFee(advertisementPermitDetail.getEncroachmentFee().setScale(2, BigDecimal.ROUND_HALF_UP));

        if (advertisementPermitDetail.getTaxAmount() != null)
            advertisementPermitDetail
                    .setTaxAmount(advertisementPermitDetail.getTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP));

        if (advertisementPermitDetail.getAdvertisement().getPendingTax() != null)
            advertisementPermitDetail.getAdvertisement().setPendingTax(
                    advertisementPermitDetail.getAdvertisement().getPendingTax().setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public AdvertisementPermitDetail getAdvertisementPermitDetailsByApplicationNumber(final String applicationNumber) {
        return advertisementPermitDetailRepository.findByApplicationNumber(applicationNumber);
    }

    public AdvertisementPermitDetail findBy(final Long advPermitId) {
        return advertisementPermitDetailRepository.findOne(advPermitId);
    }

    public EgwStatus getStatusByModuleAndCode(final String code) {
        return egwStatusHibernateDAO.getStatusByModuleAndCode(AdvertisementTaxConstants.APPLICATION_MODULE_TYPE, code);
    }

    public String getCityCode() {
        return cityService.getCityByURL(EgovThreadLocals.getDomainName()).getCode();
    }

    public List<HoardingSearch> getAdvertisementSearchResult(final AdvertisementPermitDetail advPermitDetail,
            final String searchType) {

        final List<AdvertisementPermitDetail> advPermitDtl = advertisementPermitDetailRepository
                .searchAdvertisementPermitDetailBySearchParams(advPermitDetail);
        final HashMap<String, HoardingSearch> agencyWiseHoardingList = new HashMap<String, HoardingSearch>();
        final List<HoardingSearch> hoardingSearchResults = new ArrayList<>();

        advPermitDtl.forEach(result -> {
            final HoardingSearch hoardingSearchResult = new HoardingSearch();
            hoardingSearchResult.setAdvertisementNumber(result.getAdvertisement().getAdvertisementNumber());
            hoardingSearchResult.setApplicationNumber(result.getApplicationNumber());
            hoardingSearchResult.setApplicationFromDate(result.getApplicationDate());
            hoardingSearchResult.setAgencyName(result.getAgency().getName());
            hoardingSearchResult.setStatus(result.getAdvertisement().getStatus());
            if (result.getAdvertisement().getDemandId() != null)
                if (searchType != null && searchType.equalsIgnoreCase("agency")) {
                    // PASS DEMAND OF EACH HOARDING AND GROUP BY AGENCY WISE.
                    final Map<String, BigDecimal> demandWiseFeeDetail = advertisementDemandService
                            .checkPedingAmountByDemand(result.getAdvertisement().getDemandId(), result.getAdvertisement()
                                    .getPenaltyCalculationDate());
                    // TODO: DO CODE CHANGE
                    final HoardingSearch hoardingSearchObj = agencyWiseHoardingList.get(result.getAgency().getName());
                    if (hoardingSearchObj == null) {
                        hoardingSearchResult.setPenaltyAmount(demandWiseFeeDetail
                                .get(AdvertisementTaxConstants.PENALTYAMOUNT));
                        hoardingSearchResult.setPendingDemandAmount(demandWiseFeeDetail
                                .get(AdvertisementTaxConstants.PENDINGDEMANDAMOUNT));
                        hoardingSearchResult.setTotalHoardingInAgency(1);
                        hoardingSearchResult.setHordingIdsSearchedByAgency(result.getId().toString());
                        agencyWiseHoardingList.put(result.getAgency().getName(), hoardingSearchResult);
                    } else {
                        final StringBuffer hoardingIds = new StringBuffer();
                        hoardingSearchObj.setPenaltyAmount(hoardingSearchObj.getPenaltyAmount().add(
                                demandWiseFeeDetail.get(AdvertisementTaxConstants.PENALTYAMOUNT)));
                        hoardingSearchObj.setPendingDemandAmount(hoardingSearchObj.getPendingDemandAmount().add(
                                demandWiseFeeDetail.get(AdvertisementTaxConstants.PENDINGDEMANDAMOUNT)));
                        hoardingSearchObj.setTotalHoardingInAgency(hoardingSearchObj.getTotalHoardingInAgency() + 1);

                        hoardingIds.append(hoardingSearchObj.getHordingIdsSearchedByAgency()).append("~")
                                .append(result.getId());
                        hoardingSearchObj.setHordingIdsSearchedByAgency(hoardingIds.toString());
                        agencyWiseHoardingList.put(result.getAgency().getName(), hoardingSearchObj);
                    }
                } else {

                    final Map<String, BigDecimal> demandWiseFeeDetail = advertisementDemandService
                            .checkPedingAmountByDemand(result.getAdvertisement().getDemandId(), result.getAdvertisement()
                                    .getPenaltyCalculationDate());
                    hoardingSearchResult.setPenaltyAmount(demandWiseFeeDetail.get(AdvertisementTaxConstants.PENALTYAMOUNT));
                    hoardingSearchResult.setPendingDemandAmount(demandWiseFeeDetail
                            .get(AdvertisementTaxConstants.PENDINGDEMANDAMOUNT));
                    hoardingSearchResults.add(hoardingSearchResult);
                }
        });
        if (agencyWiseHoardingList.size() > 0)
            agencyWiseHoardingList.forEach((key, value) -> {
                hoardingSearchResults.add(value);
            });
        return hoardingSearchResults;

    }

    public List<HoardingSearch> getAdvertisementSearchResult(final HoardingSearch hoardingSearch) {
        final List<AdvertisementPermitDetail> advPermitDtl = advertisementPermitDetailRepository
                .searchAdvertisementPermitDetailLike(hoardingSearch);
        final List<HoardingSearch> hoardingSearchResults = new ArrayList<>();
        final HashMap<String, HoardingSearch> agencyWiseHoardingList = new HashMap<String, HoardingSearch>();
        advPermitDtl.forEach(result -> {
            final HoardingSearch hoardingSearchResult = new HoardingSearch();
            hoardingSearchResult.setAdvertisementNumber(result.getAdvertisement().getAdvertisementNumber());
            hoardingSearchResult.setApplicationNumber(result.getApplicationNumber());
            hoardingSearchResult.setApplicationFromDate(result.getApplicationDate());
            hoardingSearchResult.setAgencyName(result.getAgency().getName());
            hoardingSearchResult.setStatus(result.getAdvertisement().getStatus());
            hoardingSearchResults.add(hoardingSearchResult);
        });
        return hoardingSearchResults;
    }
    
    public List<HoardingSearch> getAdvertisementApprovedSearchResult(final AdvertisementPermitDetail advPermitDetail) {
        final List<AdvertisementPermitDetail> advPermitDtl = advertisementPermitDetailRepository
                .searchAdvertisementPermitDetailBySearchParamsAndStatusApproved(advPermitDetail);
        final List<HoardingSearch> hoardingSearchResults = new ArrayList<>();
        advPermitDtl.forEach(result -> {
            final HoardingSearch hoardingSearchResult = new HoardingSearch();
            hoardingSearchResult.setAdvertisementNumber(result.getAdvertisement().getAdvertisementNumber());
            hoardingSearchResult.setApplicationNumber(result.getApplicationNumber());
            hoardingSearchResult.setApplicationFromDate(result.getApplicationDate());
            hoardingSearchResult.setAgencyName(result.getAgency().getName());
            hoardingSearchResult.setStatus(result.getAdvertisement().getStatus());
            hoardingSearchResult.setHordingIdsSearchedByAgency(result.getId().toString());
            hoardingSearchResults.add(hoardingSearchResult);
        });
        return hoardingSearchResults;
    }

    public Assignment getWfInitiator(final AdvertisementPermitDetail advertisementPermitDetail) {
        return assignmentService.getPrimaryAssignmentForUser(advertisementPermitDetail.getCreatedBy().getId());
    }
}
