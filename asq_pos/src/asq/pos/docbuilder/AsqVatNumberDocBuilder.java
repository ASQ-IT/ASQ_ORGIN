package asq.pos.docbuilder;

import java.util.Locale;

import dtv.docbuilding.AbstractDocBuilderField;
import dtv.docbuilding.IDocElementFactory;
import dtv.docbuilding.types.DocBuilderAlignmentType;
import dtv.i18n.formatter.output.IOutputFormatter;

public class AsqVatNumberDocBuilder extends AbstractDocBuilderField {

	public AsqVatNumberDocBuilder(String argContents, String argStyle, Integer argLocation,
			DocBuilderAlignmentType argAlignment, int argPriority, IOutputFormatter argFormatter) {
		super(argContents, argStyle, argLocation, argAlignment, argPriority, argFormatter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getContents(Object arg0, IDocElementFactory arg1, Locale arg2) {
		// TODO Auto-generated method stub
		String csidCertificateFilePath = System.getProperty("asq.zatca.company.vat.reg.number");
		return csidCertificateFilePath;
	}

}
