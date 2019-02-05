/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PeptidenotebookTestModule } from '../../../test.module';
import { CalculatedUpdateComponent } from 'app/entities/calculated/calculated-update.component';
import { CalculatedService } from 'app/entities/calculated/calculated.service';
import { Calculated } from 'app/shared/model/calculated.model';

describe('Component Tests', () => {
    describe('Calculated Management Update Component', () => {
        let comp: CalculatedUpdateComponent;
        let fixture: ComponentFixture<CalculatedUpdateComponent>;
        let service: CalculatedService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [CalculatedUpdateComponent]
            })
                .overrideTemplate(CalculatedUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CalculatedUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalculatedService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Calculated(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.calculated = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Calculated();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.calculated = entity;
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
