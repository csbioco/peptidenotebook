/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeptidenotebookTestModule } from '../../../test.module';
import { AasDetailComponent } from 'app/entities/aas/aas-detail.component';
import { Aas } from 'app/shared/model/aas.model';

describe('Component Tests', () => {
    describe('Aas Management Detail Component', () => {
        let comp: AasDetailComponent;
        let fixture: ComponentFixture<AasDetailComponent>;
        const route = ({ data: of({ aas: new Aas(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [AasDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AasDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AasDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.aas).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
