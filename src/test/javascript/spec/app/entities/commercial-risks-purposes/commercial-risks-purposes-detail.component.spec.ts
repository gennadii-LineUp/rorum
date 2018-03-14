/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CommercialRisksPurposesDetailComponent } from '../../../../../../main/webapp/app/entities/commercial-risks-purposes/commercial-risks-purposes-detail.component';
import { CommercialRisksPurposesService } from '../../../../../../main/webapp/app/entities/commercial-risks-purposes/commercial-risks-purposes.service';
import { CommercialRisksPurposes } from '../../../../../../main/webapp/app/entities/commercial-risks-purposes/commercial-risks-purposes.model';

describe('Component Tests', () => {

    describe('CommercialRisksPurposes Management Detail Component', () => {
        let comp: CommercialRisksPurposesDetailComponent;
        let fixture: ComponentFixture<CommercialRisksPurposesDetailComponent>;
        let service: CommercialRisksPurposesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [CommercialRisksPurposesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CommercialRisksPurposesService,
                    JhiEventManager
                ]
            }).overrideTemplate(CommercialRisksPurposesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommercialRisksPurposesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialRisksPurposesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CommercialRisksPurposes(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.commercialRisksPurposes).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
