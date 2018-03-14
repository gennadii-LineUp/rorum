/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GlossaryOfProcessesDetailComponent } from '../../../../../../main/webapp/app/entities/glossary-of-processes/glossary-of-processes-detail.component';
import { GlossaryOfProcessesService } from '../../../../../../main/webapp/app/entities/glossary-of-processes/glossary-of-processes.service';
import { GlossaryOfProcesses } from '../../../../../../main/webapp/app/entities/glossary-of-processes/glossary-of-processes.model';

describe('Component Tests', () => {

    describe('GlossaryOfProcesses Management Detail Component', () => {
        let comp: GlossaryOfProcessesDetailComponent;
        let fixture: ComponentFixture<GlossaryOfProcessesDetailComponent>;
        let service: GlossaryOfProcessesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [GlossaryOfProcessesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GlossaryOfProcessesService,
                    JhiEventManager
                ]
            }).overrideTemplate(GlossaryOfProcessesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GlossaryOfProcessesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GlossaryOfProcessesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GlossaryOfProcesses(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.glossaryOfProcesses).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
