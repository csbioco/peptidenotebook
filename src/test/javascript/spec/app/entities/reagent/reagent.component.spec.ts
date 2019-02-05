/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PeptidenotebookTestModule } from '../../../test.module';
import { ReagentComponent } from 'app/entities/reagent/reagent.component';
import { ReagentService } from 'app/entities/reagent/reagent.service';
import { Reagent } from 'app/shared/model/reagent.model';

describe('Component Tests', () => {
    describe('Reagent Management Component', () => {
        let comp: ReagentComponent;
        let fixture: ComponentFixture<ReagentComponent>;
        let service: ReagentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [ReagentComponent],
                providers: []
            })
                .overrideTemplate(ReagentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReagentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReagentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Reagent(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.reagents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
