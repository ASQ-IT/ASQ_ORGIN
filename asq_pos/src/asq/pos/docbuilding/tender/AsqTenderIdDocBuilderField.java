package asq.pos.docbuilding.tender;

import java.util.Locale;

import javax.inject.Inject;

import dtv.docbuilding.AbstractDocBuilderField;
import dtv.docbuilding.IDocElementFactory;
import dtv.docbuilding.types.DocBuilderAlignmentType;
import dtv.i18n.formatter.output.IOutputFormatter;
import dtv.pos.framework.tender.ITenderHelper;
import dtv.xst.cleandto.tnd.Tender;
import dtv.xst.dao.ttr.ITenderLineItem;

/**
 * @author SA20547171
 *
 */
public class AsqTenderIdDocBuilderField extends AbstractDocBuilderField {

	@Inject
	private ITenderHelper _tenderHelper;

	public AsqTenderIdDocBuilderField(String argContents, String argStyle, Integer argLocation, DocBuilderAlignmentType argAlignment, int argPriority, IOutputFormatter argFormatter) {
		super(argContents, argStyle, argLocation, argAlignment, argPriority, argFormatter);
	}

	@Override
	public String getContents(Object argSource, IDocElementFactory argFactory, Locale argLocale) {
		String tenderId = "";
		if (argSource instanceof ITenderLineItem) {
			ITenderLineItem tenderLine = (ITenderLineItem) argSource;
			Tender tender = this._tenderHelper.getTenderByLine(tenderLine, tenderLine.getWorkstationId(), "");
			if (tender != null && tender.getTenderTypecode().equalsIgnoreCase("CURRENCY")) {
				tenderId = "Cash";
			} else if (tender != null) {
				tenderId = tender.getTenderId();
			}
		}

		return tenderId;
	}
}
