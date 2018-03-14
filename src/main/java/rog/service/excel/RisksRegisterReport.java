package rog.service.excel;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsxExporterBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import rog.domain.FilledRisks;
import rog.domain.GlossaryOfPurposes;
import rog.domain.RisksPurposes;
import rog.domain.SetOfSentPurposes;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

@Service
public class RisksRegisterReport {


    private static String UPLOADED_FOLDER = System.getProperty("java.io.tmpdir");
    private static final String EXTENSION = ".xlsx";
    private int counter = 1;

    public byte[] createReport(SetOfSentPurposes setOfSentPurposes) throws IOException, DRException {

        Long orderId = setOfSentPurposes.getOrders().getId();
        String fileName = UPLOADED_FOLDER + File.separator + orderId + EXTENSION;
        JasperXlsxExporterBuilder excelExporter = getJasperXlsxExporterBuilder(fileName);
        JasperReportBuilder report = report();

        report
        .setPageFormat(PageType.A2, PageOrientation.LANDSCAPE)
        .highlightDetailEvenRows()
        .summary(cmp.multiPageList()
            .add(cmp.subreport(createDataToReport(setOfSentPurposes))));

        report.toXlsx(excelExporter);
        return FileUtils.readFileToByteArray(new File(fileName));
    }

    private JasperReportBuilder createDataToReport(SetOfSentPurposes setOfSentPurposes) {
        VerticalListBuilder summary = cmp.verticalList();
        if(setOfSentPurposes.getRisksPurposes().size() != 0)
        summary.add(cmp.subreport(createRaportTitle(setOfSentPurposes)),
        		cmp.subreport(addDataToReportSetOfSentPurposes(setOfSentPurposes)));
        else
        	summary.add(cmp.text("Brak danych do wygenerowania raportu."));

        return report().summary(summary);
    }

    private JasperReportBuilder addDataToReportSetOfSentPurposes(SetOfSentPurposes setOfSentPurposes) {

        VerticalListBuilder summary = cmp.verticalList();

        addDataOfGlossariesOfPurposes(setOfSentPurposes, summary);

        return report().summary(summary);
    }

    private void addDataOfGlossariesOfPurposes(SetOfSentPurposes setOfSentPurposes, VerticalListBuilder summary){
        for (GlossaryOfPurposes glossaryOfPurposes : setOfSentPurposes.getGlossaryOfPurposes()) {
            if(counter != 1) {
                summary.add(cmp.text(" "));
            }
            summary.add(cmp.text("Cel: " + glossaryOfPurposes.getName()));
            summary.add(cmp.subreport(createRisksTitle()));
            addDataOfRisksPurposes(setOfSentPurposes, glossaryOfPurposes, summary);
        }
    }

    private void addDataOfRisksPurposes(SetOfSentPurposes setOfSentPurposes, GlossaryOfPurposes glossaryOfPurposes, VerticalListBuilder summary){
        for (RisksPurposes risksPurposes : setOfSentPurposes.getRisksPurposes()) {
            if(glossaryOfPurposes.getId().equals(risksPurposes.getGlossaryOfPurposes().getId())) {

                risksPurposes.getFilledRisks().forEach(filledRisks ->
                    addDataOfFilledRiskToVerticalListBuilder(filledRisks, summary));
            }
        }
    }
    private void addDataOfFilledRiskToVerticalListBuilder(FilledRisks filledRisks, VerticalListBuilder summary){
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	if(!filledRisks.getSaved())
    	summary.add(
            cmp.horizontalList().add(
                cmp.text(counter),
                cmp.text(filledRisks.getCreationDate().format(formatter)),
                cmp.text(filledRisks.getGlossaryOfRisks().getName()),
                cmp.text(filledRisks.getProbability()),
                cmp.text(filledRisks.getPowerOfInfluence()),
                cmp.text(filledRisks.getStrengthOfControlFunctionProbability()),
                cmp.text(filledRisks.getStrengthOfControlFunctionPowerOfInfluence()),
                cmp.text(getProbabilityLevel(filledRisks.getProbability(), filledRisks.getStrengthOfControlFunctionProbability()) * getPowerOfInfluenceLevel(filledRisks.getPowerOfInfluence(), filledRisks.getStrengthOfControlFunctionPowerOfInfluence()))
            ));
        counter++;
    }
    private Integer getProbabilityLevel(int probability, int strengthOfControlFunction) {
    	int result = probability - strengthOfControlFunction + 1;
    	if(result <= 0)
    		result = 1;
    	return result;
    }

    private Integer getPowerOfInfluenceLevel(int pow, int strengthOfPOW) {
    	int result = pow - strengthOfPOW + 1;
    	if(result <= 0)
    		result = 1;
    	return result;
    }

    private JasperReportBuilder createRaportTitle(SetOfSentPurposes setOfSentPurposes) {
    	return report().summary(
    					cmp.text(setOfSentPurposes.getUser().getOrganisationStructure().getName()),
    					cmp.text("Rejestr ryzyk")

    			);
    			
    }
    private JasperReportBuilder createRisksTitle() {
    	return report().summary(
    			cmp.horizontalList().add(
    					cmp.text("L.p."),
    					cmp.text("Data"),
    					cmp.text("Ryzyko"),
    					cmp.text("Prawdopodobieństwo"),
    					cmp.text("Siła oddziaływania"),
    					cmp.text("SMK P"),
    					cmp.text("SMK S"),
    					cmp.text("Poziom ryzyka"))

    			);
    }

    private JasperXlsxExporterBuilder getJasperXlsxExporterBuilder(String fileName){
        return export.xlsxExporter(fileName)
            .setDetectCellType(true)
            .setIgnorePageMargins(true)
            .setWhitePageBackground(false)
            .setRemoveEmptySpaceBetweenColumns(true)
            .setIgnorePageMargins(true)
            .setWrapText(true)
            .setOnePagePerSheet(false);
    }


}
