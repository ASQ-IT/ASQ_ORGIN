package asq.pos.store.close.receipt;

import java.util.Locale;

import javax.inject.Inject;

import asq.pos.employee.commission.AsqEmployeeCommissionCalculator;
import dtv.docbuilding.AbstractDocBuilderField;
import dtv.docbuilding.IDocElementFactory;
import dtv.docbuilding.types.DocBuilderAlignmentType;
import dtv.i18n.formatter.output.IOutputFormatter;

public class AsqEmployeeCommissionDocBuilderField extends AbstractDocBuilderField {

	@Inject
	AsqEmployeeCommissionCalculator asqCommissionCal;

	public AsqEmployeeCommissionDocBuilderField(String argContents, String argStyle, Integer argLocation, DocBuilderAlignmentType argAlignment, int argPriority, IOutputFormatter argFormatter) {
		super(argContents, argStyle, argLocation, argAlignment, argPriority, argFormatter);
	}

	@Override
	public String getContents(Object arg0, IDocElementFactory arg1, Locale arg2) {
		return asqCommissionCal.calculateNetSaleCommission();
	}

}
