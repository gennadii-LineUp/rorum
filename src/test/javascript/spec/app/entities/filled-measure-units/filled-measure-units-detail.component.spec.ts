/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FilledMeasureUnitsDetailComponent } from '../../../../../../main/webapp/app/entities/filled-measure-units/filled-measure-units-detail.component';
import { FilledMeasureUnitsService } from '../../../../../../main/webapp/app/entities/filled-measure-units/filled-measure-units.service';
import { FilledMeasureUnits } from '../../../../../../main/webapp/app/entities/filled-measure-units/filled-measure-units.model';

describe('Component Tests', () => {

    describe('FilledMeasureUnits Management Detail Component', () => {
        let comp: FilledMeasureUnitsDetailComponent;
        let fixture: ComponentFixture<FilledMeasureUnitsDetailComponent>;
        let service: FilledMeasureUnitsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [FilledMeasureUnitsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FilledMeasureUnitsService,
                    JhiEventManager
                ]
            }).overrideTemplate(FilledMeasureUnitsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FilledMeasureUnitsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilledMeasureUnitsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FilledMeasureUnits(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.filledMeasureUnits).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
