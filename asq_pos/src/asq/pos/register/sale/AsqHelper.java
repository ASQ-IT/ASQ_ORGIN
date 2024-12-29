package asq.pos.register.sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.python.netty.util.internal.StringUtil;

import asq.pos.common.AsqConstant;
import asq.pos.employee.commission.AsqEmpCommissionQueryResult;
import dtv.data2.access.DataFactory;
import dtv.data2.access.IQueryKey;
import dtv.data2.access.QueryKey;
import dtv.pos.common.ConfigurationMgr;
import dtv.pos.iframework.validation.IValidationResult;
import dtv.pos.iframework.validation.SimpleValidationResult;
import dtv.util.DtvDate;

public class AsqHelper {

	private static final IQueryKey<AsqRecievingQueryResult> ASQ_IN_PROGRESS_RECEIVING = new QueryKey<AsqRecievingQueryResult>(AsqConstant.ASQ_RECEIVING_REG_CLOSE_QRY, AsqRecievingQueryResult.class);

	private static final IQueryKey<AsqEmpCommissionQueryResult> ASQ_GET_EMPLOYEE_COMM_NETSALE = new QueryKey<AsqEmpCommissionQueryResult>(AsqConstant.ASQ_GET_EMPLOYEE_COMM_NETSALE_QRY, AsqEmpCommissionQueryResult.class);

	public IValidationResult isReceivingInProgress() {
		Map<String, Object> asqRecievingInProgress = new HashMap<String, Object>();
		List<AsqRecievingQueryResult> results = DataFactory.getObjectByQueryNoThrow(ASQ_IN_PROGRESS_RECEIVING, asqRecievingInProgress);
		if (null != results && !results.isEmpty()) {
			StringBuilder shipingDoc = new StringBuilder();
			StringBuilder shipingOty = new StringBuilder();
			for (int i = 0; i < results.size(); i++) {
				AsqRecievingQueryResult result = results.get(i);
				shipingDoc = shipingDoc.append(result.getINVCTL_DOCUMENT_ID());
				shipingOty = shipingOty.append(result.getEXPECTED_COUNT().subtract(result.getPOSTED_COUNT()));
				if (i + 1 != results.size()) {
					shipingDoc.append(StringUtil.COMMA);
					shipingOty.append(StringUtil.COMMA);
				}
			}
			return SimpleValidationResult.getFailed(AsqConstant.ASQ_RECEIVING_REG_CLOSE_MSG, shipingDoc.toString(), shipingOty.toString());
		}
		return IValidationResult.SUCCESS;
	}

	public List<AsqEmpCommissionQueryResult> getEmployeeCommissionOnNetSale(int locationId, DtvDate bussinessDate) {
		Map<String, Object> asqEmpCommission = new HashMap<String, Object>();
		asqEmpCommission.put("argOrganizationId", ConfigurationMgr.getOrganizationId());
		asqEmpCommission.put("argRetailLocId", Integer.valueOf(locationId));
		asqEmpCommission.put("argOrgbusinessDate", bussinessDate);

		return DataFactory.getObjectByQueryNoThrow(ASQ_GET_EMPLOYEE_COMM_NETSALE, asqEmpCommission);
	}

}
