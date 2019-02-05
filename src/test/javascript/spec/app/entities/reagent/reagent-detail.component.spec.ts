/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeptidenotebookTestModule } from '../../../test.module';
import { ReagentDetailComponent } from 'app/entities/reagent/reagent-detail.component';
import { Reagent } from 'app/shared/model/reagent.model';

describe('Component Tests', () => {
    describe('Reagent Management Detail Component', () => {
        let comp: ReagentDetailComponent;
        let fixture: ComponentFixture<ReagentDetailComponent>;
        const route = ({ data: of({ reagent: new Reagent(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [ReagentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReagentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReagentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.reagent).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
