/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrganisationStructureDetailComponent } from '../../../../../../main/webapp/app/entities/organisation-structure/organisation-structure-detail.component';
import { OrganisationStructureService } from '../../../../../../main/webapp/app/entities/organisation-structure/organisation-structure.service';
import { OrganisationStructure } from '../../../../../../main/webapp/app/entities/organisation-structure/organisation-structure.model';

describe('Component Tests', () => {

    describe('OrganisationStructure Management Detail Component', () => {
        let comp: OrganisationStructureDetailComponent;
        let fixture: ComponentFixture<OrganisationStructureDetailComponent>;
        let service: OrganisationStructureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [OrganisationStructureDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrganisationStructureService,
                    JhiEventManager
                ]
            }).overrideTemplate(OrganisationStructureDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrganisationStructureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrganisationStructureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrganisationStructure(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.organisationStructure).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
