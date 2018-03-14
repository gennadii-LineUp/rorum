package rog.service.excel;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsxExporterBuilder;
import net.sf.dynamicreports.report.builder.chart.BarChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.constant.Orientation;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import rog.domain.FilledMeasureUnits;
import rog.domain.FilledRisks;
import rog.domain.GlossaryOfMeasureUnits;
import rog.domain.GlossaryOfPurposes;
import rog.domain.GlossaryOfRisks;
import rog.domain.MeasureUnitsPurposes;
import rog.domain.Orders;
import rog.domain.OrganisationStructure;
import rog.domain.PercentagesOfCalculatedValues;
import rog.domain.RisksPurposes;
import rog.domain.SetOfSentPurposes;
import rog.domain.User;
import rog.domain.enumeration.PurposeAccomplishmentStatus;
import rog.domain.enumeration.ReactionOnRisks;
import rog.service.*;
import rog.service.statistics.RiskReportRangesCount;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

@Service
public class RisksRegisterAdmin {

    private final Logger log = LoggerFactory.getLogger(RisksReport.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SetOfSentPurposesService setOfSentPurposesService;

    @Autowired
    private FilledRisksService filledRisksService;

    @Autowired
    private GlossaryOfRisksService glossaryOfRisksService;

    @Autowired
    private GlossaryOfMeasureUnitsService glossaryOfMeasureUnitsService;

    @Autowired
    private FilledMeasureUnitsService filledMeasureUnitsService;

    @Autowired
    private PercentagesOfCalculatedValuesService percentagesOfCalculatedValuesService;

    @Autowired
    private RisksReport risksReport;

    private static String UPLOADED_FOLDER = System.getProperty("java.io.tmpdir");
    private static final String EXTENSION = ".xlsx";
    private ArrayList<RiskReportRangesCount> rgList = new ArrayList<>();
    private JasperReportBuilder barChartBuilder = new JasperReportBuilder();
    private JasperReportBuilder globalBarChartBuilder = new JasperReportBuilder();


    public byte[] createReport(Long orderId, ArrayList<User> userList, OrganisationStructure organisationStructure, Orders order) throws IOException, DRException {

        String fileName = UPLOADED_FOLDER + File.separator + 1 + EXTENSION;
        JasperXlsxExporterBuilder excelExporter = getJasperXlsxExporterBuilder(fileName);
        JasperReportBuilder report = report();

        report
            .setPageFormat(PageType.A1, PageOrientation.LANDSCAPE)
            .highlightDetailEvenRows()
            .summary(cmp.multiPageList()
                .add(cmp.subreport(createReportData(orderId, userList, organisationStructure, order)),
                    cmp.subreport(barChartBuilder),
                    cmp.subreport(globalBarChartBuilder)
                ))
            .toXlsx(excelExporter);
        return FileUtils.readFileToByteArray(new java.io.File(fileName));
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

    public JasperReportBuilder createReportData(Long orderId, ArrayList<User> userList, OrganisationStructure organisationStructure, Orders order) {
        VerticalListBuilder summary = cmp.verticalList();
        if(userList.size() != 0) {
            summary.add(cmp.subreport(createRaportTitle(organisationStructure, order)),
                cmp.subreport(addUsers(orderId, userList))
            );
        }
        else
            summary.add(cmp.text("Brak danych do wygenerowania raportu."));

        return report().summary(summary);

    }

    public JasperReportBuilder addUsers(Long orderId, ArrayList<User> userList) {
        VerticalListBuilder summary = cmp.verticalList();
        List<PercentagesOfCalculatedValues> percentage = percentagesOfCalculatedValuesService.getAllPercentagesOfCalculatedValues();

        for (User user : userList) {

            RiskReportRangesCount rg = new RiskReportRangesCount(user, percentage);

            if(setOfSentPurposesService.getOneByOrganisationIdAndOrderId(user.getOrganisationStructure().getId(), orderId) == null)
                log.debug(" ");
            else {
                SetOfSentPurposes setOfSentPurposes = setOfSentPurposesService.getOneByOrganisationIdAndOrderId(user.getOrganisationStructure().getId(), orderId);

                if(user.getId().equals(setOfSentPurposes.getUser().getId())) {
                    addFirstUnSavedRisksAndMeasures(rg, setOfSentPurposes, summary);
                    rgList.add(rg);
                }
            }
        }
        createBarChart(rgList);
        createGlobalBarChart(rgList);

        return report()
            .summary(summary);
    }

    private void addFirstUnSavedRisksAndMeasures(RiskReportRangesCount rg, SetOfSentPurposes setOfSentPurposes, VerticalListBuilder summary) {

        Set<RisksPurposes> risksPurposes = setOfSentPurposes.getRisksPurposes();
        Set<MeasureUnitsPurposes> measureUnitsPurposes = setOfSentPurposes.getMeasureUnitsPurposes();
        Set<GlossaryOfPurposes> glossaryOfPurposes = setOfSentPurposes.getGlossaryOfPurposes();


        for (GlossaryOfPurposes glossaryOfPurposes2 : glossaryOfPurposes) {
            addUserAndGlossaryOfPurposes(setOfSentPurposes, glossaryOfPurposes2, summary);

            for (MeasureUnitsPurposes measureUnitsPurposes2 : measureUnitsPurposes) {

                if(glossaryOfPurposes2.getId().equals(measureUnitsPurposes2.getGlossaryOfPurposes().getId())) {
                    List<GlossaryOfMeasureUnits> glossaryOfMeasureUnits = glossaryOfMeasureUnitsService.getAllOfCurrentOrganisationByGlossaryOfPurposesId(glossaryOfPurposes2.getId());
                    for (GlossaryOfMeasureUnits glossaryOfMeasureUnits2 : glossaryOfMeasureUnits) {
                        FilledMeasureUnits filledMeasureUnits = filledMeasureUnitsService.findFirstUnSavedFilledMeasureUnit(measureUnitsPurposes2.getId(), glossaryOfMeasureUnits2.getId());
                        addFilledMeasureUnit(setOfSentPurposes, glossaryOfPurposes2, filledMeasureUnits, summary);
                    }
                }

            }

            for (RisksPurposes risksPurposes2 : risksPurposes) {

                if(glossaryOfPurposes2.getId().equals(risksPurposes2.getGlossaryOfPurposes().getId())) {
                    List<GlossaryOfRisks> glossaryOfRisks = glossaryOfRisksService.getAllOfCurrentOrganisationByGlossaryOfPurposesId(glossaryOfPurposes2.getId());
                    for (GlossaryOfRisks glossaryOfRisks2 : glossaryOfRisks) {
                        FilledRisks filledRisk = filledRisksService.findFirstUnSavedFilledRisk(risksPurposes2.getId(), glossaryOfRisks2.getId());
                        addFilledRisk(rg, setOfSentPurposes, glossaryOfPurposes2, filledRisk, summary);

                    }
                }

            }

        }

    }



    private void addFilledMeasureUnit(SetOfSentPurposes setOfSentPurposes, GlossaryOfPurposes glossaryOfPurposes, FilledMeasureUnits filledMeasureUnits, VerticalListBuilder summary) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        summary.add(cmp.horizontalList().add(
            cmp.text(" "),
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


    private void addFilledRisk(RiskReportRangesCount rg, SetOfSentPurposes setOfSentPurposes, GlossaryOfPurposes glossaryOfPurposes, FilledRisks filledRisks, VerticalListBuilder summary) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        summary.add(cmp.horizontalList().add(
            cmp.text(" "),
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
        rg.buildData(getProbabilityLevel(filledRisks.getProbability(), filledRisks.getStrengthOfControlFunctionProbability()) * getPowerOfInfluenceLevel(filledRisks.getPowerOfInfluence(), filledRisks.getStrengthOfControlFunctionPowerOfInfluence()));

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

    private JasperReportBuilder createRaportTitle(OrganisationStructure organisationStructure, Orders order) {
        return report().summary(
            cmp.horizontalList().add(
                cmp.text("Rejestr ryzyk dla " + organisationStructure.getName() + " za " + order.getFinancingYear())
            ),
            cmp.horizontalList().add(
                cmp.text("Jednostka / Komórka").setStyle(Style.boldStyle),
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
    private void addUserAndGlossaryOfPurposes(SetOfSentPurposes setOfSentPurposes, GlossaryOfPurposes glossaryOfPurposes, VerticalListBuilder summary) {
        summary.add(cmp.horizontalList().add(
            cmp.text(setOfSentPurposes.getUser().getOrganisationStructure().getName()),
            cmp.text(glossaryOfPurposes.getName()).setWidth(Style.purposesWidth),
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

    private void createBarChart(ArrayList<RiskReportRangesCount> rgList) {

        TextColumnBuilder<String> itemColumn = col.column("Item", "item", type.stringType());
        TextColumnBuilder<Integer> stock1Column = col.column("Ilość ryzyk na poziomie niskim", "goodRisk", type.integerType());
        TextColumnBuilder<Integer> stock2Column = col.column("Ilość ryzyk na poziomie średnim", "mediumRisk", type.integerType());
        TextColumnBuilder<Integer> stock3Column = col.column("Ilość ryzyk na poziomie wysokim", "badRisk", type.integerType());
        TextColumnBuilder<Integer> stock4Column = col.column("Ilość ryzyk na poziomie bardzo wysokim", "highRisk", type.integerType());

        DRDataSource dataSource = new DRDataSource("item","goodRisk", "mediumRisk", "badRisk", "highRisk");

        for (RiskReportRangesCount riskReportRangesCount : rgList) {
            dataSource.add(
                riskReportRangesCount.getUser().getOrganisationStructure().getName(),
                riskReportRangesCount.getV1(),
                riskReportRangesCount.getV2(),
                riskReportRangesCount.getV3(),
                riskReportRangesCount.getV4());
        }

        BarChartBuilder barChart = cht.barChart()
            .setShowValues(true)
            .series(cht.serie(stock1Column), cht.serie(stock2Column), cht.serie(stock3Column), cht.serie(stock4Column))
            .setCategory(itemColumn)
            .setDataSource(dataSource)
            .setOrientation(Orientation.HORIZONTAL)
            .setTitle("Zestawienie z realizowanych przez jednostki ryzyk")
            .setShowTickMarks(true)
            .seriesColors(Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED);

        barChartBuilder.summary(barChart);

    }


    private void createGlobalBarChart(ArrayList<RiskReportRangesCount> rgList) {
        User user = userService.getCurrentUser();
        String title = user.getOrganisationStructure().getName();
        List<PercentagesOfCalculatedValues> percentage = percentagesOfCalculatedValuesService.getAllPercentagesOfCalculatedValues();
        RiskReportRangesCount rgGlobal = new RiskReportRangesCount(user, percentage);
        int v1 = 0;
        int v2 = 0;
        int v3 = 0;
        int v4 = 0;

        TextColumnBuilder<String> itemColumn = col.column("Item", "item", type.stringType());
        TextColumnBuilder<Integer> stock1Column = col.column("Ilość ryzyk na poziomie niskim", "goodRisk", type.integerType());
        TextColumnBuilder<Integer> stock2Column = col.column("Ilość ryzyk na poziomie średnim", "mediumRisk", type.integerType());
        TextColumnBuilder<Integer> stock3Column = col.column("Ilość ryzyk na poziomie wysokim", "badRisk", type.integerType());
        TextColumnBuilder<Integer> stock4Column = col.column("Ilość ryzyk na poziomie bardzo wysokim", "highRisk", type.integerType());

        DRDataSource dataSource = new DRDataSource("item","goodRisk", "mediumRisk", "badRisk", "highRisk");

        for (RiskReportRangesCount riskReportRangesCount : rgList) {
            v1 += riskReportRangesCount.getV1();
            v2 += riskReportRangesCount.getV2();
            v3 += riskReportRangesCount.getV3();
            v4 += riskReportRangesCount.getV4();
        }
        rgGlobal.setV1(v1);
        rgGlobal.setV2(v2);
        rgGlobal.setV3(v3);
        rgGlobal.setV4(v4);

        dataSource.add(
            rgGlobal.getUser().getOrganisationStructure().getName(),
            rgGlobal.getV1(),
            rgGlobal.getV2(),
            rgGlobal.getV3(),
            rgGlobal.getV4());

        BarChartBuilder barChart = cht.barChart()
            .setShowValues(true)
            .series(cht.serie(stock1Column), cht.serie(stock2Column), cht.serie(stock3Column), cht.serie(stock4Column))
            .setCategory(itemColumn)
            .setDataSource(dataSource)
            .setOrientation(Orientation.HORIZONTAL)
            .setTitle("Zestawienie z realizowanych ryzyk w " + title)
            .setShowTickMarks(true)
            .seriesColors(Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED);

        globalBarChartBuilder.summary(barChart);

    }
}

