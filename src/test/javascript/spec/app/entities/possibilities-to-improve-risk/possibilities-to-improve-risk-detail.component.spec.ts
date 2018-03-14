/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PossibilitiesToImproveRiskDetailComponent } from '../../../../../../main/webapp/app/entities/possibilities-to-improve-risk/possibilities-to-improve-risk-detail.component';
import { PossibilitiesToImproveRiskService } from '../../../../../../main/webapp/app/entities/possibilities-to-improve-risk/possibilities-to-improve-risk.service';
import { PossibilitiesToImproveRisk } from '../../../../../../main/webapp/app/entities/possibilities-to-improve-risk/possibilities-to-improve-risk.model';

describe('Component Tests', () => {

    describe('PossibilitiesToImproveRisk Management Detail Component', () => {
        let comp: PossibilitiesToImproveRiskDetailComponent;
        let fixture: ComponentFixture<PossibilitiesToImproveRiskDetailComponent>;
        let service: PossibilitiesToImproveRiskService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [PossibilitiesToImproveRiskDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PossibilitiesToImproveRiskService,
                    JhiEventManager
                ]
            }).overrideTemplate(PossibilitiesToImproveRiskDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PossibilitiesToImproveRiskDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PossibilitiesToImproveRiskService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PossibilitiesToImproveRisk(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.possibilitiesToImproveRisk).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
