package rog.service;

import net.sf.dynamicreports.report.exception.DRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import rog.domain.SetOfSentPurposes;
import rog.repository.SetOfSentPurposesRepository;
import rog.service.excel.PurposeAccomplishmentReport;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

@Service
public class PurposeAccomplishmentReportService {

	private final Logger log = LoggerFactory.getLogger(RisksRegisterReportService.class);

	@Autowired
	private PurposeAccomplishmentReport purposeAccomplishmentReport;

    @Autowired
    private SetOfSentPurposesRepository setOfSentPurposesRepository;

    @Transactional(readOnly = true)
    public byte[] generatePurposeAccomplishmentReport(Long orderId, Long userId) throws IOException, DRException  {
        log.debug("PurposeAccomplishmentReportService- " + orderId + " userId: " + userId);
        return purposeAccomplishmentReport.createReport(findAllByOrderIdAndUserId(orderId, userId));
    }

    private SetOfSentPurposes findAllByOrderIdAndUserId(Long orderId, Long userId) {
        log.debug("FindListOfPurposes");
        return setOfSentPurposesRepository.findAllByOrdersIdAndUserIdAndIsLastVersion(orderId, userId, true);
    }
    
    public byte[] generatePurposeAccomplishmentReportAdmin(Long orderId, Long organisationStructureId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException  {
    	SetOfSentPurposes setOfSentPurposes = setOfSentPurposesRepository.getOneUnsavedByOrganisationAndOrderIds(organisationStructureId, orderId);
        return purposeAccomplishmentReport.createReport(setOfSentPurposes);
    }

}
