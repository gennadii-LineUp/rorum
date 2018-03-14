/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DecisionForRiskDetailComponent } from '../../../../../../main/webapp/app/entities/decision-for-risk/decision-for-risk-detail.component';
import { DecisionForRiskService } from '../../../../../../main/webapp/app/entities/decision-for-risk/decision-for-risk.service';
import { DecisionForRisk } from '../../../../../../main/webapp/app/entities/decision-for-risk/decision-for-risk.model';

describe('Component Tests', () => {

    describe('DecisionForRisk Management Detail Component', () => {
        let comp: DecisionForRiskDetailComponent;
        let fixture: ComponentFixture<DecisionForRiskDetailComponent>;
        let service: DecisionForRiskService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [DecisionForRiskDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DecisionForRiskService,
                    JhiEventManager
                ]
            }).overrideTemplate(DecisionForRiskDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DecisionForRiskDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DecisionForRiskService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DecisionForRisk(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.decisionForRisk).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
