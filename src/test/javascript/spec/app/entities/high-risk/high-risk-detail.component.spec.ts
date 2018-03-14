/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HighRiskDetailComponent } from '../../../../../../main/webapp/app/entities/high-risk/high-risk-detail.component';
import { HighRiskService } from '../../../../../../main/webapp/app/entities/high-risk/high-risk.service';
import { HighRisk } from '../../../../../../main/webapp/app/entities/high-risk/high-risk.model';

describe('Component Tests', () => {

    describe('HighRisk Management Detail Component', () => {
        let comp: HighRiskDetailComponent;
        let fixture: ComponentFixture<HighRiskDetailComponent>;
        let service: HighRiskService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [HighRiskDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HighRiskService,
                    JhiEventManager
                ]
            }).overrideTemplate(HighRiskDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HighRiskDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HighRiskService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new HighRisk(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.highRisk).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
