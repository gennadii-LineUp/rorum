package rog.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import net.sf.dynamicreports.report.exception.DRException;
import rog.domain.Orders;
import rog.domain.OrganisationStructure;
import rog.domain.SetOfSentPurposes;
import rog.domain.User;
import rog.repository.SetOfSentPurposesRepository;
import rog.service.excel.RisksRegisterAdmin;
import rog.service.excel.RisksReport;
import rog.service.word.RisksReportDocx;

@Service
public class RiskReportService {

    private final Logger log = LoggerFactory.getLogger(RiskReportService.class);

    @Autowired
    private SetOfSentPurposesRepository setOfSentPurposesRepository;

    @Autowired
    private RisksReport risksReport;
    
    @Autowired
    private RisksRegisterAdmin risksRegisterAdmin;
    
    @Autowired
    private RisksReportDocx risksReportDocx;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private OrdersService ordersService;

    @Transactional(readOnly = true)
    public SetOfSentPurposes findAllByOrderIdAndUserId(Long orderId, Long userId) {
        log.debug("FindListOfPurposes");
        return setOfSentPurposesRepository.findAllByOrdersIdAndUserIdAndIsLastVersion(orderId, userId, true);
    }

    public byte[] generateRiskReport(Long orderId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException  {
    	
//    	User user = userService.getUserWithAuthorities(userId);
        User user = userService.getCurrentUser();
    	
    	Long organisationStructureId = user.getOrganisationStructure().getId();
    	ArrayList<User> userList = userService.getAllParentedAndSupervisoredUsers(organisationStructureId);
    	OrganisationStructure organisationStructure = user.getOrganisationStructure();
    	Orders order = ordersService.findOne(orderId);
    	return risksReport.createReport(orderId, userList, organisationStructure, order);
    }
    
    public byte[] generateRiskReportDoc(Long orderId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException  {
    	
//    	User user = userService.getUserWithAuthorities(userId);
        User user = userService.getCurrentUser();

    	Long organisationStructureId = user.getOrganisationStructure().getId();
    	log.debug("Organisation Structure id: " + organisationStructureId);
    	ArrayList<User> userList = userService.getAllParentedAndSupervisoredUsers(organisationStructureId);
    	
    	return risksReportDocx.createDocReport(orderId, userList);
    }
    
    public byte[] generateRisksRegisterAdmin(Long orderId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException  {
        User user = userService.getCurrentUser();
    	Long organisationStructureId = user.getOrganisationStructure().getId();
    	ArrayList<User> userList = userService.getAllParentedAndSupervisoredUsers(organisationStructureId);
    	OrganisationStructure organisationStructure = user.getOrganisationStructure();
    	Orders order = ordersService.findOne(orderId);
    	return risksRegisterAdmin.createReport(orderId, userList, organisationStructure, order);
    }

}

