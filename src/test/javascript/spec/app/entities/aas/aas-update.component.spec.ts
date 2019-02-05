/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PeptidenotebookTestModule } from '../../../test.module';
import { AasUpdateComponent } from 'app/entities/aas/aas-update.component';
import { AasService } from 'app/entities/aas/aas.service';
import { Aas } from 'app/shared/model/aas.model';

describe('Component Tests', () => {
    describe('Aas Management Update Component', () => {
        let comp: AasUpdateComponent;
        let fixture: ComponentFixture<AasUpdateComponent>;
        let service: AasService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [AasUpdateComponent]
            })
                .overrideTemplate(AasUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AasUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AasService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Aas(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.aas = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Aas();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.aas = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
