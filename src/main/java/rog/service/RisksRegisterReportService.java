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
import rog.service.excel.RisksRegisterReport;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

@Service
public class RisksRegisterReportService {

    private final Logger log = LoggerFactory.getLogger(RisksRegisterReportService.class);

    @Autowired
    private SetOfSentPurposesRepository setOfSentPurposesRepository;

    @Autowired
    private RisksRegisterReport risksRegisterReport;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public SetOfSentPurposes findAllByOrderIdAndUserId(Long orderId, Long userId) {
        log.debug("FindListOfPurposes");
        return setOfSentPurposesRepository.findAllByOrdersIdAndUserIdAndIsLastVersion(orderId, userId, true);
    }

    public byte[] generateRiskRegisterReport(Long orderId) throws IOException, DRException  {

        Long userId = userService.getCurrentUser().getId();
        SetOfSentPurposes setOfSentPurposes = findAllByOrderIdAndUserId(orderId, userId);

//        return risksRegisterReport.createReport(orders, orderId, setOfSentPurposes);
        return risksRegisterReport.createReport(setOfSentPurposes);
    }
    
    public byte[] generateRisksChangesReport(Long orderId, Long organisationStructureId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException  {

    	SetOfSentPurposes setOfSentPurposes = setOfSentPurposesRepository.getOneUnsavedByOrganisationAndOrderIds(organisationStructureId, orderId);
        return risksRegisterReport.createReport(setOfSentPurposes);
    }
    

}
