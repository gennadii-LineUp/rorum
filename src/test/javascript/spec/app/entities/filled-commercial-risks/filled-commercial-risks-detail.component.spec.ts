/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FilledCommercialRisksDetailComponent } from '../../../../../../main/webapp/app/entities/filled-commercial-risks/filled-commercial-risks-detail.component';
import { FilledCommercialRisksService } from '../../../../../../main/webapp/app/entities/filled-commercial-risks/filled-commercial-risks.service';
import { FilledCommercialRisks } from '../../../../../../main/webapp/app/entities/filled-commercial-risks/filled-commercial-risks.model';

describe('Component Tests', () => {

    describe('FilledCommercialRisks Management Detail Component', () => {
        let comp: FilledCommercialRisksDetailComponent;
        let fixture: ComponentFixture<FilledCommercialRisksDetailComponent>;
        let service: FilledCommercialRisksService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [FilledCommercialRisksDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FilledCommercialRisksService,
                    JhiEventManager
                ]
            }).overrideTemplate(FilledCommercialRisksDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FilledCommercialRisksDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilledCommercialRisksService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FilledCommercialRisks(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.filledCommercialRisks).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
