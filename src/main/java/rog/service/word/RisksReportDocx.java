package rog.service.word;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperDocxExporterBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import rog.domain.User;

@Service
public class RisksReportDocx {

	private static String UPLOADED_FOLDER = System.getProperty("java.io.tmpdir");
    private static final String EXTENSION = ".xlsx";
    
	public byte[] createDocReport(Long orderId, ArrayList<User> userList) throws IOException, DRException {

//      Long orderId = setOfSentPurposes.getOrders().getId();
      String fileName = UPLOADED_FOLDER + File.separator + 1 + EXTENSION;
      JasperDocxExporterBuilder docxExporterBuilder = getJasperDocxExporterBuilder(fileName);
      JasperReportBuilder report = report();

      report
      .setPageFormat(PageType.A4, PageOrientation.PORTRAIT)
      .highlightDetailEvenRows()
      .summary(cmp.multiPageList()
      		.add(cmp.subreport(createDocxReportData(orderId, userList)))
          )
      .pageFooter(cmp.pageNumber());

      report.toDocx(docxExporterBuilder);
      return FileUtils.readFileToByteArray(new java.io.File(fileName));
  }
	
    private JasperDocxExporterBuilder getJasperDocxExporterBuilder(String fileName){
		return export.docxExporter(fileName);

    }
    
    public JasperReportBuilder createDocxReportData(Long orderId, ArrayList<User> userList) {
    	VerticalListBuilder summary = cmp.verticalList();
        addBiggining(summary);
        
		return report().summary(summary);
    	
    }
    
    private JasperReportBuilder addBiggining(VerticalListBuilder summary) {
    			
    	
    	return report()
    			.summary(summary);
    }
}
