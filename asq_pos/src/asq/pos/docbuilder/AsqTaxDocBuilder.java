package asq.pos.docbuilder;

import java.util.Locale;

import dtv.docbuilding.AbstractDocBuilderField;
import dtv.docbuilding.IDocElementFactory;
import dtv.docbuilding.types.DocBuilderAlignmentType;
import dtv.i18n.formatter.output.IOutputFormatter;
import dtv.xst.dao.trl.ISaleReturnLineItem;

public class AsqTaxDocBuilder  extends AbstractDocBuilderField{

	public AsqTaxDocBuilder(String argContents, String argStyle, Integer argLocation,
			DocBuilderAlignmentType argAlignment, int argPriority, IOutputFormatter argFormatter) {
		super(argContents, argStyle, argLocation, argAlignment, argPriority, argFormatter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getContents(Object arg0, IDocElementFactory arg1, Locale arg2) {
		if (arg0 instanceof ISaleReturnLineItem)
		{
			ISaleReturnLineItem sale=(ISaleReturnLineItem)arg0;
			if(sale.getVatAmount() != null) {
		    return getFormatter().format(sale.getVatAmount(), null);
			}
		}
		return null;
	}

}
