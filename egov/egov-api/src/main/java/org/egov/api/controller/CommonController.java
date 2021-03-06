/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.api.controller;

import org.apache.log4j.Logger;
import org.egov.api.adapter.UserAdapter;
import org.egov.api.controller.core.ApiController;
import org.egov.api.controller.core.ApiResponse;
import org.egov.api.controller.core.ApiUrl;
import org.egov.infra.admin.common.service.IdentityRecoveryService;
import org.egov.infra.admin.master.entity.Device;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.repository.DeviceRepository;
import org.egov.infra.admin.master.service.UserService;
import org.egov.portal.entity.Citizen;
import org.egov.portal.service.CitizenService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sheik
 *
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/v1.0")
public class CommonController extends ApiController {
	
	private static final Logger LOGGER = Logger.getLogger(CommonController.class);

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private IdentityRecoveryService identityRecoveryService;
    
    @Autowired
    private LocalValidatorFactoryBean validator;
    
    @Autowired
    private UserService userservice;

    // -----------------------------------------------------------------
    /**
     * This will create a new citizen along with it will capture their device also.
     * 
     * @param Citizen - As a json object
     * @return Citizen
     */
    @RequestMapping(value = ApiUrl.CITIZEN_REGISTER, method = RequestMethod.POST, consumes = { "application/json" })
    public @ResponseBody ResponseEntity<String> register(@RequestBody JSONObject citizen) {
        ApiResponse res = ApiResponse.newInstance();
        try {
            Citizen citizenCreate = new Citizen();
            citizenCreate.setUsername(citizen.get("mobileNumber").toString());
            citizenCreate.setMobileNumber(citizen.get("mobileNumber").toString());
            citizenCreate.setName(citizen.get("name").toString());
            citizenCreate.setEmailId(citizen.get("emailId").toString());
            citizenCreate.setPassword(citizen.get("password").toString());
            Device device = deviceRepository.findByDeviceUId(citizen.get("deviceId").toString());
            if (device == null) {
                device = new Device();
                device.setDeviceId(citizen.get("deviceId").toString());
                device.setType(citizen.get("deviceType").toString());
                device.setOSVersion(citizen.get("OSVersion").toString());
            }
            
            User getUser=userservice.getUserByMobileNumber(citizenCreate.getMobileNumber());
            if(getUser != null)
            {
            	return res.error(getMessage("user.register.duplicate.mobileno"));
            }
            
            if(citizenCreate.getEmailId() != null && ! citizenCreate.getEmailId().isEmpty())
            {
            	getUser=userservice.getUserByEmailId(citizenCreate.getEmailId());
                if(getUser != null)
                {
                	return res.error(getMessage("user.register.duplicate.email"));
                }
            }
            
           citizenCreate.getDevices().add(device);
           citizenService.create(citizenCreate);
           return res.setDataAdapter(new UserAdapter()).success(citizenCreate, this.getMessage("msg.citizen.reg.success"));
            
        } catch (Exception e) {
        	LOGGER.error("EGOV-API ERROR ",e);
        	return res.error(getMessage("server.error"));
        }
    }

    // --------------------------------------------------------------------------------//
    /**
     * This will activate the user account.
     * 
     * @param String userName
     * @param String activationCode
     * @return
     */
    @RequestMapping(value = ApiUrl.CITIZEN_ACTIVATE, method = RequestMethod.POST)
    public ResponseEntity<String> activate(@RequestParam("userName") String userName,
            @RequestParam("activationCode") String activationCode) {
    	ApiResponse res = ApiResponse.newInstance();
    	try
    	{
	        Citizen citizen = citizenService.getCitizenByUserName(userName);
	        if (citizen == null) {
	            citizen = citizenService.getCitizenByEmailId(userName);
	        }
	
	        if (citizen == null) {
	            return res.error(getMessage("citizen.not.found"));
	        } else if (activationCode == null) {
	            return res.error(getMessage("citizen.valid.activationCode"));
	        } else if (citizen.isActive()) {
	            return res.success("", getMessage("citizen.activated"));
	        } else if (citizen.getActivationCode().equals(activationCode)) {
	            citizen.setActive(true);
	            citizenService.update(citizen);
	            return res.success("", getMessage("citizen.success.activated"));
	        } else {
	            return res.error(getMessage("citizen.valid.activationCode"));
	        }
    	} catch (Exception e) {
        	LOGGER.error("EGOV-API ERROR ",e);
        	return res.error(getMessage("server.error"));
        }
    }

    // --------------------------------------------------------------------------------//
    /**
     * This will send an email/sms to citizen with link. User can use that link and reset their password.
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = ApiUrl.CITIZEN_PASSWORD_RECOVER, method = RequestMethod.POST)
    public ResponseEntity<String> passwordRecover(HttpServletRequest request) {
        ApiResponse res = ApiResponse.newInstance();
        try
    	{
	        String identity = request.getParameter("identity");
	        String redirectURL = request.getParameter("redirectURL");
	
	        if (identity == null || !identity.matches("\\d{10}")) {
	            return res.error("Invalid mobile number");
	        } 
	
	        Citizen citizen = citizenService.getCitizenByUserName(identity);
	        if (citizen == null) {
	            return res.error(getMessage("user.not.found"));
	        }
	       
	        if (identityRecoveryService.generateAndSendUserPasswordRecovery(
	                identity, redirectURL + "/egi/login/password/reset?token=")) {
	            return res.success("", "Password has been sent to mail");
	        }
	
	        return res.error("Password send failed");
    	}
        catch (Exception e) {
        	LOGGER.error("EGOV-API ERROR ",e);
        	return res.error(getMessage("server.error"));
        }

    }

    // -----------------------------------------------------------------
    /**
     * This will send OTP to the user
     * 
     * @param request
     * @return Citizen
     */
    @RequestMapping(value = ApiUrl.CITIZEN_SEND_OTP, method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> sendOTP(HttpServletRequest request) {
        ApiResponse res = ApiResponse.newInstance();
        String identity = request.getParameter("identity");
        String msg = "";
        Citizen citizen = null;
        try {
            if (identity.matches("\\d{10}")) {
                citizen = citizenService.getCitizenByUserName(identity);
            } else if (identity.contains("@") && identity.contains(".")) {
                citizen = citizenService.getCitizenByEmailId(identity);
            }
            if (citizen == null) {
                return res.error(getMessage("user.not.found"));
            }
            citizenService.sendActivationMessage(citizen);
            return res.setDataAdapter(new UserAdapter()).success(citizen, this.getMessage("sendOTP.success"));
        } catch (Exception e) {
        	LOGGER.error("EGOV-API ERROR ",e);
        	return res.error(getMessage("server.error"));
        }
    }

}
