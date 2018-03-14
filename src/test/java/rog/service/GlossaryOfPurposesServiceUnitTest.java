package rog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rog.RorumApp;
import rog.domain.GlossaryOfProcesses;
import rog.domain.GlossaryOfPurposes;
import rog.domain.OrganisationStructure;
import rog.repository.GlossaryOfProcessesRepository;
import rog.repository.GlossaryOfPurposesRepository;
import rog.repository.OrganisationStructureRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
@Transactional
public class GlossaryOfPurposesServiceUnitTest {

    @Autowired
    private GlossaryOfPurposesService glossaryOfPurposesService;

    @Autowired
    private GlossaryOfPurposesRepository glossaryOfPurposesRepository;

    @Autowired
    private GlossaryOfProcessesRepository glossaryOfProcessesRepository;

    @Autowired
    private OrganisationStructureRepository organisationStructureRepository;

    private static final Long PURPOSE_ID = 51L;

    @Test
    public void getAllByOrganisationIdTest(){
        GlossaryOfPurposes glossaryOfPurposes = glossaryOfPurposesRepository.findOne(PURPOSE_ID);

        GlossaryOfProcesses glossaryOfProcesses =
            glossaryOfProcessesRepository.findOneByGlossaryOfPurposesId(glossaryOfPurposes.getId());

        List<OrganisationStructure> organisationStructures =
            organisationStructureRepository.findAllByGlossaryOfProcessesId(glossaryOfProcesses.getId());

        for (OrganisationStructure organisationStructure : organisationStructures) {
            List<GlossaryOfPurposes> listGlossaryOfPurposes =
                glossaryOfPurposesService.getAllByOrganisationId(organisationStructure.getId());
            assertThat(glossaryOfPurposes).isIn(listGlossaryOfPurposes);
        }
    }
}
