/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeptidenotebookTestModule } from '../../../test.module';
import { CalculatedDetailComponent } from 'app/entities/calculated/calculated-detail.component';
import { Calculated } from 'app/shared/model/calculated.model';

describe('Component Tests', () => {
    describe('Calculated Management Detail Component', () => {
        let comp: CalculatedDetailComponent;
        let fixture: ComponentFixture<CalculatedDetailComponent>;
        const route = ({ data: of({ calculated: new Calculated(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [CalculatedDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CalculatedDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CalculatedDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.calculated).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
