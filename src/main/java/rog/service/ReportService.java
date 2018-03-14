package rog.service;

import net.sf.dynamicreports.report.exception.DRException;
import rog.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

@Service
public class ReportService {

    @Autowired
    private PurposeAccomplishmentReportService purposeAccomplishmentReportService;

    @Autowired
    private RisksRegisterReportService risksRegisterReportService;

    @Autowired
    private RiskReportService riskReportService;


    public byte[] generatePurposeAccomplishmentReport(Long orderId, Long userId) throws IOException, DRException  {
        return purposeAccomplishmentReportService.generatePurposeAccomplishmentReport(orderId, userId);
    }

    public byte[] generateRiskRegisterReport(Long orderId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {
        return risksRegisterReportService.generateRiskRegisterReport(orderId);
    }

    public byte[] generateRiskReport(Long orderId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {
        return riskReportService.generateRiskReport(orderId); 
    }
    public byte[] generateRiskRegisterAdmin(Long orderId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {
        return riskReportService.generateRisksRegisterAdmin(orderId); 
    }
    
    public byte[] generateRiskReportDoc(Long orderId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {
        return riskReportService.generateRiskReportDoc(orderId);
    }
    
    public byte[] generateRisksChangesReport(Long orderId, Long organisationStructureId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {
        
    	return risksRegisterReportService.generateRisksChangesReport(orderId, organisationStructureId);
    }
    
    public byte[] generatePurposeAccomplishmentReportAdmin(Long orderId, Long organisationStructureId) throws IOException, DRException, XPathExpressionException, ParserConfigurationException, SAXException  {
        return purposeAccomplishmentReportService.generatePurposeAccomplishmentReportAdmin(orderId, organisationStructureId);
    }
    
}
