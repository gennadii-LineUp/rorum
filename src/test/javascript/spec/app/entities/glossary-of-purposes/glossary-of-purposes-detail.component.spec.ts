/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GlossaryOfPurposesDetailComponent } from '../../../../../../main/webapp/app/entities/glossary-of-purposes/glossary-of-purposes-detail.component';
import { GlossaryOfPurposesService } from '../../../../../../main/webapp/app/entities/glossary-of-purposes/glossary-of-purposes.service';
import { GlossaryOfPurposes } from '../../../../../../main/webapp/app/entities/glossary-of-purposes/glossary-of-purposes.model';

describe('Component Tests', () => {

    describe('GlossaryOfPurposes Management Detail Component', () => {
        let comp: GlossaryOfPurposesDetailComponent;
        let fixture: ComponentFixture<GlossaryOfPurposesDetailComponent>;
        let service: GlossaryOfPurposesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [GlossaryOfPurposesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GlossaryOfPurposesService,
                    JhiEventManager
                ]
            }).overrideTemplate(GlossaryOfPurposesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GlossaryOfPurposesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GlossaryOfPurposesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GlossaryOfPurposes(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.glossaryOfRisksService).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
