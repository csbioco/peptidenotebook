/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PeptidenotebookTestModule } from '../../../test.module';
import { ReagentUpdateComponent } from 'app/entities/reagent/reagent-update.component';
import { ReagentService } from 'app/entities/reagent/reagent.service';
import { Reagent } from 'app/shared/model/reagent.model';

describe('Component Tests', () => {
    describe('Reagent Management Update Component', () => {
        let comp: ReagentUpdateComponent;
        let fixture: ComponentFixture<ReagentUpdateComponent>;
        let service: ReagentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [ReagentUpdateComponent]
            })
                .overrideTemplate(ReagentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReagentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReagentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Reagent(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.reagent = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Reagent();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.reagent = entity;
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
