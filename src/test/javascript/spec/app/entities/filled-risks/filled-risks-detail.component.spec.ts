/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FilledRisksDetailComponent } from '../../../../../../main/webapp/app/entities/filled-risks/filled-risks-detail.component';
import { FilledRisksService } from '../../../../../../main/webapp/app/entities/filled-risks/filled-risks.service';
import { FilledRisks } from '../../../../../../main/webapp/app/entities/filled-risks/filled-risks.model';

describe('Component Tests', () => {

    describe('FilledRisks Management Detail Component', () => {
        let comp: FilledRisksDetailComponent;
        let fixture: ComponentFixture<FilledRisksDetailComponent>;
        let service: FilledRisksService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [FilledRisksDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FilledRisksService,
                    JhiEventManager
                ]
            }).overrideTemplate(FilledRisksDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FilledRisksDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilledRisksService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FilledRisks(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.filledRisks).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
