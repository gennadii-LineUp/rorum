/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GlossaryOfRisksDetailComponent } from '../../../../../../main/webapp/app/entities/glossary-of-risks/glossary-of-risks-detail.component';
import { GlossaryOfRisksService } from '../../../../../../main/webapp/app/entities/glossary-of-risks/glossary-of-risks.service';
import { GlossaryOfRisks } from '../../../../../../main/webapp/app/entities/glossary-of-risks/glossary-of-risks.model';

describe('Component Tests', () => {

    describe('GlossaryOfRisks Management Detail Component', () => {
        let comp: GlossaryOfRisksDetailComponent;
        let fixture: ComponentFixture<GlossaryOfRisksDetailComponent>;
        let service: GlossaryOfRisksService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [GlossaryOfRisksDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GlossaryOfRisksService,
                    JhiEventManager
                ]
            }).overrideTemplate(GlossaryOfRisksDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GlossaryOfRisksDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GlossaryOfRisksService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GlossaryOfRisks(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.glossaryOfRisksDTO).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
