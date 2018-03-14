/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GlossaryOfCommercialRisksDetailComponent } from '../../../../../../main/webapp/app/entities/glossary-of-commercial-risks/glossary-of-commercial-risks-detail.component';
import { GlossaryOfCommercialRisksService } from '../../../../../../main/webapp/app/entities/glossary-of-commercial-risks/glossary-of-commercial-risks.service';
import { GlossaryOfCommercialRisks } from '../../../../../../main/webapp/app/entities/glossary-of-commercial-risks/glossary-of-commercial-risks.model';

describe('Component Tests', () => {

    describe('GlossaryOfCommercialRisks Management Detail Component', () => {
        let comp: GlossaryOfCommercialRisksDetailComponent;
        let fixture: ComponentFixture<GlossaryOfCommercialRisksDetailComponent>;
        let service: GlossaryOfCommercialRisksService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [GlossaryOfCommercialRisksDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GlossaryOfCommercialRisksService,
                    JhiEventManager
                ]
            }).overrideTemplate(GlossaryOfCommercialRisksDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GlossaryOfCommercialRisksDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GlossaryOfCommercialRisksService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GlossaryOfCommercialRisks(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.glossaryOfCommercialRisksDTO).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
