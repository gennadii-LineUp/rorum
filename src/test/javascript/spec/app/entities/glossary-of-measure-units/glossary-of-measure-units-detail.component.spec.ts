/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GlossaryOfMeasureUnitsDetailComponent } from '../../../../../../main/webapp/app/entities/glossary-of-measure-units/glossary-of-measure-units-detail.component';
import { GlossaryOfMeasureUnitsService } from '../../../../../../main/webapp/app/entities/glossary-of-measure-units/glossary-of-measure-units.service';
import { GlossaryOfMeasureUnits } from '../../../../../../main/webapp/app/entities/glossary-of-measure-units/glossary-of-measure-units.model';

describe('Component Tests', () => {

    describe('GlossaryOfMeasureUnits Management Detail Component', () => {
        let comp: GlossaryOfMeasureUnitsDetailComponent;
        let fixture: ComponentFixture<GlossaryOfMeasureUnitsDetailComponent>;
        let service: GlossaryOfMeasureUnitsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [GlossaryOfMeasureUnitsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GlossaryOfMeasureUnitsService,
                    JhiEventManager
                ]
            }).overrideTemplate(GlossaryOfMeasureUnitsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GlossaryOfMeasureUnitsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GlossaryOfMeasureUnitsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GlossaryOfMeasureUnits(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.glossaryOfMeasureUnitsDTO).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
