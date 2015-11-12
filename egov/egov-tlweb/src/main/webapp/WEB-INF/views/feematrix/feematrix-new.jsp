<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<script src="<c:url value='/resources/app/js/feematrix.js' context='/tl'/>"></script>
<script src="<c:url value='/resources/js/helper.js' context='/tl'/>"></script>
<div class="row">
    <div class="col-md-12">
      <div class="panel panel-primary" data-collapsed="0"> 
        <div class="panel-heading">
          <div class="panel-title">FeeMatrix</div>
        </div>
        <div class="panel-body">
          <form:form role="form" action="feematrix/create" modelAttribute="feeMatrix" id="feematrix-new" name="feematrix-new"
            cssClass="form-horizontal form-groups-bordered" enctype="multipart/form-data">
            <div class="form-group">
              <label class="col-sm-3 control-label text-right"><spring:message code="lbl.natureofbusiness" /> <span
                class="mandatory"></span> </label>
              <div class="col-sm-3 add-margin">
                <form:select path="natureOfBusiness" id="natureOfBusiness" cssClass="form-control"   required="required"
                  cssErrorClass="form-control error" >
                  <form:option value="">
                    <spring:message code="lbl.select" />
                  </form:option>
                  <form:options items="${natureOfBusinesss}" itemValue="id" itemLabel="name" />
                </form:select>
              </div>
               <label class="col-sm-3 control-label text-right"><spring:message code="lbl.licenseapptype" /> <span
	              class="mandatory"></span> </label>
	            <div class="col-sm-3 add-margin">
	              <form:select path="licenseAppType" id="licenseAppType" cssClass="form-control"   required="required"
	                cssErrorClass="form-control error">
	                <form:option value="">
	                  <spring:message code="lbl.select" />
	                </form:option>
	                <form:options items="${licenseAppTypes}" itemValue="id" itemLabel="name" />
	              </form:select>
	            </div>
            </div>
            
             <div class="form-group">
	            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.feetype" /> <span
	                class="mandatory"></span> </label>
	              <div class="col-sm-3 add-margin">
	                <form:select path="feeType" id="feeType" cssClass="form-control"   required="required"
	                  cssErrorClass="form-control error">
	                  <form:option value="">
	                    <spring:message code="lbl.select" />
	                  </form:option>
	                  <form:options items="${feeTypes}" itemValue="id" itemLabel="name" />
	                </form:select>
	              </div>
	              <label class="col-sm-3 control-label text-right"><spring:message code="lbl.financialyear" /> <span
	                class="mandatory"></span> </label>
	                <div class="col-sm-3 add-margin">
	                <form:select path="financialYear" id="financialYear" cssClass="form-control"   required="required"
	                  cssErrorClass="form-control error">
	                  <form:option value="">
	                    <spring:message code="lbl.select" />
	                  </form:option>
	                  <form:options items="${financialYears}" itemValue="id" itemLabel="finYearRange" />
	                </form:select>
	              </div>
            </div>
            
            <div class="form-group">
            	<label class="col-sm-3 control-label text-right"><spring:message code="lbl.licensecategory" /> <span
	              class="mandatory"></span> </label>
	            <div class="col-sm-3 add-margin">
	              <form:select path="licenseCategory" id="licenseCategory" cssClass="form-control"   required="required"
	                cssErrorClass="form-control error">
	                <form:option value="">
	                  <spring:message code="lbl.select" />
	                </form:option>
	                <form:options items="${licenseCategorys}" itemValue="id" itemLabel="name" />
	              </form:select>
	            </div>
	            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.subcategory" /> <span
	                class="mandatory"></span> </label>
	              <div class="col-sm-3 add-margin">
	                <form:select path="subCategory" id="subCategory" cssClass="form-control"   required="required"
	                  cssErrorClass="form-control error">
	                  <form:option value="">
	                    <spring:message code="lbl.select" />
	                  </form:option>
	                  <form:options items="${subCategorys}" itemValue="id" itemLabel="name" />
	                </form:select>
	              </div>
            </div>
            
            <div class="form-group">
              <label class="col-sm-3 control-label text-right"><spring:message code="lbl.unitofmeasurement" /> <span
	              class="mandatory"></span> </label>
	            <div class="col-sm-3 add-margin">
	              <form:select path="unitOfMeasurement" id="unitOfMeasurement" cssClass="form-control"   required="required"
	                cssErrorClass="form-control error">
	                <form:option value="">
	                  <spring:message code="lbl.select" />
	                </form:option>
	                <form:options items="${unitOfMeasurements}" itemValue="id" itemLabel="name" />
	              </form:select>
	            </div>
            </div>
            
            <div class="form-group text-center">
                   <button type="button" class="btn btn-primary" id="search">Submit</button>
                  <button type="button" class="btn btn-default" data-dismiss="modal" onclick="window.close();"> Close</button>
            </div>
            <c:if test="${hideTemporary}">
            <script>
            $("#natureOfBusiness option:contains('Permanent')").attr('selected', 'selected');
            $('#natureOfBusiness').attr("disabled", true); 
            </script>
            </c:if>
            
            <c:if test="${hideRenew}">
            <script>
            $("#licenseAppType option:contains('New')").attr('selected', 'selected');
            $('#licenseAppType').attr("disabled", true); 
            </script>
            </c:if>
            <div id="resultdiv">
            </div>
          </form:form>
        </div>
      </div>
    </div>
</div>

<script>

function checkValue(obj){
	var rowobj=getRow(obj);
	var tbl = document.getElementById('result');
	var uomToval=getControlInBranch(tbl.rows[rowobj.rowIndex],'uomTo').value;
	var uomFromval=getControlInBranch(tbl.rows[rowobj.rowIndex],'uomFrom').value;
	if(uomFromval!='' && uomToval!='' && (eval(uomFromval)>eval(uomToval))){
		alert("\"UOM To\" should be greater than \"UOM From\".");
		getControlInBranch(tbl.rows[rowobj.rowIndex],'uomTo').value="";
		return false;
	} 
  	var lastRow = (tbl.rows.length)-1;
    var curRow=rowobj.rowIndex; 
    if(curRow!=lastRow){
		var uomFromVal1=getControlInBranch(tbl.rows[rowobj.rowIndex+1],'uomFrom').value;
		if(uomToval!=uomFromVal1)
			getControlInBranch(tbl.rows[rowobj.rowIndex+1],'uomFrom').value=uomToval; 
    }
}

function checkforNonEmptyPrevRow(){
	var tbl=document.getElementById("result");
    var lastRow = (tbl.rows.length)-1;
    var uomFromval=getControlInBranch(tbl.rows[lastRow],'uomFrom').value;
    var uomToval=getControlInBranch(tbl.rows[lastRow],'uomTo').value;
    var amountVal=getControlInBranch(tbl.rows[lastRow],'amount').value;
    if(uomFromval=='' || uomToval=='' || amountVal==''){
    	alert("Enter all values for existing rows before adding.");
		return false;       
    } 
    return true;
}  

function getPrevUOMFromData(){
	var tbl=document.getElementById("result");
    var lastRow = (tbl.rows.length)-1;
    return getControlInBranch(tbl.rows[lastRow],'uomTo').value;
}

function intiUOMFromData(obj){
	var tbl=document.getElementById("result");
    var lastRow = (tbl.rows.length)-1;
    getControlInBranch(tbl.rows[lastRow],'uomFrom').value=obj;
} 

function deleteThisRow(obj){
	var tbl=document.getElementById("result");
    var lastRow = (tbl.rows.length)-1;
    var curRow=getRow(obj).rowIndex; 
    var counts = lastRow - 1;
    if(curRow == 1)	{
 		 alert('Cannot delete first row');
  	     return false;
    } else if(curRow != lastRow){
    	alert('Cannot delete in between. Delete from last.');
 	    return false;
    } else	{
        if(getControlInBranch(tbl.rows[lastRow],'detailId').value==''){
	  	  	tbl.deleteRow(curRow);
			return true;
	    } else if(getControlInBranch(tbl.rows[lastRow],'detailId').value!=''){
	    	var r = confirm("This will delete the row permanently. Press OK to Continue. ");
			if (r != true) {
				return false;
			} else{
					$.ajax({
						url: "/tl/domain/commonAjax-deleteFee.action?feeMatrixDetailId="+getControlInBranch(tbl.rows[lastRow],'detailId').value+"",
						type: "GET",
						dataType: "json",
						success: function (response) {
							tbl.deleteRow(curRow);
						}, 
						error: function (response) {
							alert("Unable to delete this row.");
							console.log("failed");
						}
					});
			}
		}
  	}
}

function validateDetailsBeforeSubmit(){
	var tbl=document.getElementById("result");
    var tabLength = (tbl.rows.length)-1;
    var uomFromval,uomToval;
    for(var i=1;i<=tabLength;i++){
    	uomFromval=getControlInBranch(tbl.rows[i],'uomFrom').value;
    	uomToval=getControlInBranch(tbl.rows[i],'uomTo').value;
    	if(uomFromval!='' && uomToval!='' && (eval(uomFromval)>eval(uomToval))){
    		alert("\"UOM To\" should be greater than \"UOM From\" for row "+(i)+".");
    		getControlInBranch(tbl.rows[i],'uomTo').value="";
    		getControlInBranch(tbl.rows[i],'uomTo').focus();
    		return false;
    	}  
    }
    return true;
}

</script>