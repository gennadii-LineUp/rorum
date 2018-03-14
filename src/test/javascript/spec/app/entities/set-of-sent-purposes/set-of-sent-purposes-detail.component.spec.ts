/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RorumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SetOfSentPurposesDetailComponent } from '../../../../../../main/webapp/app/entities/set-of-sent-purposes/set-of-sent-purposes-detail.component';
import { SetOfSentPurposesService } from '../../../../../../main/webapp/app/entities/set-of-sent-purposes/set-of-sent-purposes.service';
import { SetOfSentPurposes } from '../../../../../../main/webapp/app/entities/set-of-sent-purposes/set-of-sent-purposes.model';

describe('Component Tests', () => {

    describe('SetOfSentPurposes Management Detail Component', () => {
        let comp: SetOfSentPurposesDetailComponent;
        let fixture: ComponentFixture<SetOfSentPurposesDetailComponent>;
        let service: SetOfSentPurposesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RorumTestModule],
                declarations: [SetOfSentPurposesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SetOfSentPurposesService,
                    JhiEventManager
                ]
            }).overrideTemplate(SetOfSentPurposesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SetOfSentPurposesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SetOfSentPurposesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SetOfSentPurposes(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.setOfSentPurposes).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
