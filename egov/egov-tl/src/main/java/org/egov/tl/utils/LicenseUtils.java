/*******************************************************************************
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *     accountability and the service delivery of the government  organizations.
 *
 *      Copyright (C) <2015>  eGovernments Foundation
 *
 *      The updated version of eGov suite of products as by eGovernments Foundation
 *      is available at http://www.egovernments.org
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program. If not, see http://www.gnu.org/licenses/ or
 *      http://www.gnu.org/licenses/gpl.html .
 *
 *      In addition to the terms of the GPL license to be adhered to in using this
 *      program, the following additional terms are to be complied with:
 *
 *  	1) All versions of this program, verbatim or modified must carry this
 *  	   Legal Notice.
 *
 *  	2) Any misrepresentation of the origin of the material is prohibited. It
 *  	   is required that all modified versions of this material be marked in
 *  	   reasonable ways as different from the original version.
 *
 *  	3) This license does not grant any rights to any user of the program
 *  	   with regards to rights under trademark law for use of the trade names
 *  	   or trademarks of eGovernments Foundation.
 *
 *    In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 ******************************************************************************/
package org.egov.tl.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;
import org.egov.collection.integration.models.BillReceiptInfo;
import org.egov.collection.integration.models.ReceiptInstrumentInfo;
import org.egov.collection.integration.services.CollectionIntegrationService;
import org.egov.commons.ContractorGrade;
import org.egov.commons.Installment;
import org.egov.commons.dao.InstallmentDao;
import org.egov.demand.model.EgBill;
import org.egov.demand.model.EgBillDetails;
import org.egov.demand.model.EgDemand;
import org.egov.demand.model.EgDemandDetails;
import org.egov.demand.model.EgdmCollectedReceipt;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.BoundaryType;
import org.egov.infra.admin.master.entity.Department;
import org.egov.infra.admin.master.entity.HierarchyType;
import org.egov.infra.admin.master.entity.Module;
import org.egov.infra.admin.master.entity.Role;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.BoundaryService;
import org.egov.infra.admin.master.service.BoundaryTypeService;
import org.egov.infra.admin.master.service.DepartmentService;
import org.egov.infra.admin.master.service.HierarchyTypeService;
import org.egov.infra.admin.master.service.ModuleService;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.persistence.entity.Address;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.infstr.services.PersistenceService;
import org.egov.infstr.utils.HibernateUtil;
import org.egov.tl.domain.entity.License;
import org.egov.tl.domain.entity.LicenseStatus;
import org.egov.tl.domain.entity.LicenseStatusValues;
import org.egov.tl.domain.entity.SubCategory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Class LicenseUtils. A utility class used across the License Products
 */
public class LicenseUtils {
    private static final Logger LOGGER = Logger.getLogger(LicenseUtils.class);
    public static final String ADMIN_HIERARCHY_TYPE = "ADMINISTRATION";
    private static final String ZONE_BOUNDARY_TYPE = "Zone";
    private static final String WARD_BOUNDARY_TYPE = "Ward";
    private static final String CITY_BOUNDARY_TYPE = "City";
    public static final String LOCATION_HIERARCHY_TYPE = "LOCATION";
    private static final String DEMAND_ID = "demandId";
    @Autowired
    private PersistenceService persistenceService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private BoundaryService boundaryService;
    @Autowired
    private BoundaryTypeService boundaryTypeService;
    @Autowired
    private HierarchyTypeService hierarchyTypeService;
    @Autowired
    protected CollectionIntegrationService collectionIntegrationService;
    @Autowired
    private AppConfigValueService appConfigValueService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private InstallmentDao installmentDao;
    @Autowired
    private UserService userService;

    public void setCollectionIntegrationService(
            final CollectionIntegrationService collectionIntegrationService) {
        this.collectionIntegrationService = collectionIntegrationService;
    }

    public Module getModule(final String moduleName) {
        return moduleService.getModuleByName(moduleName);
    }

    public void setPersistenceService(final PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public LicenseUtils() {
        //session = persistenceService.getSession();
    }

    public LicenseUtils(final SessionFactory factory) {
    }

    // private TradeIdGenerator generator;
    private Session session;
    // private ContractorService contractorService;
    private final HashMap<String, Object> contractorDetailsMap = new HashMap<String, Object>();

    protected Session getHibSession() {
        return persistenceService.getSession();
    }

    public Boundary getBoundary(final String id) {
        return boundaryService.getBoundaryById(Long.valueOf(id));
    }

    /**
     * called while fetching the child boundaries for a given boundary
     *
     * @param String boundaryId
     * @exception ApplicationRuntimeException
     * @return List<Boundary> child boundaries
     */
    public List<Boundary> getChildBoundaries(final String boundaryId) {
        List<Boundary> cBoundaries = null;
        try {
            cBoundaries = boundaryService.getChildBoundariesByBoundaryId(Long.valueOf(boundaryId));
        } catch (final Exception e) {
            LOGGER.error("getChildBoundaries()--Exception is thrown");
            throw new ApplicationRuntimeException("Unable to load boundary information", e);
        }
        return cBoundaries;
    }

    public String findGlCodeForDemand(final EgDemand demand) {
        String glCode = Constants.EMPTY_STRING;
        for (final EgDemandDetails demandDetails : demand.getEgDemandDetails())
            if (!Constants.PENALTY_CODE.equals(demandDetails.getEgDemandReason().getEgDemandReasonMaster().getCode())) {
                final StringBuilder strBuf = new StringBuilder(2000);
                strBuf.append(" select ch.glcode from CChartOfAccounts ch, EgDemandDetails demDet where demDet.id=:demandId and demDet.egDemandReason.glcodeId.id=ch.id");
                final Query qry = getSession().createQuery(strBuf.toString()).setLong(LicenseUtils.DEMAND_ID,
                        demandDetails.getId());
                glCode = (String) qry.uniqueResult();
            }
        return glCode;
    }

    @SuppressWarnings("unchecked")
    public List<Boundary> getCrossHeirarchyChildren(final String hierarchyType, final String boundaryType, final int boundaryId) {
        /*
         * HierarchyType hType = null; try { hType = HierarchyTypeDAO.getHierarchyTypeByName(hierarchyType); } catch (final
         * Exception e) { LOGGER.error("getCrossHeirarchyChildren()--Exception"); throw new
         * ApplicationRuntimeException("Unable to load hierarchy information", e); } final BoundaryType childBoundaryType =
         * boundaryTypeDAO.getBoundaryType(boundaryType, hType); final Boundary parentBoundary =
         * boundaryService.getBoundaryById(Long.valueOf(boundaryId));
         */
        return null; // new LinkedList(boundaryService.getCrossHeirarchyChildren(parentBoundary, childBoundaryType));
    }

    // Fetch HierarchyType
    @SuppressWarnings("unchecked")
    public List<Boundary> getAllZone() {
        HierarchyType hType = null;
        try {
            hType = hierarchyTypeService.getHierarchyTypeByName(LicenseUtils.ADMIN_HIERARCHY_TYPE);
        } catch (final Exception e) {
            LOGGER.error("getAllZone()--Exception");
            throw new ApplicationRuntimeException("Unable to load Heirarchy information", e);
        }
        List<Boundary> zoneList = null;
        final BoundaryType bType = boundaryTypeService.getBoundaryTypeByNameAndHierarchyType(LicenseUtils.ZONE_BOUNDARY_TYPE,
                hType);
        zoneList = boundaryService.getAllBoundariesByBoundaryTypeId(bType.getId());
        return zoneList;
    }

    // Fetch HierarchyType
    @SuppressWarnings("unchecked")
    public List<Boundary> getAllCity() {
        HierarchyType hType = null;
        try {
            hType = hierarchyTypeService.getHierarchyTypeByName(LicenseUtils.ADMIN_HIERARCHY_TYPE);
        } catch (final Exception e) {
            LOGGER.error("getAllCity()--Exception");
            throw new ApplicationRuntimeException("Unable to load Heirarchy information", e);
        }
        List<Boundary> cityList = null;
        final BoundaryType bType = boundaryTypeService.getBoundaryTypeByNameAndHierarchyType(LicenseUtils.CITY_BOUNDARY_TYPE,
                hType);
        cityList = boundaryService.getAllBoundariesByBoundaryTypeId(bType.getId());
        return cityList;
    }

    // Fetch HierarchyType
    @SuppressWarnings("unchecked")
    public List<Boundary> getAllWard() {
        HierarchyType hType = null;
        try {
            hType = hierarchyTypeService.getHierarchyTypeByName(LicenseUtils.ADMIN_HIERARCHY_TYPE);
        } catch (final Exception e) {
            LOGGER.error("getAllWard()--Exception");
            throw new ApplicationRuntimeException("Unable to load Heirarchy information", e);
        }
        List<Boundary> wardList = null;
        final BoundaryType bType = boundaryTypeService.getBoundaryTypeByNameAndHierarchyType(LicenseUtils.WARD_BOUNDARY_TYPE,
                hType);
        wardList = boundaryService.getAllBoundariesByBoundaryTypeId(bType.getId());
        return wardList;
    }

    public static String getInstallmentString(final Date date) {
        final int Year = date.getYear() % 100;
        return (Year / 10 > 0 ? Year : "0" + Year) + "-" + ((Year + 1) % 100 / 10 > 0 ? Year + 1 : "0" + (Year + 1) % 100);
    }

    public static String getYearString(final Date date) {
        final int Year = date.getYear() % 100;
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) + "-" + ((Year + 1) % 100 / 10 > 0 ? Year + 1 : "0" + (Year + 1) % 100);
    }

    /**
     * Gets the predefined Application Configuration values for the requested Keys
     *
     * @param String key
     * @param String moduleName
     * @return the app config value for the key
     */
    public String getAppConfigValue(final String key, final String moduleName) {
        String value = Constants.EMPTY_STRING;
        final AppConfigValues appConfigValues = appConfigValueService.getAppConfigValueByDate(moduleName, key, new Date());
        value = appConfigValues.getValue();
        return value;
    }

    /**
     * called while calculating the Total Bill Amount for Collecting TAX fetches the Demand Details & Installment
     */
    protected Session getSession() {
        return persistenceService.getSession();
    }

    public String getInstallDescription(final Installment dmdInstallment) {
        return "License Fee for " + LicenseUtils.getYearString(dmdInstallment.getFromDate());
    }

    public String getParameterValue(final String field, final Map<String, String[]> parameters) {
        final String[] fieldArray = parameters.get(field);
        return fieldArray != null ? fieldArray[0] : null;
    }

    public static boolean validateByRegex(final String value, final String regex) {
        if (value == null)
            return false;
        final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        final Matcher m = pattern.matcher(value);
        return m.matches();
    }

    public static String validateEffecFrom(final Date newEffecFrom, final Date oldEffecFrom) {
        if (!newEffecFrom.after(new Date()) || org.apache.commons.lang.time.DateUtils.isSameDay(newEffecFrom, new Date()))
            return "tradelicense.error.effectivefrom.currentdate";
        if (!newEffecFrom.after(oldEffecFrom))
            return "tradelicense.error.effectivefrom.after";
        return null;
    }

    public boolean checkOverlap(final Long iFrom, final Long iFrom_Base, final Long iTo, final Long iTo_Base) {
        if (iFrom >= iFrom_Base && iTo <= iTo_Base)
            return true;
        else if (iFrom <= iFrom_Base && iTo > iFrom_Base && iTo <= iTo_Base)
            return true;
        else if (iFrom >= iFrom_Base && iFrom < iTo_Base && iTo >= iTo_Base)
            return true;
        else
            return false;
    }

    /**
     * @param simpleName Simple name is the name of the license Subclass and same name should be inserted to EGTL_MSTR_LICENSE_TYPE
     * table
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SubCategory> getAllTradeNames(final String simpleName) {
        return persistenceService.findAllBy("from org.egov.tl.domain.entity.SubCategory where licenseType.name=?",
                simpleName);
    }

    @SuppressWarnings("unchecked")
    public List<Installment> getInstallmentYears(final Module module) {
        List<Installment> cFinancialYear = null;
        final Query query = getSession().createQuery("from Installment I where I.module=:module ");
        query.setEntity("module", module);
        final ArrayList<Installment> list = (ArrayList<Installment>) query.list();
        if (list.size() > 0)
            cFinancialYear = list;
        return cFinancialYear;
    }

    /**
     * @param simpleName Simple name is the name of the license Subclass and same name should be inserted to EGTL_MSTR_LICENSE_TYPE
     * table
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SubCategory> getAllTradeNamesByLicenseSubType(final String subType) {
        return persistenceService.findAllBy("from org.egov.tl.domain.entity.SubCategory where licenseSubType.code=?",
                subType);
    }

    /**
     * @param simpleName Simple name is the name of the license Subclass and same name should be inserted to EGTL_MSTR_LICENSE_TYPE
     * table
     * @return
     */
    public LicenseStatus getLicenseStatusbyCode(final String statusCode) {
        return (LicenseStatus) persistenceService.find("FROM org.egov.tl.domain.entity.LicenseStatus where statusCode=?",
                statusCode);
    }

    public LicenseStatusValues getCurrentStatus(final License license) {
        // Set licenseStatusValSet=license.getLicenseStatusValuesSet();
        return (LicenseStatusValues) persistenceService.find(
                "from org.egov.tl.domain.entity.LicenseStatusValues  where license=? and active=true", license);
    }

    public Map<Integer, String> getCancellationReasonMap() {
        final Map<Integer, String> reasonMap = new TreeMap<Integer, String>();
        reasonMap.put(Constants.REASON_CANCELLATION_NO_1, Constants.REASON_CANCELLATION_VALUE_1);
        reasonMap.put(Constants.REASON_CANCELLATION_NO_2, Constants.REASON_CANCELLATION_VALUE_2);
        reasonMap.put(Constants.REASON_CANCELLATION_NO_3, Constants.REASON_CANCELLATION_VALUE_3);
        return reasonMap;

    }

    public Map<Integer, String> getObjectionReasons() {
        final Map<Integer, String> objectionReasons = new TreeMap<Integer, String>();
        objectionReasons.put(Constants.REASON_OBJECTION_NO_1, Constants.REASON_OBJECTION_VALUE_1);
        objectionReasons.put(Constants.REASON_OBJECTION_NO_2, Constants.REASON_OBJECTION_VALUE_2);
        objectionReasons.put(Constants.REASON_OBJECTION_NO_3, Constants.REASON_OBJECTION_VALUE_3);
        return objectionReasons;
    }

    @SuppressWarnings("unchecked")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    /**
     * Returns the number of months between the the 2 given dates.
     * @param startDate the start date
     * @param endDate the end date
     * @return the number of months
     * @author Sapna
     * @return
     */
    public static int getNumberOfMonths(final java.util.Date expiryDate, final java.util.Date dateOfRenew) {
        // add one day to date of Expiry

        // assert startDate.before(endDate);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(expiryDate);
        calendar.add(Calendar.DATE, 1);

        final int startMonth = calendar.get(Calendar.MONTH) + 1;
        final int startYear = calendar.get(Calendar.YEAR);
        calendar.setTime(dateOfRenew);
        int endMonth = calendar.get(Calendar.MONTH) + 1;
        final int endYear = calendar.get(Calendar.YEAR);
        int diffMonth = 0;
        if (startYear < endYear)
            endMonth += (endYear - startYear) * 12;
        diffMonth = endMonth - startMonth;
        // adding one month in the total difference of month as both dates are included
        return diffMonth + 1;
    }

    public Installment getCurrInstallment(final Module module)
    {
        final Installment currentInstall = installmentDao.getInsatllmentByModuleForGivenDate(module, new Date());
        return currentInstall;

    }

    public List<Installment> getFinYearByDateRange(final Module module, final Date sDate, final Date eDate) {
        List<Installment> cFinancialYear = null;
        final Query query = getSession().createQuery(
                "from Installment I where I.module=:module and (I.fromDate >= :fromYear AND I.toDate <=:toYear)");
        query.setEntity("module", module);
        query.setDate("fromYear", sDate);
        query.setDate("toYear", eDate);
        @SuppressWarnings("unchecked")
        final ArrayList<Installment> list = (ArrayList<Installment>) query.list();
        if (list.size() > 0)
            cFinancialYear = list;
        return cFinancialYear;
    }

    public Map<String, List<ReceiptInstrumentInfo>> getReceiptInfo(final License license) {
        // Set<EgBill> egBills = license.getLicenseDemand().getEgBills();
        final Set<EgBill> egBills = license.getCurrentDemand().getEgBills();

        final Set<String> egBillNumbers = new HashSet();
        String serviceCode = "";
        for (final EgBill bill : egBills) {
            egBillNumbers.add(bill.getId().toString());
            serviceCode = bill.getServiceCode();
        }
        final Map<String, List<BillReceiptInfo>> billReceipt = collectionIntegrationService
                .getBillReceiptInfo(serviceCode, egBillNumbers);
        List<BillReceiptInfo> billReceiptInfoList = null;
        List<ReceiptInstrumentInfo> instrumentInfoList = null;
        final Map<String, List<ReceiptInstrumentInfo>> map = new HashMap<String, List<ReceiptInstrumentInfo>>();

        for (final EgBill bill : egBills)
            if (null != billReceipt.get(bill.getId().toString())) {
                billReceiptInfoList = billReceipt.get(bill.getId().toString());

                for (final EgBillDetails egd : bill.getEgBillDetails()) {

                    egd.getEgBill().getId();

                    for (final BillReceiptInfo billReceiptInfo : billReceiptInfoList)
                        if (billReceiptInfo.getBillReferenceNum().equalsIgnoreCase(
                                String.valueOf(egd.getEgBill().getId()))) {
                            for (final ReceiptInstrumentInfo instrumentInfo : billReceiptInfo.getInstrumentDetails()) {
                                instrumentInfoList = new ArrayList<ReceiptInstrumentInfo>();
                                instrumentInfoList.add(instrumentInfo);
                            }
                            map.put(egd.getEgDemandReason().getEgDemandReasonMaster().getCode(), instrumentInfoList);
                        }
                }
            }
        return map;
    }

    public ContractorGrade getWorksEstimateDetailsForTradeName(final String tradeName)
    {
        ContractorGrade contractorGrade = null;
        try {
            final Query query = getSession().createQuery("from org.egov.commons.ContractorGrade cg where cg.code=:code");
            query.setString("code", tradeName);
            contractorGrade = (ContractorGrade) query.uniqueResult();
        } catch (final HibernateException e) {
            LOGGER.error("Error while loading getWorksEstimateDetailsForTradeName" + e.getMessage());
        }
        return contractorGrade;
    }

    public Map<String, BillReceiptInfo> getBillReceipt(final License license) {
        final Set<EgBill> egBills = license.getCurrentDemand().getEgBills();
        final Set<String> egBillNumbers = new HashSet();
        String serviceCode = "";
        for (final EgBill bill : egBills) {
            egBillNumbers.add(bill.getId().toString());
            serviceCode = bill.getServiceCode();
        }
        final Map<String, List<BillReceiptInfo>> billReceipt = collectionIntegrationService
                .getBillReceiptInfo(serviceCode, egBillNumbers);
        List<BillReceiptInfo> billReceiptInfoList = null;
        final Map<String, BillReceiptInfo> map = new HashMap<String, BillReceiptInfo>();

        for (final EgBill bill : egBills)
            if (null != billReceipt.get(bill.getId().toString())) {
                billReceiptInfoList = billReceipt.get(bill.getId().toString());

                for (final EgBillDetails egd : bill.getEgBillDetails()) {

                    egd.getEgBill().getId();

                    for (final BillReceiptInfo billReceiptInfo : billReceiptInfoList)
                        if (billReceiptInfo.getBillReferenceNum().equalsIgnoreCase(
                                String.valueOf(egd.getEgBill().getId())))
                            map.put(egd.getEgDemandReason().getEgDemandReasonMaster().getCode(), billReceiptInfo);
                }
            }
        return map;
    }

    public List<ViolationReceiptDetails> getViolationFeeBillReceipt(
            final License license) {
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        final Set<EgDemandDetails> egDemandDetails = license.getCurrentDemand().getEgDemandDetails();
        final List<ViolationReceiptDetails> violationReceiptList = new ArrayList<ViolationReceiptDetails>();
        for (final EgDemandDetails demandDetails : egDemandDetails) {
            final ViolationReceiptDetails violationReceiptDetails = new ViolationReceiptDetails();
            if (demandDetails.getEgDemandReason().getEgDemandReasonMaster().getCode().equalsIgnoreCase("Violation Fee")) {
                violationReceiptDetails.setReceiptNumber("Not Collected");
                violationReceiptDetails.setReceiptdate("Not Collected");
                violationReceiptDetails.setViolationDate(demandDetails.getCreateDate());
                violationReceiptDetails.setViolationFee(demandDetails.getAmount().toString());
                if (!demandDetails.getEgdmCollectedReceipts().isEmpty())
                    for (final EgdmCollectedReceipt egdm : demandDetails.getEgdmCollectedReceipts()) {
                        violationReceiptDetails.setReceiptNumber(egdm.getReceiptNumber());
                        violationReceiptDetails.setReceiptdate(format.format(egdm.getReceiptDate()).toString());
                    }
                violationReceiptList.add(violationReceiptDetails);
            }
        }
        return violationReceiptList;
    }

    public String getRolesForUserId(final Long userId) {
        LOGGER.debug("Entered into getRolesForUserId method");
        LOGGER.debug("User id : " + userId);
        String roleName;
        final List<String> roleNameList = new ArrayList<String>();
        final User user = userService.getUserById(userId);
        for (final Role role : user.getRoles()) {
            roleName = role.getName() != null ? role.getName() : "";
            roleNameList.add(roleName.toUpperCase());
        }
        LOGGER.debug("Exit from method getRolesForUserId with return value : " + roleNameList.toString().toUpperCase());
        return roleNameList.toString().toUpperCase();
    }

    /*
     * public void createContractorForDepartment(final License license, final String status) throws ValidationException {
     * contractorDetailsMap = createContractorDetailsMap(license, status);
     * contractorService.createContractorForDepartment(contractorDetailsMap); }
     */

    public HashMap<String, Object> createContractorDetailsMap(final License license, final String status)
    {
        HashMap<String, Object> contractorMap = new HashMap<String, Object>();
        contractorMap = new HashMap<String, Object>();
        if (!org.apache.commons.lang.StringUtils.isBlank(license.getContractorCode())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getContractorCode()))
            contractorMap.put(Constants.WORKS_KEY_CODE, license.getContractorCode());
        else if (!org.apache.commons.lang.StringUtils.isBlank(license.getLicenseNumber())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getLicenseNumber()))
            contractorMap.put(Constants.WORKS_KEY_CODE, license.getLicenseNumber());
        String deptCode = null;
        if (license.getTradeName().getLicenseType().getModule().getName().equalsIgnoreCase(Constants.PWDLICENSE_MODULENAME))
            deptCode = Constants.PWD_DEPT_CODE;
        else if (license.getTradeName().getLicenseType().getModule().getName()
                .equalsIgnoreCase(Constants.ELECTRICALLICENSE_MODULENAME))
            deptCode = Constants.ELECTRICAL_DEPT_CODE;
        if (!org.apache.commons.lang.StringUtils.isBlank(deptCode) && !org.apache.commons.lang.StringUtils.isEmpty(deptCode))
            contractorMap.put(Constants.WORKS_KEY_DEPT_CODE, deptCode);
        if (!org.apache.commons.lang.StringUtils.isBlank(license.getLicensee().getApplicantName())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getLicensee().getApplicantName()))
            contractorMap.put(Constants.WORKS_KEY_NAME, license.getLicensee().getApplicantName());
        final Address address = license.getLicensee().getAddress();
        if (!org.apache.commons.lang.StringUtils.isBlank(address.getHouseNoBldgApt())
                && !org.apache.commons.lang.StringUtils.isEmpty(address.getHouseNoBldgApt())) {
            final String streetAddress = address.getHouseNoBldgApt();
            if (address.getStreetRoadLine() != null)
                streetAddress.concat(address.getStreetRoadLine());
            contractorMap.put(Constants.WORKS_KEY_CORRES_ADDR, streetAddress);
        }
        // contractorMap.put(Constants.WORKS_KEY_PAYMENT_ADDR ,"");
        if (!org.apache.commons.lang.StringUtils.isBlank(license.getLicensee().getApplicantName())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getLicensee().getApplicantName()))
            contractorMap.put(Constants.WORKS_KEY_CONTACT_PERSON, license.getLicensee().getApplicantName());
        if (!org.apache.commons.lang.StringUtils.isBlank(license.getLicensee().getEmailId())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getLicensee().getEmailId()))
            contractorMap.put(Constants.WORKS_KEY_EMAIL, license.getLicensee().getEmailId());
        if (!org.apache.commons.lang.StringUtils.isBlank(license.getRemarks())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getRemarks()))
            contractorMap.put(Constants.WORKS_KEY_NARRATION, license.getRemarks());
        if (!org.apache.commons.lang.StringUtils.isBlank(license.getCompanyPanNumber())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getCompanyPanNumber()))
            contractorMap.put(Constants.WORKS_KEY_PAN_NUMBER, license.getCompanyPanNumber());
        if (!org.apache.commons.lang.StringUtils.isBlank(license.getTinNumber())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getTinNumber()))
            contractorMap.put(Constants.WORKS_KEY_TIN_NUMBER, license.getTinNumber());
        // contractorMap.put(Constants.WORKS_KEY_BANK_CODE ,"");
        if (!org.apache.commons.lang.StringUtils.isBlank(license.getBankIfscCode())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getBankIfscCode()))
            contractorMap.put(Constants.WORKS_KEY_IFSC_CODE, license.getBankIfscCode());
        // contractorMap.put(Constants.WORKS_KEY_BANK_ACCOUNT,"");
        // contractorMap.put(Constants.WORKS_KEY_APPROVAL_CODE ,"");
        if (!org.apache.commons.lang.StringUtils.isBlank(license.getLicenseNumber())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getLicenseNumber()))
            contractorMap.put(Constants.WORKS_KEY_REG_NUM, license.getLicenseNumber());
        if (!org.apache.commons.lang.StringUtils.isBlank(status) && !org.apache.commons.lang.StringUtils.isEmpty(status))
            contractorMap.put(Constants.WORKS_KEY_STATUS, status);
        if (!org.apache.commons.lang.StringUtils.isBlank(license.getTradeName().getName())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getTradeName().getName())) {
            final ContractorGrade contr = getWorksEstimateDetailsForTradeName(license.getTradeName().getName());
            contractorMap.put(Constants.WORKS_KEY_CLASS, contr.getGrade());
        }
        if (!org.apache.commons.lang.StringUtils.isBlank(license.getDateOfCreation().toString())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getDateOfCreation().toString()))
            contractorMap.put(Constants.WORKS_KEY_STARTDATE, license.getDateOfCreation());
        if (!org.apache.commons.lang.StringUtils.isBlank(license.getDateOfExpiry().toString())
                && !org.apache.commons.lang.StringUtils.isEmpty(license.getDateOfExpiry().toString()))
            contractorMap.put(Constants.WORKS_KEY_END_DATE, license.getDateOfExpiry());
        return contractorMap;
    }

    /*
     * public void updateContractorForDepartment(final License license, final String updateType, final String status) throws
     * ValidationException { contractorDetailsMap = createContractorDetailsMap(license, status); // Set any of these values to
     * WORKS_KEY_CONTRACTOR_UPDATE_TYPE 'Renewal', 'Update', 'Upgrade', 'Inactive'
     * contractorDetailsMap.put(Constants.WORKS_KEY_CONTRACTOR_UPDATE_TYPE, updateType);
     * contractorService.updateContractorForDepartment(contractorDetailsMap); }
     */

    /*
     * public Boolean checkActiveContractorExistsForPwd(final String contractorCode) { Boolean contractorExist = false; final Long
     * contractorId = contractorService.getActiveContractorForDepartment(contractorCode, Constants.PWD_DEPT_CODE); if
     * (contractorId != null) contractorExist = true; return contractorExist; }
     */

    @SuppressWarnings("unchecked")
    public Boolean checkApprovedLicenseContractorExists(final String contractorCode, final String moduleName)
    {
        Boolean contractorExist = false;
        License license = null;
        final Query query = getSession()
                .createQuery(
                        "from org.egov.tl.domain.entity.License lic where lic.contractorCode is not null and lic.contractorCode=:contrCode and lic.tradeName.licenseType.module.moduleName =:moduleName");
        query.setString("contrCode", contractorCode);
        query.setString("moduleName", moduleName);
        final List licenseList = query.list();
        final Iterator itrLic = licenseList.iterator();
        while (itrLic.hasNext())
        {
            license = (License) itrLic.next();
            if (license.getState() != null) {
                final List<StateHistory> states = license.getState().getHistory();
                for (final StateHistory state : states)
                    if (state.getValue().contains(
                            Constants.WORKFLOW_STATE_TYPE_CREATENEWLICENSE + Constants.WORKFLOW_STATE_APPROVED)) {
                        contractorExist = true;
                        break;
                    }
            } else if (license.getOldLicenseNumber() != null && license.getStatus().getStatusCode().equals("ACT"))
                contractorExist = true;
            if (contractorExist == true)
                break;
        }
        return contractorExist;
    }

    /*
     * public void setContractorService(final ContractorService contractorService) { this.contractorService = contractorService; }
     */

    @SuppressWarnings("unchecked")
    public List<Installment> getEffectivetInstallmentsforModuleAndDate(final Date date, final Module module) {
        List<Installment> installments = null;
        final Query query = getSession().createQuery(
                "from Installment I where I.fromDate <= :dateToCompare and I.module=:module ");
        query.setEntity("module", module);
        query.setDate("dateToCompare", date);
        final ArrayList<Installment> list = (ArrayList<Installment>) query.list();
        if (list.size() > 0)
            installments = list;
        return installments;
    }

    @SuppressWarnings("unchecked")
    public Map<Integer, Integer> populateInstallmentYears(final Module module) {
        final Map<Integer, Integer> installmentYearMap = new TreeMap<Integer, Integer>();
        List<Installment> instList = null;
        Calendar cal = Calendar.getInstance();
        instList = getEffectivetInstallmentsforModuleAndDate(new Date(), module);
        final Iterator<Installment> iter = instList.iterator();
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/mm/dd");
        while (iter.hasNext()) {
            final Installment inst = iter.next();
            formatter.format(inst.getInstallmentYear());
            cal = formatter.getCalendar();
            installmentYearMap.put(inst.getId(), cal.get(Calendar.YEAR));
        }
        return sortMapByValues(installmentYearMap);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map sortMapByValues(final Map unsortedMap) {
        HashMap map = null;
        if (unsortedMap != null && !unsortedMap.isEmpty()) {
            map = new LinkedHashMap();
            final List unsortedMapKeys = new ArrayList(unsortedMap.keySet());
            final List unsortedMapValues = new ArrayList(unsortedMap.values());
            final TreeSet sortedSet = new TreeSet(unsortedMapValues);
            final Object[] sortedArray = sortedSet.toArray();
            final int size = sortedArray.length;
            for (int i = 0; i < size; i++)
                map.put(unsortedMapKeys.get(unsortedMapValues
                        .indexOf(sortedArray[i])), sortedArray[i]);
        }
        return map;
    }
}
