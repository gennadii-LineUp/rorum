package rog.service.excel;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsxExporterBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.*;
import rog.domain.enumeration.PurposeAccomplishmentStatus;
import rog.domain.enumeration.ReactionOnRisks;
import rog.service.FilledMeasureUnitsService;
import rog.service.FilledRisksService;
import rog.service.GlossaryOfMeasureUnitsService;
import rog.service.GlossaryOfRisksService;
import rog.service.statistics.RiskReportRangesCount;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;


@Service
public class PurposeAccomplishmentReport {

    @Autowired
    private RisksReport risksReport;

    @Autowired
    private GlossaryOfMeasureUnitsService glossaryOfMeasureUnitsService;

    @Autowired
    private FilledMeasureUnitsService filledMeasureUnitsService;

    @Autowired
    private GlossaryOfRisksService glossaryOfRisksService;

    @Autowired
    private FilledRisksService filledRisksService;

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

    private JasperReportBuilder createDataToReport(SetOfSentPurposes setOfSentPurposes) {
        VerticalListBuilder summary = cmp.verticalList();

        summary.add(cmp.subreport(createRaportTitle(setOfSentPurposes)));
        addLastRisksAndMeasures(setOfSentPurposes, summary);

        return report().summary(summary);
    }

    private JasperReportBuilder addDataTitle() {
        VerticalListBuilder summary = cmp.verticalList();
        summary.add(
                cmp.horizontalList().add(
                        cmp.text("L.p.").setStyle(Style.boldStyle),
                        cmp.text("Cel / zadanie").setWidth(Style.purposesWidth).setStyle(Style.boldStyle),
                        cmp.text("Rodzaj celu (zadaniowy, pozostały)").setWidth(Style.standardWidth).setStyle(Style.boldStyle),
                        cmp.text("Symbol procesu z SZJ").setWidth(Style.symbolProcesuZSZJ).setStyle(Style.boldStyle),
                        cmp.text("Miernik realizacji celu").setWidth(Style.measureUnitsWidth).setStyle(Style.boldStyle),
                        cmp.text("Wykonany poziom miernika").setWidth(Style.standardWidth).setStyle(Style.boldStyle),
                        cmp.text("Opis ryzyka").setWidth(Style.riskWidth).setStyle(Style.boldStyle),
                        cmp.text("Mechanizmy kontrolne").setWidth(Style.standardWidth).setStyle(Style.boldStyle),
                        cmp.text("Właściciel ryzyka").setWidth(Style.standardWidth).setStyle(Style.boldStyle),
                        cmp.text("Prawdopodobieństwo wystąpienia").setWidth(Style.standardWidth).setStyle(Style.boldStyle),
                        cmp.text("Siła oddziaływania ryzyka").setStyle(Style.boldStyle),
                        cmp.text("Siła mechanizmów kontrolnych - Prawdopodobieństwo").setStyle(Style.boldStyle),
                        cmp.text("Siła mechanizmów kontrolnych - Siła oddziaływania ryzyka").setStyle(Style.boldStyle),
                        cmp.text("Poziom prawdopodobieństwa").setStyle(Style.boldStyle),
                        cmp.text("Poziom siły oddziaływania").setStyle(Style.boldStyle),
                        cmp.text("Poziom ryzyka").setStyle(Style.boldStyle),
                        cmp.text("Reakcja na ryzyko").setStyle(Style.boldStyle).setWidth(Style.reactionOnRisk),
                        cmp.text("Opis reakcji na ryzko").setStyle(Style.boldStyle)
                ));
        return report().summary(summary);
    }


    private void addDataOfGlossariesOfPurposes(SetOfSentPurposes setOfSentPurposes, VerticalListBuilder summary){
    	summary.add(cmp.text(setOfSentPurposes.getUser().getOrganisationStructure().getName()));

    	for (GlossaryOfPurposes glossaryOfPurposes : setOfSentPurposes.getGlossaryOfPurposes()) {
            if(counter != 1) {
                summary.add(cmp.text(" "));
            }
            summary.add(cmp.subreport(addDataTitle()));

            addDataOfMeasureUnitsPurposes(setOfSentPurposes, glossaryOfPurposes, summary);
            addDataOfRisksPurposes(setOfSentPurposes, glossaryOfPurposes, summary);
        }
    }

    private void addDataOfMeasureUnitsPurposes(SetOfSentPurposes setOfSentPurposes, GlossaryOfPurposes glossaryOfPurposes, VerticalListBuilder summary){
        for (MeasureUnitsPurposes measureUnitsPurposes : setOfSentPurposes.getMeasureUnitsPurposes()) {
            if(glossaryOfPurposes.getId().equals(measureUnitsPurposes.getGlossaryOfPurposes().getId())) {
                String glossaryOfPurposeName = measureUnitsPurposes.getGlossaryOfPurposes().getName();

                measureUnitsPurposes.getFilledMeasureUnits().forEach(filledMeasureUnits ->
                    addDataOfFilledMeasureUnitsToVerticalListBuilder(measureUnitsPurposes, filledMeasureUnits, glossaryOfPurposeName, summary));
            }
        }
    }


    private void addDataOfRisksPurposes(SetOfSentPurposes setOfSentPurposes, GlossaryOfPurposes glossaryOfPurposes, VerticalListBuilder summary){
        for (RisksPurposes risksPurposes : setOfSentPurposes.getRisksPurposes()) {
            if(glossaryOfPurposes.getId().equals(risksPurposes.getGlossaryOfPurposes().getId())) {
                String glossaryOfPurposeName = risksPurposes.getGlossaryOfPurposes().getName();

                risksPurposes.getFilledRisks().forEach(filledRisks ->
                    addDataOfFilledRiskToVerticalListBuilder(filledRisks, glossaryOfPurposeName, summary));
            }
        }
    }

    private void addDataOfFilledMeasureUnitsToVerticalListBuilder(MeasureUnitsPurposes measureUnitsPurposes, FilledMeasureUnits filledMeasureUnits, String glossaryOfPurposesName, VerticalListBuilder summary){
        if(!filledMeasureUnits.getSaved()) {
    	summary.add(
            cmp.horizontalList().add(
                cmp.text(counter),
                cmp.text(glossaryOfPurposesName).setWidth(Style.purposesWidth),
                cmp.text("-").setWidth(Style.standardWidth),
                cmp.text(measureUnitsPurposes.getGlossaryOfPurposes().getGlossaryOfProcesses().getName()).setWidth(Style.symbolProcesuZSZJ),
                cmp.text(filledMeasureUnits.getGlossaryOfMeasureUnits().getName()).setWidth(Style.measureUnitsWidth),
                cmp.text(risksReport.getPurposeAccomplishmentReportName(filledMeasureUnits)).setWidth(Style.standardWidth),
                cmp.text("-").setWidth(Style.riskWidth),
                cmp.text("-").setWidth(Style.standardWidth),
                cmp.text("-").setWidth(Style.standardWidth),
                cmp.text("-").setWidth(Style.standardWidth),
                cmp.text("-"),
                cmp.text("-"),
                cmp.text("-"),
                cmp.text("-"),
                cmp.text("-"),
                cmp.text("-"),
                cmp.text("-").setWidth(Style.reactionOnRisk),
                cmp.text("-")
            ));
        counter++;
        }
    }

    private void addDataOfFilledRiskToVerticalListBuilder(FilledRisks filledRisks, String glossaryOfPurposesName, VerticalListBuilder summary){
    	if(!filledRisks.getSaved()) {
        summary.add(
            cmp.horizontalList().add(
                cmp.text(counter),
                cmp.text(glossaryOfPurposesName).setWidth(Style.purposesWidth),
                cmp.text("-").setWidth(Style.standardWidth),
                cmp.text("-").setWidth(Style.symbolProcesuZSZJ),
                cmp.text("-").setWidth(Style.measureUnitsWidth),
                cmp.text("-").setWidth(Style.standardWidth),
                cmp.text(filledRisks.getGlossaryOfRisks().getName()).setWidth(Style.riskWidth),
                cmp.text("-").setWidth(Style.standardWidth),
                cmp.text(filledRisks.getResponsiblePerson()).setWidth(Style.standardWidth),
                cmp.text(filledRisks.getProbability()).setWidth(Style.standardWidth),
                cmp.text(filledRisks.getPowerOfInfluence()),
                cmp.text(filledRisks.getStrengthOfControlFunctionProbability()),
                cmp.text(filledRisks.getStrengthOfControlFunctionPowerOfInfluence()),
                cmp.text(getProbabilityLevel(filledRisks.getProbability(), filledRisks.getStrengthOfControlFunctionProbability())),
                cmp.text(getPowerOfInfluenceLevel(filledRisks.getPowerOfInfluence(), filledRisks.getStrengthOfControlFunctionPowerOfInfluence())),
                cmp.text(getProbabilityLevel(filledRisks.getProbability(), filledRisks.getStrengthOfControlFunctionProbability()) * getPowerOfInfluenceLevel(filledRisks.getPowerOfInfluence(), filledRisks.getStrengthOfControlFunctionPowerOfInfluence())),
                cmp.text(risksReport.getReactionOnRiskName(filledRisks)).setWidth(Style.reactionOnRisk),
                cmp.text(filledRisks.getNotationConcernRisk())
            ));
        counter++;
    	}
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

    private void addLastRisksAndMeasures (SetOfSentPurposes setOfSentPurposes, VerticalListBuilder summary) {

        Set<RisksPurposes> risksPurposes = setOfSentPurposes.getRisksPurposes();
        Set<MeasureUnitsPurposes> measureUnitsPurposes = setOfSentPurposes.getMeasureUnitsPurposes();
        Set<GlossaryOfPurposes> glossaryOfPurposes = setOfSentPurposes.getGlossaryOfPurposes();


        for (GlossaryOfPurposes glossaryOfPurposes2 : glossaryOfPurposes) {
            risksReport.addUserAndGlossaryOfPurposes(setOfSentPurposes, glossaryOfPurposes2, summary);

            for (MeasureUnitsPurposes measureUnitsPurposes2 : measureUnitsPurposes) {

                if(glossaryOfPurposes2.getId().equals(measureUnitsPurposes2.getGlossaryOfPurposes().getId())) {
                    List<GlossaryOfMeasureUnits> glossaryOfMeasureUnits = glossaryOfMeasureUnitsService.getAllOfCurrentOrganisationByGlossaryOfPurposesId(glossaryOfPurposes2.getId());
                    for (GlossaryOfMeasureUnits glossaryOfMeasureUnits2 : glossaryOfMeasureUnits) {
                        FilledMeasureUnits filledMeasureUnits = filledMeasureUnitsService.findLastFilledMeasureUnit(measureUnitsPurposes2.getId(), glossaryOfMeasureUnits2.getId());
                        addFilledMeasureUnit(setOfSentPurposes, glossaryOfPurposes2, filledMeasureUnits, summary);
                    }
                }

            }

            for (RisksPurposes risksPurposes2 : risksPurposes) {

                if(glossaryOfPurposes2.getId().equals(risksPurposes2.getGlossaryOfPurposes().getId())) {
                    List<GlossaryOfRisks> glossaryOfRisks = glossaryOfRisksService.getAllOfCurrentOrganisationByGlossaryOfPurposesId(glossaryOfPurposes2.getId());
                    for (GlossaryOfRisks glossaryOfRisks2 : glossaryOfRisks) {
                        FilledRisks filledRisk = filledRisksService.findLastFilledRisk(risksPurposes2.getId(), glossaryOfRisks2.getId());
                        addFilledRisk(setOfSentPurposes, glossaryOfPurposes2, filledRisk, summary);

                    }
                }

            }

        }

    }

    private void addFilledMeasureUnit(SetOfSentPurposes setOfSentPurposes, GlossaryOfPurposes glossaryOfPurposes, FilledMeasureUnits filledMeasureUnits, VerticalListBuilder summary) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        summary.add(cmp.horizontalList().add(
            cmp.text(" ").setWidth(Style.purposesWidth),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text(filledMeasureUnits.getCreationDate().format(formatter)),
            cmp.text(filledMeasureUnits.getGlossaryOfMeasureUnits().getName()),
            cmp.text(filledMeasureUnits.getBaseValue()),
            cmp.text(filledMeasureUnits.getFinalValue()),
            cmp.text(filledMeasureUnits.getActualValue()),
            cmp.text(filledMeasureUnits.getGlossaryOfMeasureUnits().getUnitsOfMeasurement().name()),
            cmp.text(filledMeasureUnits.getCostOfPurposeRealisation()),
            cmp.text(risksReport.getPurposeAccomplishmentReportName(filledMeasureUnits)),
            cmp.text("-").setWidth(Style.riskWidth),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-")
        ));
    }


    private void addFilledRisk(SetOfSentPurposes setOfSentPurposes, GlossaryOfPurposes glossaryOfPurposes, FilledRisks filledRisks, VerticalListBuilder summary) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        summary.add(cmp.horizontalList().add(
            cmp.text(" ").setWidth(Style.purposesWidth),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text(filledRisks.getCreationDate().format(formatter)),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text("-"),
            cmp.text(filledRisks.getGlossaryOfRisks().getName()).setWidth(Style.riskWidth),
            cmp.text("-"),
            cmp.text(filledRisks.getProbability()),
            cmp.text(filledRisks.getPowerOfInfluence()),
            cmp.text("-"),
            cmp.text(filledRisks.getProbability() * filledRisks.getPowerOfInfluence()),
            cmp.text(filledRisks.getStrengthOfControlFunctionProbability()),
            cmp.text(filledRisks.getStrengthOfControlFunctionPowerOfInfluence()),
            cmp.text(getProbabilityLevel(filledRisks.getProbability(), filledRisks.getStrengthOfControlFunctionProbability()) * getPowerOfInfluenceLevel(filledRisks.getPowerOfInfluence(), filledRisks.getStrengthOfControlFunctionPowerOfInfluence())),
            cmp.text(risksReport.getReactionOnRiskName(filledRisks)),
            cmp.text(filledRisks.getNotationConcernRisk()),
            cmp.text(filledRisks.getResponsiblePerson())
        ));
    }

    private JasperReportBuilder createRaportTitle(SetOfSentPurposes setOfSentPurposes) {
        return report().summary(
            cmp.horizontalList().add(
                cmp.text("Stopień realizacji celu dla " +  setOfSentPurposes.getUser().getOrganisationStructure().getName())
            ),
            cmp.horizontalList().add(
                cmp.text("Cel/zadanie").setStyle(Style.boldStyle).setWidth(Style.purposesWidth),
                cmp.text("Rodzaj celu").setStyle(Style.boldStyle),
                cmp.text("Symbol procesu z SZJ").setStyle(Style.boldStyle),
                cmp.text("Data").setStyle(Style.boldStyle),
                cmp.text("Miernik realizacji celu").setStyle(Style.boldStyle),
                cmp.text("Wartość bazowa").setStyle(Style.boldStyle),
                cmp.text("Wartość planowana").setStyle(Style.boldStyle),
                cmp.text("Wartość aktualna").setStyle(Style.boldStyle),
                cmp.text("Jednostka miary").setStyle(Style.boldStyle),
                cmp.text("Koszt realizacji celu").setStyle(Style.boldStyle),
                cmp.text("Stopień realizacji celu").setStyle(Style.boldStyle),
                cmp.text("Opis ryzyka").setStyle(Style.boldStyle).setWidth(Style.riskWidth),
                cmp.text("Kategoria ryzyka").setStyle(Style.boldStyle),
                cmp.text("Prawdopodobieństwo").setStyle(Style.boldStyle),
                cmp.text("Siła oddziaływania ryzyka").setStyle(Style.boldStyle),
                cmp.text("Mechanizmy kontrolne").setStyle(Style.boldStyle),
                cmp.text("Poziom ryzyka inherentnego").setStyle(Style.boldStyle),
                cmp.text("SMK P").setStyle(Style.boldStyle),
                cmp.text("SMK S").setStyle(Style.boldStyle),
                cmp.text("Poziom ryzyka rezydualnego").setStyle(Style.boldStyle),
                cmp.text("Reakcja na ryzyko").setStyle(Style.boldStyle),
                cmp.text("Opis reakcji na ryzyko").setStyle(Style.boldStyle),
                cmp.text("Właściciel ryzyka").setStyle(Style.boldStyle)
            )

        );
    }
}
