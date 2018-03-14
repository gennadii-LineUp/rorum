/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IncidentDetailComponent } from '../../../../../../main/webapp/app/entities/incident/incident-detail.component';
import { IncidentService } from '../../../../../../main/webapp/app/entities/incident/incident.service';
import { Incident } from '../../../../../../main/webapp/app/entities/incident/incident.model';

describe('Component Tests', () => {

    describe('Incident Management Detail Component', () => {
        let comp: IncidentDetailComponent;
        let fixture: ComponentFixture<IncidentDetailComponent>;
        let service: IncidentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [IncidentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IncidentService,
                    JhiEventManager
                ]
            }).overrideTemplate(IncidentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IncidentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncidentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Incident(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.incident).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
