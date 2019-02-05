/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeptidenotebookTestModule } from '../../../test.module';
import { CalculatedEntryDetailComponent } from 'app/entities/calculated-entry/calculated-entry-detail.component';
import { CalculatedEntry } from 'app/shared/model/calculated-entry.model';

describe('Component Tests', () => {
    describe('CalculatedEntry Management Detail Component', () => {
        let comp: CalculatedEntryDetailComponent;
        let fixture: ComponentFixture<CalculatedEntryDetailComponent>;
        const route = ({ data: of({ calculatedEntry: new CalculatedEntry(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [CalculatedEntryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CalculatedEntryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CalculatedEntryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.calculatedEntry).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
