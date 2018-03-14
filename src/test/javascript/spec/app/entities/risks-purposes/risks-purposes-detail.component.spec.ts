/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RisksPurposesDetailComponent } from '../../../../../../main/webapp/app/entities/risks-purposes/risks-purposes-detail.component';
import { RisksPurposesService } from '../../../../../../main/webapp/app/entities/risks-purposes/risks-purposes.service';
import { RisksPurposes } from '../../../../../../main/webapp/app/entities/risks-purposes/risks-purposes.model';

describe('Component Tests', () => {

    describe('RisksPurposes Management Detail Component', () => {
        let comp: RisksPurposesDetailComponent;
        let fixture: ComponentFixture<RisksPurposesDetailComponent>;
        let service: RisksPurposesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [RisksPurposesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RisksPurposesService,
                    JhiEventManager
                ]
            }).overrideTemplate(RisksPurposesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RisksPurposesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RisksPurposesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RisksPurposes(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.risksPurposes).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
