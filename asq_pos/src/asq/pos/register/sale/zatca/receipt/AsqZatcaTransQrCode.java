package asq.pos.register.sale.zatca.receipt;

import java.util.Locale;

import javax.inject.Inject;

import asq.pos.common.AsqValueKeys;
import dtv.docbuilding.AbstractDocBuilderField;
import dtv.docbuilding.IDocElementFactory;
import dtv.docbuilding.types.DocBuilderAlignmentType;
import dtv.i18n.formatter.output.IOutputFormatter;
import dtv.pos.framework.scope.TransactionScope;

public class AsqZatcaTransQrCode extends AbstractDocBuilderField {

	@Inject
	protected TransactionScope _transactionScope;

	public AsqZatcaTransQrCode(String argContents, String argStyle, Integer argLocation, DocBuilderAlignmentType argAlignment, int argPriority, IOutputFormatter argFormatter) {
		super(argContents, argStyle, argLocation, argAlignment, argPriority, argFormatter);
	}

	@Override
	public String getContents(Object paramObject, IDocElementFactory paramIDocElementFactory, Locale paramLocale) {
		_transactionScope.getTransaction();
		return _transactionScope.getValue(AsqValueKeys.ASQ_ZATCA_QR_CODE);
	}

}
