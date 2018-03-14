/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MeasureUnitsPurposesDetailComponent } from '../../../../../../main/webapp/app/entities/measure-units-purposes/measure-units-purposes-detail.component';
import { MeasureUnitsPurposesService } from '../../../../../../main/webapp/app/entities/measure-units-purposes/measure-units-purposes.service';
import { MeasureUnitsPurposes } from '../../../../../../main/webapp/app/entities/measure-units-purposes/measure-units-purposes.model';

describe('Component Tests', () => {

    describe('MeasureUnitsPurposes Management Detail Component', () => {
        let comp: MeasureUnitsPurposesDetailComponent;
        let fixture: ComponentFixture<MeasureUnitsPurposesDetailComponent>;
        let service: MeasureUnitsPurposesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [MeasureUnitsPurposesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MeasureUnitsPurposesService,
                    JhiEventManager
                ]
            }).overrideTemplate(MeasureUnitsPurposesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MeasureUnitsPurposesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MeasureUnitsPurposesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MeasureUnitsPurposes(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.measureUnitsPurposes).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
