/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PeptidenotebookTestModule } from '../../../test.module';
import { CalculatedEntryComponent } from 'app/entities/calculated-entry/calculated-entry.component';
import { CalculatedEntryService } from 'app/entities/calculated-entry/calculated-entry.service';
import { CalculatedEntry } from 'app/shared/model/calculated-entry.model';

describe('Component Tests', () => {
    describe('CalculatedEntry Management Component', () => {
        let comp: CalculatedEntryComponent;
        let fixture: ComponentFixture<CalculatedEntryComponent>;
        let service: CalculatedEntryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [CalculatedEntryComponent],
                providers: []
            })
                .overrideTemplate(CalculatedEntryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CalculatedEntryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalculatedEntryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CalculatedEntry(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.calculatedEntries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
