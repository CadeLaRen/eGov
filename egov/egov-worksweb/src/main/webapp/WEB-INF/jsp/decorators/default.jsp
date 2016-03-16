<!-- -------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency,
#    accountability and the service delivery of the government  organizations.
# 
#     Copyright (C) <2015>  eGovernments Foundation
# 
#     The updated version of eGov suite of products as by eGovernments Foundation
#     is available at http://www.egovernments.org
# 
#     This program is free software: you can redistribute it and/or modify
#     it under the terms of the GNU General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     any later version.
# 
#     This program is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU General Public License for more details.
# 
#     You should have received a copy of the GNU General Public License
#     along with this program. If not, see http://www.gnu.org/licenses/ or
#     http://www.gnu.org/licenses/gpl.html .
# 
#     In addition to the terms of the GPL license to be adhered to in using this
#     program, the following additional terms are to be complied with:
# 
# 	1) All versions of this program, verbatim or modified must carry this
# 	   Legal Notice.
# 
# 	2) Any misrepresentation of the origin of the material is prohibited. It
# 	   is required that all modified versions of this material be marked in
# 	   reasonable ways as different from the original version.
# 
# 	3) This license does not grant any rights to any user of the program
# 	   with regards to rights under trademark law for use of the trade names
# 	   or trademarks of eGovernments Foundation.
# 
#   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
#------------------------------------------------------------------------------- -->
<%@ include file="/includes/taglibs.jsp" %>
<html>
<head>
 <%@ include file="/includes/meta.jsp" %>  
<title>eGov Works <decorator:title/></title>

<link href="<c:url value='/resources/css/works.css?${app_release_no}'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/backup/commonegovNew.css' context='/egi'/>" rel="stylesheet" type="text/css" />

<link href="<c:url value='/resources/global/css/bootstrap/bootstrap.css' context='/egi'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/resources/global/css/egov/custom.css' context='/egi'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/resources/global/css/egov/backup/header-custom.css' context='/egi'/>" rel="stylesheet" type="text/css" />

 
<link rel="stylesheet" type="text/css" href="/egi/commonyui/yui2.8/fonts/fonts-min.css"/>
<link rel="stylesheet" type="text/css" href="/egi/commonyui/yui2.8/datatable/assets/skins/sam/datatable.css"/>	
<link rel="stylesheet" type="text/css" href="/egi/commonyui/yui2.8/assets/skins/sam/autocomplete.css" />	
<link href="<c:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egi'/>" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<c:url value='/resources/global/css/font-icons/font-awesome-4.3.0/css/font-awesome.min.css' context='/egi'/>">

<style>
body
{
  font-size: 14px;
  font-family:regular;
}
</style>

<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/jquery.js' context='/egi'/>"> </script>

<script type="text/javascript" src="/egi/commonyui/yui2.8/yahoo-dom-event/yahoo-dom-event.js"></script> 
<script type="text/javascript" src="/egi/commonyui/yui2.8/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="/egi/commonyui/yui2.8/element/element-min.js"></script>
<script type="text/javascript" src="/egi/commonyui/yui2.8/connection/connection-min.js"></script>
<script type="text/javascript" src="/egi/commonyui/yui2.8/datasource/datasource-min.js"></script>
<script type="text/javascript" src="/egi/commonyui/yui2.8/datatable/datatable-min.js"></script>
<script type="text/javascript" src="/egi/commonyui/yui2.8/animation/animation-min.js"></script>
<script type="text/javascript" src="/egi/commonyui/yui2.8/container/container_core-min.js"></script>
<script type="text/javascript" src="/egi/commonyui/yui2.8/menu/menu-min.js"></script>
<script type="text/javascript" src="/egi/commonyui/yui2.8/button/button-min.js"></script>
<script type="text/javascript" src="/egi/commonyui/yui2.8/editor/editor-min.js"></script>
<script type="text/javascript" src="/egi/commonyui/yui2.8/autocomplete/autocomplete-min.js" ></script>

<script type="text/javascript" src="<egov:url path='/resources/js/helper.js'/>"></script>
<script type="text/javascript" src="<c:url value='/commonjs/calendar.js' context='/egi'/>" ></script>
<script type="text/javascript" src="<c:url value='/javascript/calender.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/commonjs/ajaxCommonFunctions.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/javascript/validations.js' context='/egi'/>"></script>
<%-- <script type="text/javascript" src="<c:url value='/resources/js/prototype.js'/>"></script> --%>

<%-- <script type="text/javascript" src="<c:url value='/resources/global/js/jquery-ui/jquery-ui.js' context='/egi'/>"> </script>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/global/js/jquery-ui/jquery-ui.css' context='/egi'/>" /> --%>
<script type="text/javascript" src="<c:url value='/resources/global/js/bootstrap/bootstrap.js' context='/egi'/>"></script>
<!-- <script type="text/javascript" src="/egworks/resources/js/ajax-script.js"></script> -->
<script	src="<c:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>" type="text/javascript"></script>
<script	src="<c:url value='/resources/global/js/jquery/plugins/jquery.inputmask.bundle.min.js' context='/egi'/>"></script>
<script type="text/javascript" >
window.document.onkeydown = function(event) { 
   	 switch (event.keyCode) { 
        case 116 : //F5 button
            event.returnValue = false;
            event.keyCode = 0;
            return false; 
        case 82 : //R button
            if (event.ctrlKey) { //Ctrl button
                event.returnValue = false; 
                event.keyCode = 0;  
                return false; 
            } 
    }
}
</script>
 <decorator:head/>
</head>
<body <decorator:getProperty property="body.id" writeEntireProperty="yes"/><decorator:getProperty property="body.class" writeEntireProperty="true"/> <decorator:getProperty property="body.onload" writeEntireProperty="true"/>  >
	  <div class="page-container">
		    <!-- header -->
		    <egov:breadcrumb/>
		    
		    <!-- pagecontent -->
		    <div class="main-content">
		       <decorator:body/>
		    </div>
		    
		    <!-- footer -->
		    <footer class="main">
			    Powered by <a href="http://egovernments.org/" target="_blank">eGovernments Foundation</a>
			</footer>
	  </div>
	 
	 <!-- loading indicator --> 
	 <div class="modal fade loader-class" data-backdrop="static">
			<div class="modal-dialog">
					<div class="modal-body">
						<div class="row spinner-margin text-center">
							<div class="col-md-12 ">
								<div class="spinner">
									<div class="rect1"></div>
									<div class="rect2"></div>
									<div class="rect3"></div>
									<div class="rect4"></div>
									<div class="rect5"></div>
								</div>
							</div>
							
							<div class="col-md-12 spinner-text">
								Processing your request. Please wait..
							</div>
						</div>
					</div>
			</div>
	 </div>
	  
	  <script>

	    // jQuery plugin to prevent double submission of forms
	    var jQuery=jQuery.noConflict(true);
		jQuery.fn.preventDoubleSubmission = function() {
		jQuery(this).on('submit',function(e){
		    var $form = jQuery(this);
		    if ($form.data('submitted') === true) {
		      // Previously submitted - don't submit again
		      e.preventDefault();
		    } else {
		      // Mark it so that the next submit can be ignored
		      $form.data('submitted', true);
		    }
		  });
		  // Keep chainability
		  return this;
		};

		jQuery("form").submit(function( event ) {
			jQuery('.loader-class').modal('show', {backdrop: 'static'});
		});
		
		jQuery('form').preventDoubleSubmission();

		function disableRefresh(e) {
            if ((e.which || e.keyCode) == 116)
                    e.preventDefault();
            if (e.ctrlKey)
                    if ((e.which || e.keyCode) == 82)
                            e.preventDefault();
    	};
    	jQuery(document).on("keydown", disableRefresh);

    	window.location.hash = "no-back-button";
        window.location.hash = "Again-No-back-button";//again because google chrome does not insert first hash into history
        window.onhashchange = function() {
            window.location.hash = "no-back-button";
        }


    	jQuery(".datepicker").datepicker({
    		format : "dd/mm/yyyy",
    		autoclose: true
    	});

    	try { jQuery(":input").inputmask(); }catch(e){}
    	
	  </script>
	  
	   
    </body>
  </html>

<%-- <body <decorator:getProperty property="body.id" writeEntireProperty="yes"/><decorator:getProperty property="body.class" writeEntireProperty="true"/> <decorator:getProperty property="body.onload" writeEntireProperty="true"/>  >
	    <div id="loadingMask" style="display:none;overflow:none;scroll:none;" ><img src="/egi/resources/erp2/images/bar_loader.gif"> <span id="message">Please wait....</span></div>
	    <div id="BreadCrumb">
	    	<egov:breadcrumb/>
	    </div>
	    <div class="topbar">
			<div style="margin-top:10px"><decorator:title/> </div>
		</div>
	    <decorator:body/>
	    <div class="urlwk"><div align>Works Management System Designed and Implemented by <a href="http://www.egovernments.org/">eGovernments Foundation</a> All Rights Reserved </div></div>
    </body>
</html> --%>