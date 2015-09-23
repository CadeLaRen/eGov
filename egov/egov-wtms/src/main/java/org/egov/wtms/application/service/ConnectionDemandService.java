/**
 * eGov suite of products aim to improve the internal efficiency,transparency,
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
package org.egov.wtms.application.service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ValidationException;

import org.egov.commons.Installment;
import org.egov.commons.dao.InstallmentDao;
import org.egov.demand.dao.DemandGenericDao;
import org.egov.demand.dao.EgBillDao;
import org.egov.demand.model.EgBill;
import org.egov.demand.model.EgBillType;
import org.egov.demand.model.EgDemand;
import org.egov.demand.model.EgDemandDetails;
import org.egov.demand.model.EgDemandReason;
import org.egov.infra.admin.master.service.ModuleService;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.utils.EgovThreadLocals;
import org.egov.ptis.domain.model.AssessmentDetails;
import org.egov.ptis.domain.service.property.PropertyExternalService;
import org.egov.wtms.application.entity.DemandDetail;
import org.egov.wtms.application.entity.FieldInspectionDetails;
import org.egov.wtms.application.entity.WaterConnection;
import org.egov.wtms.application.entity.WaterConnectionDetails;
import org.egov.wtms.application.repository.WaterConnectionDetailsRepository;
import org.egov.wtms.application.rest.WaterTaxDue;
import org.egov.wtms.application.service.collection.ConnectionBillService;
import org.egov.wtms.application.service.collection.WaterConnectionBillable;
import org.egov.wtms.masters.entity.DonationDetails;
import org.egov.wtms.masters.entity.WaterRatesDetails;
import org.egov.wtms.masters.entity.WaterRatesHeader;
import org.egov.wtms.masters.entity.enums.ConnectionStatus;
import org.egov.wtms.masters.entity.enums.ConnectionType;
import org.egov.wtms.masters.service.DonationDetailsService;
import org.egov.wtms.masters.service.DonationHeaderService;
import org.egov.wtms.masters.service.WaterRatesDetailsService;
import org.egov.wtms.masters.service.WaterRatesHeaderService;
import org.egov.wtms.utils.PropertyExtnUtils;
import org.egov.wtms.utils.WaterTaxNumberGenerator;
import org.egov.wtms.utils.WaterTaxUtils;
import org.egov.wtms.utils.constants.WaterTaxConstants;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ConnectionDemandService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DonationDetailsService donationDetailsService;

    @Autowired
    private DonationHeaderService donationHeaderService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private InstallmentDao installmentDao;

    @Autowired
    private DemandGenericDao demandGenericDao;

    @Autowired
    WaterConnectionService waterConnectionService;

    @Autowired
    WaterConnectionDetailsService waterConnectionDetailsService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private EgBillDao egBillDAO;

    @Autowired
    private ConnectionBillService connectionBillService;

    @Autowired
    private PropertyExtnUtils propertyExtnUtils;

    @Autowired
    private WaterConnectionDetailsRepository waterConnectionDetailsRepository;

    @Autowired
    private WaterRatesDetailsService waterRatesDetailsService;

    @Autowired
    private WaterRatesHeaderService waterRatesHeaderService;

    @Autowired
    private WaterTaxUtils waterTaxUtils;

    @Autowired
    private WaterTaxNumberGenerator waterTaxNumberGenerator;

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    public EgDemand createDemand(final WaterConnectionDetails waterConnectionDetails) {
        final Map<String, Object> feeDetails = new HashMap<String, Object>();
        DonationDetails donationDetails = null;
        final FieldInspectionDetails fieldInspectionDetails = waterConnectionDetails.getFieldInspectionDetails();

        if (null != fieldInspectionDetails)
            feeDetails.put(WaterTaxConstants.WATERTAX_FIELDINSPECTION_CHARGE,
                    fieldInspectionDetails.getEstimationCharges());

        if (!WaterTaxConstants.BPL_CATEGORY.equalsIgnoreCase(waterConnectionDetails.getCategory().getCode()))
            if (!(WaterTaxConstants.CHANGEOFUSE.equalsIgnoreCase(waterConnectionDetails.getApplicationType().getCode()) && (WaterTaxConstants.RESIDENTIAL
                    .equalsIgnoreCase(waterConnectionDetails.getPropertyType().getCode()) || ConnectionType.NON_METERED
                    .equals(waterConnectionDetails.getConnectionType()))))

                donationDetails = donationDetailsService.findByDonationHeader(donationHeaderService
                        .findByCategoryandUsageandMinPipeSize(waterConnectionDetails.getCategory(),
                                waterConnectionDetails.getUsageType(), waterConnectionDetails.getPipeSize()
                                .getSizeInInch()));

        if (donationDetails != null) {
            feeDetails.put(WaterTaxConstants.WATERTAX_DONATION_CHARGE, donationDetails.getAmount());
            waterConnectionDetails.setDonationCharges(donationDetails.getAmount());
        }
        final Installment installment = installmentDao.getInsatllmentByModuleForGivenDateAndInstallmentType(
                moduleService.getModuleByName(WaterTaxConstants.EGMODULE_NAME), new Date(), WaterTaxConstants.YEARLY);
        // Not updating demand amount collected for new connection as per the
        // discussion.
        // double totalFee = 0.0;

        final Set<EgDemandDetails> dmdDetailSet = new HashSet<EgDemandDetails>();
        for (final String demandReason : feeDetails.keySet())
            dmdDetailSet.add(createDemandDetails((Double) feeDetails.get(demandReason), demandReason, installment));
        // totalFee += (Double) feeDetails.get(demandReason);

        final EgDemand egDemand = new EgDemand();
        egDemand.setEgInstallmentMaster(installment);
        egDemand.getEgDemandDetails().addAll(dmdDetailSet);
        egDemand.setIsHistory("N");
        egDemand.setCreateDate(new Date());
        egDemand.setModifiedDate(new Date());
        return egDemand;
    }

	private EgDemandDetails createDemandDetails(final Double amount,
			final String demandReason, final Installment installment) {
		final EgDemandReason demandReasonObj = getDemandReasonByCodeAndInstallment(
				demandReason, installment);
		final EgDemandDetails demandDetail = new EgDemandDetails();
		demandDetail.setAmount(BigDecimal.valueOf(amount));
		demandDetail.setAmtCollected(BigDecimal.ZERO);
		demandDetail.setAmtRebate(BigDecimal.ZERO);
		demandDetail.setEgDemandReason(demandReasonObj);
		demandDetail.setCreateDate(new Date());
		demandDetail.setModifiedDate(new Date());
		return demandDetail;
	}

    private EgDemandDetails createDemandDetailsrForDataEntry(final BigDecimal amount, final BigDecimal collectAmount,
            final String demandReason, final String installment,DemandDetail demandObj,WaterConnectionDetails waterConnectionDetails) {
        final Installment installObj = waterConnectionDetailsRepository.getInstallmentByDescription(
                WaterTaxConstants.PROPERTY_MODULE_NAME, installment);
         EgDemandDetails demandDetail=null;
         EgDemandDetails  demandDetailsObj =waterConnectionDetailsRepository.getEgDemandDetailById(demandObj.getId(), waterConnectionDetails.getDemand().getId());
        final EgDemandReason demandReasonObj = getDemandReasonByCodeAndInstallment(demandReason, installObj);
        if(demandDetailsObj!=null && demandObj.getId() !=null )
    	   {
    		   	demandDetail=demandDetailsObj;
    		   	if(demandDetailsObj.getAmount().compareTo(amount) !=0){
    		   	demandDetail.setAmount(amount);
    		   	}
    			if(demandDetailsObj.getAmtCollected().compareTo(collectAmount) !=0){
   	        	demandDetail.setAmtCollected(collectAmount);
    			}
   	        	demandDetail.setEgDemandReason(demandReasonObj);
   	        	demandDetail.setModifiedDate(new Date());
    	   }
    	   else
    	   {
    		   	demandDetail = new EgDemandDetails();
    	        demandDetail.setAmount(amount);
    	        demandDetail.setAmtCollected(collectAmount);
    	        demandDetail.setAmtRebate(BigDecimal.ZERO);
    	        demandDetail.setEgDemandReason(demandReasonObj);
    	        demandDetail.setCreateDate(new Date());
    	        demandDetail.setModifiedDate(new Date());
    	       
    	   }
        return demandDetail;
    }

    public EgDemandReason getDemandReasonByCodeAndInstallment(final String demandReason, final Installment installment) {
        final Query demandQuery = getCurrentSession().getNamedQuery("DEMANDREASONBY_CODE_AND_INSTALLMENTID");
        demandQuery.setParameter(0, demandReason);
        demandQuery.setParameter(1, installment.getId());
        final EgDemandReason demandReasonObj = (EgDemandReason) demandQuery.uniqueResult();
        return demandReasonObj;
    }

    public EgDemandReason getDemandReasonByCode(final String demandReason) {
        final Query demandQuery = getCurrentSession().getNamedQuery("DEMANDREASONBY_CODE_AND_INSTALLMENTID");
        demandQuery.setParameter(0, demandReason);
        // demandQuery.setParameter(1, installment.getId());
        final EgDemandReason demandReasonObj = (EgDemandReason) demandQuery.uniqueResult();
        return demandReasonObj;
    }

    public HashMap<String, Double> getSplitFee(final WaterConnectionDetails waterConnectionDetails) {
        final EgDemand demand = waterConnectionDetails.getDemand();
        final HashMap<String, Double> splitAmount = new HashMap<>();
        if (demand != null && demand.getEgDemandDetails() != null && demand.getEgDemandDetails().size() > 0)
            for (final EgDemandDetails detail : demand.getEgDemandDetails())
                if (WaterTaxConstants.WATERTAX_FIELDINSPECTION_CHARGE.equals(detail.getEgDemandReason()
                        .getEgDemandReasonMaster().getCode()))
                    splitAmount
                    .put(WaterTaxConstants.WATERTAX_FIELDINSPECTION_CHARGE, detail.getAmount().doubleValue());
                else if (WaterTaxConstants.WATERTAX_DONATION_CHARGE.equals(detail.getEgDemandReason()
                        .getEgDemandReasonMaster().getCode()))
                    splitAmount.put(WaterTaxConstants.WATERTAX_DONATION_CHARGE, detail.getAmount().doubleValue());
        return splitAmount;
    }

    public WaterTaxDue getDueDetailsByConsumerCode(final String consumerCode) {
        final WaterTaxDue waterTaxDue = new WaterTaxDue();
        final List<String> consumerCodes = new ArrayList<>();
        final WaterConnectionDetails waterConnectionDetails = waterConnectionDetailsService
                .findByApplicationNumberOrConsumerCode(consumerCode);
        if (null != waterConnectionDetails) {
            getDueInfo(waterConnectionDetails);
            consumerCodes.add(waterConnectionDetails.getConnection().getConsumerCode());
            waterTaxDue.setConsumerCode(consumerCodes);
            waterTaxDue.setPropertyID(waterConnectionDetails.getConnection().getPropertyIdentifier());
            waterTaxDue.setConnectionCount(consumerCodes.size());
            waterTaxDue.setIsSuccess(true);
        } else {
            waterTaxDue.setIsSuccess(false);
            waterTaxDue.setConsumerCode(Collections.EMPTY_LIST);
            waterTaxDue.setConnectionCount(0);
            waterTaxDue.setErrorCode(WaterTaxConstants.CONSUMERCODE_NOT_EXIST_ERR_CODE);
            waterTaxDue.setErrorMessage(WaterTaxConstants.WTAXDETAILS_CONSUMER_CODE_NOT_EXIST_ERR_MSG_PREFIX
                    + consumerCode + WaterTaxConstants.WTAXDETAILS_NOT_EXIST_ERR_MSG_SUFFIX);
        }
        return waterTaxDue;
    }

    public WaterTaxDue getDueDetailsByPropertyId(final String propertyIdentifier) {
        BigDecimal arrDmd = new BigDecimal(0);
        BigDecimal arrColl = new BigDecimal(0);
        BigDecimal currDmd = new BigDecimal(0);
        BigDecimal currColl = new BigDecimal(0);
        BigDecimal totalDue = new BigDecimal(0);
        WaterTaxDue waterTaxDue = null;
        final List<WaterConnection> waterConnections = waterConnectionService
                .findByPropertyIdentifier(propertyIdentifier);
        if (waterConnections.isEmpty()) {
            waterTaxDue = new WaterTaxDue();
            waterTaxDue.setConsumerCode(Collections.EMPTY_LIST);
            waterTaxDue.setConnectionCount(0);
            waterTaxDue.setIsSuccess(false);
            waterTaxDue.setErrorCode(WaterTaxConstants.PROPERTYID_NOT_EXIST_ERR_CODE);
            waterTaxDue.setErrorMessage(WaterTaxConstants.WTAXDETAILS_PROPERTYID_NOT_EXIST_ERR_MSG_PREFIX
                    + propertyIdentifier + WaterTaxConstants.WTAXDETAILS_NOT_EXIST_ERR_MSG_SUFFIX);
        } else {
            waterTaxDue = new WaterTaxDue();
            final List<String> consumerCodes = new ArrayList<>();
            for (final WaterConnection connection : waterConnections)
                if (connection.getConsumerCode() != null) {

                    final WaterConnectionDetails waterConnectionDetails = waterConnectionDetailsService
                            .findByConnection(connection);

                    waterTaxDue = getDueInfo(waterConnectionDetails);
                    waterTaxDue.setPropertyID(propertyIdentifier);
                    consumerCodes.add(connection.getConsumerCode());
                    arrDmd = arrDmd.add(waterTaxDue.getArrearDemand());
                    arrColl = arrColl.add(waterTaxDue.getArrearCollection());
                    currDmd = currDmd.add(waterTaxDue.getCurrentDemand());
                    currColl = currColl.add(waterTaxDue.getCurrentCollection());
                    totalDue = totalDue.add(waterTaxDue.getTotalTaxDue());
                }
            waterTaxDue.setArrearDemand(arrDmd);
            waterTaxDue.setArrearCollection(arrColl);
            waterTaxDue.setCurrentDemand(currDmd);
            waterTaxDue.setCurrentCollection(currColl);
            waterTaxDue.setTotalTaxDue(totalDue);
            waterTaxDue.setConsumerCode(consumerCodes);
            waterTaxDue.setConnectionCount(waterConnections.size());
            waterTaxDue.setIsSuccess(true);
        }
        return waterTaxDue;
    }

    private WaterTaxDue getDueInfo(final WaterConnectionDetails waterConnectionDetails) {
        final Map<String, BigDecimal> resultmap = getDemandCollMap(waterConnectionDetails);
        final WaterTaxDue waterTaxDue = new WaterTaxDue();
        if (null != resultmap && !resultmap.isEmpty()) {
            final BigDecimal currDmd = resultmap.get(WaterTaxConstants.CURR_DMD_STR);
            waterTaxDue.setCurrentDemand(currDmd);
            final BigDecimal arrDmd = resultmap.get(WaterTaxConstants.ARR_DMD_STR);
            waterTaxDue.setArrearDemand(arrDmd);
            final BigDecimal currCollection = resultmap.get(WaterTaxConstants.CURR_COLL_STR);
            waterTaxDue.setCurrentCollection(currCollection);
            final BigDecimal arrCollection = resultmap.get(WaterTaxConstants.ARR_COLL_STR);
            waterTaxDue.setArrearCollection(arrCollection);
            // Calculating tax dues
            final BigDecimal taxDue = currDmd.add(arrDmd).subtract(currCollection).subtract(arrCollection);
            waterTaxDue.setTotalTaxDue(taxDue);
        }
        return waterTaxDue;
    }

    public Map<String, BigDecimal> getDemandCollMap(final WaterConnectionDetails waterConnectionDetails) {
        final EgDemand currDemand = waterConnectionDetails.getDemand();
        Installment installment = null;
        List<Object> dmdCollList = new ArrayList<Object>();
        Installment currInst = null;
        Integer instId = null;
        BigDecimal currDmd = BigDecimal.ZERO;
        BigDecimal arrDmd = BigDecimal.ZERO;
        BigDecimal currCollection = BigDecimal.ZERO;
        BigDecimal arrColelection = BigDecimal.ZERO;
        final Map<String, BigDecimal> retMap = new HashMap<String, BigDecimal>();

        if (currDemand != null)
            dmdCollList = getDmdCollAmtInstallmentWise(currDemand);
        currInst = installmentDao.getInsatllmentByModuleForGivenDateAndInstallmentType(
                moduleService.getModuleByName(WaterTaxConstants.EGMODULE_NAME), new Date(), WaterTaxConstants.YEARLY);

        for (final Object object : dmdCollList) {
            final Object[] listObj = (Object[]) object;
            instId = Integer.valueOf(listObj[1].toString());
            installment = (Installment) installmentDao.findById(instId, false);
            if (currInst.equals(installment)) {
                if (listObj[3] != null && !listObj[3].equals(BigDecimal.ZERO))
                    currCollection = currCollection.add(new BigDecimal((Double) listObj[3]));
                currDmd = currDmd.add((BigDecimal) listObj[2]);
            } else {
                arrDmd = arrDmd.add((BigDecimal) listObj[2]);
                if (listObj[3] != null && !listObj[3].equals(BigDecimal.ZERO))
                    arrColelection = arrColelection.add((BigDecimal) listObj[2]);
            }
        }
        retMap.put(WaterTaxConstants.CURR_DMD_STR, currDmd);
        retMap.put(WaterTaxConstants.ARR_DMD_STR, arrDmd);
        retMap.put(WaterTaxConstants.CURR_COLL_STR, currCollection);
        retMap.put(WaterTaxConstants.ARR_COLL_STR, arrColelection);
        return retMap;
    }

    public List<Object> getDmdCollAmtInstallmentWise(final EgDemand egDemand) {
        final StringBuffer strBuf = new StringBuffer(2000);
        strBuf.append("select dmdRes.id,dmdRes.id_installment, sum(dmdDet.amount) as amount, sum(dmdDet.amt_collected) as amt_collected, "
                + "sum(dmdDet.amt_rebate) as amt_rebate, inst.start_date from eg_demand_details dmdDet,eg_demand_reason dmdRes, "
                + "eg_installment_master inst,eg_demand_reason_master dmdresmas where dmdDet.id_demand_reason=dmdRes.id "
                + "and dmdDet.id_demand =:dmdId and dmdRes.id_installment = inst.id and dmdresmas.id = dmdres.id_demand_reason_master "
                + "group by dmdRes.id,dmdRes.id_installment, inst.start_date order by inst.start_date ");
        return getCurrentSession().createSQLQuery(strBuf.toString()).setLong("dmdId", egDemand.getId()).list();
    }

    public String generateBill(final String consumerCode) {
        String collectXML = "";
        final SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        String currentInstallmentYear = null;
        final WaterConnectionBillable waterConnectionBillable = (WaterConnectionBillable) context
                .getBean("waterConnectionBillable");
        final WaterConnectionDetails waterConnectionDetails = waterConnectionDetailsService
                .findByApplicationNumberOrConsumerCode(consumerCode);
        if (ConnectionStatus.INPROGRESS.equals(waterConnectionDetails.getConnectionStatus()))
            currentInstallmentYear = formatYear.format(getCurrentInstallment(WaterTaxConstants.EGMODULE_NAME,
                    WaterTaxConstants.YEARLY, new Date()).getInstallmentYear());
        else if (ConnectionStatus.ACTIVE.equals(waterConnectionDetails.getConnectionStatus())
                && ConnectionType.NON_METERED.equals(waterConnectionDetails.getConnectionType()))
            currentInstallmentYear = formatYear.format(getCurrentInstallment(
                    WaterTaxConstants.WATER_RATES_NONMETERED_PTMODULE, null, new Date()).getInstallmentYear());
        else if (ConnectionStatus.ACTIVE.equals(waterConnectionDetails.getConnectionStatus())
                && ConnectionType.METERED.equals(waterConnectionDetails.getConnectionType()))
            currentInstallmentYear = formatYear.format(getCurrentInstallment(WaterTaxConstants.EGMODULE_NAME,
                    WaterTaxConstants.MONTHLY, new Date()).getInstallmentYear());
        final AssessmentDetails assessmentDetails = propertyExtnUtils.getAssessmentDetailsForFlag(
                waterConnectionDetails.getConnection().getPropertyIdentifier(),
                PropertyExternalService.FLAG_FULL_DETAILS);
        waterConnectionBillable.setWaterConnectionDetails(waterConnectionDetails);
        waterConnectionBillable.setAssessmentDetails(assessmentDetails);
        waterConnectionBillable.setUserId(EgovThreadLocals.getUserId());

        waterConnectionBillable.setReferenceNumber(waterTaxNumberGenerator.generateBillNumber(currentInstallmentYear));
        waterConnectionBillable.setBillType(getBillTypeByCode(WaterTaxConstants.BILLTYPE_AUTO));

        final String billXml = connectionBillService.getBillXML(waterConnectionBillable);
        collectXML = URLEncoder.encode(billXml);
        return collectXML;
    }

    public EgBillType getBillTypeByCode(final String typeCode) {
        final EgBillType billType = egBillDAO.getBillTypeByCode(typeCode);
        return billType;
    }

    public EgDemand getDemandByInstAndApplicationNumber(final Installment installment, final String consumerCode) {
        return waterConnectionDetailsRepository.findByApplicationNumberAndInstallment(installment, consumerCode)
                .getDemand();
    }

    /**
     * @param waterConnectionDetails
     * @param billAmount
     * @param currentDate
     * @return Updates WaterConnectionDetails after Meter Entry Demand
     *         Calculettion and Update Previous Bill and Generates New Bill
     */
    @Transactional
    public WaterConnectionDetails updateDemandForMeteredConnection(final WaterConnectionDetails waterConnectionDetails,
            final BigDecimal billAmount, final Date currentDate) {
        final Installment installment = getCurrentInstallment(WaterTaxConstants.EGMODULE_NAME,
                WaterTaxConstants.MONTHLY, currentDate);
        final EgDemand demandObj = waterConnectionDetails.getDemand();
        final Set<EgDemandDetails> dmdDetailSet = new HashSet<EgDemandDetails>();
        dmdDetailSet.add(createDemandDetails(Double.parseDouble(billAmount.toString()),
                WaterTaxConstants.WATERTAXREASONCODE, installment));
        demandObj.setBaseDemand(demandObj.getBaseDemand().add(billAmount));
        demandObj.setEgInstallmentMaster(installment);
        demandObj.getEgDemandDetails().addAll(dmdDetailSet);
        demandObj.setModifiedDate(new Date());
        waterConnectionDetails.setDemand(demandObj);
        final List<EgBill> billlist = demandGenericDao.getAllBillsForDemand(demandObj, "N", "N");
        if (!billlist.isEmpty()) {
            final EgBill billObj = billlist.get(0);
            billObj.setIs_History("Y");
            billObj.setModifiedDate(new Date());
            egBillDAO.create(billObj);
        }

        generateBillForMeterAndMonthly(waterConnectionDetails.getConnection().getConsumerCode());
        return waterConnectionDetails;
    }

    /**
     * @param waterConnectionDetails
     * @param demandDeatilslist
     * @return creation or updating demand and demanddetails for data Entry
     *         Screen
     */
    @Transactional
    public WaterConnectionDetails updateDemandForNonMeteredConnectionDataEntry(
            final WaterConnectionDetails waterConnectionDetails) {
        EgDemand demandObj = null;
        if (waterConnectionDetails.getDemand() == null)
            demandObj = new EgDemand();
        else
            demandObj = waterConnectionDetails.getDemand();
      
         Set<EgDemandDetails> dmdDetailSet = new HashSet<EgDemandDetails>();
        for (final DemandDetail ddtempObj : waterConnectionDetails.getDemandDetailBeanList()){
        	dmdDetailSet.add(createDemandDetailsrForDataEntry(ddtempObj.getActualAmount(),
                    ddtempObj.getActualCollection(), ddtempObj.getReasonMaster(), ddtempObj.getInstallment(), ddtempObj,waterConnectionDetails));
        }
        
        demandObj.setBaseDemand(demandObj.getBaseDemand().add(getToTalAmount(dmdDetailSet)));
        demandObj.setAmtCollected(demandObj.getAmtCollected().add(getToTalCollectedAmount(dmdDetailSet)));
      
        final int listlength = waterConnectionDetails.getDemandDetailBeanList().size() - 1;
        final Installment installObj = waterConnectionDetailsRepository.getInstallmentByDescription(
                WaterTaxConstants.PROPERTY_MODULE_NAME, waterConnectionDetails.getDemandDetailBeanList()
                        .get(listlength).getInstallment());
        demandObj.setEgInstallmentMaster(installObj);
        demandObj.getEgDemandDetails().addAll(dmdDetailSet);
       
        demandObj.setModifiedDate(new Date());
        if (demandObj.getIsHistory() == null)
            demandObj.setIsHistory("N");
        if (demandObj.getCreateDate() == null)
            demandObj.setCreateDate(new Date());
        waterConnectionDetails.setDemand(demandObj);
        return waterConnectionDetails;
    }

    public BigDecimal getToTalAmount(final Set<EgDemandDetails> demandDeatilslist) {
        BigDecimal currentTotalAmount = BigDecimal.ZERO;
        for (final EgDemandDetails de : demandDeatilslist){
           currentTotalAmount = currentTotalAmount.add(de.getAmount());
            
        }
        return currentTotalAmount;
    }

    public BigDecimal getToTalCollectedAmount(final Set<EgDemandDetails> demandDeatilslist) {
        BigDecimal currentTotalAmount = BigDecimal.ZERO;
        for (final EgDemandDetails de : demandDeatilslist){
        
            currentTotalAmount = currentTotalAmount.add(de.getAmtCollected());
        }
        return currentTotalAmount;
    }

    /**
     * @param consumerCode
     * @return Generates Eg_bill Entry and saved with Demand and As of now we
     *         are generating Bill and its in XML format because no Method to
     *         just to generate Bill and Save as of now in
     *         connectionBillService.
     */
    @Transactional
    public String generateBillForMeterAndMonthly(final String consumerCode) {

        final WaterConnectionBillable waterConnectionBillable = (WaterConnectionBillable) context
                .getBean("waterConnectionBillable");
        final WaterConnectionDetails waterConnectionDetails = waterConnectionDetailsService
                .findByConsumerCodeAndConnectionStatus(consumerCode, ConnectionStatus.ACTIVE);
        final AssessmentDetails assessmentDetails = propertyExtnUtils.getAssessmentDetailsForFlag(
                waterConnectionDetails.getConnection().getPropertyIdentifier(),
                PropertyExternalService.FLAG_FULL_DETAILS);
        waterConnectionBillable.setWaterConnectionDetails(waterConnectionDetails);
        waterConnectionBillable.setAssessmentDetails(assessmentDetails);
        waterConnectionBillable.setUserId(EgovThreadLocals.getUserId());
        waterConnectionBillable.setReferenceNumber(waterTaxNumberGenerator.generateMeterDemandNoticeNumber());
        waterConnectionBillable.setBillType(getBillTypeByCode(WaterTaxConstants.BILLTYPE_MANUAL));

        final String billObj = connectionBillService.getBillXML(waterConnectionBillable);

        return billObj;
    }

    public WaterConnectionDetails updateDemandForNonmeteredConnection(
            final WaterConnectionDetails waterConnectionDetails) throws ValidationException {
        final Installment installment = getCurrentInstallment(WaterTaxConstants.WATER_RATES_NONMETERED_PTMODULE, null,
                new Date());
        double totalWaterRate = 0;
        final WaterRatesHeader waterRatesHeader = waterRatesHeaderService
                .findByConnectionTypeAndUsageTypeAndWaterSourceAndPipeSize(waterConnectionDetails.getConnectionType(),
                        waterConnectionDetails.getUsageType(), waterConnectionDetails.getWaterSource(),
                        waterConnectionDetails.getPipeSize());
        final WaterRatesDetails waterRatesDetails = waterRatesDetailsService.findByWaterRatesHeader(waterRatesHeader);
        final int noofmonths = DateUtils.noOfMonths(new Date(), installment.getToDate());
        if (null != waterRatesDetails) {
            if (noofmonths > 0)
                totalWaterRate = waterRatesDetails.getMonthlyRate() * (noofmonths + 1);
            else
                totalWaterRate = waterRatesDetails.getMonthlyRate();

            final EgDemand demand = waterConnectionDetails.getDemand();
            final EgDemandDetails demandDetails = createDemandDetails(totalWaterRate,
                    WaterTaxConstants.WATERTAXREASONCODE, installment);
            demand.setBaseDemand(BigDecimal.valueOf(totalWaterRate));
            demand.setEgInstallmentMaster(installment);
            demand.getEgDemandDetails().add(demandDetails);
            demand.setModifiedDate(new Date());
            waterConnectionDetails.setDemand(demand);

        } else
            throw new ValidationException("err.water.rate.not.found");
        return waterConnectionDetails;
    }

    public Map<String, BigDecimal> getDemandCollMapForPtisIntegration(
            final WaterConnectionDetails waterConnectionDetails, final String moduleName, final String installmentType) {
        final EgDemand currDemand = waterConnectionDetails.getDemand();
        Installment installment = null;
        List<Object> dmdCollList = new ArrayList<Object>();
        Installment currInst = null;
        Integer instId = null;
        BigDecimal curDue = BigDecimal.ZERO;
        BigDecimal arrDue = BigDecimal.ZERO;

        BigDecimal arrearInstallmentfrom = BigDecimal.ZERO;
        final Map<String, BigDecimal> retMap = new HashMap<String, BigDecimal>();
        if (currDemand != null)
            dmdCollList = getDmdCollAmtInstallmentWiseWithIsDmdTrue(currDemand);
        currInst = getCurrentInstallment(moduleName, null, new Date());
        for (final Object object : dmdCollList) {
            final Object[] listObj = (Object[]) object;
            instId = Integer.valueOf(listObj[2].toString());
            installment = (Installment) installmentDao.findById(instId, false);
            if (currInst.equals(installment))
                curDue = new BigDecimal(listObj[6].toString());
            else {
                arrDue = (BigDecimal) listObj[6];
                if (arrDue.signum() > 0)
                    if (null == arrearInstallmentfrom)
                        arrearInstallmentfrom = BigDecimal.valueOf(instId);

            }
        }
        retMap.put(WaterTaxConstants.ARR_DUE, arrDue);
        retMap.put(WaterTaxConstants.CURR_DUE, curDue);
        retMap.put(WaterTaxConstants.ARR_INSTALFROM_STR, arrearInstallmentfrom);
        return retMap;
    }

    public List<Object> getDmdCollAmtInstallmentWiseWithIsDmdTrue(final EgDemand egDemand) {
        final StringBuffer strBuf = new StringBuffer(2000);
        strBuf.append("SELECT wcdid,dmdResId,installment,amount,amt_collected,amt_rebate,amount-amt_collected AS balance,"
                + "instStartDate FROM (SELECT wcd.id AS wcdid,dmdRes.id AS dmdResId,dmdRes.id_installment AS installment,"
                + "SUM(dmdDet.amount) AS amount,SUM(dmdDet.amt_collected) AS amt_collected,SUM(dmdDet.amt_rebate) AS amt_rebate,"
                + "inst.start_date AS inststartdate FROM eg_demand_details dmdDet,eg_demand_reason dmdRes,eg_installment_master inst,"
                + "eg_demand_reason_master dmdresmas,egwtr_connectiondetails wcd WHERE dmdDet.id_demand_reason=dmdRes.id "
                + "AND dmdDet.id_demand =:dmdId AND dmdRes.id_installment = inst.id AND dmdresmas.id = dmdres.id_demand_reason_master "
                + "AND dmdresmas.isdemand=TRUE AND wcd.demand = dmdDet.id_demand GROUP BY dmdRes.id, dmdRes.id_installment,"
                + "inst.start_date,wcd.id ORDER BY inst.start_date) AS dcb");
        return getCurrentSession().createSQLQuery(strBuf.toString()).setLong("dmdId", egDemand.getId()).list();
    }

    public Installment getCurrentInstallment(final String moduleName, final String installmentType, final Date date) {
        if (null == installmentType)
            return installmentDao.getInsatllmentByModuleForGivenDate(moduleService.getModuleByName(moduleName),
                    new Date());
        else
            return installmentDao.getInsatllmentByModuleForGivenDateAndInstallmentType(
                    moduleService.getModuleByName(moduleName), date, installmentType);
    }

    public Map<String, BigDecimal> getDemandCollMapForBill(final WaterConnectionDetails waterConnectionDetails,
            final String moduleName, final String installmentType) {
        final EgDemand currDemand = waterConnectionDetails.getDemand();
        List<Object> dmdCollList = new ArrayList<Object>();
        Integer instId = null;
        Double balance = null;
        Integer val = null;
        final Map<String, BigDecimal> retMap = new HashMap<String, BigDecimal>();
        if (currDemand != null)
            dmdCollList = getDmdCollAmtInstallmentWiseWithIsDmdTrue(currDemand);
        for (final Object object : dmdCollList) {
            final Object[] listObj = (Object[]) object;
            balance = (Double) listObj[6];
            if (BigDecimal.valueOf(balance).signum() > 0) {
                val = Integer.valueOf(listObj[0].toString());
                instId = Integer.valueOf(listObj[2].toString());
                retMap.put("wcdid", BigDecimal.valueOf(val));
                retMap.put("inst", BigDecimal.valueOf(instId));
            }
        }
        return retMap;
    }

    /**
     * @param waterConnectionDetails
     * @param givenDate
     *            It Checks the Meter Entry Exist For the Entred Date Month and
     *            Returns True if It Exists and checks with Demand Current
     *            Installment
     */
    public Boolean meterEntryAllReadyExistForCurrentMonth(final WaterConnectionDetails waterConnectionDetails,
            final Date givenDate) {
        Boolean currrentInstallMentExist = false;
        final Installment installment = getCurrentInstallment(WaterTaxConstants.EGMODULE_NAME,
                WaterTaxConstants.MONTHLY, givenDate);
        if (waterConnectionDetails.getDemand() != null
                && waterConnectionDetails.getDemand().getEgInstallmentMaster() != null)
            if (installment != null
            && installment.getInstallmentNumber().equals(
                    waterConnectionDetails.getDemand().getEgInstallmentMaster().getInstallmentNumber()))
                currrentInstallMentExist = true;
        return currrentInstallMentExist;
    }

}
