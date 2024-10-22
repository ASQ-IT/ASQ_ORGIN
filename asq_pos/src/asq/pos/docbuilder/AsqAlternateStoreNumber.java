package asq.pos.docbuilder;

import java.util.Locale;

import javax.inject.Inject;

import org.python.netty.util.internal.StringUtil;

import dtv.docbuilding.AbstractDocBuilderField;
import dtv.docbuilding.IDocElementFactory;
import dtv.docbuilding.types.DocBuilderAlignmentType;
import dtv.i18n.formatter.output.IOutputFormatter;
import dtv.pos.iframework.ILocationFactory;
import dtv.xst.dao.loc.IRetailLocation;
import dtv.xst.dao.trn.IPosTransaction;

public class AsqAlternateStoreNumber extends AbstractDocBuilderField {

	@Inject
	private ILocationFactory _locFactory;

	public AsqAlternateStoreNumber(String argContents, String argStyle, Integer argLocation, DocBuilderAlignmentType argAlignment, int argPriority, IOutputFormatter argFormatter) {
		super(argContents, argStyle, argLocation, argAlignment, argPriority, argFormatter);
	}

	@Override
	public String getContents(Object var1, IDocElementFactory var2, Locale var3) {
		IPosTransaction tran = (IPosTransaction) var1;
		IRetailLocation retailLocation = this._locFactory.getStoreById(tran.getRetailLocationId());
		if (retailLocation.getAlternateStoreNbr() != null && !retailLocation.getAlternateStoreNbr().trim().isEmpty()) {
			return this.getFormatter().format(retailLocation.getAlternateStoreNbr(), var3);
		} else {
			return this.getFormatter().format(StringUtil.EMPTY_STRING, var3);
		}
	}
}
