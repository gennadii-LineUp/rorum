/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HighCommercialRiskDetailComponent } from '../../../../../../main/webapp/app/entities/high-commercial-risk/high-commercial-risk-detail.component';
import { HighCommercialRiskService } from '../../../../../../main/webapp/app/entities/high-commercial-risk/high-commercial-risk.service';
import { HighCommercialRisk } from '../../../../../../main/webapp/app/entities/high-commercial-risk/high-commercial-risk.model';

describe('Component Tests', () => {

    describe('HighCommercialRisk Management Detail Component', () => {
        let comp: HighCommercialRiskDetailComponent;
        let fixture: ComponentFixture<HighCommercialRiskDetailComponent>;
        let service: HighCommercialRiskService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [HighCommercialRiskDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HighCommercialRiskService,
                    JhiEventManager
                ]
            }).overrideTemplate(HighCommercialRiskDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HighCommercialRiskDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HighCommercialRiskService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new HighCommercialRisk(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.highCommercialRisk).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
